package servlet;

import dao.RoomTypeDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.RoomType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "RoomSearchServlet", value = "/RoomSearchServlet")
public class RoomSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve check-in and check-out dates from the form
        String checkInDate = request.getParameter("checkInDate");
        String checkOutDate = request.getParameter("checkOutDate");

        HttpSession session = request.getSession();
        session.setAttribute("checkInDate", checkInDate);
        session.setAttribute("checkOutDate", checkOutDate);

        try {
            // Get available room types based on the date range
            // Set the check-in and check-out dates as attributes in the request
            request.setAttribute("checkInDate", checkInDate);
            request.setAttribute("checkOutDate", checkOutDate);


            RoomTypeDao roomTypeDao = new RoomTypeDao();
            List<RoomType> availableRoomTypes = roomTypeDao.getAvailableRoomTypes(checkInDate, checkOutDate);

            // Set the available room types as an attribute in the request
            request.setAttribute("availableRoomTypes", availableRoomTypes);

            // Forward to the room search results page
            request.getRequestDispatcher("/room-search-results.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Log the exception for debugging purposes

            // Redirect to an error page or display an error message
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }


}