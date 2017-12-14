package com.github.izerui.file.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.izerui.file.vo.Deploy

import java.io.File

class ConfigUtils {

    companion object {

        val rootPath: String = System.getProperty("user.dir") + File.separator

        fun getConfig(): Deploy {
            val file = File(rootPath + "deploy.json")
            if (!file.exists()) {
                return Deploy();
            }
            val deploy:Deploy = ObjectMapper().readValue<Deploy>(File(rootPath + "deploy.json"), Deploy::class.java!!)
            return deploy
        }
    }

}
