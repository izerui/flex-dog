package com.izerui.cairngorm.commond
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.izerui.cairngorm.business.FomatRootFilePathClassDelegate;
	import com.izerui.cairngorm.event.FomatRootFilePathClassEvent;
	import mx.controls.Alert;
	import mx.resources.ResourceManager;
	import mx.rpc.IResponder;
	
	public class FomatRootFilePathClassCommond implements IResponder, ICommand
	{
		
		private var delegate:FomatRootFilePathClassDelegate;
		
		private var responseFun:Function;
		
		public function FomatRootFilePathClassCommond()
		{
			delegate = new FomatRootFilePathClassDelegate(this);
		}
		
		public function result(data:Object):void
		{
			if(responseFun){
				responseFun.call(null,data.result);
			}
		}
		
		public function fault(info:Object):void
		{
			Alert.show(info.fault.faultString,ResourceManager.getInstance().getString("jhaij","error"));
		}
		
		public function execute(event:CairngormEvent):void
		{
			var myEvent:FomatRootFilePathClassEvent = FomatRootFilePathClassEvent(event);
			responseFun = myEvent.responseFun;
			delegate.fomatRootFilePath();
		}
	}
}