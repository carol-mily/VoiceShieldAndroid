package com.example.util;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 */
public class ClientUploadUtils {

    public static void upload(String url, String filePath, String fileName ,String applicant, String receiver) throws Exception {
        /*
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
                .build();

        Request request = new Request.Builder()
                //.header("Authorization", "Client-ID " + 1)
                .header("Authorization", "Client-ID " + UUID.randomUUID())
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        return response.body(); //使用的话记得在函数声明处修改该方法的返回值
        */

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("client_receivenoise_applicantID",applicant);
        builder.addFormDataPart("client_receivenoise_receiverID",receiver);
        builder.addFormDataPart("file", fileName,
                RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)));
        RequestBody body = builder.build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
    }


    /*static void main(String[] args) throws IOException {
        try {
            String fileName = "com.jdsoft.biz.test.zip";
            String filePath = "D:\\ExtJsTools\\Sencha\\Cmd\\repo\\pkgs\\test.zip";
            String url = "http://localhost:9990/upload_app_package";
            System.out.println(upload(url, filePath, fileName).string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
