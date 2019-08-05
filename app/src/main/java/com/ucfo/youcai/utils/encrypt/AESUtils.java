package com.ucfo.youcai.utils.encrypt;

/**
 * Author:29117
 * Time: 2019-3-23.  下午 4:46
 * Email:2911743255@qq.com
 * ClassName: AESUtils
 * Package: com.ucfo.youcai.utils.encrypt
 * Description:
 * Detail:
 */

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class AESUtils {
    public String encrypt(String data, String key, String ivString) {
        //偏移量
        byte[] iv = ivString.getBytes();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int length = dataBytes.length;
            //计算需填充长度
            if (length % blockSize != 0) {
                length = length + (blockSize - (length % blockSize));
            }
            byte[] plaintext = new byte[length];
            //填充
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            //设置偏移量参数
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encryped = cipher.doFinal(plaintext);
            String result = Base64.encodeToString(encryped, Base64.DEFAULT);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public String desEncrypt(String data, String key, String ivString) {
        try {
            byte[] iv = ivString.getBytes();
            byte[] raw = key.getBytes();
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decode = Base64.decode(data, Base64.DEFAULT);//先用base64解密
            byte[] original = cipher.doFinal(decode);
            String originalString = new String(original, "UTF-8");
            return originalString;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }


}

