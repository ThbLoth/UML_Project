package com.example.patientview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class SignInController {

    @FXML
    protected TextField userInput;

    @FXML
    protected TextField passInput;

    @FXML
    protected CheckBox caregiverCheck;

    @FXML
    protected CheckBox patientCheck;

    @FXML
    protected Button signBtn;

    @FXML
    protected Label errorLabel;

    @FXML
    protected Button logInBtn;

    protected void openMainPage(){
        //Close the sign in window.
        Stage stage = (Stage) logInBtn.getScene().getWindow();
        stage.close();
        //Create and open the login window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainpage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage2 = new Stage();
            stage2.setTitle("Main menu");
            stage2.setScene(scene);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void signIn() throws SQLException, IOException {
        String username = userInput.getText();
        String password = passInput.getText();

        if(caregiverCheck.isSelected() && patientCheck.isSelected()){
            errorLabel.setVisible(true);
        }else if (caregiverCheck.isSelected() && !patientCheck.isSelected()){
            UserFactory caregiverUser = new Factory().createUser("caregiver");
            caregiverUser.CreateUser(username,password);
            FileWriter fileWriter = new FileWriter("userCAT.txt");
            fileWriter.write("1");
            fileWriter.close();
            openMainPage();
        }else if (!caregiverCheck.isSelected() && patientCheck.isSelected()){
            UserFactory patientUser = new Factory().createUser("patient");
            patientUser.CreateUser(username,password);
            FileWriter fileWriter = new FileWriter("userCAT.txt");
            fileWriter.write("2");
            fileWriter.close();
            openMainPage();
        }

    }

    @FXML
    protected void openLoginPage(){
        //Close the sign in window.
        Stage stage = (Stage) logInBtn.getScene().getWindow();
        stage.close();
        //Create and open the login window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LOGIN.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage2 = new Stage();
            stage2.setTitle("LOGIN");
            stage2.setScene(scene);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
