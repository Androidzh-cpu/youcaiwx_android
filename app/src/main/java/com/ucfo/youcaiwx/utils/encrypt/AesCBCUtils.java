package com.ucfo.youcaiwx.utils.encrypt;

/**
 * Author:29117
 * Time: 2019-3-23.  下午 4:35
 * Email:2911743255@qq.com
 * ClassName: AesCBCUtils
 */

import android.util.Base64;

import javax.crypto.Cipher;

import javax.crypto.spec.IvParameterSpec;

import javax.crypto.spec.SecretKeySpec;

public class AesCBCUtils {
    private static String sKey = "sklhdflsjfsdgdeg";
    private static String ivParameter = "cfbsdfgsdfxccvd1";
    private static AesCBCUtils instance = null;

    private AesCBCUtils() {
    }

    public static AesCBCUtils getInstance() {
        if (instance == null) {
            instance = new AesCBCUtils();
        }
        return instance;
    }

    // 加密
    public String encrypt(String sSrc, String sKey, String ivParameter) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        String string = Base64.encodeToString(encrypted, Base64.DEFAULT);
        return string;//此处使用BASE64做转码。
    }

    // 解密
    public String decrypt(String sSrc, String sKey, String ivParameter) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] decode = Base64.decode(sSrc, Base64.DEFAULT);
            byte[] original = cipher.doFinal(decode);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

}



