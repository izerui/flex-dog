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
    //拥有者
    public var owner:String;
    //服务器
    public var server:String;
    //服务器IP地址
    public var serverAddress:String;
    //服务url
    public var url:String;
    //服务端口
    public var port:Number;
    //服务状态
    public var status:String;
    //发布方式
    public var deployType:String;//发布方式
    //文件大小
    public var size:Number;//文件大小
    public var uploadTimeStr:String;
    public var deployTimeStr:String;
    public var filePath:String;
    public var appId:String;
    public var instanceId:String;

}
}