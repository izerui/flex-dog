package com.izerui.cairngorm.event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.izerui.cairngorm.controller.ZRController;
	
	public class DeleteFileClassEvent extends CairngormEvent
	{
		public var selectedFileItems:Array;

		public var responseFun:Function;
		
		public function DeleteFileClassEvent(selectedFileItems:Array,responseFun:Function)
		{
			super(ZRController.DELETE_SELECTED_FILES);
			this.selectedFileItems = selectedFileItems;

			this.responseFun = responseFun;
		}
	}
}