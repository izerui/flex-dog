package com.github.izerui.file.cairngorm.event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.github.izerui.file.cairngorm.controller.ZRController;
	
	public class GetFolderListClassEvent extends CairngormEvent
	{

		public var responseFun:Function;
		
		public function GetFolderListClassEvent(responseFun:Function)
		{
			super(ZRController.GET_FOLDER_LIST);

			this.responseFun = responseFun;
		}
	}
}