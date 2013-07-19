package com.izerui.components.searchInput
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.controls.Button;
	import mx.controls.TextInput;
	import mx.events.FlexEvent;
	
	public class SearchTextInput extends TextInput
	{
		[Bindable]
		[Embed(source="assets/img/close.png")]
		private var imgSource:Class;
		
		private var clearButton:Button;
		
		public function SearchTextInput()
		{
			//TODO: implement function
			addEventListener(Event.CHANGE,textChangeFun);
			super();
		}
		
		protected function textChangeFun(event:Event):void
		{
			// TODO Auto-generated method stub
			if(this.text&&this.getChildByName("closeButton")==null){
				if(!clearButton){
					clearButton = new Button;
					clearButton.name = "closeButton";
					clearButton.setStyle("icon",imgSource);
					clearButton.height = 16;  
					clearButton.width = 16;  
					clearButton.useHandCursor = true;  
					clearButton.addEventListener(MouseEvent.CLICK,clearButtonClick);
				}
				this.addChild(clearButton);
				clearButton.move(this.width - clearButton.width - (this.height - clearButton.height)/2,(this.height - clearButton.height) / 2);
			}else if(!this.text&&this.getChildByName("closeButton")){
				this.removeChild(clearButton);
			}
		}
		
		protected function clearButtonClick(event:MouseEvent):void
		{
			reset();
			dispatchEvent(new Event(Event.CHANGE));
		}
		
		public function reset():void{
			text = null;
			if(this.getChildByName("closeButton")){
				this.removeChild(clearButton);
			}
		}
		
		
	}
}