package com.example.test;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.util.*;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;


public class ChangeNickname extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nickname);
        Button Save_button = (Button) findViewById(R.id.Save_button);
        //服务器地址
        String url = "http://47.99.201.254:8080/test1_war_exploded/android/getUserInfo";

        //获取phone
        Intent intent2 = getIntent();
        Bundle bundle = intent2.getExtras();
        String phone = bundle.getString("phone");
        String photo=bundle.getString("photo");

        Save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = ((EditText)findViewById(R.id.NickName_editText)).getText().toString().trim();
                AlertDialog.Builder builder=new AlertDialog.Builder(ChangeNickname.this);
                if(TextUtils.isEmpty(nickname)){
//                    Toast.makeText(ChangeNickname.this, "请输入用户名", Toast.LENGTH_LONG).show();
                    DialogUtil.changDialog(builder,"请输入用户名");
                }
                else if (nickname.length()>10){
                    DialogUtil.changDialog(builder,"用户昵称长度需为1-10位！");
                    return;
                }
                else if(!nickname.matches("^[\\u4E00-\\u9FA5A-Za-z0-9]+$")) {
                    DialogUtil.changDialog(builder,"用户昵称只能包含大小写字母、数字和汉字！");
                    return;
                }
                else{
                    JSONObject pp = JSONUtil.changeNickname(phone,nickname);
                    System.out.println("PPPP:"+pp);
                    final CountDownLatch cdl=new CountDownLatch(1);//这里的数字，开启几个线程就写几
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String getStr=UserInfoModel.postChangeNickname(pp,url);
                                JSONObject getObj= new JSONObject(getStr);
                                if(getObj.getInt("Code")==1){
                                    System.out.println(getStr);
                                    //  Toast.makeText(ChangeNickname.this,"修改成功！",Toast.LENGTH_LONG).show();
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

                    PersonalPlace.instance.finish();
                    HomePage.instance.finish();
                    Intent intent = new Intent(ChangeNickname.this,PersonalPlace.class);
                    ChangeNickname.this.finish();
                    intent.putExtra("name",nickname);
                    intent.putExtra("phone",phone);
                    intent.putExtra("photo",photo);
                    startActivity(intent);


//                    Intent intent=new Intent();
//                    intent.putExtra("name",nickname);
////                    intent.putExtra("phone",phone);
//                    setResult(2,intent);
//                    finish();

                }


            }
        });
    }



}


