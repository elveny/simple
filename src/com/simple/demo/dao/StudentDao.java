/**
 * elven.site Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package com.simple.demo.dao;

import com.simple.demo.model.Page;
import com.simple.demo.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author qiusheng.wu
 * @Filename StudentDao.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/10/30 22:45</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class StudentDao {
    private final static Logger LOGGER = Logger.getLogger("StudentDao");

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
        String url = "jdbc:mysql://39.108.56.253:3306/test?characterEncoding=utf8&useSSL=true";
        String user = "test";
        String password = "1223334444";
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public void close(Connection conn, Statement statement, ResultSet rs){
        try {
            if(rs != null){
                rs.close();
            }
            if(statement != null){
                statement.close();
            }
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.info("异常："+e.getMessage());
            e.printStackTrace();
        }
    }

    public Map<String, Object> getStudents(Map<String, String> keys, int pageNum, int pageSize) {
        Map<String, Object> result = new HashMap<>();
        List<Student> students = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            statement = conn.createStatement();

            StringBuilder sql = new StringBuilder("");
            sql.append("select id, name, sex, age from student ");
            if(keys != null && !keys.isEmpty()){
                int i = 0;
                for (String key: keys.keySet()) {
                    if(i == 0){
                        sql.append(" where ").append(key).append("='").append(keys.get(key)).append("'");
                    }
                    else {
                        sql.append(" and ").append(key).append("='").append(keys.get(key)).append("'");
                    }
                    i++;
                }
            }

            // 分页计算
            rs = statement.executeQuery(sql.toString());
            rs.last();
            int totalElements = rs.getRow();
            int currPage = pageNum;
            int totalPages = totalElements%pageSize == 0 ? totalElements/pageSize : totalElements/pageSize + 1;
            Page page = new Page(currPage, totalPages, totalElements, pageSize);
            result.put("page", page);

            // 分页查询
            int startRow = (pageNum-1)*pageSize;
            sql.append(" order by id desc limit ").append(startRow).append(",").append(pageSize);

            rs = statement.executeQuery(sql.toString());
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");
                students.add(new Student(id,name,sex,age));
            }
            result.put("elements", students);

        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.info("异常："+e.getMessage());
            e.printStackTrace();
        } finally {
            close(conn, statement, rs);
        }
        return result;

    }

    public Student getStudent(Student student) {
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            statement = conn.createStatement();

            StringBuilder sql = new StringBuilder("");
            sql.append("select id, name, sex, age from student where id = ").append(student.getId());

            rs = statement.executeQuery(sql.toString());
            if (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");
                student = new Student(id,name,sex,age);
            }

        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.info("异常："+e.getMessage());
            e.printStackTrace();
        } finally {
            close(conn, statement, rs);
        }
        return student;

    }

    public int addStudent(Student student){
        int result = 0;
        Connection conn = null;
        Statement statement = null;
        try {
            conn = getConnection();

            statement = conn.createStatement();

            StringBuilder sql = new StringBuilder("");
            sql.append("insert into student (name, sex, age) values('").append(student.getName()).append("','").append(student.getSex()).append("',").append(student.getAge()).append(") ");

            result = statement.executeUpdate(sql.toString());

        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.info("异常："+e.getMessage());
            e.printStackTrace();
        } finally {

            close(conn, statement, null);
        }
        return result;
    }

    public int updateStudent(Student student){
        int result = 0;
        Connection conn = null;
        Statement statement = null;
        try {
            conn = getConnection();

            statement = conn.createStatement();

            StringBuilder sql = new StringBuilder("");
            sql.append("update student set name = '").append(student.getName()).append("', sex = '").append(student.getSex()).append("', age = ").append(student.getAge()).append(" where id = ").append(student.getId());
            LOGGER.info("sql："+sql.toString());

            result = statement.executeUpdate (sql.toString());

        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.info("异常："+e.getMessage());
            e.printStackTrace();
        } finally {

            close(conn, statement, null);
        }
        return result;
    }

    public int deleteStudent(Student student){
        int result = 0;
        Connection conn = null;
        Statement statement = null;
        try {
            conn = getConnection();

            statement = conn.createStatement();

            StringBuilder sql = new StringBuilder("");
            sql.append("delete from student where id = ").append(student.getId());

            result = statement.executeUpdate (sql.toString());

        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.info("异常："+e.getMessage());
            e.printStackTrace();
        } finally {

            close(conn, statement, null);
        }
        return result;
    }
}