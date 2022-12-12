<%-- 
    Document   : quiz
    Created on : Dec 12, 2022, 12:44:48 PM
    Author     : Harry
--%>

<%@page import="java.util.List"%>
<%@page import="extra.Questions"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Integer score = (Integer) request.getAttribute("points");
            List<Questions> questions = (List<Questions>) request.getAttribute("questions");
            System.out.println("High score: " + score);
        %>
        <h1>Quizz!  </h1>
        <h3>Previous score: <%= score %> / <%= questions.size() %></h3> 
        <form method="POST">
            
        <% 
            
            
            for (int i = 0; i < questions.size(); i++) {
        %>
        
                <h1><%= questions.get(i).getText() %></h1><!<!-- Title of question -->
        <%
                    for (int j = 0; j < questions.get(i).getOptions().size(); j++) {
        %> 
                        <a><%= questions.get(i).getOptions().get(j) %></a>
                        <input type="checkbox" name="<%= i + "," + j %>" value="1">

        <%
                    }
            }
        %>
        <br>
        <br>
        <input type="submit" value="Submit">
            
        </form>
        
            
    </body>
</html>
