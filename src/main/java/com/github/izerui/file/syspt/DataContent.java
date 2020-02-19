package com.github.izerui.file.syspt;

import lombok.Data;

@Data
public class DataContent {

    private String companyId = "";
    private String companyName = "";
    private String userId = "";
    private Long connectMethod = 3L;
    private String mechineId = "";
    private String source = "";
    private String target = "yj2025.com";
    private Long reqDuring = 0l;
    private Long contLen = 0l;
    private Long operateTime = 0l;
}
