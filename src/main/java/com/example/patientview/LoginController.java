package com.example.patientview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginController {

    @FXML
    protected Button signinBTN;

    @FXML
    protected TextField userInput;

    @FXML
    protected PasswordField passwordInput;

    @FXML
    protected Label incorrectLabel;

    @FXML
    protected void verifyCredentials() throws SQLException, IOException {
        String userCred = userInput.getText();
        String passwordCred = userInput.getText();
        int userVerify=0;
        String dbPassword ="";

        Connection connection = DBConnector.getConnection();
        ResultSet rs = connection.createStatement().executeQuery("select 1  from user_information ui where ui.username ='"+userCred+"'");
        while (rs.next()){
            userVerify = rs.getInt("1");
        }

        if (userVerify==1){
            ResultSet rs2 = connection.createStatement().executeQuery("select ui.password from user_information ui where ui.username ='"+userCred+"'");
            while (rs2.next()){
                dbPassword = rs2.getString("password");
            }

            if (passwordInput.getText().equals(dbPassword)){
                //Write user cat
                FileWriter fileWriter = new FileWriter("userCAT.txt");
                ResultSet rs3 = connection.createStatement().executeQuery("select ui.CAT_user from user_information ui where ui.username = '"+userCred+"'");
                while (rs3.next()) {
                    fileWriter.write(rs3.getString("CAT_user"));
                    fileWriter.close();
                }

                //Write user ID
                FileWriter fileWriter2 = new FileWriter("userID.txt");
                ResultSet rs4 = connection.createStatement().executeQuery("select ui.ID_user from user_information ui where ui.username ='"+userCred+"'");
                while(rs4.next()){
                    fileWriter2.write(rs4.getString("ID_user"));
                    fileWriter2.close();
                }

                //Close the login window.
                Stage stage = (Stage) signinBTN.getScene().getWindow();
                stage.close();
                //Create and open the sign in window
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
            }else{
                incorrectLabel.setVisible(true);
            }

        }else{
            incorrectLabel.setVisible(true);
        }
    }

    @FXML
    protected void openSignInPage(){
        //Close the login window.
        Stage stage = (Stage) signinBTN.getScene().getWindow();
        stage.close();
        //Create and open the sign in window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SIGN.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage2 = new Stage();
            stage2.setTitle("SIGN IN");
            stage2.setScene(scene);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
