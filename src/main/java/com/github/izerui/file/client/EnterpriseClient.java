package com.github.izerui.file.client;

import com.ecworking.rbac.remote.EnterpriseRemote;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("rbac-api")
public interface EnterpriseClient extends EnterpriseRemote{
}
