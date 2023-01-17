package com.example.test;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import kr.co.namee.permissiongen.PermissionGen;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.User;
import com.example.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;

public class Login extends AppCompatActivity {
    private Button c_logon;
    private Button c_register;
    //    private Button c_forget;
    private EditText c_phone;
    private EditText c_password;
    public boolean isSuccessful;
    public boolean isSuccessful2;
    public static User user;
    public static int count = 0;
    public String code;
    public String photo;

    String url = "http://47.99.201.254:8080/test1_war_exploded/android/getUserInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        申请权限
         */
        PermissionGen.with(this)
                .addRequestCode(100)
                .permissions(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
                .request();

        user=new User();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        c_phone=findViewById(R.id.user_phone);
        c_password=findViewById(R.id.user_password);

        /**
         * 登录按钮监听器
         */
        c_logon=findViewById(R.id.logon);
        c_logon.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    logOn(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * 注册按钮监听器
         */
        c_register=findViewById(R.id.register);
        c_register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                register(v);
            }
        });

//        /**
//         * 忘记密码按钮监听器
//         */
//        c_forget=findViewById(R.id.forget);
//        c_forget.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                forget(v);
//            }
//        });
    }


    /**
     * 登录事件
     * @param v
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void logOn(View v) throws Exception {
        String phone=c_phone.getText().toString();
        String password=c_password.getText().toString();
        AlertDialog.Builder builder=new AlertDialog.Builder( Login.this);
        if(!StringUtil.isValidPhoneNumber(phone)){
            DialogUtil.changDialog(builder,"手机号输入错误！");
            return;
        }
        if(phone.isEmpty()) {   //判断手机号是否为空
            if(StringUtil.isEmpty(password)) {   //判断密码是否为空
                DialogUtil.changDialog(builder,"手机号码和密码不能为空！");
                return;
            }
            DialogUtil.changDialog(builder,"手机号码不能为空！");
            return;
        }
        if(StringUtil.isEmpty(password)) {   //判断密码是否为空
            DialogUtil.changDialog(builder,"密码不能为空！");
            return;
        }


//        //使用Base64对密码进行加密简单的处理(废)
//        String encoder1=EncryptionUtil.Base64(password);
//        System.out.println("Base64加密"+encoder1);

        //使用MD5加密
        String encoderText=EncryptionUtil.MD5(password);
//        System.out.println("MD5加密"+encoderText);

        UserInfoModel userInfoModel = new UserInfoModel();
        //测试okhttp
//        JSONObject obj= JSONUtil.checkLogInfo(phone,password);
        JSONObject obj= JSONUtil.checkLogInfo(phone,encoderText);
        System.out.println(obj);
        JSONObject pp = JSONUtil.getUser(phone);
        //测试成功案例1---用于测试接收到后端的信息
//       userInfoModel.postRequest(obj);

        //测试案例2---正式使用
        isSuccessful=false;
        final CountDownLatch cdl=new CountDownLatch(1);//这里的数字，开启几个线程就写几
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String getStr=userInfoModel.Post(obj);
                    //返回字符串为空时，表示该账号不存在或密码错误
                    JSONObject getObj= new JSONObject(getStr);
                    System.out.println("length:"+getObj.length());
                    if (getObj.length()==0){
                        isSuccessful=false;
                    }else{
                        isSuccessful=true;
                        //测试getString
                        System.out.println("user1:"+getObj.getString("userName"));
                        System.out.println("user1:"+getObj.getString("userPhone"));
                        //测试user
                        user.setUserName(getObj.getString("userName"));
                        user.setUserPhone(getObj.getString("userPhone"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cdl.countDown(); //使得cdl中个数减1
            }
        }).start();
        cdl.await();//主线程等待子线程执行输出

        /*
        获取头像
         */
        final CountDownLatch cdl1=new CountDownLatch(1);//这里的数字，开启几个线程就写几个
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String getStr= UserInfoModel.handlePersonalResponse(pp);
                    JSONObject getObj= new JSONObject(getStr);
                    code = getObj.getString("code");
                    if(code.equals("0")){
                        isSuccessful2 = false;
                        System.out.println("头像失败了！");
                    }
                    else{
                        isSuccessful2 = true;
                        photo = getObj.getString("photo");
                        user.setPhoto(getObj.getString("photo"));

                        System.out.println("头像是！"+photo);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("GG了！");
                }
                cdl1.countDown(); //使得cdl中个数减1
            }
        }).start();
        try {
            cdl1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //测试user

        if(isSuccessful==false){
            DialogUtil.changDialog(builder,"该账号不存在或密码错误！");
        }else{

            System.out.println("user2:"+user.getUserName());
            System.out.println("user2:"+user.getUserPhone());
            System.out.println("user2:"+user.getPhoto());
            Intent intent=new Intent(Login.this,HomePage.class);    //跳转页面

            Bundle bundle=new Bundle();
            bundle.putString("name",user.getUserName());
            bundle.putString("phone",user.getUserPhone());
            bundle.putString("photo",user.getPhoto());
            System.out.println("bundle里面的名字是！"+user.getUserName());
            System.out.println("bundle里面的头像是！"+user.getPhoto());
            //测试intent传递信息
//            bundle.putString("name",phone);
//            bundle.putString("phone",phone);
            intent.putExtras(bundle);
            Login.this.finish();
            startActivity(intent);
        }

    }

    /**
     * 注册事件
     * @param v
     */
    protected void register(View v){
        Intent intent=new Intent(Login.this,Register.class);
        startActivity(intent);
    }

    /**
     * 忘记密码事件
     * @param v
     */
    protected void forget(View v){
        Intent intent=new Intent(Login.this,ForgetPassword.class);
        startActivity(intent);
    }



}

