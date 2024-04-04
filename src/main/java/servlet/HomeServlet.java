package servlet;

import dao.RoomDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "HomeServlet", value = "/HomeServlet")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if the user is logged in
        if (session.getAttribute("email") == null) {
            // If not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            // If logged in, set isFirstVisit and forward to the index page
            session.setAttribute("isFirstVisit", true);

            // Update room status logic
            updateRoomStatus();

            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    private void updateRoomStatus() {

        try {
            RoomDao roomDao = new RoomDao();
            roomDao.updateRoomStatusBasedOnDate();
            roomDao.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}