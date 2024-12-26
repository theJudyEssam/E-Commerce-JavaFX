package com.example.ecommerce_java_proj.controllers;



import com.example.ecommerce_java_proj.backend.Product;
import com.example.ecommerce_java_proj.backend.SceneManager;
import com.example.ecommerce_java_proj.backend.User;
import com.example.ecommerce_java_proj.backend.User_Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class New_Product {

    @FXML
    TextField product_name;
    @FXML
    TextField price;
    @FXML
    TextField description;
    @FXML
    TextField category;
    @FXML
    TextField image_path;

    public void GoBack(){
        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Retailer_Page.fxml");
    }
    // order: String product_name, String image_path, String description, String category, float price
    public void add_new_product(){
        new Product(product_name.getText(), image_path.getText(), description.getText(), category.getText(),Float.parseFloat(price.getText()));
    }
}
