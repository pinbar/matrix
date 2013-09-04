package com.percipient.matrix.util.Logging;

import java.util.List;

public class LoggingUtil {
    public static  String formattedInputArgs(List<String> args){
        StringBuffer buf = new StringBuffer();
        for(Object s : args){
            buf.append(s);
            buf.append(" ");  
        }
       return  buf.toString();
    }

}
