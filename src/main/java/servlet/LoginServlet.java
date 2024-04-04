package servlet;

import dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
        RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate input
        if (isNullOrEmpty(email) || isNullOrEmpty(password)) {
            request.setAttribute("error", "Invalid input. Please fill in all fields.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
            return;
        }
        if (!isEmailRegistered(email)) {
            request.setAttribute("error", "Email is not exists");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            try {
                UserDao userDao = new UserDao();
                String storedHashedPassword = userDao.getHashPassword(email);

                if (storedHashedPassword != null && BCrypt.checkpw(password, storedHashedPassword)) {
                    User user = userDao.getUserByEmail(email);

                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("email", email);
                    //use to update status of all reservation rooms

                    session.setAttribute("isFirstVisit", true);


                    //id client if we need
                    response.sendRedirect("HomeServlet");
                } else {
                    // Passwords do not match, login failed
                    request.setAttribute("error", "Incorrect password. Please try again.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                    dispatcher.forward(request, response);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }

    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isEmailRegistered(String email) {
        boolean emailExists = false;

        try {
            UserDao userDao = new UserDao();
            emailExists = userDao.isEmailExists(email);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return emailExists;
    }

}

