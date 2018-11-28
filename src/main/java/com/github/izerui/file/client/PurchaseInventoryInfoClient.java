package com.github.izerui.file.client;

import com.ecworking.purchase.remote.PurchaseInventoryInfoRemote;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("purchase-api")
public interface PurchaseInventoryInfoClient extends PurchaseInventoryInfoRemote{
}
