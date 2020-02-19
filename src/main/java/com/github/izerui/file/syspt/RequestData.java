package com.github.izerui.file.syspt;

import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

@Data
public class RequestData {

    private final static String formatStr = "yyyyMMddHHmmss";

    private String userId;
    private String beginTime;
    private String endTime;

    public Date getBegin(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(beginTime);
        return dateTime.toDate();
    }

    public Date getEnd(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(endTime);
        return dateTime.toDate();
    }
}
