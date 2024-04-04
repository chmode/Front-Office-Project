package servlet;

import dao.ClientDao;
import dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SingUpServlet", value = "/SingUpServlet")
public class SingUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
        RequestDispatcher view  = request.getRequestDispatcher("/signup.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String role = "client";
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");

        // Validate input
        if (isNullOrEmpty(name) || isNullOrEmpty(email) || isNullOrEmpty(password) || isNullOrEmpty(confirm)) {
            request.setAttribute("error", "Invalid input. Please fill in all fields.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Check if the email is already registered
        if (isEmailRegistered(email)) {
            request.setAttribute("error", "Email is already registered. Please use a different email.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/singup.jsp");
            dispatcher.forward(request, response);
            return;

        }
        if (!isPasswordConfirm(password,confirm)) {
            request.setAttribute("error", "Password is not confirmed ");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/singup.jsp");
            dispatcher.forward(request, response);
            return;

        }

        // Create a User object
        User user = new User(name, email,phone,role, password);

        // Add the user to the database
        try {
            ClientDao clientDao = new ClientDao();
            clientDao.createClient(user);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("email", email);
            //use to update status of all reservation rooms

            session.setAttribute("isFirstVisit", true);
            //id client if we need
            response.sendRedirect("HomeServlet");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("/singup.jsp");
        }
    }

    // Utility methods for validation

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

    private boolean isPasswordConfirm(String password, String confirm) {
        return password.equals(confirm);
    }




}
