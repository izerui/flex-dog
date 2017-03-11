package com.github.izerui.file.utils
{
	import flash.display.Sprite;
	
	import mx.resources.ResourceManager;

	public class AlertShow
	{
		import mx.controls.Alert;
		import mx.events.CloseEvent;
		import flash.display.Sprite;
		
		public static function operateConfirm(msg:String,msgTitle:String,dispObj:Object,callFunctionForOK:Function):void{
			
			Alert.okLabel=ResourceManager.getInstance().getString("jhaij","confirm");
			Alert.cancelLabel=ResourceManager.getInstance().getString("jhaij","cancel");
			Alert.show(msg,msgTitle,Alert.OK|Alert.CANCEL,dispObj as Sprite,myClick,null,Alert.CANCEL);  
			function myClick(evt:CloseEvent):void{   
				if(evt.detail==Alert.OK){   //点了确认按钮
					if (callFunctionForOK){
						callFunctionForOK.call();
					}
				}   
			}
		}
		
	}
}