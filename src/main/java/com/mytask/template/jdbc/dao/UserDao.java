package com.mytask.template.jdbc.dao;

import com.mytask.template.jdbc.JdbcTemplate;
import com.mytask.template.jdbc.RowMapper;
import com.mytask.template.jdbc.pojo.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

/**
 * 工程名:pattern-template
 * 包名:com.mytask.template.jdbc.dao
 * 文件名:UserDao
 * description:
 *
 * @author lcwen
 * @version V1.0: UserDao.java 2019/10/17 9:41
 **/
public class UserDao extends JdbcTemplate {

    public UserDao(DataSource source) {
        super(source);
    }

    public List<?> selectAll(){
        String sql = "select * from user";

        return super.executeQuery(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws Exception {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAge(rs.getInt("age"));
                return user;
            }
        },null);

    }

}
