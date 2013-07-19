package com.izerui.components.loading
{
	import flash.net.URLRequest;
	
	import mx.controls.Image;
	import mx.events.FlexEvent;
	
	import org.bytearray.gif.player.GIFPlayer;

	public class ExtendsImage extends Image
	{
		public function ExtendsImage()
		{
			this.width = 128;
			this.height = 16;
			this.addEventListener(FlexEvent.CREATION_COMPLETE,imgCreateFun);
		}
		
		
		protected function imgCreateFun(event:FlexEvent):void
		{
			// we create the GIFPlayer, it plays automatically by default
			var myGIFPlayer:GIFPlayer = new GIFPlayer();
			// we show it
			addChild ( myGIFPlayer );
			// you can also load any valid GIF stream (ByteArray) with the loadBytes method (version 0.2)
			myGIFPlayer.load(new URLRequest("assets/img/loading.gif"));
		}
	}
}