package com.github.izerui.file.repository;

import com.ecworking.jpa.PlatformJpaRepository;
import com.github.izerui.file.entity.AuditRecordEntity;
import org.springframework.data.jpa.repository.Query;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface AuditRecordRepository extends PlatformJpaRepository<AuditRecordEntity, Long> {

    @Query("select count(x) from AuditRecordEntity x where x.bYear = ?1 and x.bMonth = ?2 and x.bDay = ?3")
    long count(int year, int monthOfYear, int dayOfMonth);

    @Query("select count(distinct x.userCode) from AuditRecordEntity x where x.bYear = ?1 and x.bMonth = ?2 and x.bDay = ?3")
    long countGroupByUsers(int year, int monthOfYear, int dayOfMonth);

    @Query("select count(distinct x.entCode) from AuditRecordEntity x where x.bYear = ?1 and x.bMonth = ?2 and x.bDay = ?3")
    long countGroupByEnts(int year, int monthOfYear, int dayOfMonth);

    @Query("select x from AuditRecordEntity x where x.accountName in ?1 and x.begin >= ?2 and x.end <= ?3")
    List<AuditRecordEntity> findUserIdsAndTimeBetween(List<String> userIds, Date beginTime, Date endTime);
}
