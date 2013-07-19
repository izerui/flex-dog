package com.izerui.cairngorm.commond
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.izerui.cairngorm.business.RenameFolderClassDelegate;
	import com.izerui.cairngorm.event.RenameFolderClassEvent;
	
	import mx.controls.Alert;
	import mx.resources.ResourceManager;
	import mx.rpc.IResponder;
	
	public class RenameFolderClassCommond implements IResponder, ICommand
	{
		
		private var delegate:RenameFolderClassDelegate;
		
		private var responseFun:Function;
		
		public function RenameFolderClassCommond()
		{
			delegate = new RenameFolderClassDelegate(this);
		}
		
		public function result(data:Object):void
		{
			if(responseFun){
				responseFun.call(null,data.result);
			}
		}
		
		public function fault(info:Object):void
		{
			Alert.show(ResourceManager.getInstance().getString("jhaij","fileInUse"),ResourceManager.getInstance().getString("jhaij","error"));
		}
		
		public function execute(event:CairngormEvent):void
		{
			var myEvent:RenameFolderClassEvent = RenameFolderClassEvent(event);
			responseFun = myEvent.responseFun;
			delegate.renameFolder(myEvent.folderPath,myEvent.folderNewName);
		}
	}
}