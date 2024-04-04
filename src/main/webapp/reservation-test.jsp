<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Display Submitted Data</title>
    <!-- Add any necessary CSS -->
</head>
<body>

<h1>Submitted Reservation Data</h1>

<div>
    <strong>Check-In Date:</strong> <span>${submittedReservation.checkInDate}</span><br>
    <strong>Check-Out Date:</strong> <span>${submittedReservation.checkOutDate}</span><br>
    <strong>Room Type:</strong> <span>${submittedReservation.room.type.label}</span><br>
    <strong>Room ID:</strong> <span>${submittedReservation.room.id}</span><br>
    <!-- Add more fields as needed -->
</div>

<!-- Add any additional information or links as needed -->

</body>
</html>
