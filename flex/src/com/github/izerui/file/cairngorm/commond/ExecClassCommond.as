package com.github.izerui.file.cairngorm.commond {
import com.adobe.cairngorm.commands.ICommand;
import com.adobe.cairngorm.control.CairngormEvent;
import com.github.izerui.file.cairngorm.business.ExecClassDelegate;
import com.github.izerui.file.cairngorm.event.ExecClassEvent;
import com.github.izerui.file.components.loading.LoaderManager;

import mx.controls.Alert;
import mx.resources.ResourceManager;
import mx.rpc.IResponder;

public class ExecClassCommond implements IResponder, ICommand {

    private var delegate:ExecClassDelegate;

    private var responseFun:Function;

    public function ExecClassCommond() {
        delegate = new ExecClassDelegate(this);
    }

    public function result(data:Object):void {
        LoaderManager.hideLoading();
        if (responseFun) {
            responseFun.call(null,data.result);
        }
    }

    public function fault(info:Object):void {
        LoaderManager.hideLoading();
        Alert.show(info.fault.faultString, ResourceManager.getInstance().getString("jhaij", "error"));
    }

    public function execute(event:CairngormEvent):void {
        var myEvent:ExecClassEvent = ExecClassEvent(event);
        responseFun = myEvent.responseFun;
        delegate.exec(myEvent.filePath);
    }
}
}