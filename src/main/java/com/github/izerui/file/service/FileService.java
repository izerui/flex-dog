package com.github.izerui.file.service;

import com.ecworking.esms.global.mchuan.SmsSendResponse;
import com.ecworking.esms.mchuan.MchuanSmsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.file.config.FileConfig;
import com.github.izerui.file.entity.FileEntity;
import com.github.izerui.file.entity.LogEntity;
import com.github.izerui.file.repository.FileRepository;
import com.github.izerui.file.repository.LogRepository;
import com.google.common.collect.Lists;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import eureka.client.EurekaClient;
import eureka.client.EurekaClientImpl;
import eureka.client.EurekaInstanceStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RemotingDestination
@Service("fileService")
@Transactional
@EnableConfigurationProperties(FileConfig.class)
public class FileService {


    @Value("${root-path}")
    private String rootPath;
    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String eurekaServiceUrl;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileConfig fileConfig;
    @Autowired
    private MchuanSmsService mchuanSmsService;
    @Autowired
    private UcloudService ucloudService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private LogRepository logRepository;

    private static RestTemplate REST_TEMPLATE;
    private Configuration configuration = new Configuration();

    static {
        REST_TEMPLATE = new RestTemplate();
        REST_TEMPLATE.setMessageConverters(Lists.newArrayList(new StringHttpMessageConverter(Charset.forName("UTF-8"))));
    }

//    //修复数据
//    @PostConstruct
//    public void init() throws IOException {
//        String uhostJson = ucloudService.getDescribeUHostInstance();
//        Map<String, Object> map = objectMapper.readValue(uhostJson, Map.class);
//        List<Map<String, Object>> uhostList = (List<Map<String, Object>>) map.get("UHostSet");
//
//        List<FileEntity> all = fileRepository.findAll();
//
//        for (FileEntity entity : all) {
//            File file = new File(rootPath + entity.getFileName());
//            if (file.exists()) {
//                entity.setFilePath(file.getPath());
//                entity.setSize(file.length());
//                entity.setUploadTime(new Date(file.lastModified()));
//            }
//            if (entity.getDeployTime() == null) {
//                DeployEntity one = deployRepository.findOne(entity.getFileName());
//                if (one != null) {
//                    entity.setDeployTime(one.getDeployTime());
//                }
//            }
//            if (uhostList != null) {
//                for (Map<String, Object> uhost : uhostList) {
//                    if (entity.getServer().equals(uhost.get("Name"))) {
//                        String ips = "";
//                        for (Map<String, Object> ip : (List<Map<String, Object>>) uhost.get("IPSet")) {
//                            if ("".equals(ips)) {
//                                ips = (String) ip.get("IP");
//                            } else {
//                                ips += "," + (String) ip.get("IP");
//                            }
//                        }
//                        entity.setServerAddress(ips);
//                    }
//
//                }
//            }
//            fileRepository.save(entity);
//        }
//    }

    public void sendCaptcha(String phone) {
        String content = "验证码: [%s] ，请在1分钟内输入。";
        String captcha = RandomStringUtils.randomNumeric(4);
        content = String.format(content, captcha);
        SmsSendResponse smsSendResponse = mchuanSmsService.sendCaptcha("file-dog", phone, content, "new-service", captcha, 60);
        if (smsSendResponse.getError() != null) {
            throw new RuntimeException("无法发送验证码：" + smsSendResponse.getError().getMessage() + "[错误代码：" + smsSendResponse.getError().getCode() + "]");
        }
    }

    public boolean validateCaptcha(String phone, String captcha) {
        return mchuanSmsService.isValidCaptcha(phone, "new-service", captcha);
    }

    public Map<String, String> getUpToken() throws QiniuException {
        Auth auth = Auth.create(fileConfig.getAccessKey(), fileConfig.getSecretKey());
        String token = auth.uploadToken(fileConfig.getBucketName());
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("url", configuration.upHost(token));
        return map;
    }

    public void addServices(List<FileEntity> fileEntities) {
        for (FileEntity fileEntity : fileEntities) {
            addService(fileEntity);
        }
    }

    public void addService(FileEntity fileEntity) {
        long count = fileRepository.countByFileNameAndServer(fileEntity.getFileName(), fileEntity.getServer());
        Assert.state(count == 0, "服务器已经存在当前服务");
        fileEntity.setId(UUID.randomUUID().toString());
        fileEntity.setFilePath(FilenameUtils.getPath(rootPath) + fileEntity.getFileName());
        fileRepository.save(fileEntity);
        logRepository.save(new LogEntity("service", String.format("服务器: %s 增加服务: %s", fileEntity.getServer(), fileEntity.getFileName())));
    }

