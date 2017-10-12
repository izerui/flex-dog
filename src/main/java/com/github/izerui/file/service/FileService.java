package com.github.izerui.file.service;

import com.github.izerui.file.vo.FileItem;
import com.github.izerui.file.vo.Server;

import java.util.List;

public interface FileService {

    public List<FileItem> listFiles() throws Exception;

    public String exec(String fileName) throws Exception;

    public List<Server> getServerList() throws Exception;
}
