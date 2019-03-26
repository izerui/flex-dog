package com.github.izerui.file.components {
import flash.events.Event;

public class SearchBoxEvent extends Event {
    public static const ITEM_SELECTED:String = "itemSelected";

    public var item:Object;

    public function SearchBoxEvent(type:String, item:Object, bubbles:Boolean = true, cancelable:Boolean = false) {
        this.item = item;
        super(type, bubbles, cancelable);
    }
}
}
