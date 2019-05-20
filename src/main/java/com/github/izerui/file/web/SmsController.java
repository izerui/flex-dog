package com.github.izerui.file.web;

import com.github.izerui.file.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Autowired
    private SmsService smsService;


    @GetMapping("/api/v1/send-captcha")
    public Response sendCaptcha(@RequestParam("phone")String phone){
        smsService.sendCaptcha(phone);
        return Response.success();
    }
}
