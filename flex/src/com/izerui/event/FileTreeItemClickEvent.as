package com.izerui.event
{
	import com.izerui.vo.FileTree;
	
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