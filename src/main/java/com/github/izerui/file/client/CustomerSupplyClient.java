package com.github.izerui.file.client;

import com.ecworking.warehouse.remote.CustomerSupplyRemote;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("warehouse-api")
public interface CustomerSupplyClient extends CustomerSupplyRemote{
}
