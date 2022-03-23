package com.example.patientview;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class HistoryController implements Initializable {

    protected int user_Cat;

    @FXML
    protected Button closeBtn;
    @FXML
    protected Button refreshBtn;

    @FXML
    protected ChoiceBox patList;

    @FXML
    private TableView<HistoryTable> hist_table;
    @FXML
    private TableColumn<HistoryTable,String> medName_col;
    @FXML
    private TableColumn<HistoryTable,String> lastTime_col;

    @FXML
    protected void closeScene(){
        //Close the sign in window.
        Stage stage = (Stage) closeBtn.getScene().getWindow();
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
    protected void refreshTable() throws SQLException {
        Connection connection = DBConnector.getConnection();
        int patID = 0;
        ResultSet rs = connection.createStatement().executeQuery("select ui.ID_user  from user_information ui where ui.username ='"+patList.getValue()+"'");
        while (rs.next()){
            patID = rs.getInt("ID_user");
        }
        initTable(patID);
    }

    protected void initTable(int patID) throws SQLException {

        ObservableList<HistoryTable> obList = FXCollections.observableArrayList();

        Connection connection = DBConnector.getConnection();
        ResultSet rs = connection.createStatement().executeQuery("select m.NAME_med, ml.last_taking  from medication_list ml natural join medication m where ml.ID_pat = '"+patID+"'");

        while(rs.next()){
            obList.add(new HistoryTable(rs.getString("NAME_med"),
                    rs.getString("last_taking")));
        }

        medName_col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMedName()));
        lastTime_col.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getLastTaken()));

        hist_table.setItems(obList);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            int userID=0;
            FileReader fileReader = new FileReader("userCAT.txt");
            FileReader fileReader2 = new FileReader("userID.txt");
            Scanner scanner = new Scanner(fileReader);
            Scanner scanner2 = new Scanner(fileReader2);

            while (scanner.hasNextLine()){
                user_Cat = scanner.nextInt();
            }

            while (scanner2.hasNextLine()){
                userID = scanner2.nextInt();
            }

            if (user_Cat ==1){
                refreshBtn.setVisible(true);
                patList.setVisible(true);

                String[] patArr = {} ;
                Connection connection = DBConnector.getConnection();
                ResultSet rs = connection.createStatement().executeQuery("select ui.username from user_information ui where ui.CAT_user =2");

                while(rs.next()){
                    patArr = Arrays.copyOf(patArr,patArr.length+1);
                    patArr[patArr.length-1] = rs.getString("username");
                }

                for(int i =0;i< patArr.length;i++){
                    patList.getItems().add(patArr[i]);
                }

            }else{
                initTable(userID);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
