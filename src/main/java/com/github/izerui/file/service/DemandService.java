package com.github.izerui.file.service;

import com.ecworking.commons.vo.PageVo;
import com.ecworking.development.vo.BusinessInventoryVo;
import com.ecworking.esms.global.mchuan.SmsSendResponse;
import com.ecworking.esms.mchuan.MchuanSmsService;
import com.ecworking.mrp.vo.OccupiedStockVo;
import com.ecworking.mrp.vo.PurgeResultVo;
import com.ecworking.rbac.dto.EntSearch;
import com.ecworking.rbac.dto.EnterpriseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.file.client.BomClient;
import com.github.izerui.file.client.BusinessClient;
import com.github.izerui.file.client.EnterpriseClient;
import com.github.izerui.file.client.MrpClient;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RemotingDestination
@Service("demandService")
@ConfigurationProperties
public class DemandService {


    @Autowired
    private MrpClient mrpClient;

    @Autowired
    private EnterpriseClient enterpriseClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BomClient bomClient;

    @Autowired
    private MchuanSmsService mchuanSmsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private BusinessClient businessClient;

    /**
     * 获取账套列表
     *
     * @return
     */
    public List<EnterpriseEntity> getEntList() {
        EntSearch entSearch = new EntSearch();
        entSearch.setType(1);
        return enterpriseClient.data(entSearch);
    }

