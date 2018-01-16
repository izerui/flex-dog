package com.github.izerui.file.vo;

import java.util.List;

public class Deploy {
    private String publicKey;
    private String privateKey;
    private List<Ent> entList;
    private List<Server> servers;

    public List<Ent> getEntList() {
        return entList;
    }

    public void setEntList(List<Ent> entList) {
        this.entList = entList;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }
}
