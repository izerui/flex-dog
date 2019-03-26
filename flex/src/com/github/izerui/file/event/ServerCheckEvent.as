package com.github.izerui.file.event {
import flash.events.Event;

public class ServerCheckEvent extends Event {
    public var selItem:Object;

    public function ServerCheckEvent(type:String, selItem:Object) {
        super(type, true, false);
        this.selItem = selItem;
    }
}
}