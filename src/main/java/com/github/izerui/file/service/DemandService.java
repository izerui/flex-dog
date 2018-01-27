package com.github.izerui.file.service;

import com.ecworking.commons.vo.PageVo;
import com.ecworking.mrp.vo.*;
import com.ecworking.rbac.remote.vo.ent.SimplifiedEntVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.izerui.file.client.EnterpriseClient;
import com.github.izerui.file.client.MrpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
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

    /**
     * 获取账套列表
     *
     * @return
     */
    public List<SimplifiedEntVo> getEntList() {
        return enterpriseClient.searchAll();
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
                                                      String keyword) {
        return mrpClient.inventoryDemands(entCode, page, pageSize, keyword);
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
    public PageVo<DemandResultVo> inventoryBusinessDemands(String entCode,
                                                           Integer page,
                                                           Integer pageSize,
                                                           String inventoryId) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<String, Object>();
        valueMap.set("entCode", entCode);
        valueMap.set("inventoryId", inventoryId);
        valueMap.set("page", page);
        valueMap.set("pageSize", pageSize);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(valueMap);
        return restTemplate.postForObject("http://mrp-api/v3/inventory/business/demands", httpEntity, PageVo.class);
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
    public PageVo<DemandLogVo> inventoryDemandsHistory(String entCode,
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
        return restTemplate.postForObject("http://mrp-api/v3/inventory/demands/history", httpEntity, PageVo.class);
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
    public List<DemandResultVo> findDemandResults(String entCode,
                                                  String businessKey) {
        return mrpClient.findDemandResults(entCode, businessKey);
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
}
