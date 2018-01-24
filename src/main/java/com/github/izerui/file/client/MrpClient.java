package com.github.izerui.file.client;

import com.ecworking.mrp.remote.MrpRemote;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("mrp-api")
public interface MrpClient extends MrpRemote{
}
