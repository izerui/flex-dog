package com.github.izerui.file.web;

import com.github.izerui.file.entity.PublisherEntity;
import com.github.izerui.file.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PublisherController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/api/v1/publishers")
    public Response getPublishers() throws Exception {
        List<PublisherEntity> publishers = smsService.getPublishers();
        return Response.success(publishers);
    }
}
