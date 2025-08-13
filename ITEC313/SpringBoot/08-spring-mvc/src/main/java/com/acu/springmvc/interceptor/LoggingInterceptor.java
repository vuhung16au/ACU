package com.acu.springmvc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        
        logger.info("Request: {} {} from {}", 
            request.getMethod(), 
            request.getRequestURI(), 
            request.getRemoteAddr());
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // This method is called after the handler method is called, but before the view is rendered
        logger.debug("Post-handle: {} {} - Status: {}", 
            request.getMethod(), 
            request.getRequestURI(), 
            response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        logger.info("Response: {} {} - Status: {} - Duration: {}ms", 
            request.getMethod(), 
            request.getRequestURI(), 
            response.getStatus(), 
            duration);
        
        if (ex != null) {
            logger.error("Exception occurred: {}", ex.getMessage(), ex);
        }
    }
}
