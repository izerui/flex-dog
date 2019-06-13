package com.github.izerui.file.web;

import com.ecworking.commons.vo.PageVo;
import com.ecworking.mrp.vo.InventoryDemandVo;
import com.github.izerui.file.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.github.izerui.file.web.Response.success;

@RestController
public class DemandController {

    @Autowired
    private DemandService demandService;

    @GetMapping("/api/v1/demands")
    public Response demands(@RequestParam("entCode") String entCode,
                            @RequestParam("page") Integer page,
                            @RequestParam("pageSize") Integer pageSize,
                            @RequestParam("keyword") String keyword) {

        PageVo<InventoryDemandVo> pageVo = demandService.inventoryDemands(entCode, page, pageSize, keyword);
        return success(pageVo);
    }
}
