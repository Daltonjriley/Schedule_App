package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utility.Alerts;
import utility.AppointmentDBA;
import utility.UserDBA;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class provides functionality to login screen.
 */
public class LoginFormController implements Initializable {
    public TextField userIDField;
    public TextField passwordField;
    public Button logInButton;
    public Label userLocation;
    public Button exitButton;
    public Label locationLabel;
    public Label passwordLabel;
    public Label IDLabel;
    public Label mainLabel;

    public String errorTitle;
    public String errorHeader;
    public String errorContent;

    private String currentTime = Appointment.formatDate(LocalDateTime.now());

    ResourceBundle rb;

    /**
     * This method checks if the username and password are valid. If they are it takes them to the main screen.
     * If they aren't it displays an error message.
     * This method uses a lambda expression to create the string that is written into the login activity text file.
     * @param actionEvent The login button is pushed.
     * @throws IOException
     * @throws SQLException
     */
    public void onLogIn(ActionEvent actionEvent) throws IOException, SQLException {
        String username = userIDField.getText();
        String password = passwordField.getText();
        boolean foundUser = UserDBA.verifyLogin(username, password);
        if(foundUser){
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainFormView.fxml"));
            Stage primaryStage = (Stage) logInButton.getScene().getWindow();
            primaryStage.setTitle("Scheduling Application");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();



        }
        else {
            Alerts.error(errorTitle, errorHeader, errorContent);
        }



        String attempt = " failed to login";
        if(foundUser){
            attempt = " successfully logged in";
        }

        String finalAttempt = attempt;
        /**
         * This lambda expression creates the string that is written to the login activity text file.
         */
        Message m = () -> "User " + username + finalAttempt + " at - " + currentTime;

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter append = new PrintWriter(fileWriter);
        append.println(m.createString());
        append.close();
    }

    public void onExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Initializes the language of text on the screen according to the user language settings.
     * @param url
     * @param rb This the resource bundle with translations for the text on the screen.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        if(this.rb == null){
            this.rb = ResourceBundle.getBundle("resources/rb", Locale.getDefault());
        }
        System.out.println(Locale.getDefault());

        mainLabel.setText(this.rb.getString("mainLabel"));
        locationLabel.setText(this.rb.getString("locationLabel"));
        IDLabel.setText(this.rb.getString("IDLabel"));
        passwordLabel.setText(this.rb.getString("passwordLabel"));
        exitButton.setText(this.rb.getString("exitButton"));
        logInButton.setText(this.rb.getString("loginButton"));
        errorTitle = this.rb.getString("title");
        errorHeader = this.rb.getString("header");
        errorContent = this.rb.getString("content");

        userLocation.setText(ZoneId.systemDefault().getId());


    }

    interface Message {
        String createString();
    }
}
