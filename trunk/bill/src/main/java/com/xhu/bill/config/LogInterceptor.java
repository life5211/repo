package com.xhu.bill.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author xhz
 * @version 1.0
 * @date 2019/6/28 17:19
 */
public class LogInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    LogInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("request=>{}", request.getRequestURL());
        return true;
    }

}
