package com.github.izerui.file.event {
import flash.events.Event;

public class TextChangeEvent extends Event {

    public var text:String;

    public function TextChangeEvent(type:String) {
        super(type, true, false);
    }
}
}