package com.github.izerui.file.service

import com.github.izerui.file.utils.ApiClient
import com.github.izerui.file.utils.ConfigUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.flex.remoting.RemotingDestination
import org.springframework.stereotype.Service
import java.util.*

@RemotingDestination
@Service("ucloudService")
open class UcloudService {

    val describeUHostInstance: String
        get() {
            val params = HashMap<String, String>()
            params.put("ProjectId", "org-skwhwp")
            params.put("Zone", "cn-gd-02")
            params.put("Region", "cn-gd")
            params.put("Offset", "0")
            params.put("Limit", "5000")
            params.put("Action", "DescribeUHostInstance")
            params.put("_timestamp", System.currentTimeMillis().toString())
            return apiClient!!.execute(params)
        }

    val metricOverview: String
        get() {
            val params = HashMap<String, String>()
            params.put("ProjectId", "org-skwhwp")
            params.put("Zone", "cn-gd-02")
            params.put("Region", "cn-gd")
            params.put("ResourceType", "uhost")
            params.put("Offset", "0")
            params.put("Limit", "60")
            params.put("Action", "GetMetricOverview")
            params.put("_timestamp", System.currentTimeMillis().toString())
            return apiClient!!.execute(params)
        }

    val dbMetricOverview: String
        get() {
            val params = HashMap<String, String>()
            params.put("ProjectId", "org-skwhwp")
            params.put("Zone", "cn-gd-02")
            params.put("Region", "cn-gd")
            params.put("ResourceType", "udb")
            params.put("Offset", "0")
            params.put("Limit", "60")
            params.put("Action", "GetMetricOverview")
            params.put("_timestamp", System.currentTimeMillis().toString())
            return apiClient!!.execute(params)
        }
    val redisMetricOverview: String
        get() {
            val params = HashMap<String, String>()
            params.put("ProjectId", "org-skwhwp")
            params.put("Zone", "cn-gd-02")
            params.put("Region", "cn-gd")
            params.put("ResourceType", "uredis")
            params.put("Offset", "0")
            params.put("Limit", "60")
            params.put("Action", "GetMetricOverview")
            params.put("_timestamp", System.currentTimeMillis().toString())
            return apiClient!!.execute(params)
        }

    companion object {
        private val apiClient: ApiClient = ApiClient(ConfigUtils.getConfig().publicKey, ConfigUtils.getConfig().privateKey)
    }


}
