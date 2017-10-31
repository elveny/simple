/**
 * msxf.com Inc.
 * Copyright (c) 2017-2026 All Rights Reserved.
 */
package com.simple.demo.controller;

import com.simple.demo.dao.StudentDao;
import com.simple.demo.model.Page;
import com.simple.demo.model.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author qiusheng.wu
 * @Filename StudentController.java
 * @description
 * @Version 1.0
 * @History <li>Author: qiusheng.wu</li>
 * <li>Date: 2017/10/30 22:44</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class StudentController extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger("StudentController");
    private final static String ACTION_LIST = "list";
    private final static String ACTION_ADD_PAGE = "addPage";
    private final static String ACTION_ADD_DO = "addDo";
    private final static String ACTION_UPDATE_PAGE = "updatePage";
    private final static String ACTION_UPDATE_DO = "updateDo";
    private final static String ACTION_DELETE = "delete";
    private final static String ACTION_QUERY = "query";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            String action = req.getParameter("action");
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String sex = req.getParameter("sex");
            String age = req.getParameter("age");
            String pageNum = req.getParameter("pageNum");
            String pageSize = req.getParameter("pageSize");
            LOGGER.info("action:"+action+",id:"+id+",name:"+name+",sex:"+sex+",age:"+age+",pageNum:"+pageNum+",pageSize:"+pageSize);

            Student student = new Student();
            Map<String, String> keys = new HashMap<>();
            if(id != null && !"".equals(id)){
                student.setId(Integer.parseInt(id));
                keys.put("id", id);
            }

            if(name != null && !"".equals(name)){
                student.setName(name);
                keys.put("name", name);
            }

            if(sex != null && !"".equals(sex)){
                student.setSex(sex);
                keys.put("sex", sex);
            }

            if(age != null && !"".equals(age)){
                student.setAge(Integer.parseInt(age));
                keys.put("age", age);
            }

            StudentDao studentDao = new StudentDao();
            String dispatcherUrl = "/index.jsp";

            if(ACTION_LIST.equals(action)){
                Map<String, Object> dataMap= studentDao.getStudents(keys, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
                List<Student> students = (List<Student>) dataMap.get("elements");
                Page page = (Page) dataMap.get("page");
                req.setAttribute("students", students);
                req.setAttribute("page", page);
                dispatcherUrl = "/student/list.jsp";
            }
            else if(ACTION_ADD_PAGE.equals(action)){
                dispatcherUrl = "/student/add.jsp";
            }
            else if(ACTION_ADD_DO.equals(action)){
                int result = studentDao.addStudent(student);
                dispatcherUrl = "studentController?action=list&pageNum=1&pageSize=10";
            }
            else if(ACTION_UPDATE_PAGE.equals(action)){
                student = studentDao.getStudent(student);
                req.setAttribute("student", student);
                dispatcherUrl = "/student/update.jsp";
            }
            else if(ACTION_UPDATE_DO.equals(action)){
                int result = studentDao.updateStudent(student);
                dispatcherUrl = "studentController?action=list&pageNum=1&pageSize=10";
            }
            else if(ACTION_DELETE.equals(action)){
                int result = studentDao.deleteStudent(student);
                req.setAttribute("result", result);
                dispatcherUrl = "studentController?action=list&pageNum=1&pageSize=10";
            }
            else if(ACTION_QUERY.equals(action)){
                Student student1 = studentDao.getStudent(student);
                req.setAttribute("student", student1);
            }
            req.getRequestDispatcher(dispatcherUrl).forward(req,resp);
            return;
        }
        catch (Exception e){
            LOGGER.info("异常："+e.getMessage());
            e.printStackTrace();
        }
    }
}