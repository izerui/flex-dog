package com.github.izerui.file.vo;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private String server;
    private List<Service> services = new ArrayList<>();


    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public static class Service {
        private String file;
        private String type;

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
