package com.wonderful.interceptor;

import com.wonderful.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserInterceptor implements HandlerInterceptor {

    public final static Set<String> flagSet = new HashSet<>();
    public final static Set<String> hostNameSet = new HashSet<>();
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

        String hostName = InetAddress.getLocalHost().getHostName();
        boolean contains = hostNameSet.contains(hostName);
        if(contains){
            ConcurrentHashMap all = userCacheService.getAll();
            if(!all.isEmpty()){
                return flagSet.stream().anyMatch(o -> all.keySet().contains(o));
            }else{
                return false;
            }
        }else{
            return false;
        }


    }
}
