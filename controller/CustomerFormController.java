package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.FirstLevelDivisions;
import utility.Alerts;
import utility.CustomerDBA;
import utility.DivisionDBA;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class provides functionality to the main customer screen.
 */
public class CustomerFormController implements Initializable {
    @FXML
    private TableView customerTable;
    public TableColumn cIDCol;
    public TableColumn cNameCol;
    public TableColumn cAddressCol;
    public TableColumn cZipCol;
    public TableColumn cStateCol;
    public TableColumn cPhoneCol;
    public Button modButton;
    public Button delButton;
    public Button addButton;
    public Button backButton;

    private ObservableList<Customer> customers = CustomerDBA.getAllCustomers();

    public ObservableList<Customer> getCustomers(){
        return customers;
    }


    /**
     * This method takes the user to the modify customer screen when the button is pressed.
     * @param actionEvent The modify customer button is pressed.
     * @throws IOException
     */
    public void onModifyCustomer(ActionEvent actionEvent) throws IOException {

        ModifyCustomerController.setModifyCustomer((Customer) customerTable.getSelectionModel().getSelectedItem());

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyCustomerFormView.fxml"));
        Stage primaryStage = (Stage) modButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer deleteCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if (deleteCustomer == null) {
            utility.Alerts.error("Error", "No customer selected.", "Please select a customer.");
        } else {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("You are about to delete a customer.");
            confirm.setHeaderText("Delete Customer?");
            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (CustomerDBA.checkCustomerAppointments(deleteCustomer) == false) {
                    utility.Alerts.error("Error", "This customer has existing appointments.", "Please delete this customer's appointments to delete this customer.");
                } else {
                    CustomerDBA.deleteCustomer(deleteCustomer);
                    customers = CustomerDBA.getAllCustomers();
                    customerTable.setItems(customers);
                    Alerts.customerDeleted(deleteCustomer);
                }
            }
        }
    }
    /**
     * This method takes the user to the add customer screen when the button is pressed.
     * @param actionEvent The add customer button is pressed.
     * @throws IOException
     */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));
        Stage primaryStage = (Stage) addButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method redirects the user to the main screen when the press the back button.
     * @param actionEvent The back button is pressed.
     * @throws IOException
     */
    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainFormView.fxml"));
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method populates the table with the customer objects.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerTable.setItems(customers);

        cIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        cNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        cZipCol.setCellValueFactory(new PropertyValueFactory<>("zip"));
        cStateCol.setCellValueFactory(new PropertyValueFactory<>("division_id"));
        cPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }
}
