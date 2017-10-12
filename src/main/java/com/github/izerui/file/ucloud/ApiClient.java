package com.github.izerui.file.ucloud;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ApiClient {

    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 私钥
     */
    private String privateKey;

    private static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
                .pingInterval(30, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public ApiClient(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }


    public final String execute(Map<String, String> params) {
        try {
            Request request = new Request.Builder()
                    .post(createFormBody(params))
                    .url("https://api.ucloud.cn")
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private FormBody createFormBody(Map<String, String> params) {
        params.put("PublicKey", this.publicKey);
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        builder.add("Signature", verify(params));
        return builder.build();
    }


    private String verify(Map<String, String> params) {

        StringBuffer str = new StringBuffer();
        Set<String> keys = params.keySet();
        List<String> keyList = new ArrayList<String>();
        for (String key : keys) {
            keyList.add(key);
        }
        Collections.sort(keyList);
        for (String key : keyList) {
//            System.out.println(key + " " + params.get(key).toString());
            str.append(key).append(params.get(key).toString());
        }
        str.append(this.privateKey);

        // use sha1 to encode keys
        return DigestUtils.sha1Hex(str.toString());
    }


}
