package com.github.izerui.file.client;

import com.ecworking.development.remote.BusinessRemote;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("development-api")
public interface BusinessClient extends BusinessRemote{
}
