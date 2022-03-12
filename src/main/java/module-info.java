module com.example.projetuml {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projetuml to javafx.fxml;
    exports com.example.projetuml;
}