package com.example.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
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


public class SetPassword extends AppCompatActivity {
    private Button c_setYes;
    private ImageButton c_tip1;
    private ImageButton c_tip2;
    private EditText c_password;
    private EditText c_second;
    public boolean isSuccessful;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        c_password=findViewById(R.id.inPassword);
        c_second=findViewById(R.id.inSecond);

        /**
         * 确认按键监听
         */
        c_setYes=findViewById(R.id.setYes);
        c_setYes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    //检测intent的数据传递
//                    Intent intent1=getIntent();
//                    Bundle bundle=intent1.getExtras();
//                    String phone=bundle.getString("phone");
//                    c_setYes.setText(phone);
                    setYes(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * Tip1监听
         */
        c_tip1=findViewById(R.id.tip1);
        c_tip1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(SetPassword.this, "密码为6-12位,包含大小写字母和数字！", Toast.LENGTH_SHORT).show();   //弹出弹窗提示
            }
        });

        /**
         * Tip2监听
         */
        c_tip2=findViewById(R.id.tip2);
        c_tip2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(SetPassword.this, "请再次输入密码！", Toast.LENGTH_SHORT).show();   //弹出弹窗提示
            }
        });
    }

    /**
     * 确认事件
     * @param v
     */
    private void setYes(View v) throws Exception {
        String password=c_password.getText().toString();
        String second=c_second.getText().toString();
        int len=password.length();
        //接收上一个页面得到的电话号码
        Intent intent1=getIntent();
        Bundle bundle=intent1.getExtras();
        String phone="";
        phone=bundle.getString("phone");
        if(TextUtils.isEmpty(phone)){
            System.out.println("错啦！！！");
        }else{
            System.out.println("phone:"+phone);
        }

        //验证是否能接收上一个页面的信息
        //c_setYes.setText(phone);
        AlertDialog.Builder builder=new AlertDialog.Builder( SetPassword.this);
        if(StringUtil.isEmpty(password)) {   //判断密码是否为空
            DialogUtil.changDialog(builder,"密码不能为空！");
            return;
        }
        if(len<6||len>12) {
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

//        //使用Base64对密码进行加密简单的处理(废)
//        String encoder1=EncryptionUtil.Base64(password);
//        System.out.println("Base64加密"+encoder1);

        //使用MD5加密
        String encoderText=EncryptionUtil.MD5(password);
//        System.out.println("MD5加密"+encoderText);

        //将密码传输给后端
        UserInfoModel userInfoModel = new UserInfoModel();
        JSONObject obj= JSONUtil.setPassword(phone,encoderText);
        //测试成功案例1
//        userInfoModel.postRequest(obj);
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
            DialogUtil.changDialog(builder,"该手机号还未注册账号！");
            return;
        } else {
//            DialogUtil.changDialog(builder,"密码更改成功,请登录！");
            //跳转页面至已登录
            builder.setTitle("提示：");
            builder.setMessage("密码更改成功！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    SetPassword.this.finish();  //关设置密码界面
                    //忘记密码的跳转
//                    Intent intent2=new Intent(SetPassword.this,Login.class); //跳转至登录界面
//                    startActivity(intent2);
                }
            });
            builder.create();
            AlertDialog b=builder.create();
            b.show();
        }
    }


}


