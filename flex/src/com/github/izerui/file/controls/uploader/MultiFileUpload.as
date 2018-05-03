///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	Multi-File Upload Component Ver 1.1
//
//  Copyright (C) 2006 Ryan Favro and New Media Team Inc.
//  This program is free software; you can redistribute it and/or
//  modify it under the terms of the GNU General Public License
//  as published by the Free Software Foundation; either version 2
//	of the License, or (at your option) any later version.
//	
//	This program is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//	GNU General Public License for more details.
//	
//	You should have received a copy of the GNU General Public License
//	along with this program; if not, write to the Free Software
//	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//
//	Any questions about this component can be directed to it's author Ryan Favro at ryanfavro@hotmail.com
//
//  To use this component create a new instance of this component and give it ten parameters
//
//	EXAMPLE:
//
//	multiFileUpload = new MultiFileUpload(
//	      filesDG,   		// <-- DataGrid component to show the cue'd files
//	      browseBTN, 		// <-- Button componenent to be the browser button
//	      clearButton, 		// <-- Button component to be the button that removes all files from the cue
//	      delButton, 		// < -- Button component to be the button that removes a single selected file from the cue
//	      upload_btn, 		// <-- Button component to be the  button that triggers the actual file upload action
//	      progressbar, 		// <-- ProgressBar Component that will show the file upload progress in bytes
//	      "http://[Your Server Here]/MultiFileUpload/upload.cfm", // <-- String Type the url to the server side upload component can be a full domain or relative
//	      postVariables,  	// < -- URLVariables type that will contain addition variables to accompany the upload
//	      350000, 			//< -- Number type to set the max file size for uploaded files in bytes. A value of 0 (zero) = no file limit
//	      filesToFilter 	// < -- Array containing FileFilters an empty Array will allow all file types
//	       );
//
//
//
//	Enjoy!
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