    public List<FileEntity> listFiles(String server) {
        List<FileEntity> files = new ArrayList<>();
        if (StringUtils.isEmpty(server) || server.equals("全部")) {
            files = fileRepository.findAllOrderByUploadTimeDesc();
        } else {
            files = fileRepository.findByServerOrderByUploadTimeDesc(server);
        }

        EurekaClient eurekaClient = new EurekaClientImpl(eurekaServiceUrl);
        List<Application> registeredApplications = eurekaClient.applications().getRegisteredApplications();

        for_each_files:
        for (FileEntity file : files) {
            for (Application application : registeredApplications) {
                if (file.getFileName().toUpperCase().contains(application.getName().toUpperCase())) {
                    for (InstanceInfo instance : application.getInstancesAsIsFromEureka()) {
                        //rootPath.contains("dog") 表示测试环境，调试用，正式环境不要包含dog相关名字的目录
                        if (rootPath.contains("dog") || file.getServerAddress().contains(instance.getHostName())) {
                            file.setUrl(instance.getHomePageUrl());
                            file.setPort(instance.getPort());
                            file.setStatus(instance.getStatus().name());
                            file.setAppId(instance.getAppName());
                            file.setInstanceId(instance.getInstanceId());
                            continue for_each_files;
                        }
                    }


                }
            }
        }

        return files;
    }

    public void stopService(String appId, String instanceId) {
        for (String eurekaServiceUrl : eurekaServiceUrl.split(",")) {
            EurekaClient eurekaClient = new EurekaClientImpl(eurekaServiceUrl);
            //先使服务失效,再更改服务状态
            eurekaClient.outOfService(appId, instanceId);
            eurekaClient.backInService(appId, instanceId, EurekaInstanceStatus.DOWN);
        }
        logRepository.save(new LogEntity("service", String.format("停用服务: %s id: %s", appId, instanceId)));
    }

    public void startService(String appId, String instanceId) {
        for (String eurekaServiceUrl : eurekaServiceUrl.split(",")) {
            EurekaClient eurekaClient = new EurekaClientImpl(eurekaServiceUrl);
            //先使服务失效,再更改服务状态
            eurekaClient.outOfService(appId, instanceId);
            eurekaClient.backInService(appId, instanceId, EurekaInstanceStatus.UP);
        }
        logRepository.save(new LogEntity("service", String.format("启用服务: %s id: %s", appId, instanceId)));
    }


    public String exec(String id) throws Exception {
        FileEntity entity = fileRepository.getOne(id);
        if (StringUtils.isEmpty(entity.getFilePath()) || !new File(entity.getFilePath()).exists()) {
            throw new IllegalArgumentException("未上传程序包,发布失败.");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        DefaultExecutor exec = new DefaultExecutor();

        exec.setExitValues(null);
        exec.setWatchdog(new ExecuteWatchdog(60000));

        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);

        exec.setStreamHandler(streamHandler);
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdir();
        }

        exec.setWorkingDirectory(file);
        exec.execute(CommandLine.parse("chmod 777 /etc/ansible/application-operation.sh"));
        exec.execute(CommandLine.parse("/bin/sh /etc/ansible/application-operation.sh " + entity.getDeployType() + " " + entity.getServer() + " " + FilenameUtils.getName(entity.getFilePath())));

        String output = outputStream.toString();
        log.info(output);
        //保存发布记录
        entity.setDeployTime(new Date());
        fileRepository.save(entity);
        logRepository.save(new LogEntity("service", String.format("服务器: %s 发布服务: %s", entity.getServer(), entity.getFileName())));
        return output;
    }

    public void saveFile(MultipartFile file) throws IOException {
        List<FileEntity> entities = fileRepository.findByFileName(file.getOriginalFilename());
        List<FileEntity> collect = entities.stream().filter(fileEntity -> fileEntity.getFileName().equals(file.getOriginalFilename())).collect(Collectors.toList());
        if (collect.isEmpty()) {
            throw new RuntimeException("不支持该文件上传");
        }
        File newFile = new File(rootPath + file.getOriginalFilename());
        IOUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
        Date time = new Date();
        for (FileEntity one : entities) {
            one.setSize(file.getSize());
            one.setUploadTime(time);
            one.setFilePath(newFile.getPath());
            fileRepository.save(one);
        }
        logRepository.save(new LogEntity("service", String.format("上传文件: %s", file.getOriginalFilename())));
    }

    public FileEntity getFile(String fileId) {
        return fileRepository.findOne(fileId);
    }

    public long getLogLength(String homePageUrl) {
        try {
            HttpHeaders httpHeaders = REST_TEMPLATE.headForHeaders(new URI(homePageUrl + "logfile"));
            return httpHeaders.getContentLength();
        } catch (Exception e) {
            return 0;
        }
    }

    public String getLogContent(String homePageUrl, Long beginRange) {
        try {
            String rangeHeader = "bytes=" + (beginRange != null ? beginRange.toString() : "") + "-";
            RequestEntity.HeadersBuilder<?> builder = RequestEntity.get(new URI(homePageUrl + "logfile"));
            builder.header(HttpHeaders.RANGE, rangeHeader);
//            builder.header(HttpHeaders.CONTENT_TYPE, "text/plain;charset=UTF-8");
//            builder.header(HttpHeaders.ACCEPT_CHARSET, "UTF-8");
            builder.header(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            ResponseEntity<String> exchange = REST_TEMPLATE.exchange(builder.build(), String.class);
            return exchange.getBody() != null ? exchange.getBody() : "";
        } catch (Exception e) {
            return "";
        }

    }
}
