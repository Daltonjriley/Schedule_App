package utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;
import model.Customer;

import java.util.Optional;

/**
 * This class provides templates for the different types of alerts used throughout the program.
 */
public class Alerts {
    public static void error(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    public static void customerDeleted(Customer customer){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Customer Deleted");
        alert.setTitle("Customer Deleted");
        alert.setContentText("Customer ID: " + customer.getId() + " Customer Name: " + customer.getName() + " deleted successfully");
        alert.show();
    }

    public static void appointmentDeleted(int appId, String appType){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Appointment Canceled");
        alert.setTitle("Appointment Canceled");
        alert.setContentText("Appointment ID: " + appId + " Appointment Type: " + appType);
        alert.show();
    }

    public static void upcomingAppointment(Appointment appointment){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Upcoming Appointment");
        alert.setTitle("An appointment within 15 minutes was found.");
        alert.setContentText("Appointment " + appointment.getId() + " is starting at " + appointment.formatDate(appointment.getStart()));
        alert.show();
    }

    public static void noUpcomingAppointment(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("No upcoming appointments");
        alert.setTitle("No upcoming appointments");
        alert.setContentText("No appointments within 15 minutes were found.");
        alert.show();
    }
}
