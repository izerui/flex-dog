package com.github.izerui.file.service;

import com.github.izerui.file.utils.ApiClient;
import com.github.izerui.file.utils.ConfigUtils;
import com.github.izerui.file.vo.Deploy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RemotingDestination
@Service("ucloudService")
@ConfigurationProperties
public class UcloudService {

    private static ApiClient apiClient;

    static {
        try {
            Deploy deployConfig = ConfigUtils.getDeployConfig();
            apiClient = new ApiClient(deployConfig.getPublicKey(), deployConfig.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getDescribeUHostInstance() {
        Map<String, String> params = new HashMap<>();
        params.put("ProjectId", "org-skwhwp");
        params.put("Zone", "cn-gd-02");
        params.put("Region", "cn-gd");
        params.put("Offset", "0");
        params.put("Limit", "5000");
        params.put("Action", "DescribeUHostInstance");
        params.put("_timestamp", String.valueOf(System.currentTimeMillis()));
        return apiClient.execute(params);
    }


}
