package com.github.izerui.file.service;

import com.github.izerui.file.entity.DeployEntity;
import com.github.izerui.file.entity.FileEntity;
import com.github.izerui.file.repository.DeployRepository;
import com.github.izerui.file.repository.FileRepository;
import com.github.izerui.file.utils.ConfigUtils;
import com.github.izerui.file.vo.Deploy;
import com.github.izerui.file.vo.Server;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RemotingDestination
@Service("fileService")
@ConfigurationProperties
@Transactional
public class FileService {

    private static Logger log = Logger.getLogger(FileService.class);


    @Autowired
    private DeployRepository deployRepository;
    @Autowired
    private FileRepository fileRepository;

    public void init() throws Exception {
        fileRepository.deleteAll();
        Deploy config = ConfigUtils.getDeployConfig();
        config.getServers().forEach(server -> {
            server.getServices().forEach(service -> {
                FileEntity fileEntity = new FileEntity();
                fileEntity.setId(UUID.randomUUID().toString());
                fileEntity.setFileName(service.getFile());
                fileEntity.setServer(server.getServer());
                fileEntity.setDeployType(service.getType());
//                fileEntity.setUploadTime(new Date());
//                fileEntity.setDeployTime(new Date());
//                fileEntity.setSize(0L);
//                fileEntity.setUploadTimeStr("");
//                fileEntity.setDeployTimeStr("");
//                fileEntity.setFileBytes(null);
                fileRepository.save(fileEntity);

            });
        });
    }


    public void addService(FileEntity fileEntity) {
        fileEntity.setId(UUID.randomUUID().toString());
        fileRepository.save(fileEntity);
    }

    public List<FileEntity> listFiles(String server) {
        if (StringUtils.isEmpty(server) || server.equals("全部")) {
            return fileRepository.findAll();
        } else {
            return fileRepository.findByServer(server);
        }
    }


    public String exec(String fileName) throws Exception {
        String output = "";
        boolean deployed = false;
        List<Server> servers = ConfigUtils.getDeployConfig().getServers();
        for (Server server : servers) {
            for (Server.Service service : server.getServices()) {
                if (service != null && service.getFile().equals(fileName)) {

                    String chmodCommand = "chmod 777 /etc/ansible/application-operation.sh";
                    Process chmodProcess = Runtime.getRuntime().exec(chmodCommand);
                    chmodProcess.waitFor();


                    String command = "/bin/sh /etc/ansible/application-operation.sh " + service.getType() + " " + server.getServer() + " " + service.getFile();
                    Process execProcess = Runtime.getRuntime().exec(command);
                    execProcess.waitFor();

                    output += IOUtils.toString(execProcess.getInputStream(), "UTF-8") + "\n";
                    log.info(output);

                    deployed = true;
                }
            }
        }

        if (!deployed) {
            throw new RuntimeException("不支持该文件");
        }

        //保存发布记录
        DeployEntity one = deployRepository.findOne(fileName);
        if (one == null) {
            one = new DeployEntity();
            one.setFileName(fileName);
        }
        one.setDeployTime(new Date());
        deployRepository.save(one);

        return output;
    }

}
