package com.github.izerui.file.client;

import com.ecworking.manufacture.remote.SearchRemote;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("manufacture-api")
public interface ManufactureInventoryClient extends SearchRemote{
}
