package com.github.izerui.file.syspt;

import com.github.izerui.file.service.StService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class AesController {

    @Autowired
    private StService stService;


    @ApiOperation("广东工业互联网应用服务平台-服务券-审核接口")
    @PostMapping(value = "/syspt/aesAndRsa",produces = "application/json;charset=UTF-8")
    public Response4App aesAndRsa(@RequestHeader("RSAPublicKey") String RSAPublicKey,
                                  @RequestBody String requestJson) {
        Response4App app = new Response4App();
        try {
            JsonMapper mapper = new JsonMapper();
            //请求参数
            RequestData requestData = mapper.fromJson(requestJson, RequestData.class);
            List<DataContent> dataContents = stService.findRecords(requestData);
            CryptoData cryptoData = CryptoUtil.encrypt(mapper.toJson(dataContents), RSAPublicKey);
            app.setData(cryptoData);
            app.setRespCode("0");
            app.setRespMsg("success");
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            app.setRespCode("999");
            app.setRespMsg(ex.getMessage());
            app.setData(new CryptoData());
        }
        return app;

    }

}
