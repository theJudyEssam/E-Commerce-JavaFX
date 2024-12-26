package com.example.ecommerce_java_proj.controllers;


import com.example.ecommerce_java_proj.backend.SceneManager;
import com.example.ecommerce_java_proj.backend.User;
import com.example.ecommerce_java_proj.backend.User_Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class Log_IN {
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    Label register_redirect;


    public void Redirect(ActionEvent actionEvent){
        User u = new User(username.getText(), password.getText());
        boolean registered = u.log_in();
        if(registered){
            if(User_Session.user_id == 1){
                SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Retailer_Page.fxml");

            }
            else
            {
                SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Client_Dashboard.fxml");
            }
            System.out.println("Welcome back: "+ User_Session.full_name);
        }
        else{
            System.out.println("no work");
        }

    }
    public void RegisterRedirect(ActionEvent actionEvent){
        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/SignIN.fxml");

    }


}
