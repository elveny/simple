<%@ page import="com.simple.demo.model.Page" %>
<%@ page import="com.simple.demo.model.Student" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: qiusheng.wu
  Date: 2017/10/31
  Time: 1:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>add</title>
</head>
<body>

<div>
    <form action="studentController" method="post">
        <input type="hidden" name="action" value="addDo">
        <table border="1" width="100%">
            <tr>
                <td width="20%" align="right">姓名：</td>
                <td width="80%"><input type="text" name="name" value=""/></td>
            </tr>
            <tr>
                <td width="20%" align="right">性别：</td>
                <td width="80%"><input type="text" name="sex" value=""/></td>
            </tr>
            <tr>
                <td width="20%" align="right">年龄：</td>
                <td width="80%"><input type="text" name="age" value=""/></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><button>添加</button></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
