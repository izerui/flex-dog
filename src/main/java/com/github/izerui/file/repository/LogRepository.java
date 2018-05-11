package com.github.izerui.file.repository;

import com.github.izerui.file.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity,String>{
}
