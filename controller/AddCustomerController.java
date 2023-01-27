package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivisions;
import utility.CountryDBA;
import utility.CustomerDBA;
import utility.DivisionDBA;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class provides functionality to the add customer screen.
 */
public class AddCustomerController implements Initializable {
    public TextField cZipField;
    public TextField cNameField;
    public TextField cAddressField;
    public TextField cPhoneField;
    public ComboBox<Country> countryBox;
    public ComboBox<FirstLevelDivisions> stateBox;
    public TextField cIDField;
    public Button cancelButton;
    public Button saveButton;

    /**
     * This method redirects the user to the main customer screen when they press the cancel button.
     * @param actionEvent The cancel button is pressed.
     * @throws IOException
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerFormView.fxml"));
        Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onSave(ActionEvent actionEvent) throws SQLException, IOException {
        int id = 0;
        String name = cNameField.getText();
        String address = cAddressField.getText();
        String zip = cZipField.getText();
        String phone = cPhoneField.getText();
        FirstLevelDivisions division = stateBox.getSelectionModel().getSelectedItem();
        int division_id = division.getId();
        Customer c = new Customer(id, name, address, zip, phone,division_id);

        CustomerDBA.addCustomer(c);

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerFormView.fxml"));
        Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryBox.setItems(CountryDBA.getAllCountries());

    }

    public void onCountrySelected(ActionEvent actionEvent) {
        int countryID = ((Country)countryBox.getSelectionModel().getSelectedItem()).getId();
        ObservableList<FirstLevelDivisions> filteredDivisions = FXCollections.observableArrayList();
        for(FirstLevelDivisions division: DivisionDBA.getDivisionList()){
            if(division.getCountry_id() == countryID){
                filteredDivisions.add(division);
            }
        }
        stateBox.setItems(filteredDivisions);
    }
}
