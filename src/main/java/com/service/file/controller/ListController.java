package com.service.file.controller;

import com.alibaba.fastjson.JSON;
import com.service.file.entity.FileEntity;
import com.service.file.util.JdbcUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@WebServlet( "/api/getList" )
/**
 * 获取最近上传列表
 */
public class ListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<FileEntity> list = JdbcUtils.getList();
            if (list.size()>0){
                String s = JSON.toJSONString(list);
                resp.getWriter().write(s);
            }
        } catch (Exception e) {
            log.error("查询列表异常" , e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
