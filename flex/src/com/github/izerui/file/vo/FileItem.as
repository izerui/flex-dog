package com.github.izerui.file.vo {
[RemoteClass(alias="com.github.izerui.file.entity.FileEntity")]
public class FileItem {

    public var id:String;
    //上传时间
    public var uploadTime:Date;
    //发布时间
    public var deployTime:Date;
    //文件名
    public var fileName:String;
    //服务器
    public var server:String;
    //发布方式
    public var deployType:String;//发布方式
    //文件大小
    public var size:Number;//文件大小
    public var uploadTimeStr:String;
    public var deployTimeStr:String;

}
}