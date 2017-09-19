package com.github.izerui.file.cairngorm.event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.github.izerui.file.cairngorm.controller.ZRController;
	
	public class ListFilesByFolderClassEvent extends CairngormEvent
	{
		public var responseFun:Function;
		
		public function ListFilesByFolderClassEvent(responseFun:Function)
		{
			super(ZRController.LISTFILES_BY_FOLDER);
			this.responseFun = responseFun;
		}
	}
}