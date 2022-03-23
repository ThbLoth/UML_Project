package com.example.patientview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainMenuController implements Initializable {

    private int user_Cat;

    @FXML
    private Button disconnectBtn;

    @FXML
    private Button tookMedBtn;

    @FXML
    protected void disconnect(){
        //Close the sign in window.
        Stage stage = (Stage) disconnectBtn.getScene().getWindow();
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

    @FXML
    protected void openMedListMenu(){
        //Close the sign in window.
        Stage stage = (Stage) disconnectBtn.getScene().getWindow();
        stage.close();
        //Create and open the login window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("med_list.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage2 = new Stage();
            stage2.setTitle("Medication list");
            stage2.setScene(scene);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void openHistMenu(){
        //Close the sign in window.
        Stage stage = (Stage) disconnectBtn.getScene().getWindow();
        stage.close();
        //Create and open the login window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("History.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage2 = new Stage();
            stage2.setTitle("History");
            stage2.setScene(scene);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void openTakenMedMenu(){
        //Create and open the login window
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TakenMed.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 250, 150);
            Stage stage2 = new Stage();
            stage2.setTitle("I took my med !");
            stage2.setScene(scene);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void openMedicationMenu() throws FileNotFoundException {
        FileReader fileReader = new FileReader("userCAT.txt");
        Scanner scanner = new Scanner(fileReader);
        int userCat = 0;

        while(scanner.hasNextLine()){
            userCat = scanner.nextInt();
        }

        if(userCat ==1 ){
            //Close the security window.
            Stage stage = (Stage) disconnectBtn.getScene().getWindow();
            stage.close();
            //Create and open the modifying window
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("medication_security.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 250, 150);
                Stage stage2 = new Stage();
                stage2.setTitle("Password");
                stage2.setScene(scene);
                stage2.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("userCAT.txt");
            Scanner scanner = new Scanner(fileReader);

            while (scanner.hasNextLine()){
                user_Cat = scanner.nextInt();
            }

            if (user_Cat == 2){
                tookMedBtn.setVisible(true);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
