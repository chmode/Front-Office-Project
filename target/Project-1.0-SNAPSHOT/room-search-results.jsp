<%@ page import="java.util.List" %>
<%@ page import="model.RoomType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Available Room Types</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        var tailwind = {
            theme: {
                extend: {
                    colors: {
                        clifford: '#da373d'
                    }
                }
            }
        };
    </script>
    <style type="text/tailwindcss">
        @layer utilities {
            .content-auto {
                content-visibility: auto;
            }
        }
    </style>
    <script src="https://cdn.tailwindcss.com?plugins=forms,typography,aspect-ratio,line-clamp"></script>

    <script>
        // Function to validate dates
        function validateDates() {
            var checkInDate = document.getElementById('checkInDate').value;
            var checkOutDate = document.getElementById('checkOutDate').value;

            var today = new Date().toISOString().split('T')[0];

            if (checkInDate < today) {
                alert('Please select a check-in date equal to or after today.');
                return false;
            }

            if (checkOutDate <= checkInDate) {
                alert('Check-out date must be after the check-in date.');
                return false;
            }

            return true;
        }
    </script>
</head>
<body class="bg-gray-100">
<div class="w-full min-h-screen flex justify-center items-center">
    <div class="grid grid-cols-1">
        <h1 class="text-2xl font-bold mb-4">Available Room Types</h1>

        <p>Check-In Date: <%= request.getAttribute("checkInDate") %></p>
        <p>Check-Out Date: <%= request.getAttribute("checkOutDate") %></p>

        <%
            List<RoomType> roomTypes = (List<RoomType>) request.getAttribute("availableRoomTypes");
            if (roomTypes != null && !roomTypes.isEmpty()) {
        %>
        <form action="/ReservationServlet" method="post" class="mb-4 w-full max-w-[439px] p-6 bg-white rounded-lg shadow flex flex-col justify-center items-center gap-6">
            <label for="roomTypeSelect" class="block mb-2">Select Room Type:</label>

            <select name="roomType" id="roomTypeSelect" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-purple focus:transition">
                <%
                    for (RoomType roomType : roomTypes) {
                %>
                <option value="<%= roomType.getId() %>">
                    <strong><%= roomType.getLabel() %></strong><br>
                    Number of Persons: <%= roomType.getNumberOfPersons() %><br>
                </option>
                <%
                    }
                %>
            </select>
            <input type="submit" value="Submit" class="mt-2 px-4 py-2 bg-violet-800 rounded-lg text-white hover:bg-violet-700 active:bg-violet-900">
        </form>
        <%
        } else {
        %>
        <p>No available room types for the selected date range.</p>
        <%
            }
        %>
    </div>
</div>



</body>
</html>
