package com.github.izerui.file.syspt;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Response4App {

    @NonNull
    private String respCode;

    @NonNull
    private String respMsg;

    @NonNull
    private CryptoData data;
}
