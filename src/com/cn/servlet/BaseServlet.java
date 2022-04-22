package com.cn.servlet;

import com.alibaba.fastjson.JSONObject;
import com.cn.entity.User;
import com.cn.util.EasyUIResult;
import com.cn.util.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* 允许跨域的主机地址 */
        resp.setHeader("Access-Control-Allow-Origin", "*");
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        resp.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        resp.setHeader("Access-Control-Max-Age", "3600");
        /* 允许跨域的请求头 */
        resp.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        //处理乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String method= req.getParameter("m");

        Class<? extends BaseServlet> clazz = this.getClass();

        try {
            Method invokeMethod = clazz.getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            invokeMethod.setAccessible(true);

            Object result = invokeMethod.invoke(clazz.newInstance(),req,resp);
            if (result instanceof String){
                String url= (String) result;
                resp.sendRedirect(url);
            }
            if (result instanceof Result){
                Result jsonResult= (Result) result;
                String jsonStr= JSONObject.toJSONString(jsonResult);
                resp.getWriter().write(jsonStr);
            }
            if (result instanceof User){
                User jsonResult= (User) result;
                String jsontr=JSONObject.toJSONString(jsonResult);
                resp.getWriter().write(jsontr);
            }
            if (result instanceof EasyUIResult){
                resp.setContentType("text/html;charset=utf-8");
                EasyUIResult jsonResult= (EasyUIResult) result;
                String jsontr=JSONObject.toJSONString(jsonResult);
                resp.getWriter().write(jsontr);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
