package com.cn.dao.Impl;

import com.cn.dao.UserDao;
import com.cn.entity.User;
import com.cn.util.DbcpUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    QueryRunner qr=new QueryRunner(DbcpUtils.ds);

    @Override
    public Integer preRegister(String userName) {
        Integer count=0;
        try {
            Long result= (Long) qr.query("select count(1) from user where userName=?",new ScalarHandler(),userName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Integer register(User user) {
        int update=0;
        try {
            update= qr.update("insert  into user values (null,?,?,?,?,?,?,?,?)",
                    user.getUserName(),
                    user.getUserPass(),
                    user.getUserGender(),
                    user.getUserAge(),
                    user.getUserPhone(),
                    user.getUserAddress(),
                    user.getUserPicture(),
                    user.getUserType()
                    );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }

    //登录
    @Override
    public User login(String userName, String userPass){
        User user=null;
        try {
            user=qr.query("SELECT * FROM `a2004`.`user` WHERE userName=? AND userPass=?",new BeanHandler<User>(User.class),userName,userPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //修改密码
    @Override
    public Integer updateUserPass(String userId, String newUserPass) {
        Integer result=0;
        try {
            result= qr.update("update user set userPass=? where userId=?",newUserPass,userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public Integer checkUserPass(String userId, String oldUserPass) {
        int count=0;
        Long  result=null;
        try {
            result= (Long) qr.query("select count(1) from user where userId=? and userPass=?",new ScalarHandler(),userId,oldUserPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        count=result.intValue();
        return count;
    }

    //修改头像
    @Override
    public void updateUserPicture(Integer userId, String fileName) {
        System.out.println(userId);
        System.out.println(fileName);
        try {
            qr.update("update user set userPicture=? where userId=?",fileName,userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User queryByUserId(Integer userId) {
        System.out.println(userId);
        User user =null;
        try {
            user=qr.query("select * from user where userId=?",new BeanHandler<User>(User.class),userId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public Integer queryUserCount(String sql,List list) {
        Integer total=0;
        Long r=null;
        try {
            r= (Long) qr.query(sql,new ScalarHandler(),list.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        total=r.intValue();
        return total;
    }

    @Override
    public List<User> queryByPage(String sql,List params) {
        List<User>users=null;
        try {
            users=qr.query(sql,new BeanListHandler<User>(User.class),params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void deleteUserByUserId(int userId) {
        try {
            qr.update("delete from user where userId=?",userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer updateUser(User user) {
        Integer result=null;
        try {
            result=qr.update("update  user set userName=?,userGender=?,userAge=?,userPhone=?,userAddress=? where  userId=?",
                    user.getUserName(),
                    user.getUserGender(),
                    user.getUserAge(),
                    user.getUserPhone(),
                    user.getUserAddress(),
                    user.getUserId()
                    );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
