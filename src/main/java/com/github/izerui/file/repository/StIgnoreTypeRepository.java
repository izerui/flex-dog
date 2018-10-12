package com.github.izerui.file.repository;

import com.github.izerui.file.entity.StIgnoreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StIgnoreTypeRepository extends JpaRepository<StIgnoreType, Long> {

    @Query("select count(x) from StIgnoreType x where x.application = ?1 and x.type = ?2 and x.name = ?3")
    long count(String application, String type, String name);
}
