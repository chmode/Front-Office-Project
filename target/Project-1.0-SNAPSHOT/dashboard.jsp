<%@ page import="model.User" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>

<%
    HttpSession sessionUser = request.getSession();
    User user = (User) sessionUser.getAttribute("user");

    if (user != null) {
%>
<h1>Welcome, <%= user.getName() %></h1>
<p>Email: <%= user.getEmail() %></p>
<p>Phone: <%= user.getPhone() %></p>
<p>Role: <%= user.getRole() %></p>
<p>Role: <%= user.getId()%></p>
<!-- Add more user details as needed -->

<form action="/LoginServlet" method="post">
    <input type="submit" value="Logout"/>
</form>
<%
} else {
%>
<p>User not logged in.</p>
<%
    }
%>

</body>
</html>
