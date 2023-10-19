package com.project.cfrboard.filter;

import com.project.cfrboard.wrapper.XssRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Component
public class XssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        XssRequestWrapper wrappedRequest = new XssRequestWrapper((HttpServletRequest) request);
//        String body = IOUtils.toString(wrappedRequest.getReader());
//        if (!StringUtils.isBlank(body)) {
//            body = Jsoup.clean(body, Safelist.basic());
////            wrappedRequest.resetInputStream(body.getBytes());
//        }
        chain.doFilter(wrappedRequest, response);
    }


}
