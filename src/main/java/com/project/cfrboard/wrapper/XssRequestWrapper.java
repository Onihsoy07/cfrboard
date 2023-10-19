package com.project.cfrboard.wrapper;

import com.project.cfrboard.domain.constant.CusSafelist;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class XssRequestWrapper extends HttpServletRequestWrapper {

////    private byte[] rawData;
//    private HttpServletRequest request;
//    private ResettableServletInputStream servletStream;
//
//    public void resetInputStream(byte[] data) {
//        servletStream.stream = new ByteArrayInputStream(data);
//    }
//
////    @Override
////    public ServletInputStream getInputStream() throws IOException {
////        if (rawData == null) {
////            rawData = IOUtils.toByteArray(this.request.getReader());
////            servletStream.stream = new ByteArrayInputStream(rawData);
////        }
////        return servletStream;
////    }
//
////    @Override
////    public BufferedReader getReader() throws IOException {
////        if (rawData == null) {
////            rawData = IOUtils.toByteArray(this.request.getReader());
////            servletStream.stream = new ByteArrayInputStream(rawData);
////        }
////        return new BufferedReader(new InputStreamReader(servletStream));
////    }
//
//
//    private class ResettableServletInputStream extends ServletInputStream {
//
//        private InputStream stream;
//
//        @Override
//        public int read() throws IOException {
//            return stream.read();
//        }
//
//        @Override
//        public boolean isFinished() {
//            return false;
//        }
//
//        @Override
//        public boolean isReady() {
//            return false;
//        }
//
//        @Override
//        public void setReadListener(ReadListener listener) {
//
//        }
//    }


    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
//        this.request = request;
//        this.servletStream = new ResettableServletInputStream();
    }

    @Override
    public String[] getParameterValues(String name) {
//        return super.getParameterValues(name);
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
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        if (parameter == null) {
            return null;
        }
        return Jsoup.clean(parameter, Safelist.basic());
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
//        return super.getHeaders(name);
        List result = new ArrayList<>();
        Enumeration headers = super.getHeaders(name);
        while (headers.hasMoreElements()) {
            String header = headers.nextElement().toString();
            String[] tokens = header.split(",");
            for (String token : tokens) {
                result.add(Jsoup.clean(token, Safelist.basic()));
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
        return Jsoup.clean(header, Safelist.basic());
    }
}
