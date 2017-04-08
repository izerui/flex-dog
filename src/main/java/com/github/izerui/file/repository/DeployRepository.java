package com.github.izerui.file.repository;

import com.github.izerui.file.entity.DeployEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by serv on 2017/4/8.
 */
public interface DeployRepository extends JpaRepository<DeployEntity,String> {
}
