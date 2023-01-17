package com.example.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.RecordInfo;
import com.google.gson.reflect.TypeToken;

public class OkHttpUtil {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static void sendRequestWithOkHttp(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                        .url(address)
                        .build();
        client.newCall(request).enqueue(callback);
        }

    /*
    POST请求
     */
    public static String post (String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static List<RecordInfo> getRecordinfo(String phone) throws IOException {
        String url = "http://47.99.201.254:8080/test1_war_exploded/android/getUserInfo";


        //OkHttpClient client = new OkHttpClient();
        JSONObject request_phone = new JSONObject();
        try {
            request_phone.put("applicant", phone);
            request_phone.put("itemName", "getReceiveNoise");
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        String strByJson = post(url, request_phone.toString());

        System.out.println(strByJson);
        /*
        处理得到的json
         */
        //先转JsonObject
        JsonObject jsonObject = new JsonParser().parse(strByJson).getAsJsonObject();
        //再转JsonArray 加上数据头
        JsonArray jsonArray = jsonObject.getAsJsonArray("receiver");

        Gson gson = new Gson();
        List<RecordInfo> recordData = new ArrayList<>();

        //循环遍历
        for (JsonElement user : jsonArray) {
            //通过反射 得到UserBean.class
            RecordInfo userBean = gson.fromJson(user, new TypeToken<RecordInfo>() {}.getType());
            recordData.add(userBean);
        }

        return recordData;
    }

    public static void changePortrait(JSONObject pp,String url){
        final CountDownLatch cdl=new CountDownLatch(1);//这里的数字，开启几个线程就写几
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String getStr= UserInfoModel.postChangeNickname(pp,url);
                    JSONObject getObj= new JSONObject(getStr);
                    if(getObj.getInt("Code")==1){
                        System.out.println(getStr);
                    }
                    else{
                        //  Toast.makeText(ChangeNickname.this,"修改失败，请重试！",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("GG了！");
                }
                cdl.countDown(); //使得cdl中个数减1
            }
        }).start();
        //测试user
        try {
            cdl.await();//主线程等待子线程执行输出
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("申请线程失败！");
        }
    }






}
