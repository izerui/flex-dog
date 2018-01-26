package com.github.izerui.file.service;

import com.ecworking.commons.vo.PageVo;
import com.ecworking.mrp.vo.*;
import com.ecworking.rbac.remote.vo.ent.SimplifiedEntVo;
import com.github.izerui.file.client.EnterpriseClient;
import com.github.izerui.file.client.MrpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

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


    /**
     * 获取账套列表
     * @return
     */
    public List<SimplifiedEntVo> getEntList() {
        List<SimplifiedEntVo> simplifiedEntVos = enterpriseClient.searchAll();
        return simplifiedEntVos;
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
     * 查看货品在单据上的需求情况
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
        return mrpClient.inventoryBusinessDemands(entCode, page, pageSize, inventoryId);
    }


    /**
     * 查看货品需求改变历史
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
        return mrpClient.inventoryDemandsHistory(entCode, inventoryId, sourceId, page, pageSize);
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
     * 获取单据任务上的货品需求情况
     *
     * @param entCode
     * @param businessKey
     * @param inventoryId
     * @return
     */
    public DemandResultVo findDemandResult(String entCode,
                                           String businessKey,
                                           String inventoryId) {
        return mrpClient.findDemandResult(entCode, businessKey, inventoryId);
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
