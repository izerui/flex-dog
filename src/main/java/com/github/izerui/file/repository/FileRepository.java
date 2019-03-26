package com.github.izerui.file.repository;

import com.ecworking.jpa.PlatformJpaRepository;
import com.github.izerui.file.entity.FileEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends PlatformJpaRepository<FileEntity, String> {

    long countByFileNameAndServer(String fileName, String server);

    List<FileEntity> findByServerOrderByUploadTimeDesc(String server);

    @Query("select a from FileEntity a order by a.uploadTime desc")
    List<FileEntity> findAllOrderByUploadTimeDesc();

    List<FileEntity> findByFileName(String fileName);

    List<FileEntity> findByServerInAndFileName(List<String> servers, String fileName);
}
