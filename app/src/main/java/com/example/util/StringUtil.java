package com.example.util;

import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if(str==null||"".equals(str.trim())) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断字符串是否不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if(str!=null&&!"".equals(str.trim())) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断字符串中是否同时包含大小写字母和数字
     * @param str
     * @return
     */
    public static boolean isInclude(String str) {
        int num=str.length();
        int uppercase=0;
        int lowercase=0;
        int number=0;
        char []ch=new char[num];
        ch=str.toCharArray();
        for(int i=0;i<num;i++) {
            if(ch[i]>='A'&&ch[i]<='Z') {
                uppercase++;
            }
            if(ch[i]>='a'&&ch[i]<='z') {
                lowercase++;
            }
            if(ch[i]>='0'&&ch[i]<='9') {
                number++;
            }
        }
        if(uppercase!=0&&lowercase!=0&&number!=0) {
            return true;
        }else {
            return false;
        }
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if ((phoneNumber.length()==11)&&(phoneNumber != null) && (!phoneNumber.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);  //判断手机号是否合法
        }
        return false;
    }

    /*
    处理日期string 30-12-2020 03:38:39
     */
    public static String dealLastDate(String lastDate){
        String []str;
        String delimeter1 = " ";
        str = lastDate.split(delimeter1);
        System.out.println("第一次分割："+str);
        if(str.length<2){
            return "错了！";
        }
        else{
            String date = str[0];
            String time = str[1];
            String delimeter2 = "-";
            String []str2;
            str2 = date.split(delimeter2);
            if(str2.length>=3){
                    String day = str2[0];
                    String month = str2[1];
                    String year = str2[2];
                return year+"年"+month+"月"+day+"日"+" "+time;
            }
            else {
                return "错了！";
            }
        }
        //return "错了！";
    }
}
