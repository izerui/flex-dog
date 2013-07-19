package com.izerui.event
{
	import flash.events.Event;
	
	public class FileUploadEvent extends Event
	{
		
		public static const UPLOAD_SINGLE_FILE_COMPLETE:String="upload_single_file_complete";
		
		public static const UPLOAD_COMPLETE:String="upload_complete";
		
		public function FileUploadEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}