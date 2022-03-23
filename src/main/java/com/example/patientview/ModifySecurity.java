package com.example.patientview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ModifySecurity {
    @FXML
    protected PasswordField passField;

    @FXML
    protected Button validateBtn;

    @FXML
    protected void checkPassword() throws SQLException, InterruptedException, FileNotFoundException {

        FileReader fileReader = new FileReader("userID.txt");
        Scanner scanner = new Scanner(fileReader);
        int caregiverID = 0;
        while(scanner.hasNextLine()){
            caregiverID = scanner.nextInt();
        }

        String pw = null;
        Connection connection = DBConnector.getConnection();
        ResultSet rs = connection.createStatement().executeQuery("select ui.password  from user_information ui where ui.ID_user = '"+caregiverID+"'");

        while(rs.next()){
            pw = rs.getString("password");

        }

        if (passField.getText().equals(pw)){
            //Close the security window.
            Stage stage = (Stage) passField.getScene().getWindow();
            stage.close();
            //Create and open the modifying window
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modify.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage2 = new Stage();
                stage2.setTitle("Modify the patient list");
                stage2.setScene(scene);
                stage2.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            validateBtn.setText("Password error");
        }
    }


}
