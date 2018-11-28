package com.github.izerui.file.client;

import com.ecworking.rbac.remote.RoleRemote;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("rbac-api")
public interface RoleClient extends RoleRemote{
}
