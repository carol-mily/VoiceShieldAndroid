package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
//package com.example.bmob_test.ui;//程序启动动画，图片颜色由浅到深，方法一

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;



public class Welcome extends AppCompatActivity {
    private ImageView welcomeImg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeImg = (ImageView) this.findViewById(R.id.welcome_image);
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(3000);// 设置动画显示时间
        welcomeImg.startAnimation(anima);
        anima.setAnimationListener(new Welcome.AnimationImpl());
    }

    private class AnimationImpl implements AnimationListener
    {

        @Override
        public void onAnimationStart(Animation animation)
        {
            welcomeImg.setBackgroundResource(R.drawable.image_back);
        }

        @Override
        public void onAnimationEnd(Animation animation)
        {
            skip(); // 动画结束后跳转到别的页面
        }

        @Override
        public void onAnimationRepeat(Animation animation)
        {

        }

    }

    private void skip()
    {
        startActivity(new Intent(this, Login.class));
        finish();
    }
}