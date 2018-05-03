package com.github.izerui.file.service;

import com.github.izerui.file.config.FileConfig;
import com.github.izerui.file.entity.FileEntity;
import com.github.izerui.file.repository.DeployRepository;
import com.github.izerui.file.repository.FileRepository;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RemotingDestination
@Service("fileService")
@ConfigurationProperties
@Transactional
@EnableConfigurationProperties(FileConfig.class)
public class FileService {

    private static Logger log = Logger.getLogger(FileService.class);


    @Value("${root-path}")
    private String rootPath;
    @Autowired
    private DeployRepository deployRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileConfig fileConfig;

    private Configuration configuration = new Configuration();


    public Map<String, String> getUpToken() throws QiniuException {
        Auth auth = Auth.create(fileConfig.getAccessKey(), fileConfig.getSecretKey());
        String token = auth.uploadToken(fileConfig.getBucketName());
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("url", configuration.upHost(token));
        return map;
    }

    public void addService(FileEntity fileEntity) {
        long count = fileRepository.countByFileNameAndServer(fileEntity.getFileName(), fileEntity.getServer());
        Assert.state(count == 0, "服务器已经存在当前服务");
        fileEntity.setId(UUID.randomUUID().toString());
        fileRepository.save(fileEntity);
    }

    public List<FileEntity> listFiles(String server) {
        if (StringUtils.isEmpty(server) || server.equals("全部")) {
            return fileRepository.findAllOrderByUploadTimeDesc();
        } else {
            return fileRepository.findByServerOrderByUploadTimeDesc(server);
        }
    }


    public String exec(String id) throws Exception {
        FileEntity entity = fileRepository.getOne(id);
        if (StringUtils.isEmpty(entity.getFilePath()) || !new File(entity.getFilePath()).exists()) {
            throw new IllegalArgumentException("未上传程序包,发布失败.");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        DefaultExecutor exec = new DefaultExecutor();

        exec.setExitValues(null);

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
        return output;
    }

    public List<String> getServers(String fileName) {
        List<FileEntity> entities = fileRepository.findByFileName(fileName);
        return entities.stream().map(FileEntity::getServer).collect(Collectors.toList());
    }

    public void saveFile(MultipartFile file) throws IOException {
        File newFile = new File(rootPath + file.getOriginalFilename());
        IOUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
        List<FileEntity> entities = fileRepository.findByFileName(file.getOriginalFilename());
        Date time = new Date();
        for (FileEntity one : entities) {
            one.setSize(file.getSize());
            one.setUploadTime(time);
            one.setFilePath(newFile.getPath());
            fileRepository.save(one);
        }
    }

    public FileEntity getFile(String fileId) {
        return fileRepository.findOne(fileId);
    }
}
