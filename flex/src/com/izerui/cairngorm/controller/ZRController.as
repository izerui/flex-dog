package com.izerui.cairngorm.controller
{
	import com.adobe.cairngorm.control.FrontController;
	import com.izerui.cairngorm.commond.DeleteFileClassCommond;
	import com.izerui.cairngorm.commond.FomatRootFilePathClassCommond;
	import com.izerui.cairngorm.commond.GetFolderListClassCommond;
	import com.izerui.cairngorm.commond.InterceptPathClassCommond;
	import com.izerui.cairngorm.commond.ListFilesByFolderClassCommond;
	import com.izerui.cairngorm.commond.OptFolderClassCommond;
	import com.izerui.cairngorm.commond.RenameFolderClassCommond;

	public class ZRController extends FrontController
	{
		public static const GET_FOLDER_LIST:String = "GET_FOLDER_LIST";
		public static const CREATE_DELETE_FOLDER:String = "CREATE_DELETE_FOLDER";
		public static const LISTFILES_BY_FOLDER:String = "LISTFILES_BY_FOLDER";
		public static const RENAME_FOLDER:String = "RENAME_FOLDER";
		public static const INTERCEPT_PATH:String = "INTERCEPT_PATH";
		public static const FOMAT_ROOT_FILEPATH:String = "FOMAT_ROOT_FILEPATH";
		public static const DELETE_SELECTED_FILES:String = "DELETE_SELECTED_FILES";
		
		
		
		public function ZRController()
		{
			this.initialize();
		}
		
		public function initialize():void{
			this.addCommand(GET_FOLDER_LIST,GetFolderListClassCommond);
			this.addCommand(CREATE_DELETE_FOLDER,OptFolderClassCommond);
			this.addCommand(LISTFILES_BY_FOLDER,ListFilesByFolderClassCommond);
			this.addCommand(RENAME_FOLDER,RenameFolderClassCommond);
			this.addCommand(INTERCEPT_PATH,InterceptPathClassCommond);
			this.addCommand(FOMAT_ROOT_FILEPATH,FomatRootFilePathClassCommond);
			this.addCommand(DELETE_SELECTED_FILES,DeleteFileClassCommond);
		}
		
	}
}