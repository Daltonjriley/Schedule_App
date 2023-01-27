package utility;

import controller.AddAppointmentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.User;
import utility.UserDBA;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * This class provides create, read, update and delete function for the Appointment objects.
 */
public class AppointmentDBA {
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointment A = new Appointment(appointmentId, appointmentTitle, description, location, type, start, end, customerID, userID, contactID);
                appointmentList.add(A);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

    public static ObservableList<Appointment> getCustomerAppointments(int customerId){
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments where Customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointment A = new Appointment(appointmentId, appointmentTitle, description, location, type, start, end, customerID, userID, contactID);
                appointmentList.add(A);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

    public static void addAppointment(Appointment newAppointment) throws SQLException {

        String sql = "INSERT INTO appointments VALUE(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, newAppointment.getTitle());
        ps.setString(2, newAppointment.getDescription());
        ps.setString(3, newAppointment.getLocation());
        ps.setString(4, newAppointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(newAppointment.getStart()));
        ps.setTimestamp(6, Timestamp.valueOf(newAppointment.getEnd()));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, UserDBA.getCurrentUser().getUsername());
        ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(10, UserDBA.getCurrentUser().getUsername());
        ps.setString(11, String.valueOf(newAppointment.getCustomer_id()));
        ps.setString(12, String.valueOf(newAppointment.getUser_id()));
        ps.setString(13, String.valueOf(newAppointment.getContact_id()));



        ps.executeUpdate();


    }

    public static void modifyAppointment(Appointment modifyAppointment) throws SQLException {

        String sql = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, " +
                "Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, modifyAppointment.getId());
        ps.setString(2, modifyAppointment.getTitle());
        ps.setString(3, modifyAppointment.getDescription());
        ps.setString(4, modifyAppointment.getLocation());
        ps.setString(5, modifyAppointment.getType());
        ps.setTimestamp(6, Timestamp.valueOf(modifyAppointment.getStart()));
        ps.setTimestamp(7, Timestamp.valueOf(modifyAppointment.getEnd()));
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(9, UserDBA.getCurrentUser().getUsername());
        ps.setString(10, String.valueOf(modifyAppointment.getCustomer_id()));
        ps.setString(11, String.valueOf(modifyAppointment.getUser_id()));
        ps.setString(12, String.valueOf(modifyAppointment.getContact_id()));
        ps.setInt(13, modifyAppointment.getId());


        ps.executeUpdate();


    }

    public static void deleteAppointment(Appointment deleteAppointment) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, deleteAppointment.getId());
        ps.executeUpdate();
    }
}
