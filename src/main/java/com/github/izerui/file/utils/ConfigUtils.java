package com.github.izerui.file.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.file.vo.Deploy;

import java.io.File;

public class ConfigUtils {

    public static String rootPath;

    public static ObjectMapper objectMapper = new ObjectMapper();

    static {
        rootPath = System.getProperty("user.dir") + File.separator;
    }


    public static Deploy getDeployConfig() throws Exception{
        File file = new File(rootPath + "deploy.json");
        if (!file.exists()) {
            throw new RuntimeException("部署配置文件不存在");
        }
        Deploy deploy = objectMapper.readValue(new File(rootPath + "deploy.json"), Deploy.class);
        return deploy;
    }

}
