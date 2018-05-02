package com.github.izerui.file.event
{
	import flash.events.Event;

import mx.collections.ArrayCollection;

public class FileUploadEvent extends Event
	{
		public var fileId:String;
		public var fileName:String;
		public var size:Number;
		public var servers:ArrayCollection;
		public static const UPLOAD_SINGLE_FILE_COMPLETE:String="upload_single_file_complete";
		
		public static const UPLOAD_COMPLETE:String="upload_complete";
		
		public function FileUploadEvent(type:String, fileName:String = null, fileId:String = null, size:Number = 0, servers:ArrayCollection = null)
		{
			super(type, true, false);
			this.fileName = fileName;
			this.fileId = fileId;
			this.size = size;
			this.servers = servers;
		}
	}
}