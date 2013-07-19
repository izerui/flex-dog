package com.izerui.vo
{
	import com.adobe.cairngorm.vo.IValueObject;
	
	import mx.collections.ArrayCollection;
	
	
	[RemoteClass(alias="com.izerui.vo.FileTree")]
	public class FileTree implements IValueObject
	{
		public var foldername:String;
		public var folderpath:String;
		public var children:ArrayCollection;
		
	}
}