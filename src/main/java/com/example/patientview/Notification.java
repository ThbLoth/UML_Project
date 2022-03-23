package com.example.patientview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Popup;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Notification implements Runnable{

    @Override
    public void run() {
        while (true) {

            //Get Current Date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            Timestamp currentDate = new Timestamp(calendar.getTimeInMillis());
            String currentDateGF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(currentDate);

            //Get User Cat & User ID

            int userCAT =0,userID = 0;

            try {
                FileReader file1 = new FileReader("userCAT.txt");
                FileReader file2 = new FileReader("userID.txt");

                Scanner scanner1 = new Scanner(file1);
                Scanner scanner2 = new Scanner(file2);

                while (scanner1.hasNextLine()){
                    userCAT = scanner1.nextInt();
                }

                while (scanner2.hasNextLine()){
                    userID = scanner2.nextInt();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (userCAT == 2){
                try {
                    String[] medNameTab ={};
                    Timestamp[] dateTab={};

                    Connection connection = DBConnector.getConnection();
                    ResultSet rs = connection.createStatement().executeQuery("select m.NAME_med, ml.next_taking  from medication_list ml natural join medication m where ID_pat = "+userID);
                    while (rs.next()){
                        //fill medNameTab
                        medNameTab = Arrays.copyOf(medNameTab,medNameTab.length+1);
                        medNameTab[medNameTab.length-1] = rs.getString("NAME_med");

                        //fill dateTab
                        dateTab = Arrays.copyOf(dateTab,dateTab.length+1);
                        dateTab[dateTab.length-1] = rs.getTimestamp("next_taking");
                    }

                    for(int i=0;i<dateTab.length;i++){
                        String dbDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dateTab[i]);
                        if(dbDate.equals(currentDateGF)){
                            System.out.println("Time to take : "+medNameTab[i]);
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            //sleep for 1min
            try{

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
