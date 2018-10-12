package com.github.izerui.file.repository;

import com.ecworking.jpa.PlatformJpaRepository;
import com.github.izerui.file.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends PlatformJpaRepository<LogEntity,String> {
}
