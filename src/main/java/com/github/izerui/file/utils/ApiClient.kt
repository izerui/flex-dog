package com.github.izerui.file.utils

import okhttp3.ConnectionPool
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.commons.codec.digest.DigestUtils
import java.util.*
import java.util.concurrent.TimeUnit

class ApiClient(private val publicKey: String, private val privateKey: String) {


    fun execute(params: MutableMap<String, String>): String {
        try {
            val request = Request.Builder()
                    .post(createFormBody(params))
                    .url("https://api.ucloud.cn")
                    .build()
            val response = client!!.newCall(request).execute()
            return response.body()!!.string()
        } catch (ex: Exception) {
            throw RuntimeException(ex.message, ex)
        }

    }

    private fun createFormBody(params: MutableMap<String, String>): FormBody {
        params.put("PublicKey", this.publicKey)
        val builder = FormBody.Builder()
        for (key in params.keys) {
            builder.add(key, params[key])
        }
        builder.add("Signature", verify(params))
        return builder.build()
    }


    private fun verify(params: Map<String, String>): String {

        val str = StringBuffer()
        val keys = params.keys
        val keyList = ArrayList<String>()
        for (key in keys) {
            keyList.add(key)
        }
        Collections.sort(keyList)
        for (key in keyList) {
            str.append(key).append(params[key].toString())
        }
        str.append(this.privateKey)

        // use sha1 to encode keys
        return DigestUtils.sha1Hex(str.toString())
    }

    companion object {

        private val client: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
                .pingInterval(30, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

    }


}
