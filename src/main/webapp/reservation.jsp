
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reservation Page</title>
    <!-- Add any necessary CSS -->
</head>
<body>

<h1>Reservation Page</h1>

<div>Email: <span><%= session.getAttribute("email") %></span></div>

<form action="/ReservationSearchServlet" method="post">
    <label for="checkInDate">Check-In Date:</label>
    <input type="date" id="checkInDate" name="checkInDate" required>

    <label for="checkOutDate">Check-Out Date:</label>
    <input type="date" id="checkOutDate" name="checkOutDate" required>

    <button type="submit">Submit Search</button>
</form>

<!-- Add any necessary JavaScript -->

</body>
</html>
