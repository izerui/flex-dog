package com.github.izerui.file.event {
import com.github.izerui.file.vo.FileItem;

import flash.events.Event;

public class DeployEvent extends Event {

    public var item:FileItem;

    public function DeployEvent(type:String, bubbles:Boolean = false, cancelable:Boolean = false) {
        super(type, bubbles, cancelable);
    }
}
}