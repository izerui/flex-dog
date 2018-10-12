package com.github.izerui.file.repository;

import com.ecworking.jpa.PlatformJpaRepository;
import com.github.izerui.file.entity.UserStatistical;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserStatisticalRepository extends PlatformJpaRepository<UserStatistical, Long> {

    @Query("select count(x) from UserStatistical x where x.userCode = ?1 and x.application = ?2 and x.type = ?3 and x.name = ?4")
    long count(String userCode, String application, String type, String name);

    @Query("select x from UserStatistical x where x.userCode = ?1 and x.application = ?2 and x.type = ?3 and x.name = ?4")
    UserStatistical get(String userCode, String application, String type, String name);

    @Modifying
    @Query("delete from UserStatistical x where x.application = ?1 and x.type = ?2 and x.name = ?3")
    void delete(String application, String type, String name);
}
