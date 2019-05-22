package com.github.izerui.file.web;

import com.github.izerui.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Controller
public class LogController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/api/v1/log-content")
    public ResponseEntity<String> getLogContent(@RequestParam("logUrl") String logUrl,
                                                @RequestParam("begin") Long begin) throws UnsupportedEncodingException {
        String content = fileService.getLogContent(logUrl, begin);
//        content += "\n.......................\n";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>(content,
                headers, HttpStatus.OK);
    }

}
