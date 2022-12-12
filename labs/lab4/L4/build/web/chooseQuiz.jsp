<%-- 
    Document   : chooseQuiz
    Created on : Dec 12, 2022, 11:19:17 AM
    Author     : Harry
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Choose subject!</h1>
        <%
            List<String> quizzes = (List<String>) request.getAttribute("quizzes");
        %>
        <%
            for(int i = 0; i < quizzes.size(); i++) {
        %>
            <a href="/L4/quiz?q=<%= i + 1%>">${quizzes.get(i)}</a>
        <%
            }
        %>
        
    </body>
</html>
