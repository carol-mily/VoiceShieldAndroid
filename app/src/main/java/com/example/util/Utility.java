package com.example.util;

import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.Contactor;
import com.example.User;
import com.example.test.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utility {
    /*
    处理获得用户数据
     */
    public static boolean handlePersonalResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                User user = new User();
                JSONObject userObject = new JSONObject(response);
                user.setUserPhone(userObject.getString("phone"));
                //user.setLastDate(userObject.getString("lastDate"));
                user.setUserName(userObject.getString("userName"));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
    解析联系人列表
     */
    public static boolean handleContactResponse(String response){
        if(TextUtils.isEmpty(response)){
            try{
                JSONArray allContact = new JSONArray(response);
                for(int i=0;i<allContact.length();i++){
                    JSONObject contactObject = allContact.getJSONObject(i);
                    Contactor contactor = new Contactor();
                    contactor.setNickName(contactObject.getString("nickName"));
                    contactor.setLastTime(contactObject.getString("lastTime"));
                    contactor.setPhone(contactObject.getString("phone"));
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        } return false;
    }
    /*
    将返回的JSON解析为实体类
     */
    public static User handleUserResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                User user = new User();
                JSONObject userObject = new JSONObject(response);
                user.setUserPhone(userObject.getString("phone"));
               // user.setLastDate(userObject.getString("lastDate"));
                user.setUserName(userObject.getString("userName"));
                return user;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
    改头像
     */
    public static void setPortrait(String photo, ImageButton portraitBt){
        if(photo==null)
            portraitBt.setBackgroundResource(R.drawable.myface);
        else if (photo.equals("boy1.jpg")){
            portraitBt.setBackgroundResource(R.drawable.boy1);
            //System.out.println("走了这里呀");
        }
        else if (photo.equals("boy2.jpg"))
            portraitBt.setBackgroundResource(R.drawable.boy2);
        else if (photo.equals("boy3.jpg"))
            portraitBt.setBackgroundResource(R.drawable.boy3);
        else if (photo.equals("genshin1.jpg"))
            portraitBt.setBackgroundResource(R.drawable.genshin1);
        else if (photo.equals("genshin2.jpg"))
            portraitBt.setBackgroundResource(R.drawable.genshin2);
        else if (photo.equals("genshin3.jpg"))
            portraitBt.setBackgroundResource(R.drawable.genshin3);
        else if (photo.equals("genshin4.jpg"))
            portraitBt.setBackgroundResource(R.drawable.genshin4);
        else if (photo.equals("genshin5.jpg"))
            portraitBt.setBackgroundResource(R.drawable.genshin5);
        else{
            portraitBt.setBackgroundResource(R.drawable.myface);
            System.out.println("原来不相等？");
        }
    }
    public static void putPortrait(String photo, ImageView iv){
        if(photo==null)
            iv.setImageResource(R.drawable.myface);
        else if (photo.equals("boy1.jpg")){
            iv.setImageResource(R.drawable.boy1);
            //System.out.println("走了这里呀");
        }
        else if (photo.equals("boy2.jpg"))
            iv.setImageResource(R.drawable.boy2);
        else if (photo.equals("boy3.jpg"))
            iv.setImageResource(R.drawable.boy3);
        else if (photo.equals("genshin1.jpg"))
            iv.setImageResource(R.drawable.genshin1);
        else if (photo.equals("genshin2.jpg"))
            iv.setImageResource(R.drawable.genshin2);
        else if (photo.equals("genshin3.jpg"))
            iv.setImageResource(R.drawable.genshin3);
        else if (photo.equals("genshin4.jpg"))
            iv.setImageResource(R.drawable.genshin4);
        else if (photo.equals("genshin5.jpg"))
            iv.setImageResource(R.drawable.genshin5);
        else{
            iv.setImageResource(R.drawable.myface);
            System.out.println("原来不相等？");
        }

    }
}
