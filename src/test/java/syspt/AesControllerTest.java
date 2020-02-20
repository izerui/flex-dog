package syspt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.file.syspt.*;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class AesControllerTest {

    @Test
    public void testAESandRsa() throws Exception {
        RSACoder rsaCoder = new RSACoder();
        Map<String, Object> map = new HashMap<String, Object>();
        //生成密钥对
        Map<String, Object> keyMap = RSACoder.initKey();
        //公钥
        String publicKey = RSACoder.getPublicKey(keyMap);

        //私钥
        String privateKey = RSACoder.getPrivateKey(keyMap);
        System.out.println("公钥：/n" + publicKey);

        System.out.println("私钥：/n" + privateKey);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("RSAPublicKey", publicKey);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> bodyEntity = new HttpEntity<>("{\n" +
                "\"beginTime\":20190306000000,\n" +
                "\"endTime\":20200307000000,\n" +
                "\"userId\":\"13686486070\"\n" +
                "}", headers);
        String resultJson = restTemplate.postForObject("https://yj2025.com/syspt/aesAndRsa", bodyEntity, String.class);
        System.out.println(resultJson);

        JsonMapper mapper = new JsonMapper();
        Response4App app = mapper.fromJson(resultJson,Response4App.class);
        System.out.println(app.getData());
        ObjectMapper objectMapper = new ObjectMapper();
        CryptoData data = objectMapper.convertValue(app.getData(), CryptoData.class);
        System.out.println("结果:"+data);
        System.out.println(CryptoUtil.decrypt(data,privateKey));
    }


    @Test
    public void testDes() throws Exception {
        //生成密钥对
        Map<String, Object> keyMap = RSACoder.initKey();
        //公钥
        String publicKey = RSACoder.getPublicKey(keyMap);

        //私钥
        String privateKey = RSACoder.getPrivateKey(keyMap);
        System.out.println("公钥：/n" + publicKey);

        System.out.println("私钥：/n" + privateKey);


        String aesString = "abc";

        CryptoData cryptoData = CryptoUtil.encrypt(aesString,publicKey);

        String decrypt = CryptoUtil.decrypt(cryptoData, privateKey);
        System.out.println(decrypt);

    }


}