package com.example.test;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.util.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import kr.co.namee.permissiongen.PermissionGen;

public class SendMessage extends AppCompatActivity {

    private MediaRecorder myAutoRecorder;
    private String outputFile = null;
    private Button start, stop, play, upload;
    private File file; //寻找文件储存位置
    private String applicant, receiver;

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        Intent intent2 = getIntent();
        Bundle bundle = intent2.getExtras();
        String applicant = bundle.getString("applicant");
        setApplicant(applicant);
        String receiver = bundle.getString("receiver");
        setReceiver(receiver);


        start = findViewById(R.id.button1);
        stop = findViewById(R.id.button2);
        play = findViewById(R.id.button3);
        upload = findViewById(R.id.button4);

        stop.setEnabled(false);
        play.setEnabled(false);
        upload.setEnabled(false);

        //outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"myrecording.3gp";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        file = new File(directory, "MediaRecorderToAudio.3gp");
        //file = new File(Environment.getExternalStorageDirectory(), "MediaRecorderToAudio.3gp");

        myAutoRecorder = new MediaRecorder();
        myAutoRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAutoRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAutoRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAutoRecorder.setOutputFile(file.getAbsolutePath());
    }

    public void stop(View view) {
        if (myAutoRecorder != null) {
            try {
                myAutoRecorder.stop();
            } catch (IllegalStateException e) {
                // TODO 如果当前java状态和jni里面的状态不一致，
                e.printStackTrace();
                myAutoRecorder = null;
                myAutoRecorder = new MediaRecorder();
            }
            myAutoRecorder.release();
            myAutoRecorder = null;
        }

        stop.setEnabled(false);
        play.setEnabled(true);
        upload.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                Toast.LENGTH_SHORT).show();
    }

    public void start(View view) {
        try {
            myAutoRecorder.prepare();
            myAutoRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        start.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_SHORT).show();
    }

    public void play(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException {
        MediaPlayer m = new MediaPlayer();
        m.setDataSource(file.getAbsolutePath());
        m.prepare();
        m.start();
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_SHORT).show();
    }

    public void upload(View view) {
        //String url = " ";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ClientUploadUtils.upload("http://47.99.201.254:8080/test1_war_exploded/upload/androidAudio",
                            file.getAbsolutePath(),
                            "MediaRecorderToAudio.3gp", getApplicant(), getReceiver());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        upload.setEnabled(false);
        Toast.makeText(getApplicationContext(), "Audio uploaded successfully",
                Toast.LENGTH_SHORT).show();

        SendMessage.this.finish();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/



}