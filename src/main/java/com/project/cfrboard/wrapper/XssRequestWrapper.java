package com.project.cfrboard.wrapper;

import com.project.cfrboard.domain.constant.CusSafelist;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class XssRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = Jsoup.clean(values[i], Safelist.basic());
        }
        return encodedValues;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> params = super.getParameterMap();
        if(params != null) {
            params.forEach((key, value) -> {
                for(int i=0; i<value.length; i++) {
                    value[i] = Jsoup.clean(value[i], Safelist.basic());
                }
            });
        }
        return params;
    }

    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        if (parameter == null) {
            return null;
        }
        return Jsoup.clean(parameter, Safelist.none());
    }

    @Override
    public String getQueryString() {
        String queryString = super.getQueryString();
        if (queryString == null) {
            return null;
        }
        return Jsoup.clean(queryString, Safelist.none());
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List result = new ArrayList<>();
        Enumeration headers = super.getHeaders(name);
        while (headers.hasMoreElements()) {
            String header = headers.nextElement().toString();
            String[] tokens = header.split(",");
            for (String token : tokens) {
                result.add(Jsoup.clean(token, Safelist.none()));
            }
        }
        return Collections.enumeration(result);
    }

    @Override
    public String getHeader(String name) {
        String header = super.getParameter(name);
        if (header == null) {
            return null;
        }
        return Jsoup.clean(header, Safelist.none());
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
//        Part part = super.getPart(name);
//        System.out.println("aaaa  :  " + part.getContentType());
//        System.out.println("aaaa  :  " + part.getName());
//        System.out.println("aaaa  :  " + part.getSubmittedFileName());
//        System.out.println("aaaa  :  " + part.getHeaderNames());
//        System.out.println("aaaa  :  " + part.getHeader());
//        System.out.println("aaaa  :  " + part.getHeaders());
        return super.getPart(name);
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
//        Collection<Part> parts = super.getParts();
//        for (Part part : parts) {
//            String contentType = part.getContentType();
//            String name = part.getName();
//            System.out.println("aaaa  :  " + name);
//            String submittedFileName = part.getSubmittedFileName();
//            System.out.println("aaaa  :  " + submittedFileName);
//            Collection<String> headerNames = part.getHeaderNames();
//            System.out.println("aaaa  :  " + headerNames);
//            InputStream inputStream = part.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String collect = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
//            System.out.println("collect = " + collect);
//        }
        return super.getParts();
    }
}
