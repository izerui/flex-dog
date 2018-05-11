package com.github.izerui.file.components.loading
{
	import com.github.izerui.file.controls.Index;

import flash.events.TimerEvent;

import flash.utils.Timer;

import mx.core.FlexGlobals;
	import mx.managers.PopUpManager;
	
	public class LoaderManager
	{
		
		private static var instance:ExtendsImage;

		private static var timer:Timer;
		
		private var loadingImg:ExtendsImage = new ExtendsImage();
		
		public function LoaderManager()
		{
		
		}
		
		private static function getLoadingInstance():ExtendsImage{
			if(instance==null){
				instance = new ExtendsImage();
			}
			return instance;
		}
		
		public static function showLoading(modal:Boolean=false):void{
			if(timer){
                timer.stop();
			}else{
                timer = new Timer(2000, 1);
			}
            timer.addEventListener(TimerEvent.TIMER, function deferredMethod(event:TimerEvent):void {
                var loading:ExtendsImage = getLoadingInstance();
                PopUpManager.addPopUp(loading,FlexGlobals.topLevelApplication as Index,modal);
                PopUpManager.centerPopUp(loading);
            });
			timer.start();
		}


		public static function hideLoading():void{
            var removeIns:ExtendsImage = getLoadingInstance();
            if(removeIns){
                PopUpManager.removePopUp(getLoadingInstance());
            }
            if(timer){
                timer.stop();
			}
		}
	}
}