    /**
     * 查看需求情况
     *
     * @param entCode
     * @param page
     * @param pageSize
     * @param keyword
     * @return
     */
    public Map inventoryDemands(String entCode,
                                Integer page,
                                Integer pageSize,
                                String keyword) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
        valueMap.set("entCode", entCode);
        valueMap.set("keyword", keyword);
        valueMap.set("page", page);
        valueMap.set("pageSize", pageSize);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(valueMap);
        Map map = restTemplate.postForObject("http://mrp-api/v3/inventory/demands", httpEntity, Map.class);
        return map;
    }


    /**
     * 货品的订单需求
     *
     * @param entCode
     * @param page
     * @param pageSize
     * @param inventoryId
     * @return
     */
    public Map inventoryBusinessDemands(String entCode,
                                        Integer page,
                                        Integer pageSize,
                                        String inventoryId) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
        valueMap.set("entCode", entCode);
        valueMap.set("inventoryId", inventoryId);
        valueMap.set("page", page);
        valueMap.set("pageSize", pageSize);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(valueMap);
        Map map = restTemplate.postForObject("http://mrp-api/v3/inventory/business/demands", httpEntity, Map.class);
        List<Map> contents = (List<Map>) map.get("content");
        for (Map content : contents) {
            BusinessInventoryVo business = businessClient.getBusiness(entCode, (String) content.get("businessKey"));
            if (business.isMade() || business.isOutsourcing()) {
                content.put("bomId", business.getBomId());
            }
        }
        return map;
    }


    /**
     * 需求日志
     *
     * @param entCode
     * @param inventoryId
     * @param sourceId
     * @param page
     * @param pageSize
     * @return
     */
    public Map inventoryDemandsHistory(String entCode,
                                       String inventoryId,
                                       String sourceId,
                                       Integer page,
                                       Integer pageSize) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
        valueMap.set("entCode", entCode);
        valueMap.set("inventoryId", inventoryId);
        valueMap.set("sourceId", sourceId);
        valueMap.set("page", page);
        valueMap.set("pageSize", pageSize);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(valueMap);
        Map map = restTemplate.postForObject("http://mrp-api/v3/inventory/demands/history", httpEntity, Map.class);
        List<Map> contents = (List<Map>) map.get("content");
        for (Map content : contents) {
            BusinessInventoryVo business = businessClient.getBusiness(entCode, (String) content.get("businessKey"));
            if (business.isMade() || business.isOutsourcing()) {
                content.put("bomId", business.getBomId());
            }
        }
        return map;
    }


    /**
     * 出入库记录
     *
     * @param entCode
     * @param inventoryId
     * @param page
     * @param pageSize
     * @return
     */
    public PageVo inventoryStockHistory(String entCode,
                                        String inventoryId,
                                        Integer page,
                                        Integer pageSize) throws IOException {
        String s = "{\n" +
                "\t\"inventoryId\":\"%s\",\n" +
                "\t\"page\":\"%s\",\n" +
                "\t\"pageSize\":\"%s\"\n" +
                "}";
        s = String.format(s, inventoryId, page, pageSize);

        HttpHeaders headers = new HttpHeaders();
        headers.set("entCode", entCode);
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        HttpEntity<String> bodyEntity = new HttpEntity<String>(s, headers);
        Map map = restTemplate.postForObject("http://warehouse-pc/v3/pc/warehouse/stock/query/stock/change/detail", bodyEntity, Map.class);

        String data = objectMapper.writeValueAsString(map.get("data"));

        return objectMapper.readValue(data, PageVo.class);
    }


    /**
     * 获取货品的占用情况
     *
     * @param entCode
     * @param inventoryId
     * @return
     */
    public List<OccupiedStockVo> occupiedDetail(String entCode,
                                                String inventoryId) {
        return mrpClient.occupiedDetail(entCode, inventoryId);
    }


    /**
     * 获取单据任务上的货品需求情况
     *
     * @param entCode
     * @param businessKey
     * @return
     */
    public List findDemandResults(String entCode, String businessKey) {
        MultiValueMap<String, Object> header = new LinkedMultiValueMap<String, Object>();
        header.set("entCode", entCode);
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
        valueMap.set("businessKey", businessKey);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity(valueMap, header);
        Map map = restTemplate.postForObject("http://process-pc/v1/order/inventory/info", httpEntity, Map.class);
        return (List) map.get("data");
    }


    /**
     * 获取货品亏的数量
     *
     * @param entCode
     * @param inventoryId
     * @return
     */
    public BigDecimal getLossQty(String entCode,
                                 String inventoryId) {
        return mrpClient.getLossQty(entCode, inventoryId);
    }


    /**
     * 获取净需求列表
     *
     * @return
     */
    public List<PurgeResultVo> findPurges(String entCode,
                                          String sourceId,
                                          List<String> attributeCodes) {
        return mrpClient.findPurges(entCode, sourceId, attributeCodes);
    }

    /**
     * 获取可用量(在途、在制、库存)
     *
     * @param entCode
     * @param inventoryId
     */
    public BigDecimal getUsableQty(String entCode,
                                   String inventoryId) {
        return mrpClient.getUsableQty(entCode, inventoryId);
    }


    /**
     * 获取可用量(在途、在制、库存)
     *
     * @param entCode
     * @param inventoryIds
     */
    public Map<String, BigDecimal> getUsableQtys(String entCode,
                                                 Set<String> inventoryIds) {
        return mrpClient.getUsableQtys(entCode, inventoryIds);
    }

    /**
     * 根据bomId获取bom
     *
     * @param entCode
     * @param bomId
     * @return
     */
    public List<Map> getBom(String entCode, String bomId) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
        valueMap.set("entCode", entCode);
        valueMap.set("bomId", bomId);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(valueMap);
        Map bom = restTemplate.postForObject("http://development-api/v3/bom/get", httpEntity, Map.class);
        return Lists.newArrayList(bom);
    }


    public void sendUpdateDemandCaptcha(String phone) {
        String content = "验证码: [%s] ，请在5分钟内输入。";
        String captcha = RandomStringUtils.randomNumeric(4);
        content = String.format(content, captcha);
        SmsSendResponse smsSendResponse = mchuanSmsService.sendCaptcha("file-dog", phone, content, "update-demand", captcha, 300);
        if (smsSendResponse.getError() != null) {
            throw new RuntimeException("无法发送验证码：" + smsSendResponse.getError().getMessage() + "[错误代码：" + smsSendResponse.getError().getCode() + "]");
        }
    }


    public void updateDemand(String phone,
                             String captcha,
                             String entCode,
                             String userCode,
                             String businessKey,
                             String sourceId,
                             String remark,
                             String inventoryId,
                             BigDecimal changeDemandQty,
                             BigDecimal changePurgeQty) {
        boolean isValid = mchuanSmsService.isValidCaptcha(phone, "update-demand", captcha);
        Assert.state(isValid, "验证码无效");

        Map<String, Object> msg = new HashMap<>();
        msg.put("entCode", entCode);
        msg.put("userCode", userCode);
        msg.put("businessKey", businessKey);
        msg.put("sourceId", sourceId);
        msg.put("remark", remark);
        msg.put("inventoryId", inventoryId);
        msg.put("changeDemandQty", changeDemandQty);
        msg.put("changePurgeQty", changePurgeQty);
        rabbitTemplate.convertAndSend("ierp", "ierp.demand.update", msg);
    }

    public void addBomDemand(String phone,
                             String captcha,
                             String entCode,
                             String userCode,
                             String bomId,
                             BigDecimal quantity) {
        boolean isValid = mchuanSmsService.isValidCaptcha(phone, "update-demand", captcha);
        Assert.state(isValid, "验证码无效");
        mrpClient.addBomDemand(entCode, userCode, bomId, quantity);
    }

    public void subBomDemand(String phone,
                             String captcha,
                             String entCode,
                             String userCode,
                             String bomId,
                             BigDecimal lastQuantity,
                             BigDecimal quantity) {
        boolean isValid = mchuanSmsService.isValidCaptcha(phone, "update-demand", captcha);
        Assert.state(isValid, "验证码无效");
        mrpClient.subBomDemand(entCode, userCode, bomId, lastQuantity, quantity);
    }


}
