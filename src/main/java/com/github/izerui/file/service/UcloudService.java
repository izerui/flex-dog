package com.github.izerui.file.service;

import com.github.izerui.file.utils.ApiClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RemotingDestination
@Service("ucloudService")
@ConfigurationProperties
public class UcloudService {

    private static ApiClient apiClient = new ApiClient(
            "YDpdHw/bRMv/E4ROKDPVcL3PVMO2dnHO2NnX7gi4Dng9zRtX7qC3aw=="
            , "a4593c02274873fa0ae03f7ccb1d546edd950fe4");

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

    public String getMetricOverview() {
        Map<String, String> params = new HashMap<>();
        params.put("ProjectId", "org-skwhwp");
        params.put("Zone", "cn-gd-02");
        params.put("Region", "cn-gd");
        params.put("ResourceType", "uhost");
        params.put("Offset", "0");
        params.put("Limit", "60");
        params.put("Action", "GetMetricOverview");
        params.put("_timestamp", String.valueOf(System.currentTimeMillis()));
        return apiClient.execute(params);
    }

    public String getDbMetricOverview() {
        Map<String, String> params = new HashMap<>();
        params.put("ProjectId", "org-skwhwp");
        params.put("Zone", "cn-gd-02");
        params.put("Region", "cn-gd");
        params.put("ResourceType", "udb");
        params.put("Offset", "0");
        params.put("Limit", "60");
        params.put("Action", "GetMetricOverview");
        params.put("_timestamp", String.valueOf(System.currentTimeMillis()));
        return apiClient.execute(params);
    }

    public String getRedisMetricOverview() {
        Map<String, String> params = new HashMap<>();
        params.put("ProjectId", "org-skwhwp");
        params.put("Zone", "cn-gd-02");
        params.put("Region", "cn-gd");
        params.put("ResourceType", "uredis");
        params.put("Offset", "0");
        params.put("Limit", "60");
        params.put("Action", "GetMetricOverview");
        params.put("_timestamp", String.valueOf(System.currentTimeMillis()));
        return apiClient.execute(params);
    }


}
