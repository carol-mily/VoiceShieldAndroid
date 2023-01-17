 package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.util.StringUtil;
import com.example.util.Utility;


 public class SearchContactor extends AppCompatActivity {
    private Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contactor);

        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();

        String name = bundle.getString("name");
        String phone = bundle.getString("serch_phone");
        String from_phone = bundle.getString("from_phone");
        String lastDate = bundle.getString("lastDate");
        String photo = bundle.getString("photo");
        lastDate = StringUtil.dealLastDate(lastDate);
        String state = bundle.getString("state");
        System.out.println("哈哈哈哈哈哈哈！！！name"+name+" phone"+phone+" lastdate"+lastDate+" state"+state);

        TextView tv_name = (TextView)findViewById(R.id.Nickname_textView);
        tv_name.setText(name);
        TextView tv_state = (TextView)findViewById(R.id.state_textView);
        tv_state.setText(state);
        TextView tv_phone = (TextView)findViewById(R.id.Phone_textView);
        tv_phone.setText(phone);
        TextView tv_lastDate = (TextView)findViewById(R.id.lastDate_textView);
        tv_lastDate.setText(lastDate);
        ImageView iv_portrait = (ImageView)findViewById(R.id.imageView);
        Utility.putPortrait(photo,iv_portrait);

        call = findViewById(R.id.call_button);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SearchContactor.this,SendMessage.class);
                Bundle bundle=new Bundle();
                bundle.putString("applicant",from_phone);
                bundle.putString("receiver",phone);
                intent2.putExtras(bundle);


                startActivity(intent2);
            }
        });
    }



 }