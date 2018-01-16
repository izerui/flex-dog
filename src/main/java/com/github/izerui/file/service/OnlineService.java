package com.github.izerui.file.service;

import com.github.izerui.file.utils.ConfigUtils;
import com.github.izerui.file.vo.Deploy;
import com.github.izerui.file.vo.Ent;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RemotingDestination
@Service("onlineService")
@ConfigurationProperties
public class OnlineService {

    private RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, String>> onlineUsers() throws Exception {
        List<Map<String, String>> results = new ArrayList<>();
        Deploy config = ConfigUtils.getDeployConfig();
        for (Ent ent : config.getEntList()) {

            Map<String, Object> online = restTemplate.getForObject("https://yj2025.com/ierp/websocket-server/online/" + ent.getCode(), Map.class);

            Map<String, String> map = new HashMap<>();
            map.put("code", ent.getCode());
            map.put("name", ent.getName());
            map.put("count", String.valueOf(online.get("在线用户数")));
            map.put("users", StringUtils.join(((List<String>) online.get("在线用户列表")).toArray(),","));
            results.add(map);
        }
        return results;
    }

}
