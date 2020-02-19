package com.github.izerui.file.syspt;

import org.apache.commons.codec.binary.Base64;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CryptoUtil
 * @Description TODO
 * @Author lenovo
 * @Date 2019/2/26 9:26
 * @Version 1.0
 */
public class CryptoUtil {
    /**
     * @param data      返回数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static CryptoData encrypt(String data, String publicKey) throws Exception {
        //1、产生AES密钥
        String keyString = AESSecurityUtil.generateKeyString();

        //2、用AES法加密数据
        String cryptograph = AESSecurityUtil.encrypt(keyString, data);

        //3、用RSA加密AES密钥
        byte[] finalKey = RSACoder.encryptByPublicKey(Base64.decodeBase64(keyString), publicKey);
//        System.out.print("用RSA加密AES密钥为:" + finalKey);
//        System.out.print("加密数据:" + cryptograph);

        CryptoData cryptoData = new CryptoData();
        cryptoData.setKey(Base64.encodeBase64String(finalKey));
        cryptoData.setContent(cryptograph);

        //4、返回数据
        return cryptoData;
    }

    /**
     * @param cryptoData 加密数据
     * @param privateKey rsa私钥
     * @return
     * @throws Exception
     */
    public static String decrypt(CryptoData cryptoData, String privateKey) throws Exception {
        //1、解密密钥
        byte[] decryptKey = RSACoder.decryptByPrivateKey(Base64.decodeBase64(cryptoData.getKey()), privateKey);

        //2、解密内容
        String decryptData = AESSecurityUtil.decrypt(Base64.encodeBase64String(decryptKey), cryptoData.getContent());

        //3、返回
        return decryptData;

    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> keyMap = RSACoder.initKey();
        //公钥
        String publicKey = RSACoder.getPublicKey(keyMap);
        //私钥
        String privateKey = RSACoder.getPrivateKey(keyMap);
        //请求数据
        Map<String, Object> requestMap = new HashMap<>();

        requestMap.put("userId", "1,2,3,4");
        requestMap.put("beginTime", new Date());
        requestMap.put("endTime", new Date());
        System.out.println("元数据：" + requestMap);
        JsonMapper mapper = new JsonMapper();

        CryptoData cryptoData = CryptoUtil.encrypt(mapper.toJson(requestMap), publicKey);

        System.out.println("加密后数据:" + cryptoData);

        System.out.println("---解密--");

        String result = CryptoUtil.decrypt(cryptoData, privateKey);

        System.out.println("解密后数据:" + result);


    }
}