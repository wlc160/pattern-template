package com.mytask.template.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 工程名:pattern-template
 * 包名:com.mytask.template.jdbc
 * 文件名:JdbcTemplate
 * description:
 *
 * @author lcwen
 * @version V1.0: JdbcTemplate.java 2019/10/17 9:12
 **/
public abstract class JdbcTemplate {

    private DataSource source;

    public JdbcTemplate(DataSource source){
        this.source = source;
    }

    public List<?> executeQuery(String sql ,RowMapper<?> rowMapper ,Object[] values){
        try{
            //1、获取连接
            Connection conn = this.connection();

            //2、创建语句集
            PreparedStatement ps = this.createPrepareStatement(conn,sql);

            //3、执行语句集
            ResultSet rs = this.executeQuery(ps,values);

            //4、处理结果集
            List<?> result = this.parseResultSet(rs,rowMapper);

            //5、关闭结果集
            this.closeResultSet(rs);

            //6、关闭语句集
            this.closeStatement(ps);

            //7、关闭连接
            this.closeConnection(conn);

            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    protected void closeConnection(Connection conn) throws Exception{
        conn.close();
    }

    protected void closeStatement(PreparedStatement ps) throws Exception {
        ps.close();
    }


    protected void closeResultSet(ResultSet rs) throws Exception{
        rs.close();
    }

    protected List<?> parseResultSet(ResultSet rs, RowMapper<?> rowMapper) throws Exception{
        List<Object> res = new ArrayList<Object>();
        int rowNum = 1;
        while (rs.next()){
            res.add(rowMapper.mapRow(rs,rowNum++));
        }
        return res;
    }

    protected ResultSet executeQuery(PreparedStatement ps ,Object[] value) throws Exception{
        for (int i = 0; i < value.length; i++) {
            ps.setObject(i,value[i]);
        }
        return ps.executeQuery();
    }

    protected PreparedStatement createPrepareStatement(Connection conn, String sql) throws Exception{
        return conn.prepareStatement(sql);
    }

    protected Connection connection() throws Exception{
        return source.getConnection();
    }


}
