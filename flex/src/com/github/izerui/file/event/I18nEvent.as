package com.github.izerui.file.event
{
	import flash.events.Event;
	
	public class I18nEvent extends Event
	{
		
		public static const I18NRESOURCECHANGE:String = "I18nResourceChange";
		
		
		public function I18nEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}