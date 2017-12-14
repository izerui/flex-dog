package com.github.izerui.file.vo

import java.io.Serializable
import java.util.*

class FileItem : Serializable {
    var filename: String? = null//文件全名
    var extension: String? = null//后缀名
    var basename: String? = null//去掉后缀名
    var folderpath: String? = null//所属文件夹路径
    var size: Long = 0//文件大小
    var hidden: Boolean = false//是否是隐藏文件
    var folder: Boolean = false
    var lashmodifyDate: Date = Date()
    var relativeLashmodifyDate: String? = null
    var deployTime: Date? = null
    var servers: String? = null
    var relativeDeployTime: String? = null
}
