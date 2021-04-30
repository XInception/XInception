package org.sat.common.utils;

import com.google.gson.Gson;

import java.util.HashMap;

public class JsonUtils {
    static Gson gson=new Gson();
    public static <K,V> HashMap<K,V> toMap(String data){
       return gson.fromJson(data, HashMap.class);
    }

    public  static String toJson(Object obj){
        return gson.toJson(obj);
    }

}
