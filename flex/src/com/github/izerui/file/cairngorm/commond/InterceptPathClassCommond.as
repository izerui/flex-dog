package com.github.izerui.file.cairngorm.commond
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.github.izerui.file.cairngorm.business.InterceptPathClassDelegate;
	import com.github.izerui.file.cairngorm.event.InterceptPathClassEvent;
	
	import mx.controls.Alert;
	import mx.resources.ResourceManager;
	import mx.rpc.IResponder;
	
	public class InterceptPathClassCommond implements IResponder, ICommand
	{
		
		private var delegate:InterceptPathClassDelegate;
		
		private var responseFun:Function;
		
		public function InterceptPathClassCommond()
		{
			delegate = new InterceptPathClassDelegate(this);
		}
		
		public function result(data:Object):void
		{
			if(responseFun){
				responseFun.call(null,data.result);
			}
		}
		
		public function fault(info:Object):void
		{
			Alert.show(info.fault.faultString ,ResourceManager.getInstance().getString("jhaij","error"));
		}
		
		public function execute(event:CairngormEvent):void
		{
			var myEvent:InterceptPathClassEvent = InterceptPathClassEvent(event);
			responseFun = myEvent.responseFun;
			delegate.interceptPath(myEvent.folderPath);
		}
	}
}