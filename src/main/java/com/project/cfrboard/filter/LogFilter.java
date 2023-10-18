package com.project.cfrboard.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LogFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        MDC.put("requestURL", httpServletRequest.getRequestURL().toString());

        chain.doFilter(httpServletRequest, response);
        MDC.clear();
    }
}
