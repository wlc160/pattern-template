package com.mytask.template.jdbc;

import com.mytask.template.jdbc.dao.UserDao;
import com.mytask.template.jdbc.pojo.User;

import java.util.List;

/**
 * 工程名:pattern-template
 * 包名:com.mytask.template.jdbc
 * 文件名:UserDaoTest
 * description:
 *
 * @author lcwen
 * @version V1.0: UserDaoTest.java 2019/10/17 9:48
 **/
public class UserDaoTest {

    public static void main(String[] args) {
        UserDao dao = new UserDao(null);
        List<User> users = (List<User>) dao.selectAll();
        System.out.println(users);
    }
}
