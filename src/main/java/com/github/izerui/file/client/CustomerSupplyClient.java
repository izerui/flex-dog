package com.github.izerui.file.client;

import com.ecworking.warehouse.remote.CustomerSupplyRemote;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("warehouse-api")
public interface CustomerSupplyClient extends CustomerSupplyRemote{
}
