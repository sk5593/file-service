package com.service.file.controller;

import com.alibaba.fastjson.JSON;
import com.service.file.entity.FileEntity;
import com.service.file.util.JdbcUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@Slf4j
@WebServlet( "/api/getDetails" )
public class DetailsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            FileEntity fileEntity = JdbcUtils.getDetails(id);
            PrintWriter out = resp.getWriter();
            String s = JSON.toJSONString(fileEntity);
            System.out.println(s);
            out.write(s);
        } catch (Exception e) {
           log.error("查询详情异常" , e);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
