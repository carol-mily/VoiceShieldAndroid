package com.example.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import static com.example.test.Login.user;


public class Register extends AppCompatActivity {
    //    private Button c_getCheck;
    private Button c_register;
    private ImageButton c_tip1;
    private ImageButton c_tip2;
    private ImageButton c_tip3;
    private ImageButton c_tip4;
    private EditText c_phone;
    private EditText c_name;
    private EditText c_password;
    private EditText c_second;
    //    private EditText c_check;
//    private String check;
    public boolean isSuccessful;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        c_phone=findViewById(R.id.inPhone);
        c_name=findViewById(R.id.inName);
        c_password=findViewById(R.id.inPass);
        c_second=findViewById(R.id.inSecond);

        /**
         * Tip1监听
         */
        c_tip1=findViewById(R.id.tip1);
        c_tip1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(Register.this, "请输入11位中国内陆手机号！", Toast.LENGTH_SHORT).show();   //弹出弹窗提示
            }
        });

        /**
         * Tip2监听
         */
        c_tip2=findViewById(R.id.tip2);
        c_tip2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(Register.this, "用户名为1-10位,不能包括特殊字符！", Toast.LENGTH_SHORT).show();   //弹出弹窗提示
            }
        });

        /**
         * Tip3监听
         */
        c_tip3=findViewById(R.id.tip3);
        c_tip3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(Register.this, "密码为6-12位,包含大小写字母和数字！", Toast.LENGTH_SHORT).show();   //弹出弹窗提示
            }
        });

        /**
         * Tip4监听
         */
        c_tip4=findViewById(R.id.tip4);
        c_tip4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(Register.this, "请再次输入密码！", Toast.LENGTH_SHORT).show();   //弹出弹窗提示
            }
        });

//        c_check=findViewById(R.id.inCheck);
//        check=null;

//        /**
//         * 获取验证码监听器
//         */
//        c_getCheck=findViewById(R.id.getCheck);
//        c_getCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    check(v);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        /**
         * 注册按钮监听器
         */
        c_register=findViewById(R.id.register);
        c_register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    register(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }




