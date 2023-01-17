package com.example.test;


import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.RecordInfo;
import com.example.util.ClientDownloadUtils;
import com.example.util.ClientUploadUtils;
import com.example.util.JSONUtil;
import com.example.util.OkHttpUtil;
import com.example.util.UserInfoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.namee.permissiongen.PermissionGen;

public class HomePage extends AppCompatActivity {
    /*
    变量
     */
    private List<RecordInfo> recordData = new ArrayList<>(); //存储用户信息的类的List，在onBindViewHolder()中适配信息给相关控件
    private RecyclerView recordRecyclerView;
    private FloatingActionButton float_button_search;
    private FloatingActionButton float_button_fresh;
    private String userPhone;
    private String userName;
    private String userPhoto;
    private static boolean isExit = false;  // 标识是否退出
    public static HomePage instance=null;

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhoto(){
        return userPhoto;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder { //viewholder来hold住recyclelist单独的每个item
        ImageView userImage;
        TextView  text;
        Button    upload;
        Button    download;
        Button    play;

        public MyViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            text      = itemView.findViewById(R.id.userFrom);
            upload    = itemView.findViewById(R.id.upload_back);
            download  = itemView.findViewById(R.id.download_record);
            play      = itemView.findViewById(R.id.play_record);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.record_list_item,parent,false); //这里将每个item与布局文件相关联
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { //在这里修改每个item中各个控件的信息
            RecordInfo recordInfo = recordData.get(position);
            final List<String> temp_photo = new ArrayList<>();
            final List<String> temp_nickName = new ArrayList<>();
            JSONObject pp = JSONUtil.getUser(recordInfo.getClient_receivenoise_applicantID());
            final CountDownLatch cdl1=new CountDownLatch(1);//这里的数字，开启几个线程就写几个
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String getStr= UserInfoModel.handlePersonalResponse(pp);
                        JSONObject getObj= new JSONObject(getStr);
                        String code = getObj.getString("code");
                        if(code.equals("0")){
                            System.out.println("头像失败了！");
                        }
                        else{
                            String photo = getObj.getString("photo");
                            temp_photo.add(photo);
                            String name = getObj.getString("nickName");
                            temp_nickName.add(name);
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


            System.out.println(temp_nickName.get(0) + "  " + temp_photo.get(0));
            holder.text.setText(temp_nickName.get(0));
            String photo = temp_photo.get(0);
            if(photo==null)
                holder.userImage.setImageDrawable(getResources().getDrawable(R.drawable.myface));
            else if (photo.equals("boy1.jpg")){
                holder.userImage.setImageDrawable(getResources().getDrawable(R.drawable.boy1));
            }
            else if (photo.equals("boy2.jpg"))
                holder.userImage.setImageDrawable(getResources().getDrawable(R.drawable.boy2));
            else if (photo.equals("boy3.jpg"))
                holder.userImage.setImageDrawable(getResources().getDrawable(R.drawable.boy3));
            else if (photo.equals("genshin1.jpg"))
                holder.userImage.setImageDrawable(getResources().getDrawable(R.drawable.genshin1));
            else if (photo.equals("genshin2.jpg"))
                holder.userImage.setImageDrawable(getResources().getDrawable(R.drawable.genshin2));
            else if (photo.equals("genshin3.jpg"))
                holder.userImage.setImageDrawable(getResources().getDrawable(R.drawable.genshin3));
            else if (photo.equals("genshin4.jpg"))
                holder.userImage.setImageDrawable(getResources().getDrawable(R.drawable.genshin4));
            else if (photo.equals("genshin5.jpg"))
                holder.userImage.setImageDrawable(getResources().getDrawable(R.drawable.genshin5));
            /*Bitmap bitmap = BitmapFactory.decodeFile("/drawable/" + temp_photo.get(0));
            holder.userImage.setImageBitmap(bitmap);*/

            holder.play.setEnabled(false);
            /*String applicant_phone = recordData.get(position).getClient_receivenoise_applicantID();
            String receiver_phone  = getUserPhone();
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
            String fileName = new String(applicant_phone + "_" + receiver_phone + ".3gp");
            File file = new File(directory, fileName);
            if (file.exists()){
                holder.play.setEnabled(true);
            }*/


            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    String applicant_phone = recordData.get(position).getClient_receivenoise_applicantID();
                    String receiver_phone  = getUserPhone();
                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    File directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
                    String fileName = new String(applicant_phone + "_" + receiver_phone + ".3gp");

                    String url = "http://47.99.201.254:8080/test1_war_exploded/upload/download?filename=android/"
                            +   applicant_phone
                            +   "_"
                            +   receiver_phone
                            +   ".3gp";
                    //String url = "http://47.99.201.254:8080/test1_war_exploded/upload/download?filename=android/18125248962_18328660781.3gp";

                    ClientDownloadUtils test = new ClientDownloadUtils();
                    test.downloadFile(url, directory, fileName);
                    holder.play.setEnabled(true);
                }
            });

            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String applicant_phone = recordData.get(position).getClient_receivenoise_applicantID();
                        String receiver_phone  = getUserPhone();
                        ContextWrapper cw = new ContextWrapper(getApplicationContext());
                        File directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
                        String fileName = new String(applicant_phone + "_" + receiver_phone + ".3gp");
                        File file = new File(directory, fileName);
                        play(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    RecordInfo recordInfo = recordData.get(position);

                    Intent intent2=new Intent(HomePage.this,SendMessage.class);    //跳转页面
                    Bundle bundle2=new Bundle();
                    bundle2.putString("applicant", getUserPhone());
                    bundle2.putString("receiver", recordInfo.getClient_receivenoise_applicantID());
                    intent2.putExtras(bundle2);
                    startActivity(intent2);
                }
            });
        }

        @Override
        public int getItemCount() {
            return recordData.size(); //显示的item的数量即为list中用户类的个数
        }
    }

    private static void play(File file)throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException{
        MediaPlayer m = new MediaPlayer();
        m.setDataSource(file.getAbsolutePath());
        m.prepare();
        m.start();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        instance = this;
        /*
        获取phone
         */
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        String username = bundle.getString("name");
        String phone = bundle.getString("phone");
        String photo = bundle.getString("photo");
        setUserName(username);
        setUserPhone(phone);
        setUserPhoto(photo);

        /*
        获取接收消息列表
         */
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    recordData = OkHttpUtil.getRecordinfo(getUserPhone());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
        try {
            thread.join(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        float_button_search = findViewById(R.id.floatingbutton_search);
        float_button_fresh = findViewById(R.id.floatingButton_fresh);
        Button personalBt = findViewById(R.id.PersonalPlace_button);

        personalBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,PersonalPlace.class);
                Bundle bundle=new Bundle();

                bundle.putString("name",getUserName());
                bundle.putString("phone",getUserPhone());
                bundle.putString("photo",getUserPhoto());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        float_button_fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            recordData = OkHttpUtil.getRecordinfo(getUserPhone());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                recordRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        float_button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(HomePage.this,Contact.class);    //跳转页面
                Bundle bundle2=new Bundle();
                bundle2.putString("phone", userPhone);
                bundle2.putString("name", userName);
                intent2.putExtras(bundle2);
                startActivity(intent2);
            }
        });

        /*recordData.add(new RecordInfo("18328660781"));
        recordData.add(new RecordInfo("17716137958"));
        recordData.add(new RecordInfo("13032838021"));
        recordData.add(new RecordInfo("15881109186"));
        recordData.add(new RecordInfo("112233"));*/

        recordRecyclerView = (RecyclerView) findViewById(R.id.recordRecyclerView);
        recordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordRecyclerView.setAdapter(new MyAdapter());


    }

   /* private static Handler mHandler = new Handler() {

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
    }
*/
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