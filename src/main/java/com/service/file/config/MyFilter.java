package com.service.file.config;

import com.service.file.util.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.LogRecord;
@Slf4j
@WebFilter
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //解决response返回中文乱码，一定要在out前面声明
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String uuid = request.getHeader("X-SID");
        String signature = request.getHeader("X-Signature");
        //公钥解密鉴权
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(signature)){
            out.print("403");
        }else {
            try {
                String decrypt = RsaUtils.decrypt(signature, MainConfig.PUBLIC_KEY);
                if (uuid.equals(decrypt)){
                    filterChain.doFilter(request, response);
                }else {
                    out.print("403");
                }
            } catch (Exception e) {
                out.print("403");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
