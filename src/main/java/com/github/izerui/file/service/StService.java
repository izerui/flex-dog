package com.github.izerui.file.service;

import com.ecworking.audit.Record;
import com.github.izerui.file.entity.AuditRecordEntity;
import com.github.izerui.file.repository.AuditRecordRepository;
import com.github.izerui.file.repository.StIgnoreTypeRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RemotingDestination
public class StService {

    @Autowired
    private AuditRecordRepository auditRecordRepository;

    @Autowired
    private StIgnoreTypeRepository stIgnoreTypeRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Async
    public void logCount(Record record) {
        //如果在排除列表就不记录
        if (stIgnoreTypeRepository.count(record.getApplication(), record.getType(), record.getName()) > 0L) {
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

}
