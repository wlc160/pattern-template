package com.mytask.template.jdbc.pojo;

/**
 * 工程名:pattern-template
 * 包名:com.mytask.template.jdbc
 * 文件名:User
 * description:
 *
 * @author lcwen
 * @version V1.0: User.java 2019/10/17 9:37
 **/
public class User {

    private int id;
    private String username;
    private String password;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