//    /**
//     * 获取手机验证码
//     * @param v
//     */
//    private void check(View v) throws Exception {
//        String phone=c_phone.getText().toString();
//        AlertDialog.Builder builder=new Builder( Register.this);
//        if(StringUtil.isEmpty(phone)) {   //判断手机号是否为空
//            DialogUtil.changDialog(builder,"手机号不能为空！");
//            return;
//        }
//        if(!StringUtil.isValidPhoneNumber(phone)){
//            DialogUtil.changDialog(builder,"手机号输入错误！");
//            return;
//        }
//
//        Toast.makeText(Register.this, "验证码已发送，请注意查收", Toast.LENGTH_SHORT).show();   //弹出弹窗提示
//        //倒计时计时
//        int countTimer=60*1000; //设置时间为60s
//        new CountDownTimer(countTimer,1000){
//            public void onTick(long millisUntilFinished){
//                c_getCheck.setBackgroundColor(Color.parseColor("#677B73"));
//                c_getCheck.setEnabled(false);   //将按钮设置为不可操作
//                c_getCheck.setTextColor(Color.parseColor("#FFFFFF"));
//                c_getCheck.setText("重新获取("+(millisUntilFinished / 1000)+")");
//            }
//            public  void onFinish(){
//                c_getCheck.setBackgroundColor(Color.parseColor("#1B8F85"));
//                c_getCheck.setEnabled(true);
//                c_getCheck.setTextColor(Color.parseColor("#FFFFFF"));
//                c_getCheck.setText("获取验证码");
//            }
//        }.start();
//
//        //服务器给手机发验证码
//        UserInfoModel userInfoModel1 = new UserInfoModel();
//        JSONObject obj1=JSONUtil.checkPhone(phone);
//        //测试成功案例1
////        userInfoModel1.postRequest(obj1);
//        //测试案例2---正式使用
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String getStr=userInfoModel1.Post(obj1);
//                    JSONObject getObj= new JSONObject(getStr);
//                    System.out.println("length:"+getObj.length());
//                    if (getObj.length()==0){
//                        check=null;
//                    }else{
//                        check=getObj.getString("check");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        return;
//    }

    /**
     * 注册事件
     * @param v
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void register(View v) throws Exception {
        String phone=c_phone.getText().toString();
        String name=c_name.getText().toString();
//        String getcheck=c_check.getText().toString();
        String password=c_password.getText().toString();
        String second=c_second.getText().toString();
        AlertDialog.Builder builder=new Builder( Register.this);
        //对用户名进行判断
        if(StringUtil.isEmpty(name)){
            DialogUtil.changDialog(builder,"用户昵称不能为空！");
            return;
        }else if (name.length()>10){
            DialogUtil.changDialog(builder,"用户昵称长度需为1-10位！");
            return;
        }
        else if(!name.matches("^[\\u4E00-\\u9FA5A-Za-z0-9]+$")) {
            DialogUtil.changDialog(builder,"用户昵称只能包含大小写字母、数字和汉字！");
            return;
        }
        //对密码的判断

        if(StringUtil.isEmpty(password)) {   //判断密码是否为空
            DialogUtil.changDialog(builder,"密码不能为空！");
            return;
        }
        if(password.length()<6||password.length()>12) {
            DialogUtil.changDialog(builder,"密码长度需为6-12位！");
            return;
        }
        if(!password.matches("^[a-z0-9A-Z]+$")) {
            DialogUtil.changDialog(builder,"密码只能包含大小写字母和数字！");
            return;
        }
        if(!StringUtil.isInclude(password)) {
            DialogUtil.changDialog(builder,"密码需同时包含大小写字母和数字！");
            return;
        }
        if(!password.equals(second)) {
            DialogUtil.changDialog(builder,"两次密码输入不同！");
            return;
        }
        //对手机号码及验证码
        if(phone.isEmpty()) {   //判断手机号是否为空
//            if(StringUtil.isEmpty(getcheck)) {   //判断注册码是否为空
//                DialogUtil.changDialog(builder,"手机号码和注册码不能为空！");
//                return;
//            }
            DialogUtil.changDialog(builder,"手机号码不能为空！");
            return;
        }
        if(!StringUtil.isValidPhoneNumber(phone)){
            DialogUtil.changDialog(builder,"手机号输入错误！");
            return;
        }
//        if(StringUtil.isEmpty(getcheck)) {   //判断注册码是否为空
//            DialogUtil.changDialog(builder,"注册码不能为空！");
//            return;
//        }
        //与后端通信--------------------------------------------正式使用时放开-------------------------------------------------------
//        if((!getcheck.equals(check))&&phone.equals(c_phone.getText().toString())){    //检查验证码是否正确
//            DialogUtil.changDialog(builder,"验证码错误！");
//            return;
//        }

//        //使用Base64对密码进行加密简单的处理(废)
//        String encoder1=EncryptionUtil.Base64(password);
//        System.out.println("Base64加密"+encoder1);

        //使用MD5加密
        String encoderText=EncryptionUtil.MD5(password);
//        System.out.println("MD5加密"+encoderText);

        //与后端通信-------------------------------------------------------------------------
        UserInfoModel userInfoModel2 = new UserInfoModel();
        JSONObject obj2=JSONUtil.setRegisterInfo(phone,name,encoderText);
        //测试成功案例1
//        userInfoModel2.postRequest(obj2);
        //测试案例2---正式使用
        isSuccessful=false;
        final CountDownLatch cdl=new CountDownLatch(1);//这里的数字，开启几个线程就写几
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String getStr=userInfoModel2.Post(obj2);
                    //返回字符串为空时，表示该账号不存在或密码错误
                    JSONObject getObj= new JSONObject(getStr);
                    System.out.println("getObj:"+getObj);
                    if (getObj.getInt("isSuccessful")==1){
                        isSuccessful=true;

                    }else{
                        isSuccessful=false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cdl.countDown(); //使得cdl中个数减1
            }
        }).start();
        //测试user
        cdl.await();//主线程等待子线程执行输出

        if(isSuccessful==false) {
            DialogUtil.changDialog(builder,"该手机号已存在账号！");
            return;
        } else {
            /*DialogUtil.changDialog(builder,"账号创建成功，请登录！");*/
            builder.setTitle("提示：");
            builder.setMessage("账号创建成功，请登录！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Register.this.finish();
                    /* Intent intent=new Intent(Register.this,Login.class); //跳转至登录界面
                    startActivity(intent);*/
                }
            });
            builder.create();
            AlertDialog b=builder.create();
            b.show();
        }
    }



}


