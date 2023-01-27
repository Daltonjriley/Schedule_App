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
import model.Appointment;
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
 * This class provides functionality to the modify customer screen.
 */
public class ModifyCustomerController implements Initializable {
    public TextField cIDField;
    public TextField cZipField;
    public TextField cNameField;
    public TextField cAddressField;
    public TextField cPhoneField;
    public ComboBox countryBox;
    public ComboBox stateBox;
    public Button cancelButton;
    public Button saveButton;


    private static Customer modifyCustomer = null;

    public static void setModifyCustomer(Customer customer){
        modifyCustomer = customer;
    }

    /**
     * This method takes the user back to the main screen.
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
        int id = modifyCustomer.getId();;
        String name = cNameField.getText();
        String address = cAddressField.getText();
        String zip = cZipField.getText();
        String phone = cPhoneField.getText();
        FirstLevelDivisions division = (FirstLevelDivisions) stateBox.getSelectionModel().getSelectedItem();
        int division_id = division.getId();

        Customer c = new Customer(id, name, address, zip, phone,division_id);

        CustomerDBA.modifyCustomer(c);

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerFormView.fxml"));
        Stage primaryStage = (Stage) cancelButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(CountryDBA.getAllCountries());

        FirstLevelDivisions currentDivision = null;
        for(FirstLevelDivisions division: DivisionDBA.getDivisionList()){
            if(division.getId() == modifyCustomer.getDivision_id()){
                 currentDivision = division;
            }
        }

        stateBox.setValue(currentDivision);

        Country currentCountry = null;
        for (Country country : CountryDBA.getCountryList()){
            assert currentDivision != null;
            if (country.getId() == currentDivision.getCountry_id()){
                currentCountry = country;
            }

        }

        countryBox.setValue(currentCountry);

        cIDField.setText(String.valueOf(modifyCustomer.getId()));
        cZipField.setText(String.valueOf(modifyCustomer.getZip()));
        cNameField.setText(String.valueOf(modifyCustomer.getName()));
        cAddressField.setText(String.valueOf(modifyCustomer.getAddress()));
        cPhoneField.setText(String.valueOf(modifyCustomer.getPhone()));


    }
}
