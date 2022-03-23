package com.example.patientview;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MedList implements Initializable{

    public static String getPatientName(int userID) throws SQLException, FileNotFoundException {
        Connection connection = DBConnector.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("select username from user_information WHERE ID_user = '"+userID+"'");
        while(resultSet.next()){
            String name = resultSet.getString("username");
            return name;
        }

        return null;
    }
    protected int user_Cat;

    @FXML
    private Label labelMed;

    @FXML
    private Button modifyBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private ChoiceBox patientList;

    //patient name
    @FXML
    protected Label patientNameLabel;
    //table manager
    @FXML
    private TableView<MedTable> med_tab;

    @FXML
    private TableColumn<MedTable,String> medName_col;
    @FXML
    private TableColumn<MedTable,String> medQty_col;
    @FXML
    private TableColumn<MedTable,String> medDoTime_col;

    @FXML
    protected void closeScene(){
        //Close the sign in window.
        Stage stage = (Stage) returnBtn.getScene().getWindow();
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
    protected void open_Modify_Scene_Security(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modify_security.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 250, 150);
            Stage stage = new Stage();
            stage.setTitle("Security");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void initTable(int patID) throws SQLException {

        ObservableList<MedTable> obList = FXCollections.observableArrayList();

        Connection connection = DBConnector.getConnection();
        ResultSet rs = connection.createStatement().executeQuery("select m.NAME_med,ml.qty,ml.dosing_times from medication_list ml left join medication m on m.ID_med = ml.ID_med where ml.ID_pat = '"+patID+"';");

        while(rs.next()){
            obList.add(new MedTable(rs.getString("NAME_med"),
                    rs.getString("qty"),
                    rs.getString("dosing_times")));
        }

        medName_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMedName()));
        medQty_col.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getQty()));
        medDoTime_col.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getDosingTime()));

        med_tab.setItems(obList);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            //set caregiver button visibility depending of the data stored

            FileReader fileReader = new FileReader("userCAT.txt");
            Scanner scanner = new Scanner(fileReader);

            int userID = 0;

            FileReader fileReader2 = new FileReader("userID.txt");
            Scanner scanner2 = new Scanner(fileReader2);
            while (scanner2.hasNextLine()){
                userID = scanner2.nextInt();
            }

            while(scanner.hasNextLine()){
                user_Cat = scanner.nextInt();
            }

            if (user_Cat == 1){
                modifyBtn.setVisible(true);
                patientList.setVisible(true);

                try {
                    String[] medArr = {} ;
                    Connection connection = DBConnector.getConnection();
                    ResultSet rs = connection.createStatement().executeQuery("select ui.username from user_information ui where ui.CAT_user =2");

                    while(rs.next()){
                        medArr = Arrays.copyOf(medArr,medArr.length+1);
                        medArr[medArr.length-1] = rs.getString("username");
                    }

                    for(int i =0;i< medArr.length;i++){
                        patientList.getItems().add(medArr[i]);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                String pName = getPatientName(userID);
                patientNameLabel.setText(pName);
                initTable(userID);
                labelMed.setVisible(true);
            }

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void refresh_Table() throws FileNotFoundException, SQLException {
        FileReader fileReader = new FileReader("userCAT.txt");
        Scanner scanner = new Scanner(fileReader);
        Connection connection = DBConnector.getConnection();

        while(scanner.hasNextLine()){
            user_Cat = scanner.nextInt();
        }

        if (user_Cat == 1){
            int patID =0;
            ResultSet rs1 = connection.createStatement().executeQuery("select ui.ID_user  from user_information ui where ui.username ='"+patientList.getValue()+"'");
            while(rs1.next())
                patID = rs1.getInt("ID_user");
            initTable(patID);

        }
    }
}
