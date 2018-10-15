package com.github.izerui.file.repository;

import com.ecworking.jpa.PlatformJpaRepository;
import com.github.izerui.file.entity.AuditRecordEntity;
import org.springframework.data.jpa.repository.Query;

public interface AuditRecordRepository extends PlatformJpaRepository<AuditRecordEntity, Long> {

    @Query("select count(x) from AuditRecordEntity x where x.bYear = ?1 and x.bMonth = ?2 and x.bDay = ?3")
    long count(int year, int monthOfYear, int dayOfMonth);


    @Query("select count(x) from AuditRecordEntity x where x.bYear = ?1 and x.bMonth = ?2 and x.bDay = ?3 group by x.userCode")
    long countGroupByUsers(int year, int monthOfYear, int dayOfMonth);

    @Query("select count(x) from AuditRecordEntity x where x.bYear = ?1 and x.bMonth = ?2 and x.bDay = ?3 group by x.entCode")
    long countGroupByEnts(int year, int monthOfYear, int dayOfMonth);
}
