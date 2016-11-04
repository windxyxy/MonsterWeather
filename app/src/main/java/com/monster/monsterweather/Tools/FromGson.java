package com.monster.monsterweather.Tools;

import com.google.gson.Gson;

/**
 * Created by ${MonsetrQiao} on 2016/11/3.
 * Describe:Gson解析Json数据封装方法
 */
public class FromGson {
    public FromGson() {
        super();
    }

    public static <T> T getTianqi(String jsonString,Class<T> cls){
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString,cls);
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }
}
