package com.github.izerui.file.cairngorm.controller {
import com.adobe.cairngorm.control.FrontController;
import com.github.izerui.file.cairngorm.commond.DeleteFileClassCommond;
import com.github.izerui.file.cairngorm.commond.ExecClassCommond;
import com.github.izerui.file.cairngorm.commond.ListFilesByFolderClassCommond;

public class ZRController extends FrontController {
    public static const LISTFILES_BY_FOLDER:String = "LISTFILES_BY_FOLDER";
    public static const DELETE_SELECTED_FILES:String = "DELETE_SELECTED_FILES";
    public static const EXEC:String = "EXEC";


    public function ZRController() {
        this.initialize();
    }

    public function initialize():void {
        this.addCommand(LISTFILES_BY_FOLDER, ListFilesByFolderClassCommond);
        this.addCommand(DELETE_SELECTED_FILES, DeleteFileClassCommond);
        this.addCommand(EXEC, ExecClassCommond);
    }

}
}