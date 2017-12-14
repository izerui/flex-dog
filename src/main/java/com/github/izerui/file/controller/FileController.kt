package com.github.izerui.file.controller

import com.github.izerui.file.utils.ConfigUtils
import org.apache.commons.io.IOUtils
import org.apache.log4j.Logger
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class FileController {

    private val log = Logger.getLogger(FileController::class.java)

    @RequestMapping(value = "/upload", method = arrayOf(RequestMethod.POST))
    @Throws(Exception::class)
    fun upload(
            @RequestParam("Filedata") file: MultipartFile,
            request: HttpServletRequest,
            response: HttpServletResponse) {

        if (file.originalFilename != "deploy.json") {
            var exist = false
            val deployConfig = ConfigUtils.getConfig()
            for (server in deployConfig.servers) {
                for (service in server.services) {
                    if (service != null && service!!.file == file.originalFilename) {
                        exist = true
                    }
                }
            }
            if (!exist) {
                throw RuntimeException("不支持上传该文件")
            }
        }

        val newFile = File(ConfigUtils.rootPath + file.originalFilename)

        IOUtils.copy(file.inputStream, FileOutputStream(newFile))

        response.contentType = "text/html;charset=UTF-8"
        response.writer.write("success")
    }

    @RequestMapping(value = "/download", method = arrayOf(RequestMethod.GET))
    @Throws(Exception::class)
    fun download(@RequestParam("name") filename: String, response: HttpServletResponse) {
        val file = File(ConfigUtils.rootPath + filename)
        if (file.isFile) {//存在文件
            response.reset()
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"")
            response.addHeader("Content-Length", "" + file.length())
            response.contentType = "application/octet-stream;charset=UTF-8"
            IOUtils.copy(FileInputStream(file), response.outputStream)
        }
    }

}
