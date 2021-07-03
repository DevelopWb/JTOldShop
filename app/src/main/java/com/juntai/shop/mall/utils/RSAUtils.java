package com.juntai.shop.mall.utils;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by Ma
 * on 2019/11/21
 */
public class RSAUtils {
    /**RSA算法*/
    public static final String RSA = "RSA";
    /**加密方式，android的*/
//  public static final String TRANSFORMATION = "RSA/None/NoPadding";
    /**加密方式，标准jdk的*/
    public static final String TRANSFORMATION = "RSA/None/PKCS1Padding";
    //公钥
    public static final String PUBLIC = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAODW0RwInNjjJ3GdNXcA7mZ+vqr+h+JnYW5q+6sujbnFHPRgfPwywh2WmXWbmGLaffENvEk+DK6Q/y19eDB/LosCAwEAAQ==";

    /** 使用公钥加密 */
    public static byte[] encryptByPublicKey(byte[] data) throws Exception {
        // 得到公钥对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(PUBLIC.getBytes(), Base64.NO_WRAP));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance(TRANSFORMATION);
        cp.init(Cipher.ENCRYPT_MODE, pubKey);
        return cp.doFinal(data);
    }

}
