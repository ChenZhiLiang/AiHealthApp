package com.app.aihealthapp.core.helper;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 * 二次封装 Gson
 */

public class GsonHelper {

    private static Gson gson = null;
    static {
        if (gson == null) {
            gson = new Gson();
        }
    }
    private GsonHelper() { }
    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 获取json int类型字段数据
     * @param gsonString json数据
     * @param name 字段名称
     */
    public static int GsonToInt(String gsonString,String name){
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(gsonString);
        return jsonObject.get(name).getAsInt();
    }

    /**
     * 获取json String类型字段数据
     * @param gsonString json数据
     * @param name 字段名称
     */
    public static String GsonToString(String gsonString,String name){
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(gsonString);
        return jsonObject.get(name).getAsString();
    }

    /**
     * @param gsonString 获取json boolean类型字段数据
     * @param name
     * @return
     */
    public static boolean GsonToBoolean(String gsonString,String name){
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(gsonString);
        return jsonObject.get(name).getAsBoolean();
    }

    /**
     * 获取data数据
     * @param gsonString
     * @return
     */
    public static JsonObject GsonToData(String gsonString,String name){
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(gsonString);
        return jsonObject.get(name).getAsJsonObject();
    }

    /**
     * 获取dataArray
     * @param gsonString
     * @return
     */
    public static JsonArray GsonToDataArray(String gsonString,String name){
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(gsonString);
        return jsonObject.get(name).getAsJsonArray();
    }


    public static List<String> GsonList(String json,String name){

        List<String> list = new ArrayList<>();
        JsonArray jsonarray = GsonToDataArray(json,name);
        for (int i = 0; i < jsonarray.size(); i++) {
            list.add(jsonarray.get(i).getAsString());
        }
        return  list;
    }
    /**
     * 转成list
     * @param json
     * @param t
     * @return
     */
    public static <T> List<T> GsonToList(String json, Class<T> t,String key) {
        List<T> list = new ArrayList<>();
        JsonArray jsonarray = GsonToDataArray(json,key);
        for (JsonElement element : jsonarray) {
            list.add(gson.fromJson(element, t));
        }
        return list;
    }
}
