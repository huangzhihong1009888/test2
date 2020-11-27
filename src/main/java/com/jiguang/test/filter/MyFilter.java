package com.jiguang.test.filter;

import com.jiguang.test.config.datasource.ThreadLocalUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author HuangZhiHong
 * @create 2020/9/15 21:14
 **/
@Component
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getHeader("token");
        ThreadLocalUtils.setTenantId("5000");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
