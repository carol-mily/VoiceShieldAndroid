package com.example.util;

import androidx.appcompat.app.AlertDialog;

public class DialogUtil {

    /**
     * 产生对话弹窗
     * @param builder
     * @param str
     */
    public static void changDialog(AlertDialog.Builder builder, String str){
        builder.setTitle("提示：");
        builder.setMessage(str);
        builder.create();
        AlertDialog b=builder.create();
        b.show();
    }
}