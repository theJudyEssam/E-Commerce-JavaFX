module com.example.ecommerce_java_proj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.ecommerce_java_proj to javafx.fxml;
    exports com.example.ecommerce_java_proj;
    exports com.example.ecommerce_java_proj.controllers;
    opens com.example.ecommerce_java_proj.controllers to javafx.fxml;
}