package com.github.izerui.file.service;

import com.ecworking.audit.Record;
import com.ecworking.rbac.dto.EntSearch;
import com.ecworking.rbac.dto.EnterpriseEntity;
import com.ecworking.rbac.enums.EnterpriseModelType;
import com.ecworking.rbac.remote.vo.RoleVo;
import com.ecworking.rbac.remote.vo.UserVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.file.client.EnterpriseClient;
import com.github.izerui.file.client.RoleClient;
import com.github.izerui.file.client.UserClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
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
    @Autowired
    private ObjectMapper objectMapper;

    private List<Record> userRecordListLimit100 = new ArrayList<>();


    public List<RoleVo> findRoles(String entCode) {
        List<RoleVo> roles = roleClient.findRoles(entCode);
        return roles;
    }

    public List<UserVo> findByRoleCode(String entCode, String roleCode) {
        return userClient.findByRoleCode(entCode, roleCode);
    }

    public List<Map<String, Object>> onlineUsers() throws Exception {
        List<Map<String, Object>> results = new ArrayList<>();

        EntSearch entSearch = new EntSearch();
        entSearch.setType(1);

        List<EnterpriseEntity> entities = enterpriseClient.data(entSearch);

        for (EnterpriseEntity entity : entities) {
            if (!entity.isEnable()) {
                continue;
            }
            Map<String, Object> online = restTemplate.getForObject("http://websocket-server/online/" + entity.getCode(), Map.class);

            Map<String, Object> map = new HashMap<>();
            map.put("code", entity.getCode());
            map.put("name", entity.getName());
            map.put("type", EnterpriseModelType.valueOf(entity.getType()).getText());
            map.put("status", entity.getDeposit().getText());
            List<String> codes = userClient.getRelatedUserCodes(entity.getCode());
            if (codes != null) {
                map.put("relatedCount", codes.size());
            } else {
                map.put("relatedCount", 0);
            }
            map.put("postCount", userClient.postAttachedUserCount(entity.getCode()));
            map.put("count", online.get("在线用户数"));
            map.put("date", entity.getCreateDate());
            map.put("users", StringUtils.join(((List<String>) online.get("在线用户列表")).toArray(), ","));
            results.add(map);
        }
        List<Map<String, Object>> collect = results.stream().sorted((o1, o2) -> (Integer) o2.get("count") - (Integer) o1.get("count")).collect(Collectors.toList());
        return collect;
    }


    @RabbitListener(queues = "audit")
    public void input(Message<byte[]> message) throws IOException {
        Record record = objectMapper.readValue(message.getPayload(), Record.class);
        if (userRecordListLimit100.size() > 100) {
            userRecordListLimit100.remove(0);
        }
        userRecordListLimit100.add(record);
    }

    public List<Record> getUserRecordListLimit100() {
        List<Record> records = new ArrayList<>(userRecordListLimit100);
        Collections.reverse(records);
        return records;
    }


}
