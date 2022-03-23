package com.example.patientview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TakenMedController implements Initializable {

    @FXML
    protected ChoiceBox MedList;

    @FXML
    protected void closeWindow(){
        Stage stage = (Stage) MedList.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void medTakenFn() throws SQLException {
        int patID = 0;
        try (FileReader fileReader = new FileReader("userCAT.txt")) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()){
                patID = scanner.nextInt();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Update last time taken to now
        Connection connection = DBConnector.getConnection();
        connection.createStatement().executeUpdate("UPDATE medication_list SET last_taking = CURRENT_TIMESTAMP WHERE medication_list.ID_med = (SELECT ID_med FROM medication WHERE medication.NAME_med='"+MedList.getValue()+"') AND ID_pat = '"+patID+"'");

        //Compute next taking time
        java.sql.Timestamp date = null;
        int hour = 0;
        ResultSet rs = connection.createStatement().executeQuery("select ml.last_taking ,ml.dosing_times from medication_list ml where ml.ID_med = (SELECT ID_med FROM medication WHERE medication.NAME_med='"+MedList.getValue()+"') AND ID_pat = '"+patID+"'");
        while (rs.next()){
            date = rs.getTimestamp("last_taking");
            hour = rs.getInt("dosing_times");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,hour);
        Timestamp nextDate = new Timestamp(calendar.getTimeInMillis());

        connection.createStatement().executeUpdate("UPDATE medication_list SET next_taking ='"+nextDate+"'WHERE medication_list.ID_med = (SELECT ID_med FROM medication WHERE medication.NAME_med='"+MedList.getValue()+"') AND ID_pat = '"+patID+"'");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int patID = 0;
        try (FileReader fileReader = new FileReader("userCAT.txt")) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()){
                patID = scanner.nextInt();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initSelect("select m.NAME_med  from medication_list ml inner join medication m on m.ID_med = ml.ID_med where ml.ID_pat ='"+patID+"'","NAME_med",MedList);
    }
}
