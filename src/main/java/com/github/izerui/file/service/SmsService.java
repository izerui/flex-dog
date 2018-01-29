package com.github.izerui.file.service;

import com.ecworking.esms.global.mchuan.SmsSendResponse;
import com.ecworking.esms.mchuan.MchuanSmsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

/**
 * Created by BanPengfei on 2016/9/28.
 */
@RemotingDestination
@Service("smsService")
public class SmsService {

    @Autowired
    private MchuanSmsService mchuanSmsService;

    private final int SMS_EXPIRE_MINUTES = 10;

    public void sendCaptcha(String phone) {
        phone = phone.replace("6","");
        String content = "验证码: [%s] ，请在10分钟内输入有效。";
        String captcha = RandomStringUtils.randomNumeric(4);
        content = String.format(content, captcha);
        SmsSendResponse smsSendResponse = mchuanSmsService.sendCaptcha("file-dog", phone, content, "file-dog", captcha, SMS_EXPIRE_MINUTES * 60);
        if (smsSendResponse.getError() != null) {
            throw new RuntimeException("无法发送验证码：" + smsSendResponse.getError().getMessage() + "[错误代码：" + smsSendResponse.getError().getCode() + "]");
        }
    }

    public boolean validate(String phone, String captcha) {
        phone = phone.replace("6","");
        boolean isValid = mchuanSmsService.isValidCaptcha(phone, "file-dog", captcha);
        return isValid;
    }

}
