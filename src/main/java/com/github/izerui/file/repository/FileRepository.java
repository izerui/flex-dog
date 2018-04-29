package com.github.izerui.file.repository;

import com.github.izerui.file.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, String> {

    List<FileEntity> findByServer(String server);
}
