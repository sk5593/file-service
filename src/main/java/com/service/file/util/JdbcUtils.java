package com.service.file.util;

import com.service.file.config.MainConfig;
import com.service.file.entity.FileEntity;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class JdbcUtils {
    /**
     * 插入
     * @param fileEntity
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static int insert(FileEntity fileEntity) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        int i = 0;
        try {
            Class.forName(MainConfig.DERBY_DRIVER);
            Properties properties = new Properties();
            properties.put("create", "true"); // 新建数据库
            conn = DriverManager.getConnection(MainConfig.DB_URL, properties);
            //创建表
            try {
                String sql = "CREATE TABLE file("
                        + " size BIGINT, "
                        + " type VARCHAR(50), "
                        + " name VARCHAR(255), "
                        + " createTime BIGINT, "
                        + " dir VARCHAR(255), "
                        + " envelope VARCHAR(255)"
                        + ")";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.executeUpdate();
            } catch (SQLException sql) {
            }
            String insert = "insert into file(size,type,name,createTime,dir,envelope) values(?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(insert);
            preparedStatement.setLong(1, fileEntity.getSize());
            preparedStatement.setString(2, fileEntity.getType());
            preparedStatement.setString(3, fileEntity.getName());
            preparedStatement.setLong(4, fileEntity.getCreateTime());
            preparedStatement.setString(5, fileEntity.getDir());
            preparedStatement.setString(6, fileEntity.getEnvelope());
            i = preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw e;
        } finally {
            conn.close();
            // 内嵌模式数据库操作用完之后需要关闭数据库,这里没有执行数据库名称则全部关闭.
//            try {
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            } catch (SQLException e) {
//                e.getMessage();
//            }
        }
        return i;
    }

    /**
     * 根据uuid获取详情
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static FileEntity getDetails(String id) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Class.forName(MainConfig.DERBY_DRIVER);
        Properties properties = new Properties();
        properties.put("create", "true"); // 新建数据库
        conn = DriverManager.getConnection(MainConfig.DB_URL, properties);
        //创建表
        try {
            String sql = "CREATE TABLE file("
                    + " size BIGINT, "
                    + " type VARCHAR(50), "
                    + " name VARCHAR(255), "
                    + " createTime BIGINT, "
                    + " dir VARCHAR(255), "
                    + " envelope VARCHAR(255)"
                    + ")";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException sql) {
        }
        String sql = "select size,type,name,createTime,dir,envelope from file where dir like ?";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, "%" + id + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        FileEntity fileEntity = null;
        while (resultSet.next()) {
            String type = resultSet.getString("type");
            String name = resultSet.getString("name");
            String dir = resultSet.getString("dir");
            String envelope = resultSet.getString("envelope");
            long size = resultSet.getLong("size");
            long createTime = resultSet.getLong("createTime");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String format = simpleDateFormat.format(new Date(createTime));
            fileEntity = FileEntity.builder().type(type)
                    .size(size)
                    .name(name)
                    .envelope(envelope)
                    .dir(dir)
                    .formatTime(format)
                    .build();
        }
        return fileEntity;
    }

    /**
     * 获取最近上传的10个文件
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static List<FileEntity> getList() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Class.forName(MainConfig.DERBY_DRIVER);
        Properties properties = new Properties();
        properties.put("create", "true"); // 新建数据库
        conn = DriverManager.getConnection(MainConfig.DB_URL, properties);
        //创建表
        try {
            String sql = "CREATE TABLE file("
                    + " size BIGINT, "
                    + " type VARCHAR(50), "
                    + " name VARCHAR(255), "
                    + " createTime BIGINT, "
                    + " dir VARCHAR(255), "
                    + " envelope VARCHAR(255)"
                    + ")";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException sql) {
        }
        String sql = "select size,type,name,createTime,dir,envelope from file order by createTime desc";
        preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        FileEntity fileEntity = null;
        List<FileEntity> list = new ArrayList();
        int i = 0;
        //derby没有limit关键字
        while (resultSet.next() && i < 10) {
            i++;
            String type = resultSet.getString("type");
            String name = resultSet.getString("name");
            String dir = resultSet.getString("dir");
            String envelope = resultSet.getString("envelope");
            long size = resultSet.getLong("size");
            long createTime = resultSet.getLong("createTime");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(new Date(createTime));
            fileEntity = FileEntity.builder().type(type)
                    .size(size)
                    .name(name)
                    .envelope(envelope)
                    .dir(dir)
                    .formatTime(format)
                    .build();
            list.add(fileEntity);
        }
        return list;
    }
}
