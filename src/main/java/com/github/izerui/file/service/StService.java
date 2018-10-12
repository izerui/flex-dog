package com.github.izerui.file.service;

import com.ecworking.audit.Record;
import com.github.izerui.file.entity.AuditRecordEntity;
import com.github.izerui.file.repository.AuditRecordRepository;
import com.github.izerui.file.repository.StIgnoreTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StService {

    @Autowired
    private AuditRecordRepository auditRecordRepository;

    @Autowired
    private StIgnoreTypeRepository stIgnoreTypeRepository;

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
        recordEntity.setAppId(record.getAppId());
        recordEntity.setToken(record.getToken());
        auditRecordRepository.save(recordEntity);
    }


}
