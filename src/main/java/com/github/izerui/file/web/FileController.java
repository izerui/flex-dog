package com.github.izerui.file.web;

import com.ecworking.rest.exception.BusinessException;
import com.github.izerui.file.entity.FileEntity;
import com.github.izerui.file.service.FileService;
import com.github.izerui.file.service.SmsService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.izerui.file.web.Response.success;

@RestController
public class FileController {


    @Value("${root-path}")
    private String rootPath;

    @Autowired
    private FileService fileService;
    @Autowired
    private SmsService smsService;

    @RequestMapping(value = {"/upload", "/api/v1/upload"}, method = RequestMethod.POST)
    public Map<String, Object> upload(
            @RequestParam("Filedata") MultipartFile file) throws Exception {
        fileService.saveFile(file);
        Map<String, Object> result = new HashMap<>();
        result.put("success", "true");
        return result;
    }

    @RequestMapping(value = {"/download", "/api/v1/download"}, method = RequestMethod.GET)
    public void download(@RequestParam("id") String fileId, HttpServletResponse response) throws Exception {
        FileEntity entity = fileService.getFile(fileId);
        File file = new File(entity.getFilePath());
        if (file.exists()) {//存在文件
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(file.getName().getBytes("utf-8"), "ISO-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream;charset=UTF-8");
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        }
    }

    @GetMapping("/api/v1/files")
    public Response getFiles(@RequestParam(value = "server", required = false) String server) {
        List<FileEntity> fileEntities = fileService.listFiles(server);
        return success(fileEntities);
    }

    @GetMapping("/api/v1/file/send-captcha")
    public Response sendCaptcha(@RequestParam("phone") String phone) {
        fileService.sendCaptcha(phone);
        return success();
    }

    @GetMapping("/api/v1/file/enable")
    public Response enableService(@RequestParam("appId") String appId,
                                  @RequestParam("instanceId") String instanceId,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("code") String code) {
        Assert.state(smsService.validate(phone, code), "验证码验证失败");
        fileService.startService(appId, instanceId);
        return success();
    }

    @GetMapping("/api/v1/file/disable")
    public Response disableService(@RequestParam("appId") String appId,
                                  @RequestParam("instanceId") String instanceId,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("code") String code) {
        Assert.state(smsService.validate(phone, code), "验证码验证失败");
        fileService.stopService(appId, instanceId);
        return success();
    }

    @PostMapping("/api/v1/new-service")
    public Response newService(@RequestParam("servers[]") List<String> servers,
                               @RequestParam("fileName") String fileName,
                               @RequestParam("owner") String owner,
                               @RequestParam("type") String type,
                               @RequestParam("sender") String sender,
                               @RequestParam("code") String code) {
        boolean validateCaptcha = fileService.validateCaptcha(sender, code);
        if (!validateCaptcha) {
            throw new BusinessException("验证码不对");
        }
        List<FileEntity> collect = servers.stream().map(server -> {
            String[] split = server.split(";");
            FileEntity entity = new FileEntity();
            entity.setFileName(fileName);
            entity.setOwner(owner);
            entity.setServer(split[0]);
            entity.setServerAddress(split[1]);
            entity.setDeployType(type);
            return entity;
        }).collect(Collectors.toList());
        fileService.addServices(collect);
        return success();
    }

}
