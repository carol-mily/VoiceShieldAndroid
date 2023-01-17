package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.util.DialogUtil;
import com.example.util.JSONUtil;
import com.example.util.StringUtil;
import com.example.util.UserInfoModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

public class Contact extends AppCompatActivity {
    String searchNickname;
    String searchState;
    String searchLastDate;
   String contactPhone;
   String code;
   String searchPhoto;
   Boolean isSuccessful;
   String photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Button personalBt = (Button) findViewById(R.id.PersonalPlace_button);
        ImageButton searchBt = (ImageButton)findViewById(R.id.Search_imageButton);
        Intent intent2 = getIntent();
        Bundle bundle = intent2.getExtras();
        String phone = bundle.getString("phone");
        String username = bundle.getString("name");

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactPhone = ((EditText)findViewById(R.id.Search_editText)).getText().toString().trim();
                System.out.println("Phone:"+contactPhone);
                if(TextUtils.isEmpty(contactPhone)){
                    Toast.makeText(Contact.this, "请输入手机号", Toast.LENGTH_LONG).show();
                }
                else if(StringUtil.isValidPhoneNumber(contactPhone)!=true){
                    Toast.makeText(Contact.this, "请输入合法手机号", Toast.LENGTH_LONG).show();
                }
                else{
                    JSONObject pp = JSONUtil.getUser(contactPhone);
                    System.out.println("PPPP:"+pp);
//                    if(pp.equals("error")){
//                        Toast.makeText(Contact.this, "无法找到该联系人", Toast.LENGTH_LONG).show();
//                    }

                        final CountDownLatch cdl=new CountDownLatch(1);//这里的数字，开启几个线程就写几个
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String getStr= UserInfoModel.handlePersonalResponse(pp);
                                    JSONObject getObj= new JSONObject(getStr);
                                    code = getObj.getString("code");
                                    if(code.equals("0")){
                                        isSuccessful = false;
                                    }
                                    else{
                                        isSuccessful=true;
                                        searchNickname = getObj.getString("nickName");
                                        searchLastDate = getObj.getString("lastDate");
                                        searchPhoto = getObj.getString("photo");

                                        if(getObj.getString("state").equals("1")){
                                            searchState = "在线";
                                        }else searchState = "不在线";
                                       // photo = getObj.getString("image");
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    System.out.println("GG了！");
                                }
                                cdl.countDown(); //使得cdl中个数减1
                            }
                        }).start();

                        try {
                            cdl.await();//主线程等待子线程执行输出
                            if(isSuccessful==false){
                                Toast.makeText(Contact.this, "未找到该联系人", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Intent intent = new Intent(Contact.this,SearchContactor.class);
                                // ChangeNickname.this.finish();
                                Bundle bundle=new Bundle();

                                bundle.putString("name",searchNickname);
                                bundle.putString("serch_phone",contactPhone);
                                bundle.putString("from_phone",phone);
                                bundle.putString("lastDate",searchLastDate);
                                bundle.putString("state",searchState);
                                bundle.putString("photo",searchPhoto);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.out.println("申请线程失败！");
                        }




                        //测试intent传递信息
//            bundle.putString("name",phone);
//            bundle.putString("phone",phone);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//
//                        startActivity(intent);
                    }

                }

        });




    }

}
