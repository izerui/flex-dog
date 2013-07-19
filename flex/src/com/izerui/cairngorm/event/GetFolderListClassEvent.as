package com.izerui.cairngorm.event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.izerui.cairngorm.controller.ZRController;
	
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