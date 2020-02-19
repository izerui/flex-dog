package com.github.izerui.file.service;

import com.ecworking.audit.Record;
import com.github.izerui.file.entity.AuditRecordEntity;
import com.github.izerui.file.repository.AuditRecordRepository;
import com.github.izerui.file.syspt.DataContent;
import com.github.izerui.file.syspt.RequestData;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RemotingDestination
public class StService {

    @Autowired
    private AuditRecordRepository auditRecordRepository;

    @Value("${audit.record.signatures}")
    private String recordSignatures;

    @Async
    public void saveRecord(Record record) {
        //如果在排除列表就不记录
        if (!recordSignatures.contains(record.getSignature())) {
            return;
        }

        AuditRecordEntity recordEntity = new AuditRecordEntity();
        recordEntity.setBegin(record.getBegin());
        recordEntity.setApplication(record.getApplication());
        recordEntity.setType(record.getType());
        recordEntity.setSignature(record.getSignature());
        recordEntity.setName(record.getName());
        recordEntity.setIp(record.getIp());
        recordEntity.setUrl(record.getUrl());
        recordEntity.setAccountCode(record.getAccountCode());
        recordEntity.setAccountName(record.getAccountName());
        recordEntity.setUserCode(record.getUserCode());
        recordEntity.setUserName(record.getUserName());
        recordEntity.setEntCode(record.getEntCode());
        recordEntity.setEntName(record.getEntName());
        recordEntity.setSuccess(record.isSuccess());
        recordEntity.setEnd(record.getEnd());
        recordEntity.setTime(record.getTime());
        auditRecordRepository.save(recordEntity);
    }

    public Map<String, Long> countMap() throws CloneNotSupportedException {
        DateTime now = DateTime.now();
        // 今日访问量
        long logCount = auditRecordRepository.count(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth());

        // 今日访问用户数
        long userCount = auditRecordRepository.countGroupByUsers(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth());

        // 今日访问企业数
        long entCount = auditRecordRepository.countGroupByEnts(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth());

        return new HashMap<String, Long>() {{
            put("logCount", logCount);
            put("userCount", userCount);
            put("entCount", entCount);
        }};
    }

    public List<DataContent> findRecords(RequestData requestData) {
        List<String> userIds = Arrays.asList(StringUtils.split(requestData.getUserId(), ","));
        List<AuditRecordEntity> recordEntities = auditRecordRepository.findUserIdsAndTimeBetween(userIds, requestData.getBegin(), requestData.getEnd());
        List<DataContent> results = new ArrayList<>();
        for (AuditRecordEntity record : recordEntities) {
            DataContent content = new DataContent();
            content.setUserId(record.getAccountName());
            content.setSource(record.getIp());
            content.setTarget(record.getUrl());
            content.setReqDuring(record.getTime());
            content.setCompanyId(record.getEntCode());
            content.setCompanyName(record.getEntName());
            Date begin = record.getBegin();
            DateTime dateTime = new DateTime(begin);
            content.setOperateTime(Long.valueOf(dateTime.toString("yyyyMMddHHmmss")));
            results.add(content);
        }
        return results;
    }

}
