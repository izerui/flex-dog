package com.github.izerui.file.service;

import com.ecworking.rbac.dto.EntSearch;
import com.ecworking.rbac.dto.EnterpriseEntity;
import com.ecworking.rbac.enums.EnterpriseModelType;
import com.ecworking.rbac.remote.vo.RoleVo;
import com.ecworking.rbac.remote.vo.UserVo;
import com.github.izerui.file.client.EnterpriseClient;
import com.github.izerui.file.client.RoleClient;
import com.github.izerui.file.client.UserClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RemotingDestination
@Service("onlineService")
@ConfigurationProperties
public class OnlineService {

    @Autowired
    private EnterpriseClient enterpriseClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private RoleClient roleClient;
    @Autowired
    private RestTemplate restTemplate;


    public List<RoleVo> findRoles(String entCode){
        List<RoleVo> roles = roleClient.findRoles(entCode);
        return roles;
    }

    public List<UserVo> findByRoleCode(String entCode,String roleCode){
        return userClient.findByRoleCode(entCode,roleCode);
    }

    public List<Map<String, Object>> onlineUsers() throws Exception {
        List<Map<String, Object>> results = new ArrayList<>();

        EntSearch entSearch = new EntSearch();
        entSearch.setType(1);

        List<EnterpriseEntity> entities = enterpriseClient.data(entSearch);

        for (EnterpriseEntity entity : entities) {
            if(!entity.isEnable()){
                continue;
            }
            Map<String, Object> online = restTemplate.getForObject("http://websocket-server/online/" + entity.getCode(), Map.class);

            Map<String, Object> map = new HashMap<>();
            map.put("code", entity.getCode());
            map.put("name", entity.getName());
            map.put("type", EnterpriseModelType.valueOf(entity.getType()).getText());
            map.put("status", entity.getDeposit().getText());
            List<String> codes = userClient.getRelatedUserCodes(entity.getCode());
            if(codes!=null){
                map.put("relatedCount",codes.size());
            }else{
                map.put("relatedCount",0);
            }
            map.put("postCount",userClient.postAttachedUserCount(entity.getCode()));
            map.put("count", online.get("在线用户数"));
            map.put("date",entity.getCreateDate());
            map.put("users", StringUtils.join(((List<String>) online.get("在线用户列表")).toArray(),","));
            results.add(map);
        }
        List<Map<String, Object>> collect = results.stream().sorted((o1, o2) -> (Integer) o2.get("count") - (Integer) o1.get("count")).collect(Collectors.toList());
        return collect;
    }

}
