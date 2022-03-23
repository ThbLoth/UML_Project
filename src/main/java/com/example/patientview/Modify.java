package com.example.patientview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Modify implements Initializable {

    @FXML
    protected ChoiceBox patientList;

    @FXML
    protected ChoiceBox drugAddList;

    @FXML
    protected ChoiceBox removeDrugList;

    @FXML
    protected ChoiceBox modifyDrugList;

    @FXML
    protected Button returnBtn;

    @FXML
    protected Button addBtn;

    @FXML
    protected Button modifyDrugBtn;

    @FXML
    protected TextField addPills;

    @FXML
    protected TextField addDosingT;

    @FXML
    protected TextField modifyPills;

    @FXML
    protected TextField modifyDosingT;

    @FXML
    protected void closeScene(){
        Stage stage = (Stage) returnBtn.getScene().getWindow();
        stage.close();
    }

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

    public void refreshList() throws SQLException {
        int patID = 0;

        removeDrugList.getItems().clear();
        modifyDrugList.getItems().clear();

        Connection connection = DBConnector.getConnection();
        ResultSet rs1 = connection.createStatement().executeQuery("select ui.ID_user  from user_information ui where ui.username ='"+patientList.getValue()+"'");
        while(rs1.next())
            patID = rs1.getInt("ID_user");

        initSelect("select m.NAME_med  from medication_list ml inner join medication m on m.ID_med = ml.ID_med where ml.ID_pat ='"+patID+"'","NAME_med",removeDrugList);
        initSelect("select m.NAME_med  from medication_list ml inner join medication m on m.ID_med = ml.ID_med where ml.ID_pat ='"+patID+"'","NAME_med",modifyDrugList);
    }

    @FXML
    protected void addDrugFN(){
        int medID=0;
        int patID=0;

        try (Connection connection = DBConnector.getConnection()) {

            ResultSet rs = connection.createStatement().executeQuery("select m.ID_med  from medication m where m.NAME_med ='"+drugAddList.getValue()+"'");
            while(rs.next())
                medID = rs.getInt("ID_med");

            ResultSet rs1 = connection.createStatement().executeQuery("select ui.ID_user  from user_information ui where ui.username ='"+patientList.getValue()+"'");
            while(rs1.next())
                patID = rs1.getInt("ID_user");


            connection.createStatement().executeUpdate("insert into medication_list(ID_pat,ID_med,qty,dosing_times) VALUES('"+patID+"','"+medID+"','"+addPills.getText()+"','"+addDosingT.getText()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void removeDrugFN(){
        int medID=0;
        int patID=0;

        try (Connection connection = DBConnector.getConnection()) {

            ResultSet rs = connection.createStatement().executeQuery("select m.ID_med  from medication m where m.NAME_med ='"+removeDrugList.getValue()+"'");
            while(rs.next())
                medID = rs.getInt("ID_med");

            ResultSet rs1 = connection.createStatement().executeQuery("select ui.ID_user  from user_information ui where ui.username ='"+patientList.getValue()+"'");
            while(rs1.next())
                patID = rs1.getInt("ID_user");

            connection.createStatement().executeUpdate("delete from medication_list where ID_pat ='"+patID+"' and ID_med='"+medID+"'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void modifyDrugFN(){
        int medID=0;
        int patID=0;

        try (Connection connection = DBConnector.getConnection()) {

            ResultSet rs = connection.createStatement().executeQuery("select m.ID_med  from medication m where m.NAME_med ='"+modifyDrugList.getValue()+"'");
            while(rs.next())
                medID = rs.getInt("ID_med");

            ResultSet rs1 = connection.createStatement().executeQuery("select ui.ID_user  from user_information ui where ui.username ='"+patientList.getValue()+"'");
            while(rs1.next())
                patID = rs1.getInt("ID_user");

            connection.createStatement().executeUpdate("update medication_list set qty = '"+modifyPills.getText()+"', dosing_times ='"+modifyDosingT.getText()+"' where ID_pat ='"+patID+"' and ID_med = '"+medID+"'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSelect("select ui.username from user_information ui where ui.CAT_user =2","username",patientList);
        initSelect("select m.NAME_med  from medication m","NAME_med",drugAddList);

    }

}
