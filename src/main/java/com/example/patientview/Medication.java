package com.example.patientview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Medication implements Initializable {
    @FXML
    protected TextField drugInput;
    @FXML
    protected ChoiceBox drugList;

    public void initSelect(String sqlQuery, String columnName, ChoiceBox choiceBox){
        try {
            String[] medArr = {} ;
            Connection connection = DBConnector.getConnection();
            ResultSet rs = connection.createStatement().executeQuery(sqlQuery);

            while(rs.next()){
                medArr = Arrays.copyOf(medArr,medArr.length+1);
                medArr[medArr.length-1] = rs.getString(columnName);
            }

            for(int i =0;i< medArr.length;i++){
                choiceBox.getItems().add(medArr[i]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void CloseWindow(){
        //Close the sign in window.
        Stage stage = (Stage) drugInput.getScene().getWindow();
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
    protected void addDrug() throws SQLException {
        Connection connection = DBConnector.getConnection();
        String drugName = drugInput.getText();

        connection.createStatement().executeUpdate("insert into medication(NAME_med) VALUES('"+drugName+"')");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) { //on page loading, we fill the choicebox
        initSelect("select m.NAME_med  from medication m","NAME_med",drugList);
    }
}
