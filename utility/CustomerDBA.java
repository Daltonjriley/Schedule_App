package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Country;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class provides create, read, update and delete function for the customer objects.
 */
public class CustomerDBA {
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerZip = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int customerDivisionID = rs.getInt("Division_ID");
                Customer C = new Customer(customerId, customerName, customerAddress, customerZip, customerPhone, customerDivisionID);
                customerList.add(C);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }

    public static void deleteCustomer(Customer deleteCustomer) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, deleteCustomer.getId());
        ps.executeUpdate();
    }

    public static boolean checkCustomerAppointments(Customer deleteCustomer) throws SQLException {
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, deleteCustomer.getId());

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
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
            customerAppointments.add(A);
        }

        if (customerAppointments.size() == 0){
            return true;
        }else{
            return false;}

    }

    public static void modifyCustomer(Customer modifyCustomer) throws SQLException {

        String sql = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, " +
                "Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, modifyCustomer.getId());
        ps.setString(2, modifyCustomer.getName());
        ps.setString(3, modifyCustomer.getAddress());
        ps.setString(4, modifyCustomer.getZip());
        ps.setString(5, modifyCustomer.getPhone());
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(7, UserDBA.getCurrentUser().getUsername());
        ps.setString(8, String.valueOf(modifyCustomer.getDivision_id()));
        ps.setInt(9, modifyCustomer.getId());


        ps.executeUpdate();


    }

    public static void addCustomer(Customer newCustomer) throws SQLException {

        String sql = "INSERT INTO customers VALUE(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, newCustomer.getName());
        ps.setString(2, newCustomer.getAddress());
        ps.setString(3, newCustomer.getZip());
        ps.setString(4, newCustomer.getPhone());
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(6, UserDBA.getCurrentUser().getUsername());
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, UserDBA.getCurrentUser().getUsername());
        ps.setInt(9, newCustomer.getDivision_id());

        ps.executeUpdate();
    }
}
