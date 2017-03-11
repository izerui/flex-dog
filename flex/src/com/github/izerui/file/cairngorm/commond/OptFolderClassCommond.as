package com.github.izerui.file.cairngorm.commond
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.github.izerui.file.cairngorm.business.OptFolderClassDelegate;
	import com.github.izerui.file.cairngorm.event.OptFolderClassEvent;
	import com.github.izerui.file.components.loading.LoaderManager;
	
	import mx.controls.Alert;
	import mx.resources.ResourceManager;
	import mx.rpc.IResponder;
	
	public class OptFolderClassCommond implements IResponder, ICommand
	{
		
		private var delegate:OptFolderClassDelegate;
		
		private var responseFun:Function;
		
		public function OptFolderClassCommond()
		{
			delegate = new OptFolderClassDelegate(this);
		}
		
		public function result(data:Object):void
		{
			if(responseFun){
				responseFun.call(null,data.result);
			}
		}
		
		public function fault(info:Object):void
		{
			Alert.show(ResourceManager.getInstance().getString("jhaij","deleteFolderErrorAlert"),ResourceManager.getInstance().getString("jhaij","error"));
			LoaderManager.hideLoading();
		}
		
		public function execute(event:CairngormEvent):void
		{
			var myEvent:OptFolderClassEvent = OptFolderClassEvent(event);
			responseFun = myEvent.responseFun;
			delegate.optFolder(myEvent.path,myEvent.isCreate);
		}
	}
}