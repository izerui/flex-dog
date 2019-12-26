package com.github.izerui.file.service;

import com.yj2025.storehouse.enums.BusinessType;

import java.util.*;

import com.ecworking.commons.em.SourceType;

import com.alibaba.druid.pool.DruidDataSource;
import com.ecworking.commons.em.InventoryAttributeEnum;
import com.ecworking.commons.vo.PageVo;
import com.ecworking.commons.vo.SourceMsgVo;
import com.ecworking.development.document.Bom;
import com.ecworking.esms.global.mchuan.SmsSendResponse;
import com.ecworking.esms.mchuan.MchuanSmsService;
import com.ecworking.mrp.vo.InventoryDemandVo;
import com.ecworking.mrp.vo.PurgeResultVo;
import com.ecworking.rbac.dto.EntSearch;
import com.ecworking.rbac.dto.EnterpriseEntity;
import com.ecworking.warehouse.enums.LackMaterialEnum;
import com.ecworking.warehouse.vo.message.LackMaterialMessageVo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.file.client.BomClient;
import com.github.izerui.file.client.BusinessClient;
import com.github.izerui.file.client.EnterpriseClient;
import com.github.izerui.file.client.MrpClient;
import com.google.common.collect.Lists;
import com.yj2025.storehouse.vo.LackMaterielVo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import static com.ecworking.commons.jackson.Decimal2StringUtils.toPlainString;

@RemotingDestination
@Service("demandService")
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

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
    private String dbPassword;

    private DruidDataSource dataSource;

    @PostConstruct
    public void initJdbcTemplate() {
        dataSource = new DruidDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    }

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
    public PageVo<InventoryDemandVo> inventoryDemands(String entCode,
                                                      Integer page,
                                                      Integer pageSize,
                                                      String keyword) throws IOException {
        if(pageSize <= 0){
            pageSize = 50;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("entCode", entCode);
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
        valueMap.set("page", page);
        valueMap.set("pageSize", pageSize);
        valueMap.set("businessKey", null);
        valueMap.set("keyword", keyword);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity(valueMap, headers);
        Map postForObject = restTemplate.postForObject("http://process-pc/v3/inventory/demands", httpEntity, Map.class);

        String data = objectMapper.writeValueAsString(postForObject.get("data"));

        return objectMapper.readValue(data, PageVo.class);

    }


    /**
     * 货品的订单需求
     *
     * @param entCode
     * @param inventoryId
     * @return
     */
    public List<Map> inventoryBusinessDemands(String entCode,
                                              String inventoryId) throws IOException {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
        valueMap.set("entCode", entCode);
        valueMap.set("inventoryId", inventoryId);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(valueMap);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://mrp-api/v3/inventory/business/demands", httpEntity, String.class);
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Map.class);
        List<Map> contents = objectMapper.readValue(responseEntity.getBody(), javaType);
        return contents;
    }


    /**
     * 需求日志
     *
     * @param entCode
     * @param inventoryId
     * @param businessKey
     * @param sourceId
     * @param page
     * @param pageSize
     * @return
     */
    public Map inventoryDemandsHistory(String entCode,
                                       String inventoryId,
                                       String businessKey,
                                       String sourceId,
                                       Integer page,
                                       Integer pageSize) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
        valueMap.set("entCode", entCode);
        valueMap.set("inventoryId", inventoryId);
        valueMap.set("sourceId", sourceId);
        valueMap.set("businessKey", businessKey);
        valueMap.set("page", page);
        valueMap.set("pageSize", pageSize);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(valueMap);
        Map map = restTemplate.postForObject("http://mrp-api/v3/inventory/demands/history", httpEntity, Map.class);
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
        Map map = restTemplate.postForObject("http://process-pc/v3/order/inventory-info", httpEntity, Map.class);
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
     * @param businessKey
     * @return
     */
    public List<Map> getBom(String entCode, String businessKey) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
        valueMap.set("entCode", entCode);
        valueMap.set("businessKey", businessKey);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(valueMap);
        Map bom = restTemplate.postForObject("http://development-api/v4/bom/businessKey", httpEntity, Map.class);
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
                             Integer attributeCode,
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
        msg.put("attributeCode", attributeCode);
        rabbitTemplate.convertAndSend("ierp", "ierp.demand.update", msg);
    }

    public void changeBomDemand(String phone,
                                String captcha,
                                String entCode,
                                String userCode,
                                String bomId,
                                BigDecimal quantity) {
        boolean isValid = mchuanSmsService.isValidCaptcha(phone, "update-demand", captcha);
        Assert.state(isValid, "验证码无效");
        Bom bom = bomClient.findByBomId(entCode, bomId);
        SourceMsgVo msg = new SourceMsgVo();
        msg.setEntCode(entCode);
        msg.setUserCode(userCode);
        msg.setSourceId(UUID.randomUUID().toString());
        msg.setBusinessKey(bom.getBusinessKey());
        msg.setSourceDocNo(bom.getBusinessDocNo());
        msg.setSourceType(SourceType.ORDER_QUANTITY_CHANGE);
        msg.setBomId(bomId);
        msg.setQuantity(quantity);
        msg.setRemark("变更数量");
        msg.setCreateTime(new Date());
        rabbitTemplate.convertAndSend("ierp", "ierp.mrp.demand.source.change", msg);
    }

    public List<Map<String, Object>> findErrorDemandInventories(String entCode) throws IOException {
        //采购
        String sql = IOUtils.toString(new ClassPathResource("err_purge_qty.sql").getInputStream(), Charset.forName("utf-8"));
        List<Map<String, Object>> maps = new JdbcTemplate(dataSource).queryForList(sql, entCode);
        for (Map<String, Object> map : maps) {
            map.put("entCode", map.get("ent_code"));
            map.put("businessDocNo", map.get("business_doc_no"));
            map.put("businessKey", map.get("business_key"));
            map.put("inventoryId", map.get("inventory_id"));
            map.put("inventoryCode", map.get("inventory_code"));
            map.put("inventoryName", map.get("inventory_name"));
            map.put("attributeCode", map.get("attribute_code"));
            map.put("attributeName", InventoryAttributeEnum.getAttributeName((String) map.get("attribute_code")));
            map.put("unitName", map.get("unit_name"));
            map.put("demandQty", toPlainString((BigDecimal) map.get("demand_qty")));
            map.put("purgeQty", toPlainString((BigDecimal) map.get("purge_qty")));
            map.put("realPurgeQty", toPlainString((BigDecimal) map.get("c_purge_qty")));
        }
        return maps;
    }


    public void lackMaterial(String entCode, String userCode, String inventoryId) {
        LackMaterielVo lackMaterielVo = new LackMaterielVo();
        lackMaterielVo.setEntCode(entCode);
        lackMaterielVo.setUserCode(userCode);
        lackMaterielVo.setInventoryId(inventoryId);
        lackMaterielVo.setAttributeCode("");
        lackMaterielVo.setCustomerCode("");
        lackMaterielVo.setCustomerName("");
        lackMaterielVo.setSourceId("");
        lackMaterielVo.setSourceDocNo("");
        lackMaterielVo.setBusinessType(BusinessType.SYSTEM);
        rabbitTemplate.convertAndSend("ierp", "ierp.storehouse.lack.materiel.task", lackMaterielVo);
    }


}
