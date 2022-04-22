package com.cn.servlet;

import com.cn.entity.User;
import com.cn.service.Impl.UserServiceImpl;
import com.cn.service.UserService;
import com.cn.util.EasyUIResult;
import com.cn.util.Pager;
import com.cn.util.Result;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/easyui")
public class EasyUIServlet extends  BaseServlet{
    UserService userService=new UserServiceImpl();

    public EasyUIResult queryByPage(HttpServletRequest req, HttpServletResponse resp){
        String p=req.getParameter("page");
        String r=req.getParameter("rows");
        Integer page = Integer.parseInt(p);
        Integer rows = Integer.parseInt(r);

        System.out.println(page);
        System.out.println(rows);

        String  userName=req.getParameter("userName");
        String userGender=req.getParameter("userGender");

        Pager pager=userService.queryByPage(page,rows,userName,userGender);

        EasyUIResult easyUIResult=new EasyUIResult();
        easyUIResult.setTotal(pager.getTotal());
        easyUIResult.setRows(pager.getUsers());

        return  easyUIResult;
    }
    public Result deleteUserByUserId(HttpServletRequest req, HttpServletResponse resp){
        String id=req.getParameter("userId");
        Integer userId=Integer.parseInt(id);
        userService.deleteUserByUserId(userId);
        Result result=new Result();
        result.setStatus(1);
        return result;

    }
    public User queryByUserId(HttpServletRequest request, HttpServletResponse response){
        String userId=request.getParameter("userId");
        User user=userService.queryByUserId(Integer.parseInt(userId));
        return  user;

    }
    //更新数据
    public  Result saveOrUpdate(HttpServletRequest request, HttpServletResponse response){
        String userId=request.getParameter("userId");
        String userName=request.getParameter("userName");
        String userPass =request.getParameter("userPass");
        String userGender =request.getParameter("userGender");
        String userAge =request.getParameter("userAge");
        String userPhone =request.getParameter("userPhone");
        String userAddress =request.getParameter("userAddress");
        System.out.println("userId"+userId);
        System.out.println("userName"+userName);
        System.out.println("userAddress"+userAddress);

        if (userId==null||"".equals(userId)){
            //保存
            User user=new User(userName,userPass,userGender,userAge,userPhone,userAddress,null,null);
            Integer result=userService.register(user);

            System.out.println("保存"+user);
        }else {
            //更新
            Integer id=Integer.parseInt(userId);
            //保存
            User user=new User(id,userName,userGender,userAge,userPhone,userAddress);
            System.out.println(user);
            Integer result=userService.update(user);
        }

        return new Result(1);
    }




}
