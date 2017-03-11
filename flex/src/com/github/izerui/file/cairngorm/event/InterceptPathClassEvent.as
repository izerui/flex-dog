package com.github.izerui.file.cairngorm.event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.github.izerui.file.cairngorm.controller.ZRController;
	
	public class InterceptPathClassEvent extends CairngormEvent
	{
		public var folderPath:String;

		public var responseFun:Function;
		
		public function InterceptPathClassEvent(folderPath:String, responseFun:Function)
		{
			super(ZRController.INTERCEPT_PATH);
			this.folderPath = folderPath;

			this.responseFun = responseFun;
		}
	}
}