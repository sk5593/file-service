package com.service.file.util;

import com.service.file.config.MainConfig;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 对称加密AES工具类
 */
public class AesUtils {
    public static String getSecretKey() throws Exception {
        return getSecretKey(null);
    }
    public static String getSecretKey(String seed) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(MainConfig.ALGORITHM);
        SecureRandom secureRandom;
        if (seed != null && !"".equals(seed)) {
            secureRandom = new SecureRandom(seed.getBytes());
        } else {
            secureRandom = new SecureRandom();
        }
        keyGenerator.init(MainConfig.KEY_SIZE, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return encode(secretKey.getEncoded());
    }
    //    public static byte[] decode(String base64) throws Exception {
//        return Base64.decode(base64.getBytes());
//    }
    public static String encode(byte[] bytes) throws Exception {
        return new String(Base64.encode(bytes));
    }
    public static Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, MainConfig.ALGORITHM);
        return secretKey;
    }
}
