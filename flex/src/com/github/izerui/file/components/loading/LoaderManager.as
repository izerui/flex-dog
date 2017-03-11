package com.github.izerui.file.components.loading
{
	import com.github.izerui.file.controls.Index;
	
	import mx.core.FlexGlobals;
	import mx.managers.PopUpManager;
	
	public class LoaderManager
	{
		
		private static var instance:ExtendsImage;
		
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
		
		public static function showLoading(modal:Boolean=true):void{
			var loading:ExtendsImage = getLoadingInstance();
			PopUpManager.addPopUp(loading,FlexGlobals.topLevelApplication as Index,modal);
			PopUpManager.centerPopUp(loading);
		}
		
		public static function hideLoading():void{
			PopUpManager.removePopUp(getLoadingInstance());
		}
	}
}