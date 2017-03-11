package com.github.izerui.file.cairngorm.business
{
	import com.adobe.cairngorm.business.ServiceLocator;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	import mx.rpc.remoting.RemoteObject;
	
	public class FomatRootFilePathClassDelegate implements IResponder
	{
		private var responder:IResponder;
		private var service:RemoteObject;
		
		public function FomatRootFilePathClassDelegate(responder:IResponder)
		{
			this.responder = responder;
			this.service = ServiceLocator.getInstance().getRemoteObject("fileService");
		}
		
		public function fomatRootFilePath():void{
			var call:AsyncToken = service.fomatRootFilePath();
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