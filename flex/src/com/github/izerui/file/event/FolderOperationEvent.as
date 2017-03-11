package com.github.izerui.file.event
{
	import flash.events.Event;
	
	public class FolderOperationEvent extends Event
	{
		public var folderNameText:String;
		
		//0 新建 1 修改
		public var folderOperationType:int;
		
		public function FolderOperationEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}