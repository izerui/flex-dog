package com.github.izerui.file.event
{
	import com.github.izerui.file.vo.FileTree;
	
	import flash.events.Event;
	
	public class FileTreeItemClickEvent extends Event
	{
		public var itemObject:FileTree;
		
		public function FileTreeItemClickEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}