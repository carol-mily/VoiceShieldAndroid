package com.example.util;

import android.content.ContentValues;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSONUtil {

    private static final String TAG =  com.example.util.JSONUtil.class.getSimpleName();

    private JSONUtil(){}

    private static Gson gson = new Gson();

    /**
     * 传入一个头部，获取头部管控中的所有String信息
     * @return
     */
    public static String getHeadContext(String jsonData, String head) {
        String jsonObjectString = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonObjectString = jsonObject.get(head).toString();
            // LogUtil.d(TAG, "getHeadContext 只去头部header的数据信息：" + jsonObjectString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObjectString;
    }

    /**
     * 将一个对象转换成一个Json字符串
     * @param t
     * @return
     */
    public static <T> String objectToJson(T t){
        if (t instanceof String) {
            return t.toString();
        } else {
            return gson.toJson(t);
        }
    }

    /**
     * 将Json字符串转换成对应对象
     * @param jsonString    Json字符串
     * @param clazz        对应字节码文件.class
     * @return
     */
    @SuppressWarnings("unchecked")
    public static<T> T jsonToObject(String jsonString, Class<T> clazz){
        if (clazz == String.class) {
            return (T) jsonString;
        } else {
            return (T)gson.fromJson(jsonString, clazz);
        }
    }
    /**
          *
          * 函数名称: parseData
          * 函数描述: 将json字符串转换为map
          * @param data
          * @return
          */
    private static Map<String, String> parseData(String data){
            GsonBuilder gb = new GsonBuilder();
            Gson g = gb.create();
            Map<String, String> map = g.fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
            return map;
    }

    /**
     * 将List集合转换为json字符串
     * @param list    List集合
     * @return
     */
    public static<T> String listToJson(List<T> list){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        try {
            for (int i = 0; i < list.size(); i++) {
                jsonObject = new JSONObject(objectToJson(list.get(i)));
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (jsonObject != null) {
                jsonObject = null;
            }
        }
        return jsonArray.toString();
    }

    /**
     * 将数组转换成json字符串
     * @param array        数组
     * @return
     */
    public static<T> String arrayToJson(T[] array){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        try {
            for (int i = 0; i < array.length; i++) {
                jsonObject = new JSONObject(objectToJson(array[i]));
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (jsonObject != null) {
                jsonObject = null;
            }
        }
        return jsonArray.toString();
    }

    /**
     * 获取json字符串中的值
     * @param json    json字符串
     * @param key    键值
     * @param clazz    所取数据类型，例如：Integer.class，String.class，Double.class，JSONObject.class
     * @return  存在则返回正确值，不存在返回null
     */
    public static<T> T getJsonObjectValue(String json, String key, Class<T> clazz){
        try {
            return getJsonObjectValue(new JSONObject(json), key, clazz);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取jsonObject对象中的值
     * @param jsonObject    jsonObject对象
     * @param key    键值
     * @param clazz    所取数据类型，例如：Integer.class，String.class，Double.class，JSONObject.class
     * @return  存在则返回正确值，不存在返回null
     */
    @SuppressWarnings("unchecked")
    public static<T> T getJsonObjectValue(JSONObject jsonObject, String key, Class<T> clazz){
        T t = null;
        try {
            if (clazz == Integer.class) {
                t = (T) Integer.valueOf(jsonObject.getInt(key));
            }else if(clazz == Boolean.class){
                t = (T) Boolean.valueOf(jsonObject.getBoolean(key));
            }else if(clazz == String.class){
                t = (T) String.valueOf(jsonObject.getString(key));
            }else if(clazz == Double.class){
                t = (T) Double.valueOf(jsonObject.getDouble(key));
            }else if(clazz == JSONObject.class){
                t = (T) jsonObject.getJSONObject(key);
            }else if(clazz == JSONArray.class){
                t = (T) jsonObject.getJSONArray(key);
            }else if(clazz == Long.class){
                t = (T) Long.valueOf(jsonObject.getLong(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * json字符串转换为ContentValues
     * @param json    json字符串
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ContentValues jsonToContentValues(String json){
        ContentValues contentValues = new ContentValues();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator iterator = jsonObject.keys();
            String key;
            Object value;
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                value = jsonObject.get(key);
                String valueString = value.toString();
                if (value instanceof String) {
                    contentValues.put(key, valueString);
                }else if(value instanceof Integer){
                    contentValues.put(key, Integer.valueOf(valueString));
                }else if(value instanceof Long){
                    contentValues.put(key, Long.valueOf(valueString));
                }else if(value instanceof Double){
                    contentValues.put(key, Double.valueOf(valueString));
                }else if(value instanceof Float){
                    contentValues.put(key, Float.valueOf(valueString));
                }else if(value instanceof Boolean){
                    contentValues.put(key, Boolean.valueOf(valueString));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new Error("Json字符串不合法：" + json);
        }

        return contentValues;
    }








        /**
         * 发送手机号码
         *
         * 发送：电话号码（phone）
         *
         * 接收：验证码（check）
         */
        public static JSONObject checkPhone(String phone){
            Map<String, Object> data=new HashMap<String, Object>();
            data.put("itemName", "checkPhone");
            data.put("phone",phone);
            JSONObject obj=new JSONObject(data);
            return obj;
        }

        /**
         * 通过手机号得到用户信息
         *
         * 发送：电话号码
         *
         * 接收：
         * isExist=false
         * {
         *     "isExist":   (boolean)
         * }
         * isExist=true
         * {
         *     "isExist":  （boolean）
         *     "userName":   （String）
         *     "userPhone":   （String）
         * }
         *
         * isExist=false的情况为
         * 1、phone不存在
         */
        public static JSONObject getuserInfo(String phone){
            Map<String, Object> data=new HashMap<String, Object>();
            data.put("itemName", "getuserInfo");
            data.put("phone",phone);
            JSONObject obj=new JSONObject(data);
            return obj;
        }

        /**
         * 发送用户登录信息
         *
         * 发送：电话号码（phone）；用户密码（password）
         *
         * 接收：
         * isExist=false
         * {
         *     "isExist":   (boolean)
         * }
         * isExist=true
         * {
         *     "isExist":  （boolean）
         *     "userName":   （String）
         *     "userPhone":   （String）
         * }
         *
         * isExist=false的情况：
         * 1、phone对应的电话号码不存在
         * 2、电话号码与用户密码不匹配
         * @param phone
         */
        public static JSONObject checkLogInfo(String phone, String password) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("itemName", "checkLogInfo");
            data.put("phone", phone);
            data.put("password", password);
            JSONObject obj = new JSONObject(data);
            return obj;
        }

        /**
         * 发送用户注册信息
         *
         * 发送：电话号码（phone）；用户名（name）；用户密码（password）
         *
         * 接收：
         * boolean类型值（isSuccessful）
         *
         * isSuccessful=false的情况：
         * 1、phone对应的电话号码已经存在
         */
        public static JSONObject setRegisterInfo(String phone, String name, String password) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("itemName", "setRegisterInfo");
            data.put("phone", phone);
            data.put("name",name);
            data.put("password", password);
            JSONObject obj = new JSONObject(data);
            return obj;
        }

        /**
         * 发送密码更改信息
         *
         * 发送：电话号码（phone）；用户密码（password）
         *
         * 接收：boolean类型值（isSuccessful）
         */
        public static JSONObject setPassword(String phone, String password) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("itemName", "setPassword");
            data.put("phone", phone);
            data.put("password", password);
            JSONObject obj = new JSONObject(data);
            return obj;
        }

    /**
     * 发送用户昵称信息
     *
     * 发送：电话号码（phone）；
     *
     * 接收：boolean类型值（isSuccessful）
     */
    public static JSONObject changeNickname(String phone, String nickname) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("itemName", "changeNickname");

        data.put("nickName", nickname);
        data.put("phone", phone);
        JSONObject obj = new JSONObject(data);
        return obj;
    }

    /**
     * 发送退出登录信息
     *
     * 发送：电话号码（phone）
     *
     * 接收：boolean类型值（isSuccessful）
     */
    public static JSONObject logOut(String phone) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("itemName", "Logout");
        data.put("phone", phone);
        JSONObject obj = new JSONObject(data);
        return obj;
    }

    /**
     * 获取昵称
     *
     * 发送：电话号码（phone）
     *

     */
    public static JSONObject getUser(String phone) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("itemName", "handlePersonalResponse");
        data.put("phone", phone);
        JSONObject obj = new JSONObject(data);
        return obj;
    }

    /*
    修改头像
     */
    public static JSONObject changePortrait(String phone, String photoName) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("itemName", "changePortrait");
        data.put("phone", phone);
        data.put("photo", photoName);
        JSONObject obj = new JSONObject(data);
        return obj;
    }

}