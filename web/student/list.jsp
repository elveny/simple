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
    <title>list</title>
</head>
<body>
<form id="listForm" name="listForm" action="studentController" method="post">
    <input type="hidden" name="action" value="list">
    <input type="hidden" id="pageNum" name="pageNum" value="">
    <input type="hidden" id="pageSize" name="pageSize" value="">
    姓名：<input type="text" id="name" name="name" value="<%=request.getParameter("name") == null ? "" : request.getParameter("name")%>"/>
    <input type="button" value="查询" onclick="query()"/>
    <a href="studentController?action=addPage" rel="self">新增</a>
<div>
</div>
<div>
    <table border="1" width="100%">
        <thead>
            <tr>
                <td>编号</td>
                <td>姓名</td>
                <td>性别</td>
                <td>年龄</td>
                <td>操作</td>
            </tr>
        </thead>
        <tbody>
            <%
                List<Student> students = (List<Student>) request.getAttribute("students");
                if(students != null && !students.isEmpty()){
                    for(Student student : students){
            %>
                <tr>
                    <td><%=student.getId()%></td>
                    <td><%=student.getName()%></td>
                    <td><%=student.getSex()%></td>
                    <td><%=student.getAge()%></td>
                    <td>
                        <a href="studentController?action=updatePage&id=<%=student.getId()%>" rel="self">修改</a>
                        <a href="studentController?action=delete&id=<%=student.getId()%>" rel="self">删除</a>
                    </td>
                </tr>
            <%
                    }
            }
            %>
            <tr>
                <td colspan="5" align="right">
                    <%
                        Page pageObj = (Page) request.getAttribute("page");
                        int prev = pageObj.getCurrPage() - 1;
                        int next = pageObj.getCurrPage() + 1;
                        if(prev < 1){
                            prev = 1;
                        }

                        if(next > pageObj.getTotalPages()){
                            next = pageObj.getTotalPages();
                        }

                    %>
                    记录总数：<%=pageObj.getTotalElements()%> &nbsp;|&nbsp;

                    <%
                        if(pageObj.getCurrPage() != 1){
                    %>
                    <a href="javascript: void(0);" onclick="paging('<%=prev%>', '<%=pageObj.getPageSize()%>')"><<</a>
                    <%
                        }
                    %>

                     <%=pageObj.getCurrPage()%>/<%=pageObj.getTotalPages()%>

                    <%
                        if(pageObj.getCurrPage() != pageObj.getTotalPages()){
                    %>
                    <a href="javascript: void(0);" onclick="paging('<%=next%>', '<%=pageObj.getPageSize()%>')">>></a>
                    <%
                        }
                    %>
                </td>
            </tr>
        </tbody>
    </table>
</div>
</form>
<script language="javascript" type="text/javascript">
    function paging(pageNum, pageSize){
        document.getElementById("pageNum").value = pageNum;
        document.getElementById("pageSize").value = pageSize;
        document.forms[0].submit();
    }

    function query() {
        document.getElementById("pageNum").value = 1;
        document.getElementById("pageSize").value = '<%=pageObj.getPageSize()%>';
        document.forms[0].submit();
    }
</script>
</body>
</html>
