package com.github.izerui.file.web;

import com.ecworking.audit.Record;
import com.ecworking.rbac.dto.EnterpriseEntity;
import com.github.izerui.file.service.DemandService;
import com.github.izerui.file.service.OnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.github.izerui.file.web.Response.success;

@RestController
public class OnlineController {

    @Autowired
    private OnlineService onlineService;
    @Autowired
    private DemandService demandService;

    @GetMapping("/api/v1/onlines")
    public Response getOnlines() throws Exception {
        List<Map<String, Object>> onlineUsers = onlineService.onlineUsers();
        return success(onlineUsers);
    }

    @GetMapping("/api/v1/records/top100")
    public Response getRecordsTop100() throws Exception {
        List<Record> limit100 = onlineService.getUserRecordListLimit100();
        return success(limit100);
    }

    @GetMapping("/api/v1/ents")
    public Response getEntList() {
        List<EnterpriseEntity> entList = demandService.getEntList();
        return success(entList);
    }
}
