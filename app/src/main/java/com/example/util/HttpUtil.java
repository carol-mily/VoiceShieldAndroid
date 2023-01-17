package com.example.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {

    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public interface CallBack {
        void onRequestComplete(String request);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param  请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    //        OkHttpClient mHttpClient = new OkHttpClient();
//        //数据类型为json格式，因为后端收到的数据格式为json，所以这里把post请求到 后端的字符串转换成json格式
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8"); //表示数据格式为json
//        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(obj));   //2、构造RequestBody
//        //3、构造Request
//        final Request request = new Request
//                .Builder()
//                .post(requestBody)
//                .url("http://localhost:8080/test_war_exploded/Android_joggle.Android_Login")   //请求的地址，是onPost()传进来的参数
//                .build();
//
//        //4、将 Request 封装成 call
//        System.out.println("newcall");
//        final Call call = mHttpClient.newCall(request);
//        System.out.println("建立newcall");
//        //5、执行 call，这里就是异步的方法
//        call.enqueue(new Callback() {
//            public void onResponse( Call call,  Response response) throws IOException {
//                System.out.println("失败0");
//            }
//
//            @Override
//            public void onFailure( Call call,  IOException e) {
//                System.out.println("失败1");
//            }
//
//            public void onFailure(Request request, IOException e) {
//                System.out.println("失败");
//                e.printStackTrace();
//            }
//            public void onResponse(Response response) throws IOException, JSONException {
//                System.out.println("有反应");
//                if (response.isSuccessful()) {
//                    String result = response.body().string();//res就是后端返回的数据
//                    JSONObject o=new JSONObject(result);
//                    boolean isExist=o.getBoolean("isExist");
//                    if(isExist==false) {
//                        DialogUtil.changDialog(builder,"该账号不存在或密码错误！");
//                        return;
//                    } else {
//                        DialogUtil.changDialog(builder,"正在登录！");
//                        //跳转页面至已登录++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//                    }
//                }
//            }
//        });

}
