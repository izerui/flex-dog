package com.github.izerui.file.cairngorm.business
{
	import com.adobe.cairngorm.business.ServiceLocator;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.remoting.RemoteObject;
	
	public class OptFolderClassDelegate implements IResponder
	{
		private var responder:IResponder;
		private var service:RemoteObject;
		
		public function OptFolderClassDelegate(responder:IResponder)
		{
			this.responder = responder;
			this.service = ServiceLocator.getInstance().getRemoteObject("fileService");
		}
		
		public function optFolder(path:String,isCreate:Boolean):void{
			var call:AsyncToken = service.optFolder(path,isCreate);
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