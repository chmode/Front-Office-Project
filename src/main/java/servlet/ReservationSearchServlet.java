package servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ReservationSearchServlet", value = "/ReservationSearchServlet")
public class ReservationSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {





        // Forward to the reservation.jsp page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/reservation.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve reservation details from the form

        String checkInDate = request.getParameter("checkInDate");
        String checkOutDate = request.getParameter("checkOutDate");

        // Other form fields can be retrieved in a similar manner

        // Save the dates to the session for later use
        HttpSession session = request.getSession();
        session.setAttribute("checkInDate", checkInDate);
        session.setAttribute("checkOutDate", checkOutDate);

        // Redirect to a page where you can display available rooms or process the reservation
        response.sendRedirect(request.getContextPath() + "/available-rooms.jsp");
    }

    // Other methods...
}
