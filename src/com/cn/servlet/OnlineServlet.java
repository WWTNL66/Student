package com.cn.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OnlineServlet",urlPatterns = {"/online"})
public class OnlineServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext=req.getServletContext();
        Integer count= (Integer) servletContext.getAttribute("count");
        resp.getWriter().print(count);


    }
}
