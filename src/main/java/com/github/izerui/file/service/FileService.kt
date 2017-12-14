package com.github.izerui.file.service

import com.github.izerui.file.entity.DeployEntity
import com.github.izerui.file.repository.DeployRepository
import com.github.izerui.file.utils.ConfigUtils
import com.github.izerui.file.utils.RelativeDateFormat
import com.github.izerui.file.vo.FileItem
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.lang.StringUtils
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.flex.remoting.RemotingDestination
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.util.*

@RemotingDestination
@Service("fileService")
@ConfigurationProperties
@Transactional
open class FileService(@Autowired private var deployRepository: DeployRepository) {

    @Throws(Exception::class)
    fun listFiles(): List<FileItem> {
        val files = ArrayList<FileItem>()
        val folderFile = File(ConfigUtils.rootPath)
        val filesArray = folderFile.listFiles()
        val servers = ConfigUtils.getConfig().servers
        for (file in filesArray!!) {
            if (!file.isDirectory) {
                val fi = FileItem()
                fi.lashmodifyDate = Date(file.lastModified())
                fi.relativeLashmodifyDate = RelativeDateFormat.format(fi.lashmodifyDate)
                fi.folder = false
                fi.folderpath = FilenameUtils.getFullPath(file.path)
                fi.basename = FilenameUtils.getBaseName(file.name)
                fi.extension = FilenameUtils.getExtension(file.name)
                fi.hidden = file.isHidden
                fi.filename = file.name
                fi.size = file.length()

                val one = deployRepository?.findOne(fi.filename)
                if (one != null) {
                    fi.deployTime = one.deployTime
                    fi.relativeDeployTime = RelativeDateFormat.format(one.deployTime)
                }

                for (server in servers) {
                    for (service in server.services) {
                        if (service != null && service!!.file == file.name) {
                            if (StringUtils.isEmpty(fi.servers)) {
                                fi.servers = server.server
                            } else {
                                fi.servers = fi.servers + "," + server.server
                            }

                        }
                    }
                }
                files.add(fi)
            }
        }

        files.sortedByDescending { fileItem -> fileItem.lashmodifyDate }

        return files
    }


    @Throws(Exception::class)
    fun exec(fileName: String): String {
        var output = ""
        var deployed = false
        val servers = ConfigUtils.getConfig().servers
        for (server in servers) {
            for (service in server.services) {
                if (service != null && service!!.file == fileName) {

                    val chmodCommand = "chmod 777 /etc/ansible/application-operation.sh"
                    val chmodProcess = Runtime.getRuntime().exec(chmodCommand)
                    chmodProcess.waitFor()


                    val command = "/bin/sh /etc/ansible/application-operation.sh " + service!!.type + " " + server.server + " " + service!!.file
                    val execProcess = Runtime.getRuntime().exec(command)
                    execProcess.waitFor()

                    output += IOUtils.toString(execProcess.inputStream, "UTF-8") + "\n"
                    log.info(output)

                    deployed = true
                }
            }
        }

        if (!deployed) {
            throw RuntimeException("不支持该文件")
        }

        //保存发布记录
        var one: DeployEntity? = deployRepository.findOne(fileName)
        if (one == null) {
            one = DeployEntity()
            one.fileName = fileName
        }
        one.deployTime = Date()
        deployRepository.save<DeployEntity>(one)

        return output
    }

    companion object {

        private val log = Logger.getLogger(FileService::class.java!!)
    }

}
