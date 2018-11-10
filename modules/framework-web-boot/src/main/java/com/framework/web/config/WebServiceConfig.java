package com.framework.web.config;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

public class WebServiceConfig {

    public static final String SERVLET_MAPPING_URL_PATH = "/ws";

    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        CXFServlet cxfServlet = new CXFServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(cxfServlet, SERVLET_MAPPING_URL_PATH + "/*");
        return servletRegistrationBean;
    }
}
