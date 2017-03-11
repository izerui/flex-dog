package com.github.izerui.file.cairngorm.event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.github.izerui.file.cairngorm.controller.ZRController;
	
	public class RenameFolderClassEvent extends CairngormEvent
	{
		public var folderPath:String;
		public var folderNewName:String;

		public var responseFun:Function;
		
		public function RenameFolderClassEvent(folderPath:String, folderNewName:String, responseFun:Function)
		{
			super(ZRController.RENAME_FOLDER);
			this.folderPath = folderPath;
			this.folderNewName = folderNewName;

			this.responseFun = responseFun;
		}
	}
}