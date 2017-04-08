package com.github.izerui.file.service.impl;

import com.github.izerui.file.entity.DeployEntity;
import com.github.izerui.file.repository.DeployRepository;
import com.github.izerui.file.service.FileService;
import com.github.izerui.file.utils.ExtendFilenameUtils;
import com.github.izerui.file.vo.FileItem;
import com.github.izerui.file.vo.FileTree;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

@RemotingDestination
@Service("fileService")
@ConfigurationProperties
@Transactional
public class FileServiceImpl implements FileService {

	private static Logger log = Logger.getLogger(FileServiceImpl.class);


	private static String rootPath;

	private Map<String,String> servers;

	public Map<String, String> getServers() {
		return servers;
	}

	public void setServers(Map<String, String> servers) {
		this.servers = servers;
	}

	static {
		rootPath = System.getProperty("user.dir")+File.separator;
	}


	@Autowired
	private DeployRepository deployRepository;

//	public String fomatRootFilePath(){
//		if(!filePath.endsWith(File.separator)){
//			filePath += File.separator;
//		}
//		filePath = FilenameUtils.getFullPath(filePath);
//		filePath = FilenameUtils.normalize(filePath);
//		return filePath;
//	}

	public List<FileTree> getFolderList() {
		FileTree root = new FileTree();
		root.setFolderpath(rootPath);
		root.setFoldername(ExtendFilenameUtils.getLastFolderPathNoEndSeparator(rootPath));
		root.setChildren(getChildrenFolderPaths(root));
		
		List<FileTree> filePathTree = new ArrayList<FileTree>();
		filePathTree.add(root);
		return filePathTree;
	}
	
	private List<FileTree> getChildrenFolderPaths(FileTree parent){
		List<FileTree> children = new ArrayList<FileTree>();
		File[] files = new File(parent.getFolderpath()).listFiles();//列出子文件
		if(files!=null){
			for (File file : files) {
				if(file.isDirectory()){//如果子文件是文件夹
					String path = file.getPath()+File.separator;
					FileTree fileTree = new FileTree();
					fileTree.setFoldername(ExtendFilenameUtils.getLastFolderPathNoEndSeparator(path));
					fileTree.setFolderpath(path);
					List<FileTree> childrenList = getChildrenFolderPaths(fileTree);
					if(childrenList!=null&&childrenList.size()>0){
						fileTree.setChildren(getChildrenFolderPaths(fileTree));
					}
					children.add(fileTree);
				}
			}
			return children;
		}
		return null;
	}

	public List<FileItem> listFilesByFolder(String folderPath) {
		List<FileItem> files = new ArrayList<FileItem>();
		File folderFile = new File(folderPath);
		File[] filesArray = folderFile.listFiles();
		Arrays.sort(filesArray);
		for (File file : filesArray) {
			FileItem fi = new FileItem();
			fi.setLashmodifydate(new Date(file.lastModified()));
			if(file.isDirectory()){
				fi.setIsfolder(true);
				fi.setSize(FileUtils.sizeOfDirectory(file));
				fi.setFilename(ExtendFilenameUtils.getLastFolderPathNoEndSeparator(file.getPath()+File.separator));
				fi.setFolderpath(ExtendFilenameUtils.getFullPath(file.getPath()+File.separator));
			}else{
				fi.setIsfolder(false);
				fi.setFolderpath(ExtendFilenameUtils.getFullPath(file.getPath()));
				fi.setBasename(ExtendFilenameUtils.getBaseName(file.getName()));
				fi.setExtension(ExtendFilenameUtils.getExtension(file.getName()));
				fi.setIshidden(file.isHidden());
				fi.setFilename(file.getName());
				fi.setSize(file.length());

				DeployEntity one = deployRepository.findOne(fi.getFilename());
				if(one!=null){
					fi.setDeployTime(one.getDeployTime());
				}


			}
			files.add(fi);
		}
		return files;
	}

	public String optFolder(String path,boolean isCreate) throws RuntimeException {
		path = FilenameUtils.normalize(path);
		// TODO Auto-generated method stub
		if(isCreate&&new File(path).mkdir()){//新建
			return path+File.separator;
		}else{
			if(new File(path).list().length>0){
				throw new RuntimeException("Folder is not empty");
			}
			new File(path).delete();
		}
		return null;
	}

	public String renameFolder(String path, String FolderNewName) {
		// TODO Auto-generated method stub
		path = FilenameUtils.normalize(path);
		String pathNoEndSeparator = FilenameUtils.getFullPathNoEndSeparator(path);
		String newPath = pathNoEndSeparator.substring(0, pathNoEndSeparator.lastIndexOf(File.separator)+1)+FolderNewName+File.separator;
		if(new File(path).renameTo(new File(newPath))){
			return newPath;
		}
		return null;
		
	}
	
	
	public String interceptPath(String path) throws RuntimeException {
		path = FilenameUtils.normalize(path);
		if(path.equals(rootPath)){
			throw new RuntimeException("already is the root path"); 
		}
		String tempPath = FilenameUtils.getFullPathNoEndSeparator(path);
		return FilenameUtils.getFullPath(tempPath);
	}
	
	
	
	public void deleteFile(List<FileItem> fileItems) {
		// TODO Auto-generated method stub
			for (FileItem fileItem : fileItems) {
				try {
					if(fileItem.isIsfolder()){
						File fileFolder = new File(fileItem.getFilename());
						if(fileFolder.list().length==0){
							fileFolder.delete();
						}
						//以防万一, 不开放该方法
	//					FileUtils.deleteQuietly(new File(fileItem.getFolderpath()));//删除文件夹 及其下属所有文件/文件夹
					}else{
						new File(fileItem.getFolderpath()+fileItem.getFilename()).delete();
					}
				} catch (Exception e) {
					log.error("Folder is not empty");
//					throw new RuntimeException("Folder is not empty");
				}
			}
	}


	@Override
	public String exec(String fileName) {

	    String output = "";
		boolean deployed = false;
		try {
			String fileBaseName = FilenameUtils.getBaseName(fileName);
			String ext = FilenameUtils.getExtension(fileName);
			fileBaseName = fileBaseName.replace("-exec","");
			Iterator<String> iterator = servers.keySet().iterator();
			while (iterator.hasNext()){
				String server = iterator.next();
				String services = servers.get(server);
				if(services!=null&&!services.equals("")){
					String[] split = services.split(",");
					for (String service : split) {
						if(service!=null&&service.equals(fileBaseName)){

                            String chmodCommand = "chmod 777 /etc/ansible/application-operation.sh";
                            Process chmodProcess = Runtime.getRuntime().exec(chmodCommand);
                            chmodProcess.waitFor();


							String command = "/bin/sh /etc/ansible/application-operation.sh "+ext+" "+server+" "+service;
							Process execProcess = Runtime.getRuntime().exec(command);
							execProcess.waitFor();

                            output += IOUtils.toString(execProcess.getInputStream(),"UTF-8") +"\n";
                            log.info(output);

							deployed = true;
						}

					}

				}

			}

			if(!deployed){
				throw new RuntimeException("不支持该文件");
			}

			//保存发布记录
			DeployEntity one = deployRepository.findOne(fileName);
			if(one==null){
				one = new DeployEntity();
				one.setFileName(fileName);
			}
			one.setDeployTime(new Date());
			deployRepository.save(one);

			return output;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
}
