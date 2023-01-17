package com.example.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtil {

    /**
     * 将二进制转化为16进制字符串
     *
     * @param inByte 二进制字节数组
     * @return String
     */
    public static String byteTohex(byte[] inByte) {
        String outHex = "";
        String stmp = "";
        for (int n = 0; n < inByte.length; n++) {
            stmp = (java.lang.Integer.toHexString(inByte[n] & 0XFF));   //转换为16进制
            if (stmp.length() == 1) {
                outHex = outHex + "0" + stmp;
            } else {
                outHex = outHex + stmp;
            }
        }
        return outHex.toUpperCase();
    }

    /**
     * 十六进制字符串转化为2进制
     *
     * @param inHex
     * @return
     */
    public static byte[] hexTobyte(String inHex) {
        byte[] outByte = new byte[8];
        byte[] tmp = inHex.getBytes();
        for (int i = 0; i < 8; i++) {
            outByte[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return outByte;
    }

    /**
     * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
     *
     * @param src0 byte
     * @param src1 byte
     * @return byte
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * 使用Base64对文本进行加密
     *
     * @param inContent
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String Base64(String inContent) throws UnsupportedEncodingException {
        //final Base64.Decoder decoder = Base64.getDecoder();  //解密操作
        final Base64.Encoder encoder = Base64.getEncoder();
        final byte[] textByte = inContent.getBytes("UTF-8");
        final String encodedText = encoder.encodeToString(textByte);
        //System.out.println(encodedText);   //加密结果输出
        //解码
        //System.out.println(new String(decoder.decode(encodedText), "UTF-8"));
        return encodedText;
    }

    /**
     * 使用MD5对文本进行加密
     *
     * @param inContent
     * @return
     */
    public static String MD5(String inContent) {
        byte[] getCode = null;
        try {
            //得到一个md5消息摘要
            getCode = MessageDigest.getInstance("MD5").digest(inContent.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        //将摘要转换为字符串
        String encodedText = byteTohex(getCode);
        return encodedText;
    }
}