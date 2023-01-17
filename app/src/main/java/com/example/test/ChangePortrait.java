package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.util.JSONUtil;
import com.example.util.OkHttpUtil;
import com.example.util.UserInfoModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

public class ChangePortrait extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_portrait);
        String url = "http://47.99.201.254:8080/test1_war_exploded/android/getUserInfo";
        /*
        跳转
         */
        ImageButton boy1Bt = (ImageButton)findViewById(R.id.imageButton_boy1);
        ImageButton boy2Bt = (ImageButton)findViewById(R.id.imageButton_boy2);
        ImageButton boy3Bt = (ImageButton)findViewById(R.id.imageButton_boy3);
        ImageButton genshin1Bt = (ImageButton)findViewById(R.id.imageButton_genshin1);
        ImageButton genshin2Bt = (ImageButton)findViewById(R.id.imageButton_genshin2);
        ImageButton genshin3Bt = (ImageButton)findViewById(R.id.imageButton_genshin3);
        ImageButton genshin4Bt = (ImageButton)findViewById(R.id.imageButton_genshin4);
        ImageButton genshin5Bt = (ImageButton)findViewById(R.id.imageButton_genshin5);
        ImageButton myfaceBt = (ImageButton)findViewById(R.id.imageButton_myface);
        Intent intent1=getIntent();
        Bundle bundle=intent1.getExtras();
        String phone=bundle.getString("phone");
        String name = bundle.getString("name");
        boy1Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject pp = JSONUtil.changePortrait(phone,"boy1.jpg");
                System.out.println("PPPP:"+pp);
                OkHttpUtil.changePortrait(pp,url);
                PersonalPlace.instance.finish();
                HomePage.instance.finish();
                Intent intent = new Intent(ChangePortrait.this,PersonalPlace.class);
                ChangePortrait.this.finish();
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("photo","boy1.jpg");
                startActivity(intent);
            }
        });
        boy2Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject pp = JSONUtil.changePortrait(phone,"boy2.jpg");
                System.out.println("PPPP:"+pp);
                OkHttpUtil.changePortrait(pp,url);
                PersonalPlace.instance.finish();
                HomePage.instance.finish();
                Intent intent = new Intent(ChangePortrait.this,PersonalPlace.class);
                ChangePortrait.this.finish();
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("photo","boy2.jpg");
                startActivity(intent);
//                Intent intent=new Intent();
//                intent.putExtra("photo","boy2.jpg");
//                intent.putExtra("phone",phone);
//                intent.putExtra("name",name);
////                    intent.putExtra("phone",phone);
//                setResult(3,intent);
//                finish();
            }
        });
        boy3Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject pp = JSONUtil.changePortrait(phone,"boy3.jpg");
                System.out.println("PPPP:"+pp);
                OkHttpUtil.changePortrait(pp,url);
                PersonalPlace.instance.finish();
                HomePage.instance.finish();
                Intent intent = new Intent(ChangePortrait.this,PersonalPlace.class);
                ChangePortrait.this.finish();
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("photo","boy3.jpg");
                startActivity(intent);
            }
        });
        genshin1Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject pp = JSONUtil.changePortrait(phone,"genshin1.jpg");
                System.out.println("PPPP:"+pp);
                OkHttpUtil.changePortrait(pp,url);
                PersonalPlace.instance.finish();
                HomePage.instance.finish();
                Intent intent = new Intent(ChangePortrait.this,PersonalPlace.class);
                ChangePortrait.this.finish();
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("photo","genshin1.jpg");
                startActivity(intent);
            }
        });
        genshin2Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject pp = JSONUtil.changePortrait(phone,"genshin2.jpg");
                System.out.println("PPPP:"+pp);
                OkHttpUtil.changePortrait(pp,url);
                PersonalPlace.instance.finish();
                HomePage.instance.finish();
                Intent intent = new Intent(ChangePortrait.this,PersonalPlace.class);
                ChangePortrait.this.finish();
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("photo","genshin2.jpg");
                startActivity(intent);
            }
        });
        genshin3Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject pp = JSONUtil.changePortrait(phone,"genshin3.jpg");
                System.out.println("PPPP:"+pp);
                OkHttpUtil.changePortrait(pp,url);
                PersonalPlace.instance.finish();
                HomePage.instance.finish();
                Intent intent = new Intent(ChangePortrait.this,PersonalPlace.class);
                ChangePortrait.this.finish();
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("photo","genshin3.jpg");
                startActivity(intent);
            }
        });
        genshin4Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject pp = JSONUtil.changePortrait(phone,"genshin4.jpg");
                System.out.println("PPPP:"+pp);
                OkHttpUtil.changePortrait(pp,url);
                PersonalPlace.instance.finish();
                HomePage.instance.finish();
                Intent intent = new Intent(ChangePortrait.this,PersonalPlace.class);
                ChangePortrait.this.finish();
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("photo","genshin4.jpg");
                startActivity(intent);
            }
        });
        genshin5Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject pp = JSONUtil.changePortrait(phone,"genshin5.jpg");
                System.out.println("PPPP:"+pp);
                OkHttpUtil.changePortrait(pp,url);
                PersonalPlace.instance.finish();
                HomePage.instance.finish();
                Intent intent = new Intent(ChangePortrait.this,PersonalPlace.class);
                ChangePortrait.this.finish();
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("photo","genshin5.jpg");
                startActivity(intent);
            }
        });
        myfaceBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject pp = JSONUtil.changePortrait(phone,"myface.jpg");
                System.out.println("PPPP:"+pp);
                OkHttpUtil.changePortrait(pp,url);
                PersonalPlace.instance.finish();
                HomePage.instance.finish();
                Intent intent = new Intent(ChangePortrait.this,PersonalPlace.class);
                ChangePortrait.this.finish();
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("photo","myface.jpg");
                startActivity(intent);

            }
        });
    }




}
