package com.github.izerui.file.web;

import com.github.izerui.file.entity.FileEntity;
import com.github.izerui.file.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {


    @Value("${root-path}")
    private String rootPath;

    @Autowired
    private FileService fileService;

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Map<String, Object> upload(
            @RequestParam("Filedata") MultipartFile file) throws Exception {
        fileService.saveFile(file);
        Map<String, Object> result = new HashMap<>();
        result.put("success", "true");
        return result;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
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

}
