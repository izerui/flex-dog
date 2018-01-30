package com.github.izerui.file.vo;

import lombok.Data;

import java.util.List;

@Data
public class Deploy {
    private String publicKey;
    private String privateKey;
    private List<Ent> entList;
    private List<Server> servers;
    private List<Publisher> publishers;
}
