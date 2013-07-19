package com.izerui.cairngorm.event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.izerui.cairngorm.controller.ZRController;
	
	public class OptFolderClassEvent extends CairngormEvent
	{
		public var path:String;
		public var isCreate:Boolean;

		public var responseFun:Function;
		
		public function OptFolderClassEvent(path:String,isCreate:Boolean,responseFun:Function)
		{
			super(ZRController.CREATE_DELETE_FOLDER);
			this.path = path;
			this.isCreate = isCreate;

			this.responseFun = responseFun;
		}
	}
}