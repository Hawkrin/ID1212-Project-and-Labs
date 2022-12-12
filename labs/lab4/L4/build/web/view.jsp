<html>
<head><title>Guess game.</title></head>
<body>
    <%
        Object status =  request.getAttribute("status");
        if (status.equals(false)) {
    %>
        <p>Welcome to The number Guess Game. Guess a number between 1 and 100.</p>
    <% 
        } else {
            Object guesses = request.getAttribute("guesses");
            Object answer = request.getAttribute("answer");
    %>
        <p> Amount of tries: ${guesses} </p>
        <% if (answer.equals(-1)) { %>
        <p>The number is lower</p>
        <% } else if (answer.equals(1)) { %>
        <p>The number is higher</p>
        <% } else { %>
        <p>You have won!</p>
        <% } %>
    <% 
        }
    %>
    
    <form name="guessForm" method="get">
        <input type="text" name="guess">
        <input type="submit" value="Guess">
    </form>
</body>
</html>