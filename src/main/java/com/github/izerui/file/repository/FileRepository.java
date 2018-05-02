package com.github.izerui.file.repository;

import com.github.izerui.file.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, String> {

    List<FileEntity> findByServerOrderByUploadTimeDesc(String server);

    @Query("select a from FileEntity a order by a.uploadTime desc")
    List<FileEntity> findAllOrderByUploadTimeDesc();

    List<FileEntity> findByFileName(String fileName);

    List<FileEntity> findByServerInAndFileName(List<String> servers,String fileName);
}
