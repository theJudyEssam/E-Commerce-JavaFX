package com.example.ecommerce_java_proj.controllers;

import com.example.ecommerce_java_proj.backend.Product;
import com.example.ecommerce_java_proj.backend.Product_Session;
import com.example.ecommerce_java_proj.backend.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Retailer_Product_Page {

    @FXML
    Label product_name;
    @FXML
    Label product_id;
    @FXML
    Label description;
    @FXML
    Label category;
    @FXML
    Label price;
    @FXML
    ImageView image_path;


    public void initialize(){
        product_id.setText(Product_Session.product_id);
        product_name.setText(Product_Session.product_name);
        price.setText(String.valueOf(Product_Session.price));
        description.setText(Product_Session.description);
        category.setText(Product_Session.category);
        image_path.setImage(new Image(Product_Session.image_path));
    }

    public void delete_product(ActionEvent actionEvent){
        boolean deleted = Product.delete_product(Integer.parseInt(Product_Session.product_id));
        if(deleted){
            SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Retailer_Page.fxml");
        }
        else{
            System.out.println("Trouble deleting, try again.");
        }
    }

    public void home(ActionEvent actionEvent){
        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Retailer_Page.fxml");
    }

    public void new_product(ActionEvent actionEvent){
        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/New_Product.fxml");
    }

}
