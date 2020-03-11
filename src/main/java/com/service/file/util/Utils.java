package com.service.file.util;

import com.service.file.config.MainConfig;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {
    public static String mkDir(String path,String dirName){
        File file=new File(path,dirName);
        if(!file.exists()){//如果文件夹不存在
            boolean mkdir = file.mkdir();//创建文件夹
        }
        return file.getPath();

    }
    public static String timeFormat(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String format = df.format(date);
        return format;
    }

}
