package com.github.izerui.file.cairngorm.business
{
	import com.adobe.cairngorm.business.ServiceLocator;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.remoting.RemoteObject;
	
	public class RenameFolderClassDelegate implements IResponder
	{
		private var responder:IResponder;
		private var service:RemoteObject;
		
		public function RenameFolderClassDelegate(responder:IResponder)
		{
			this.responder = responder;
			this.service = ServiceLocator.getInstance().getRemoteObject("fileService");
		}
		
		public function renameFolder(folderPath:String,folderNewName:String):void{
			var call:AsyncToken = service.renameFolder(folderPath,folderNewName);
			call.addResponder(responder);
		}
		
		public function result(data:Object):void
		{
			responder.result(data);
		}
		
		public function fault(info:Object):void
		{
			responder.fault(info);
		}
	}
}