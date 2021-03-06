package com.wonderful.interceptor;

import com.wonderful.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserInterceptor implements HandlerInterceptor {

    public final static Set<String> flagSet = new HashSet<>();
    @Autowired
    private UserCacheService userCacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        System.err.println(url);
        System.err.println(method);
        System.err.println(uri);*/

        String username = URLDecoder.decode(request.getCookies()[0].getValue(), "utf-8");

        ConcurrentHashMap all = userCacheService.getAll();
        if(!all.isEmpty()){
                boolean a = all.size()==flagSet.size();
                boolean b = all.get(username)!=null;
                return a&&b;
        }else{
                return false;
        }
    }
}
