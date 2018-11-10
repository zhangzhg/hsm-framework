package com.framework.core.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

	public static HttpSession getSession(){
        RequestAttributes attributes =RequestContextHolder.getRequestAttributes();
        if( attributes==null ) {
            return null;
        } else {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)attributes;
            HttpServletRequest httpServletRequest = ((HttpServletRequest) servletRequestAttributes.getRequest());
            return httpServletRequest.getSession();

        }
	}

}
