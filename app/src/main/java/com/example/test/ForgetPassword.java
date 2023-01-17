package com.example.test;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

public class ForgetPassword extends AppCompatActivity {
    private Button c_forYes;
    private Button c_getCheck;
    private EditText c_phone;
    private EditText c_check;
    private String check;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        c_phone=findViewById(R.id.inPhone);
        c_check=findViewById(R.id.inCheck);
        check=null;

        /**
         * 获取验证码监听器
         */
        c_getCheck=findViewById(R.id.getCheck);
        c_getCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    check(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * 确认按键监听
         */
        c_forYes=findViewById(R.id.forYes);
        c_forYes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    forYes(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取手机验证码
     * @param v
     */
    private void check(View v) throws Exception {
        String phone=c_phone.getText().toString();
        AlertDialog.Builder builder=new AlertDialog.Builder( ForgetPassword.this);
        if(StringUtil.isEmpty(phone)) {   //判断手机号是否为空
            DialogUtil.changDialog(builder,"手机号不能为空！");
            return;
        }
        if(!StringUtil.isValidPhoneNumber(phone)){
            DialogUtil.changDialog(builder,"手机号输入错误！");
            return;
        }

        Toast.makeText(ForgetPassword.this, "验证码已发送，请注意查收", Toast.LENGTH_SHORT).show();   //弹出弹窗提示
        //倒计时计时
        int countTimer=60*1000; //设置时间为60s
        new CountDownTimer(countTimer,1000){
            public void onTick(long millisUntilFinished){
                c_getCheck.setBackgroundColor(Color.parseColor("#677B73"));
                c_getCheck.setEnabled(false);   //将按钮设置为不可操作
                c_getCheck.setTextColor(Color.parseColor("#FFFFFF"));
                c_getCheck.setText("重新获取("+(millisUntilFinished / 1000)+")");
            }
            public  void onFinish(){
                c_getCheck.setBackgroundColor(Color.parseColor("#1B8F85"));
                c_getCheck.setEnabled(true);
                c_getCheck.setTextColor(Color.parseColor("#FFFFFF"));
                c_getCheck.setText("获取验证码");
            }
        }.start();

        //服务器给手机发验证码
        UserInfoModel userInfoModel = new UserInfoModel();
        JSONObject obj=JSONUtil.checkPhone(phone);
        //测试成功案例1
//        userInfoModel.postRequest(obj);
        //测试案例2---正式使用
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String getStr=userInfoModel.Post(obj);
                    JSONObject getObj= new JSONObject(getStr);
                    System.out.println("length:"+getObj.length());
                    if (getObj.length()==0){
                        check=null;
                    }else{
                        check=getObj.getString("check");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 确认事件
     * @param v
     */
    private void forYes(View v) throws JSONException {
        String phone = c_phone.getText().toString();
        String getcheck = c_check.getText().toString();
        AlertDialog.Builder builder=new AlertDialog.Builder( ForgetPassword.this);
        if(phone.length()!=11){
            DialogUtil.changDialog(builder,"手机号输入错误！");
            return;
        }
        if(StringUtil.isEmpty(phone)) {   //判断手机号是否为空
            if(StringUtil.isEmpty(getcheck)) {   //判断验证码是否为空
                DialogUtil.changDialog(builder,"手机号和验证码不能为空！");
                return;
            }
            DialogUtil.changDialog(builder,"手机号不能为空！");
            return;
        }
        if(StringUtil.isEmpty(getcheck)) {   //判断验证码是否为空
            DialogUtil.changDialog(builder,"验证码不能为空！");
            return;
        }
        //检查验证码---------------------------------------正式使用时放开--------------------------------------------------------
//        if((!getcheck.equals(check))&&phone.equals(c_phone.getText().toString())){    //检查验证码是否正确
//            DialogUtil.changDialog(builder,"验证码错误！");
//            return;
//        }else{
//            Intent intent=new Intent(ForgetPassword.this,SetPassword.class);    //跳转页面
//            Bundle bundle=new Bundle();
//            bundle.putString("phone",phone);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
        //跳过验证码检测
        Intent intent=new Intent(ForgetPassword.this,SetPassword.class);    //跳转页面
        Bundle bundle=new Bundle();
        bundle.putString("phone",phone);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}

