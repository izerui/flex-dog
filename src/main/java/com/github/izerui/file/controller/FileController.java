package com.github.izerui.file.controller;

import com.github.izerui.file.service.FileService;
import com.github.izerui.file.utils.ConfigUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

@ConfigurationProperties
@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    private Logger log = Logger.getLogger(FileController.class);

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam("name") String filename, HttpServletResponse response) throws Exception {
        File file = new File(ConfigUtils.rootPath + filename);
        if (file.isFile()) {//存在文件
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream;charset=UTF-8");
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        }
    }

}
