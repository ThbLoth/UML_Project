module com.example.patientview {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.patientview to javafx.fxml;
    exports com.example.patientview;
}