package com.github.izerui.file.web;

import com.github.izerui.file.service.UcloudService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UCloudController {


    @Autowired
    private UcloudService ucloudService;


    @ApiOperation("获取服务器列表")
    @GetMapping(value = "/api/v1/servers",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public String getServers() {
        return ucloudService.getDescribeUHostInstance();
    }

    @ApiOperation("获取服务器监控列表")
    @GetMapping(value = "/api/v1/metrics/ecs",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public String getMetrics() {
        return ucloudService.getMetricOverview();
    }


    @ApiOperation("获取数据库监控列表")
    @GetMapping(value = "/api/v1/metrics/dbs",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public String getDbMetrics() {
        return ucloudService.getDbMetricOverview();
    }


    @ApiOperation("获取redis监控列表")
    @GetMapping(value = "/api/v1/metrics/redis",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.ALL_VALUE
    )
    public String getRedisMetrics() {
        return ucloudService.getRedisMetricOverview();
    }

}
