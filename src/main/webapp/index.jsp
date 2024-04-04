<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Index Page</title>
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
<%
    // Check if the session is null
    if (session.getAttribute("email") == null) {
        response.sendRedirect("login.jsp"); // Redirect to the login page
    } else {
        // Continue rendering the content of the index page
%>
<div class="w-full min-h-screen flex justify-center items-center">
    <div class="grid grid-cols-1">
        <h1 class="text-2xl font-bold mb-4">Room Availability Search</h1>

        <c:if test="${not empty requestScope.error}">
            <div class="text-red-500">${requestScope.error}</div>
        </c:if>

        <div class="mb-4">Email: <span><%= session.getAttribute("email") %></span></div>
        <form action="RoomSearchServlet" method="get" onsubmit="return validateDates()" class="w-full max-w-[439px] p-6 bg-white rounded-lg shadow flex flex-col justify-center items-center gap-6">
            <label for="checkInDate" class="mb-2">Check-In Date:</label>
            <input type="date" id="checkInDate" name="checkInDate" required class="w-full h-10 p-4 py-3 bg-white bg-opacity-0 rounded-lg border border-gray-300 focus:border-purple focus:transition">

            <label for="checkOutDate" class="mb-2">Check-Out Date:</label>
            <input type="date" id="checkOutDate" name="checkOutDate" required class="w-full h-10 p-4 py-3 bg-white bg-opacity-0 rounded-lg border border-gray-300 focus:border-purple focus:transition">

            <button type="submit" class="w-full px-4 py-2.5 bg-violet-800 rounded-lg shadow hover:bg-violet-700 active:bg-violet-900">
                <span class="text-white text-sm font-bold uppercase">Search Available Rooms</span>
            </button>
        </form>
    </div>
</div>

<%
    }
%>



</body>
</html>