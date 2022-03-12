module com.example.tamer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tamer to javafx.fxml;
    exports com.example.tamer;
}