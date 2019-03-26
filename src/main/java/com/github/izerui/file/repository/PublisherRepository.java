package com.github.izerui.file.repository;

import com.ecworking.jpa.PlatformJpaRepository;
import com.github.izerui.file.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends PlatformJpaRepository<PublisherEntity, String> {
}
