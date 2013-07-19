package com.izerui.cairngorm.event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.izerui.cairngorm.controller.ZRController;
	
	public class FomatRootFilePathClassEvent extends CairngormEvent
	{

		public var responseFun:Function;
		
		public function FomatRootFilePathClassEvent(responseFun:Function)
		{
			super(ZRController.FOMAT_ROOT_FILEPATH);

			this.responseFun = responseFun;
		}
	}
}