package com.mytask.template.jdbc;

import java.sql.ResultSet;

/**
 * 工程名:pattern-template
 * 包名:com.mytask.template.jdbc
 * 文件名:RowMapper
 * description:
 *
 * @author lcwen
 * @version V1.0: RowMapper.java 2019/10/17 9:14
 **/
public interface RowMapper<T> {

    T mapRow(ResultSet rs ,int rowNum) throws Exception;

}
