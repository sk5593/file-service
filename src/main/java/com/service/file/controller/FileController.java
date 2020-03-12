package com.service.file.controller;

import com.service.file.config.MainConfig;
import com.service.file.entity.FileEntity;
import com.service.file.util.AesUtils;
import com.service.file.util.JdbcUtils;
import com.service.file.util.RsaUtils;
import com.service.file.util.Utils;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Slf4j
@WebServlet( "/api/upload" )
@MultipartConfig
/**
 * 文件上传接口
 */
public class FileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("调用get方法");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = null;
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            // 获取PrintWriter对象
            out = resp.getWriter();
            Part part = req.getPart("file");
            String disposition = part.getHeader("Content-Disposition");
            String[] split = disposition.split(";");
            String fileName = split[split.length - 1];
            String substring = fileName.substring(fileName.indexOf("=") + 2, fileName.length() - 1);
            //设置原始名字
            String suffix = disposition.substring(disposition.lastIndexOf("."), disposition.length() - 1);
            //去掉. 设置类型
            String type = suffix.replace(".", "");
            //随机的生存一个32的字符串
            UUID uuid = UUID.randomUUID();
            String filename = uuid + suffix;
            is = part.getInputStream();
            //获取项目的绝对路径
            String location = System.getProperty("user.dir");
            //时间格式化，例如20200311
            String formatTime = Utils.timeFormat(new Date());
            //创建文件夹
            String dirLocation = Utils.mkDir(location, formatTime);
            fos = new FileOutputStream(dirLocation + "\\" + filename);

            //获取对称密钥
            String key = AesUtils.getSecretKey();
            //RSA公钥加密对称密钥
            String encrypt = RsaUtils.encrypt(key, MainConfig.PUBLIC_KEY);
            Key k = AesUtils.toKey(key.getBytes());
            byte[] raw = k.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, MainConfig.ALGORITHM);
            Cipher cipher = Cipher.getInstance(MainConfig.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            CipherInputStream cin = new CipherInputStream(is, cipher);
            byte[] cache = new byte[MainConfig.CACHE_SIZE];
            int nRead = 0;
            //写入加密后的文件
            while ((nRead = cin.read(cache)) != -1) {
                fos.write(cache, 0, nRead);
                fos.flush();
            }
            FileEntity fileEntity = FileEntity.builder().createTime(System.currentTimeMillis())
                    .dir(dirLocation + "\\" + filename)
                    .envelope(encrypt)
                    .name(substring)
                    .size(part.getSize())
                    .type(type)
                    .build();
            try {
                int insert = JdbcUtils.insert(fileEntity);
                if (insert > 0) {
                    log.info("插入成功");
                    out.print(uuid);
                }else {
                    log.error("插入失败,返回值为0");
                }
            } catch (Exception e) {
                log.error("插入失败", e);
                out.print("500");
            }

        } catch (Exception e) {
            log.error("上传文件失败", e);
            out.print(500);
        } finally {
            if (fos != null)
            fos.close();
            if (is != null)
            is.close();
            if (fos != null){
                out.close();
            }
        }
    }
}