package com.github.izerui.file.controls.uploader {

// Imported Class Definitions
import com.github.izerui.file.event.FileUploadEvent;
import com.github.izerui.file.event.ServerCheckEvent;
import com.github.izerui.file.utils.RemoteObjectUtils;

import flash.events.*;
import flash.net.FileReference;
import flash.net.FileReferenceList;
import flash.net.URLRequest;
import flash.net.URLRequestMethod;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.controls.Button;
import mx.controls.DataGrid;
import mx.controls.ProgressBar;
import mx.controls.dataGridClasses.*;
import mx.core.ClassFactory;
import mx.events.CollectionEvent;
import mx.rpc.events.ResultEvent;

public class MultiFileUpload extends EventDispatcher {



    //UI Vars
    private var _datagrid:DataGrid;
    private var _browsebutton:Button;
    private var _remselbutton:Button;
    private var _remallbutton:Button;
    private var _uploadbutton:Button;
    private var _progressbar:ProgressBar;
    private var _testButton:Button;

    //DataGrid Columns
    private var _nameColumn:DataGridColumn;
    private var _typeColumn:DataGridColumn;
    private var _sizeColumn:DataGridColumn;
    private var _columns:Array;

    //File Reference Vars
    [Bindable]
    private var _files:ArrayCollection;
    private var _fileref:FileReferenceList
    private var _file:FileReference;
    private var _uploadURL:URLRequest;
    private var _totalbytes:Number;

    //File Filter vars
    private var _filefilter:Array;

    //config vars
    private var _url:String = ""; // location of the file upload handler can be a relative path or FQDM

    //Constructor
//        private var continueUploadStatus:Boolean = true;

    public function MultiFileUpload(dataGrid:DataGrid,
                                    browseButton:Button,
                                    removeAllButton:Button,
                                    removeSelectedButton:Button,
                                    uploadButton:Button,
                                    progressBar:ProgressBar,
                                    filter:Array) {
        _datagrid = dataGrid;
        _browsebutton = browseButton;
        _remallbutton = removeAllButton;
        _remselbutton = removeSelectedButton;
        _uploadbutton = uploadButton;
        _progressbar = progressBar;
        _filefilter = filter;
        init();
    }

    //Initialize  Component
    private function init():void {

        // Setup File Array Collection and FileReference
        _files = new ArrayCollection();
        _fileref = new FileReferenceList;
        _file = new FileReference;

        // Set Up Total Byes Var
        _totalbytes = 0;

        // Add Event Listeners to UI
        _browsebutton.addEventListener(MouseEvent.CLICK, browseFiles);
        _uploadbutton.addEventListener(MouseEvent.CLICK, uploadFiles);
        _remallbutton.addEventListener(MouseEvent.CLICK, clearFileCue);
        _remselbutton.addEventListener(MouseEvent.CLICK, removeSelectedFileFromCue);
        _fileref.addEventListener(Event.SELECT, selectHandler);
        _files.addEventListener(CollectionEvent.COLLECTION_CHANGE, popDataGrid);

        // Set Up Progress Bar UI
        _progressbar.mode = "manual";
        _progressbar.label = "";
        _progressbar.height = 5;

        // Set Up UI Buttons;
        _uploadbutton.enabled = false;
        _remselbutton.enabled = false;
        _remallbutton.enabled = false;


        // Set Up DataGrid UI
        _nameColumn = new DataGridColumn;
        _typeColumn = new DataGridColumn;
        _sizeColumn = new DataGridColumn;

        _nameColumn.dataField = "name";
        _nameColumn.headerText = "文件名";

        _nameColumn.itemRenderer = new ClassFactory(FileNameRenderer)

        _typeColumn.dataField = "servers";
        _typeColumn.headerText = "服务器";
        _typeColumn.width = 200;

        _sizeColumn.dataField = "size";
        _sizeColumn.headerText = "文件大小(KB)";
        _sizeColumn.labelFunction = bytesToKilobytes as Function;
        _sizeColumn.width = 90;

        _columns = new Array(_nameColumn, _sizeColumn, _typeColumn);
        _datagrid.columns = _columns
        _datagrid.sortableColumns = false;
        _datagrid.dataProvider = _files;

        _datagrid.addEventListener("serverChecked", datagrid_serverCheckedHandler);
//        _datagrid.dragEnabled = true;
//        _datagrid.dragMoveEnabled = true;
//        _datagrid.dropEnabled = true;

        // Set Up URLRequest
        _uploadURL = new URLRequest;
        _uploadURL.url = _url;

        _uploadURL.method = URLRequestMethod.POST;  // this can also be set to "GET" depending on your needs
        _uploadURL.contentType = "multipart/form-data";


    }

    /********************************************************
     *   PRIVATE METHODS                                     *
     ********************************************************/


    //Browse for files
    private function browseFiles(event:Event):void {

        _fileref.browse(_filefilter);

    }

    //Upload File Cue
    private function uploadFiles(event:Event):void {

        for each(var obj in _files) {
            if (obj.errMsg) {
                Alert.show("请移除异常文件,再开始上传.", "错误");
                return;
            }
        }

        if (_files.length > 0) {
            _file = FileReference(_files.getItemAt(0)["file"]);
            _file.addEventListener(Event.OPEN, openHandler);
            _file.addEventListener(ProgressEvent.PROGRESS, progressHandler);
//                _file.addEventListener(Event.COMPLETE, completeHandler);
            _file.addEventListener(DataEvent.UPLOAD_COMPLETE_DATA, successHandler);
            _file.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
//                _file.addEventListener(HTTPStatusEvent.HTTP_STATUS,httpStatusHandler);
            _file.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);

            _file.upload(_uploadURL, "Filedata");
            setupCancelButton(true);
        }
    }

    //Remove Selected File From Cue
    private function removeSelectedFileFromCue(event:Event):void {

        if (_datagrid.selectedIndex >= 0) {
            _files.removeItemAt(_datagrid.selectedIndex);
        }
    }


    //Remove all files from the upload cue;
    private function clearFileCue(event:Event):void {

        _files.removeAll();
    }

    // Cancel Current File Upload
    private function cancelFileIO(event:Event):void {

        _file.cancel();
        setupCancelButton(false);
        checkCue();

    }


    //label function for the datagird File Size Column
    private function bytesToKilobytes(data:Object, blank:Object):String {
        var kilobytes:String;
        kilobytes = String(Math.round(data.size / 1024));
        return kilobytes
    }



    // restores progress bar back to normal
    private function resetProgressBar():void {

        _progressbar.label = "";
        _progressbar.maximum = 0;
        _progressbar.minimum = 0;
    }

    // reset form item elements
    private function resetForm():void {
        _uploadbutton.enabled = false;
        _uploadbutton.addEventListener(MouseEvent.CLICK, uploadFiles);
        _uploadbutton.label = "传送";
        _progressbar.maximum = 0;
        _totalbytes = 0;
        _progressbar.label = "";
        _remselbutton.enabled = false;
        _remallbutton.enabled = false;
        _browsebutton.enabled = true;
    }

    // whenever the _files arraycollection changes this function is called to make sure the datagrid data jives
    private function popDataGrid(event:CollectionEvent):void {
        checkCue();
    }

    // enable or disable upload and remove controls based on files in the cue;
    private function checkCue():void {
        if (_files.length > 0) {
            _uploadbutton.enabled = true;
            _remselbutton.enabled = true;
            _remallbutton.enabled = true;
        } else {
            resetProgressBar();
            _uploadbutton.enabled = false;
        }
    }

    // toggle upload button label and function to trigger file uploading or upload cancelling
    private function setupCancelButton(x:Boolean):void {
        if (x == true) {
//                _uploadbutton.label = "取消";
            _uploadbutton.enabled = false;
            _browsebutton.enabled = false;
            _remselbutton.enabled = false;
            _remallbutton.enabled = false;
//                _uploadbutton.addEventListener(MouseEvent.CLICK,cancelFileIO);        
        } else if (x == false) {
//                _uploadbutton.removeEventListener(MouseEvent.CLICK,cancelFileIO);
            resetForm();
        }
    }


    /*********************************************************
     *  File IO Event Handlers                                *
     *********************************************************/

    //  选择文件回调
    private function selectHandler(event:Event):void {
        for each(var currentFile in event.currentTarget.fileList) {
            RemoteObjectUtils.execute("fileService", "getServers", function (ev:ResultEvent):void {

                var fileItem:Object = {
                    "name": currentFile.name,
                    "size": currentFile.size,
                    "servers": ev.result as ArrayCollection,
                    "file": currentFile,
                    "errMsg": ""
                };

                if (!(ev.result && ev.result.length > 0)) {
                    fileItem.errMsg = "不支持发布.";
                }
                _files.addItem(fileItem);
            }, currentFile.name);
        }
    }

    // called after the file is opened before upload
    private function openHandler(event:Event):void {
        trace('openHandler triggered');
        _files;
    }

    // called during the file upload of each file being uploaded | we use this to feed the progress bar its data
    private function progressHandler(event:ProgressEvent):void {
        _progressbar.setProgress(event.bytesLoaded, event.bytesTotal);
//        if (event.bytesLoaded == event.bytesTotal) {
//            _progressbar.label = "文件保存中...";
//        } else {
//            _progressbar.label = "已传送 " + Math.round(event.bytesTotal / 1024) + " kb 的 " + Math.round(event.bytesLoaded / 1024) + " kb " + (_files.length - 1) + " 个文件剩余";
//        }
    }


    // called after a file has been successully uploaded | we use this as well to check if there are any files left to upload and how to handle it
    private function successHandler(event:DataEvent):void {
        if (event && event.data) { //服务器必须返回一个输出信息,否则不会继续下一个,出错可以不返回信息
            var result:Object = JSON.parse(event.data);
            if (result.success) {
                var uploadedItem:Object = _files.removeItemAt(0);
                dispatchEvent(new FileUploadEvent(FileUploadEvent.UPLOAD_SINGLE_FILE_COMPLETE));
                if (_files.length > 0) {
                    _totalbytes = 0;
                    uploadFiles(null);//继续上传下一个文件
                } else { // 全部上传完毕触发完成事件
                    setupCancelButton(false);
//                    _progressbar.label = "传送完毕";
                    var uploadCompleted:Event = new Event(Event.COMPLETE);
                    dispatchEvent(uploadCompleted);
                }
            } else {
                Alert.show(result.data.message, "失败");
                setupCancelButton(true);
            }
        }

    }

    // only called if there is an  error detected by flash player browsing or uploading a file
    private function ioErrorHandler(event:IOErrorEvent):void {
        //trace('And IO Error has occured:' +  event);
        Alert.show("文件传输错误", "错误");
//			mx.controls.Alert.show(String(event),"ioError",0);
    }

    // only called if a security error detected by flash player such as a sandbox violation
    private function securityErrorHandler(event:SecurityErrorEvent):void {
        //trace("securityErrorHandler: " + event);
        Alert.show(String(event), "安全限制", 0);
    }

    //  This function its not required
    private function cancelHandler(event:Event):void {
        // cancel button has been clicked;
        trace('cancelled');
    }

    public function cancelUploadHandler():void {
        try {
            _file.cancel();
        } catch (error:Error) {
            trace("cancelled");
        }
    }

    //  after a file upload is complete or attemted the server will return an http status code, code 200 means all is good anything else is bad.
    private function httpStatusHandler(event:HTTPStatusEvent):void {
        //        trace("httpStatusHandler: " + event);
        if (event.status != 200) {
            Alert.show(String(event), "Error", 0);
        }
    }


    private function datagrid_serverCheckedHandler(event:ServerCheckEvent):void {
        var dataList:ArrayCollection = _datagrid.dataProvider as ArrayCollection;
        for each(var obj in dataList) {
            if (obj.id == event.selItem.id) {
                dataList.itemUpdated(obj, null, null, event.selItem)
            }
        }

    }
}
}