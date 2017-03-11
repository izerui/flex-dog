package com.github.izerui.file.vo
{
	import com.adobe.cairngorm.vo.IValueObject;
	
	import mx.collections.ArrayCollection;
	
	
	[RemoteClass(alias="com.github.izerui.file.vo.FileTree")]
	public class FileTree implements IValueObject
	{
		public var foldername:String;
		public var folderpath:String;
		public var children:ArrayCollection;
		
	}
}