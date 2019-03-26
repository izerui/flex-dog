package com.github.izerui.file.event {
import flash.events.Event;

public class SelUserEvent extends Event {

    public var user:Object;

    public function SelUserEvent(type:String) {
        super(type, true, false);
    }
}
}