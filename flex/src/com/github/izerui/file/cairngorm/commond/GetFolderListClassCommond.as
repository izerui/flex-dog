package com.github.izerui.file.cairngorm.commond
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.github.izerui.file.cairngorm.business.GetFolderListClassDelegate;
	import com.github.izerui.file.cairngorm.event.GetFolderListClassEvent;
	import com.github.izerui.file.components.loading.LoaderManager;
	
	import mx.controls.Alert;
	import mx.resources.ResourceManager;
	import mx.rpc.IResponder;
	
	public class GetFolderListClassCommond implements IResponder, ICommand
	{
		
		private var delegate:GetFolderListClassDelegate;
		
		private var responseFun:Function;
		
		public function GetFolderListClassCommond()
		{
			delegate = new GetFolderListClassDelegate(this);
		}
		
		public function result(data:Object):void
		{
			if(responseFun){
				responseFun.call(null,data.result);
			}
		}
		
		public function fault(info:Object):void
		{
			Alert.show(ResourceManager.getInstance().getString("jhaij","FolderListErrorTootip"),ResourceManager.getInstance().getString("jhaij","error"));
			LoaderManager.hideLoading();
		}
		
		public function execute(event:CairngormEvent):void
		{
			var myEvent:GetFolderListClassEvent = GetFolderListClassEvent(event);
			responseFun = myEvent.responseFun;
			delegate.getFolderList();
		}
	}
}