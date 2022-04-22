package com.cn.listener;


import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
@WebListener
public class OnlineListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        //获取context中的count
        ServletContext servletContext=httpSessionEvent.getSession().getServletContext();
        Integer count= (Integer) servletContext.getAttribute("count");
        if (count==null){
            count=1;
        }else{
            count++;
        }
        servletContext.setAttribute("count",count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        ServletContext servletContext=httpSessionEvent.getSession().getServletContext();
        Integer count= (Integer) servletContext.getAttribute("count");
        if (count==null){
            count=0;
        }else{
            count--;
        }
        servletContext.setAttribute("count",count);
    }
}
