package com.example.patientview;

import java.sql.Connection;
import java.sql.SQLException;

abstract public class UserFactory {
    protected String username;
    protected String password;
    protected int userCat;

    abstract void CreateUser(String username,String password) throws SQLException;
}

class Caregiver extends UserFactory{

    @Override
    void CreateUser(String username, String password) throws SQLException {
        Connection connection = DBConnector.getConnection();
        connection.createStatement().executeUpdate("insert into user_information(username,password,CAT_user) VALUES('"+username+"','"+password+"','1')");
    }
}

class Patient extends UserFactory{

    @Override
    void CreateUser(String username, String password) throws SQLException {
        Connection connection = DBConnector.getConnection();
        connection.createStatement().executeUpdate("insert into user_information(username,password,CAT_user) VALUES('"+username+"','"+password+"','2')");
    }
}

class Factory{
    public UserFactory createUser(String userType){
        if(userType.equalsIgnoreCase("caregiver")){
            return new Caregiver();
        }else if (userType.equalsIgnoreCase("patient")){
            return new Patient();
        }
        return null;
    }
}