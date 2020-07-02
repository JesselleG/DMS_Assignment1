<%-- 
    Document   : StudentForm
    Created on : 12/03/2020, 4:31:02 PM
    Author     : dqp6065 & vmm0807
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="styles.css" rel="stylesheet">
        <title>Student Database</title>
    </head>
    <body>
        <h1>WELCOME TO THE STUDENT DATABASE</h1>
            <h3>Active User:     
        <i><c:if test="${not empty sessionScope.CurrentUser}">
            <c:out value="${CurrentUser.username}"/>
            </c:if></i>
        </h3>
    </body>
    <div>
        <a href='<%= response.encodeURL("StudentServlet") %>'>
            <p>STUDENT LIST</p>
      </a>
        <form method="get" action="EnrolmentsEntityServlet">
            To find more information about a student, enter their ID:   
            <input type="text" name="studentID"/>
            <input type="submit" name="enter"/>
        </form>
    </div>
    <footer>        
        <a href='<%=response.encodeURL(request.getContextPath())%>'>
        <h3>Log Out</h3></a>
    </footer>
</html>
