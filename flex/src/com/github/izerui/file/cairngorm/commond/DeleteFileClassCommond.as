package com.github.izerui.file.cairngorm.commond
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.github.izerui.file.cairngorm.business.DeleteFileClassDelegate;
	import com.github.izerui.file.cairngorm.event.DeleteFileClassEvent;
	import mx.controls.Alert;
	import mx.resources.ResourceManager;
	import mx.rpc.IResponder;
	
	public class DeleteFileClassCommond implements IResponder, ICommand
	{
		
		private var delegate:DeleteFileClassDelegate;
		
		private var responseFun:Function;
		
		public function DeleteFileClassCommond()
		{
			delegate = new DeleteFileClassDelegate(this);
		}
		
		public function result(data:Object):void
		{
			if(responseFun){
				responseFun.call(null);
			}
		}
		
		public function fault(info:Object):void
		{
			Alert.show(info.fault.faultString,ResourceManager.getInstance().getString("jhaij","error"));
		}
		
		public function execute(event:CairngormEvent):void
		{
			var myEvent:DeleteFileClassEvent = DeleteFileClassEvent(event);
			responseFun = myEvent.responseFun;
			delegate.deleteFile(myEvent.selectedFileItems);
		}
	}
}