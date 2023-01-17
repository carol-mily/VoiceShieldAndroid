package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

public class PersonalPlace extends Activity {

        private boolean isSuccessful;

        public String photo;
        public String username;
    public String phone;
    public TextView tv_NickName;
    public ImageButton portraitBt;
    public String code;
    private static boolean isExit = false;  // 标识是否退出


    // 定义全局变量
    public static PersonalPlace instance=null;

// onCreate方法中赋值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_place);
        instance = this;
        /*
        获取个人信息
         */

        String url = "http://47.99.201.254:8080/test1_war_exploded/android/getUserInfo";

        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        username = bundle.getString("name");
        phone = bundle.getString("phone");
        photo = bundle.getString("photo");
        JSONObject pp = JSONUtil.getUser(phone);
        final CountDownLatch cdl = new CountDownLatch(1);//这里的数字，开启几个线程就写几个
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String getStr = UserInfoModel.handlePersonalResponse(pp);
                    JSONObject getObj = new JSONObject(getStr);
                    code = getObj.getString("code");
                    if (code.equals("0")) {
                        isSuccessful = false;
                        System.out.println("头像失败了！");
                    } else {
                        isSuccessful = true;
                        photo = getObj.getString("photo");
                        username = getObj.getString("nickName");
                        System.out.println("头像是！" + photo);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("GG了！");
                }
                cdl.countDown(); //使得cdl中个数减1
            }
        }).start();
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        /*
        往部件上放图片/名字
         */

        tv_NickName = (TextView) findViewById(R.id.UserName_Text);
        tv_NickName.setText(username);
         portraitBt = (ImageButton) findViewById(R.id.imageButton_Portrait);
        System.out.println("photo是！"+photo);
        Utility.setPortrait(photo,portraitBt);

        /*页面跳转

             */
       // Button ChangeNicknameBt = null;
        Button ChangeNicknameBt = (Button) findViewById(R.id.ChangeNickName_button);
        Button homepageBt = (Button) findViewById(R.id.HomePage_button);
        Button aboutBt = (Button) findViewById(R.id.About_button);
        Button FixpasswordBt = (Button) findViewById(R.id.FixPassword_button);
        Button LogoutBt = (Button) findViewById(R.id.LogOut_button);
        //ImageButton portraitBt = (ImageButton)findViewById(R.id.imageButton_Portrait);
        portraitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalPlace.this, ChangePortrait.class);
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone);
                bundle.putString("name", username);
                bundle.putString("photo",photo);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        LogoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject pp = JSONUtil.logOut(phone);
                System.out.println("PPPP:" + pp);
                final CountDownLatch cdl = new CountDownLatch(1);//这里的数字，开启几个线程就写几
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String getStr = UserInfoModel.Post(pp);
                            JSONObject getObj = new JSONObject(getStr);
                            if (getObj.getInt("isSuccessful") == 1) {
                                System.out.println(getStr);
                                isSuccessful = true;
                                //  Toast.makeText(ChangeNickname.this,"修改成功！",Toast.LENGTH_LONG).show();
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

                Intent intent = new Intent(PersonalPlace.this, Login.class);
                startActivity(intent);
                PersonalPlace.instance.finish();
                HomePage.instance.finish();

            }
        });
        FixpasswordBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalPlace.this, SetPassword.class);    //跳转页面
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone);
                bundle.putString("name", username);
                //bundle.putString("photo",photo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        homepageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalPlace.this, HomePage.class);
                Bundle bundle3 = new Bundle();
                // bundle2.putString("name",username);
                bundle3.putString("phone", phone);
                bundle3.putString("name", username);
                bundle3.putString("photo",photo);
                intent.putExtras(bundle3);
                startActivity(intent);

            }
        });
        ChangeNicknameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalPlace.this, ChangeNickname.class);
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone);
                bundle.putString("name", username);
                bundle.putString("photo",photo);
                intent.putExtras(bundle);
                startActivity(intent);

            }

        });
        aboutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalPlace.this, AboutUs.class);
                startActivity(intent);
                //PersonalPlace.this.finish();
            }
        });


    }
    //这里的requestCode参数，就是上面设置的 1 ，当跳转的页面返回的时候，通过这个加以判断
    //resultCode ,这个参数是在跳转的页面里面规定的，它也是一个int类型的标志
    //第三个参数包含了返回的值
    //如果不需要所跳转的页面返回值，也就不需要这个方法了
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2){
            username=data.getStringExtra("name");
            tv_NickName.setText(username);
//                textView.setText(data.getStringExtra("phone"));
        }else if(requestCode == 1 && resultCode == 3){
            photo=data.getStringExtra("photo");
        }
       // tv_NickName.notify();

    }
    @Override
  /*  protected void onResume() {
        super.onResume();
 //      onCreate(savedInstanceState);
        onCreate(null);
    }
    private static Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };



    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);  // 利用handler延迟发送更改状态信息
        } else {
            this.finish();
        }
    }*/

    public void onBackPressed(){
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //当按下键盘上返回按钮，给出退出对话框
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // System.exit(0);
            //直接退出程序
       /*Intent setIntent = new Intent(Intent.ACTION_MAIN);
       setIntent.addCategory(Intent.CATEGORY_HOME);
       setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(setIntent);
       */
            onBackPressed();
            //不退出程序仅仅返回桌面
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


}
