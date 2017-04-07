package com.github.izerui.file.cairngorm.event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.github.izerui.file.cairngorm.controller.ZRController;
	
	public class ExecClassEvent extends CairngormEvent
	{
		public var filePath:String;

		public var responseFun:Function;
		
		public function ExecClassEvent(filePath:String, responseFun:Function)
		{
			super(ZRController.EXEC);
			this.filePath = filePath;

			this.responseFun = responseFun;
		}
	}
}