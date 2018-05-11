package com.github.izerui.file.utils {
import com.github.izerui.file.components.loading.LoaderManager;

import mx.controls.Alert;
import mx.rpc.AbstractOperation;
import mx.rpc.AsyncToken;
import mx.rpc.Responder;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.rpc.remoting.RemoteObject;

public class RemoteObjectUtils {
    public function RemoteObjectUtils() {
    }

    public static function execute(destination:String, method:String, resultResponse:Function, ...args):void {
        LoaderManager.showLoading(false);
        var remoteService:RemoteObject = new RemoteObject(destination);
        remoteService.endpoint = "/messagebroker/amf";

        var remoteMethod:AbstractOperation = remoteService.getOperation(method);
        remoteMethod.arguments = args;

        var token:AsyncToken = remoteMethod.send();
        token.addResponder(new Responder(function (event:ResultEvent) {
            LoaderManager.hideLoading();
            if (resultResponse) {
                resultResponse(event);
            }
        }, function (event:FaultEvent) {
            LoaderManager.hideLoading();
            Alert.show(event.fault.rootCause.message, "错误");
        }));
    }

}
}
