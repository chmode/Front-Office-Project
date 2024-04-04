package servlet;

import dao.ClientDao;
import dao.ReservationDao;
import dao.RoomDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Reservation;
import model.Room;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@WebServlet(name = "ReservationServlet", value = "/ReservationServlet")
public class ReservationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve reservation details from the form

        // long roomTypeId = Long.parseLong(request.getParameter("roomType")); // Assuming roomType is a long

        HttpSession session = request.getSession();
        // Retrieve user's email from the session
        String userEmail = (String) session.getAttribute("email");
        // Retrieve check Int Out Date
        String checkInDate = (String )session.getAttribute("checkInDate");
        String checkOutDate = (String )session.getAttribute("checkOutDate");
        // Retrieve Id of Room Type
        String roomTypeParameter = request.getParameter("roomType");
        long roomTypeId = Long.parseLong(roomTypeParameter);

        // Get the client ID based on the user's email
        ClientDao client = null;
        RoomDao roomDao = null;

        try {
            client = new ClientDao();
            roomDao = new RoomDao();
            long clientId = client.getClientIdByEmail(userEmail);

            Room room = roomDao.getAvailableRoom(roomTypeId,checkInDate,checkOutDate);
            System.out.println(room);

            Reservation reservation = new Reservation(checkInDate,checkOutDate,room);
            ReservationDao reservationDao = new ReservationDao();
            reservationDao.saveReservation(clientId,reservation);

            System.out.println("Room Details:");
            System.out.println("Room Type Id: " + roomTypeId);
            System.out.println("Check-In Date: " + checkInDate);
            System.out.println("Check-Out Date: " + checkOutDate);
            System.out.println("Client Id: " + clientId);
            System.out.println("Room Type: " + room.getRoomType().getLabel());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/reservation-success.jsp");
            dispatcher.forward(request, response);
            sendReservationEmail(userEmail, checkInDate, checkOutDate, room.getRoomType().getLabel());

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendReservationEmail(String userEmail, String checkInDate, String checkOutDate, String roomTypeLabel) {
        // Set up mail properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");

        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("fcb.fcb.achraf@gmail.com", "ifibsknvrxlwunfr");
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress("fcb.fcb.achraf@gmail.com"));

            // Set To: header field of the header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

            // Set Subject: header field
            message.setSubject("Reservation Confirmation");

            // Now set the actual message
            String emailContent = "Dear customer,\n\nYour reservation details:\n"
                    + "Room Type: " + roomTypeLabel + "\n"
                    + "Check-In Date: " + checkInDate + "\n"
                    + "Check-Out Date: " + checkOutDate + "\n\n"
                    + "Thank you for choosing our hotel!\n";

            message.setText(emailContent);

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully...");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }



}