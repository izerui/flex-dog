package com.github.izerui.file.repository;

import com.ecworking.jpa.PlatformJpaRepository;
import com.github.izerui.file.entity.AuditRecordEntity;
import org.springframework.data.jpa.repository.Query;

public interface AuditRecordRepository extends PlatformJpaRepository<AuditRecordEntity, Long> {

    @Query("select count(x) from AuditRecordEntity x where x.bYear = ?1 and x.bMonth = ?2 and x.bDay = ?3")
    long count(int year, int monthOfYear, int dayOfMonth);


    @Query("select count(distinct x.userCode) from AuditRecordEntity x where x.bYear = ?1 and x.bMonth = ?2 and x.bDay = ?3")
    long countGroupByUsers(int year, int monthOfYear, int dayOfMonth);

    @Query("select count(distinct x.entCode) from AuditRecordEntity x where x.bYear = ?1 and x.bMonth = ?2 and x.bDay = ?3")
    long countGroupByEnts(int year, int monthOfYear, int dayOfMonth);
}
