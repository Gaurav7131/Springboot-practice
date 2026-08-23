package com.example.demo.Filter;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ExehandlerInterceptor implements HandlerInterceptor {
    // preProcess:insepct,blocked,sanitized req.before ctrler method
    // execution:Object
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());

        if (handler instanceof HandlerMethod handlerMethod) {
            System.out.println("[handlerInterceptor]preProcess(): before ctler method exection:"
                    + handlerMethod.getMethod().getName());
        }
        return true;
    }

    // PostProcess():inspect,blocked request after executing ctrler,skipped if
    // execption occured
    // method:Model&view
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
        System.out.println(
                "[handlerInteceptor] postHandle():after cltr method execution,Model&View before rending to view:");
    }

    // afterCompltetion():finally execution for resource cleanup,thread-levl
    // varible,record audit logs,exection no matter what happned before:Execetion
    public void afterCompltetion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        long startTime = (Long) request.getAttribute("startime");
        long processingTime = System.currentTimeMillis() - startTime;
        System.out.println("[handlerInterceptor] afterCompletiton:Total Handling Time" + processingTime + "ms.");

    }
}
