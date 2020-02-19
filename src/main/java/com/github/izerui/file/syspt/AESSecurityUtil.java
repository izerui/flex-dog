package com.github.izerui.file.syspt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.UUID;

public class AESSecurityUtil {

    // 加密算法
    /** 指定加密算法为RSA */
    private static final String ALGORITHM = "AES";

    // 加密密钥
    // private static final byte[] keyValue = new byte[] { 'T', 'h', 'e',
    // 'B','e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
    // 16位的加密密钥
//    private byte[] keyValue;

    /**
     * 用来进行加密的操作
     *
     * @param
     * @return
     * @throws Exception
     */
    public static String encrypt(String keyString, String data)
            throws Exception {
        Key key = generateKey(keyString);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    /**
     * 用来进行解密的操作
     *
     * @param encryptedData
     * @return
     * @throws Exception
     */
    public static String decrypt(String keyString, String encryptedData) throws Exception {
        Key key = generateKey(keyString);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    public static String generateKeyString()
    {
        //必须长度为16
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }

    /**
     * 根据密钥和算法生成Key
     *
     * @return
     * @throws Exception
     */
    private static Key generateKey(String keyString) throws Exception {
        Key key = new SecretKeySpec(keyString.getBytes(), ALGORITHM);
        return key;
    }

//    public static void main(String [] args) throws Exception
//    {
//        String keyString = generateKeyString();
////        String keyString = "1234567890123456";
//        System.out.println("密钥：" + keyString);
//
//        String source = "恭喜发财!";// 要加密的字符串
//        System.out.println("准备用密钥加密的字符串为：" + source);
//
//        String cryptograph = encrypt(keyString, source);// 生成的密文
//        System.out.print("用密钥加密后的结果为:" + cryptograph);
//        System.out.println();
//
//        String target = decrypt(keyString, cryptograph);// 解密密文
//        System.out.println("用密钥解密后的字符串为：" + target);
//        System.out.println();
//    }

}