package com.github.izerui.file.client;

import com.ecworking.rbac.remote.UserRemote;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("rbac-api")
public interface UserClient extends UserRemote{
}
