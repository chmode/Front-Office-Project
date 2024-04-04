<%@ page import="model.Room" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Rooms</title>
</head>
<body>

<h1>Available Rooms</h1>

<%
    List<Room> availableRooms = (List<Room>) request.getAttribute("availableRooms");

    if (availableRooms.isEmpty()) {
%>
<p>No available rooms for the selected date range.</p>
<%
} else {
%>
<table border="1">
    <tr>
        <th>Room ID</th>
        <th>Matricule</th>
        <th>Status</th>
        <th>Type ID</th>
    </tr>

    <%
        for (Room room : availableRooms) {
    %>
    <tr>
        <td><%= room.getId() %></td>
        <td><%= room.getMatricule() %></td>
        <td><%= room.isStatus() %></td>

    </tr>
    <%
        }
    %>
</table>
<%
    }
%>

</body>
</html>
