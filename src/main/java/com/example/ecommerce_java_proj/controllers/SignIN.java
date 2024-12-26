package com.example.ecommerce_java_proj.controllers;
import com.example.ecommerce_java_proj.backend.SceneManager;
import com.example.ecommerce_java_proj.backend.User;
import com.example.ecommerce_java_proj.backend.User_Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class SignIN {

    @FXML
    TextField fullname;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    TextField email;
    @FXML
    TextField phone_number;
    @FXML
    TextField age;

    public void Register(ActionEvent actionEvent){
        User u = new User(fullname.getText(), username.getText(), email.getText(), age.getText(), password.getText(), phone_number.getText());
        if(u.sign_up()){
            SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Client_Dashboard.fxml");
        }
        else{
            System.out.println("There was an issue logging you in");
        }
    }


}
