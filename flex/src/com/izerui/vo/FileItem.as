package com.izerui.vo
{
	import com.adobe.cairngorm.vo.IValueObject;
	
	[RemoteClass(alias="com.izerui.vo.FileItem")]
	public class FileItem implements IValueObject
	{
		
		
		public var filename:String;//文件全名
		public var extension:String;//后缀名
		public var basename:String;//去掉后缀名
		public var folderpath:String;//所属文件夹路径
		public var size:Number;//文件大小
		public var ishidden:Boolean ;//是否是隐藏文件
		public var isfolder:Boolean;//是否是文件夹
		public var lashmodifydate:Date;//是否是文件夹
		
		
	}
}