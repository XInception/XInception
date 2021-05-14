package org.xinc.common.utils;

public class StringUtils {
    static public Long parseNumber(String str){
        var sn= str.replaceAll(".*[^\\d](?=(\\d+))", "");
        if(str.equals(sn)){
            return 0L;
        }else {
            return  Long.valueOf(sn);
        }

    }

    static public String parseStr(String str){
        var sn= str.replaceAll(".*[^\\d](?=(\\d+))", "");
        if(str.equals(sn)){
            return sn;
        }else {
            return str.replaceAll(sn, "");
        }
    }

    public static Integer parsePadding(String str) {
        var sn= str.replaceAll(".*[^\\d](?=(\\d+))", "");
        return  sn.length();
    }
}
