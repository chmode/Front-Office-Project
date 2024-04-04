import dao.RoomTypeDao;
import model.RoomType;

import java.sql.SQLException;
import java.util.List;

 class ExampleClass {
    public static void main(String[] args) {
        try {
            RoomTypeDao roomTypeDao = new RoomTypeDao();
            List<RoomType> availableRoomTypes = roomTypeDao.getAvailableRoomTypes("2023-01-01", "2023-01-10");
            roomTypeDao.close();

            // Check if the list is not empty
            if (!availableRoomTypes.isEmpty()) {
                System.out.println("Available Room Types:");
                for (RoomType roomType : availableRoomTypes) {
                    System.out.println("ID: " + roomType.getId());
                    System.out.println("Label: " + roomType.getLabel());
                    System.out.println("Description: " + roomType.getDescription());
                    System.out.println("Number of Persons: " + roomType.getNumberOfPersons());
                    System.out.println("-----------------------");
                }
            } else {
                System.out.println("No available room types for the selected date range.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
}
