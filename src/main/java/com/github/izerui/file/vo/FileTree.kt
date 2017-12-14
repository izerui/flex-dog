package com.github.izerui.file.vo

import java.io.Serializable

class FileTree : Serializable {
    var foldername: String? = null//文件夹名字
    var folderpath: String? = null//文件夹实际路径
    var children: List<FileTree>? = null//子文件夹
}
