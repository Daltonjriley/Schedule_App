package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UserReportController implements Initializable {
    public Button backButton;
    public Label loginCount;


    ObservableList<String> allLoginsList = FXCollections.observableArrayList();

    File file = new File("login_activity.txt");

    Scanner scanner = new Scanner(file);

    public UserReportController() throws FileNotFoundException {
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportFormView.fxml"));
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setTitle("Scheduling Application");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onCalculate(ActionEvent actionEvent) {
        String loginAmount = String.valueOf(allLoginsList.size() - 1);
        loginCount.setText(loginAmount);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        while(scanner.hasNext()){
            allLoginsList.add(scanner.nextLine());
        }


    }


}
