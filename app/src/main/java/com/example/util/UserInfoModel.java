package com.example.util;


import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;
import android.widget.Toast;

import com.example.util.*;

import androidx.appcompat.app.AlertDialog;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserInfoModel {


    public static void postRequest(JSONObject obj) {
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        final OkHttpClient client = new OkHttpClient();
        final RequestBody[] requestBody = {RequestBody.create(JSON, String.valueOf(obj))};
        final Request request = new Request.Builder()
                .url("http://47.99.201.254:8080/test1_war_exploded/android/login")
                .post(requestBody[0])
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        //获取字符串时使用response.body().string()而response.body().toString()方法会导致请求结果乱码。
                        Log.i("VoiceShield","打印POST响应的数据：" + response.body().string());
                    } else {
                        Log.i("VoiceShield","打印POST响应的数据：" + "里面失败啦！");
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    Log.i("VoiceShield","打印POST响应的数据：" + "外面失败啦！");

                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static String Post(JSONObject obj) throws JSONException {
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        final OkHttpClient client = new OkHttpClient();
        final RequestBody[] requestBody = {RequestBody.create(JSON, String.valueOf(obj))};
        final Request request = new Request.Builder()
                .url("http://47.99.201.254:8080/test1_war_exploded/android/login")
                .post(requestBody[0])
                .build();
        String result =null;
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result=response.body().string();
                //获取字符串时使用response.body().string()而response.body().toString()方法会导致请求结果乱码。
                Log.i("VoiceShield","打印POST响应的数据：" + result);
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("result:"+result);
        return result;
    }

    public static String postChangeNickname(JSONObject obj,String url) throws JSONException {
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        final OkHttpClient client = new OkHttpClient();
        final RequestBody[] requestBody = {RequestBody.create(JSON, String.valueOf(obj))};
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody[0])
                .build();
        String result =null;
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result=response.body().string();
                //获取字符串时使用response.body().string()而response.body().toString()方法会导致请求结果乱码。
                Log.i("VoiceShield","打印POST响应的数据：" + result);
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("result:"+result);
        return result;
    }
    public static String handlePersonalResponse (JSONObject obj) throws JSONException {
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        final OkHttpClient client = new OkHttpClient();
        final RequestBody[] requestBody = {RequestBody.create(JSON, String.valueOf(obj))};
        final Request request = new Request.Builder()
                .url("http://47.99.201.254:8080/test1_war_exploded/android/getUserInfo")
                .post(requestBody[0])
                .build();
        String result =null;
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result=response.body().string();
                //获取字符串时使用response.body().string()而response.body().toString()方法会导致请求结果乱码。
                Log.i("VoiceShield","打印POST响应的数据：" + result);
            } else {
                Log.i("VoiceShield","打印POST响应的数据：" + response.body().string());
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("result:"+result);
        return result;
    }
}

