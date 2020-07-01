package com.xhu.bill.config;

import com.xhu.bill.util.AccessLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xhz
 * @version 1.0
 * @date 2019/6/28 17:19
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    LogInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("request=>{}", request.getRequestURL());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();

            String key = request.getRequestURI();

        }
        return true;
    }

}
