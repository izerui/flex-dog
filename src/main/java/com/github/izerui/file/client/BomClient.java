package com.github.izerui.file.client;

import com.ecworking.development.remote.BomRemote;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("development-api")
public interface BomClient extends BomRemote{
}
