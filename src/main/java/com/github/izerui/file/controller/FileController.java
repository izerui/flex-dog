package com.github.izerui.file.controller;

import com.github.izerui.file.service.FileService;
import com.github.izerui.file.service.impl.FileServiceImpl;
import com.github.izerui.file.vo.Server;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

@ConfigurationProperties
@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    private Logger log = Logger.getLogger(FileController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(
            @RequestParam("Filedata") MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        if (!file.getOriginalFilename().equals("deploy.json")) {
            boolean exist = false;
            List<Server> servers = fileService.getServerList();
            for (Server server : servers) {
                for (Server.Service service : server.getServices()) {
                    if (service != null && service.getFile().equals(file.getOriginalFilename())) {
                        exist = true;
                    }
                }
            }
            if (!exist) {
                throw new RuntimeException("不支持上传该文件");
            }
        }

        File newFile = new File(FileServiceImpl.rootPath + file.getOriginalFilename());

        IOUtils.copy(file.getInputStream(), new FileOutputStream(newFile));

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("success");
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam("name") String filename, HttpServletResponse response) throws Exception {
        File file = new File(FileServiceImpl.rootPath + filename);
        if (file.isFile()) {//存在文件
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream;charset=UTF-8");
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        }
    }

}
