package ucloud

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.izerui.file.utils.ApiClient
import com.github.izerui.file.utils.ConfigUtils
import com.github.izerui.file.vo.Deploy
import org.junit.Test

import java.io.File
import java.util.HashMap

class JunitExemple {

    @Test
    @Throws(Exception::class)
    fun DescribeUHostInstance() {


        val params = HashMap<String, String>()
        params.put("ProjectId", "org-skwhwp")
        params.put("Zone", "cn-gd-02")
        params.put("Region", "cn-gd")
        params.put("Offset", "0")
        params.put("Limit", "5000")
        params.put("Action", "DescribeUHostInstance")
        params.put("_timestamp", System.currentTimeMillis().toString())

        val execute = apiClient!!.execute(params)
        println(execute)
    }

    companion object {

        private var apiClient: ApiClient? = null

        init {
            val deploy = ObjectMapper().readValue<Deploy>(File(ConfigUtils.rootPath + "deploy.json"), Deploy::class.java!!)
            apiClient = ApiClient(deploy.publicKey, deploy.privateKey)
        }
    }
}
