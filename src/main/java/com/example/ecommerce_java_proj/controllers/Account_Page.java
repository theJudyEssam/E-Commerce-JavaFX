package com.example.ecommerce_java_proj.controllers;
import com.example.ecommerce_java_proj.backend.SceneManager;
import javafx.fxml.Initializable;
import com.example.ecommerce_java_proj.backend.User;
import com.example.ecommerce_java_proj.backend.User_Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;


public class Account_Page {

    @FXML
    Label username;
    @FXML
    Label full_name;
    @FXML
    Label email;
    @FXML
    Label age;
    @FXML
    Label phone_number;

    @FXML
    public void initialize(){
        username.setText(User_Session.username);
        full_name.setText(User_Session.full_name);
        email.setText(User_Session.email);
        age.setText(User_Session.age);
        phone_number.setText(User_Session.phone);

    }


    public void GoBack(){

        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Client_Dashboard.fxml");

    }


    public void log_out(){
        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Log_IN.fxml");

    }

}
