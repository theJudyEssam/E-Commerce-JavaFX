package com.example.ecommerce_java_proj.controllers;

import com.example.ecommerce_java_proj.backend.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import java.util.Objects;

public class Product_Page {

    @FXML
    Label product_name;
    @FXML
    Label category;
    @FXML
    Label product_description;
    @FXML
    ImageView image_path;
    @FXML
    Label price;
    @FXML
    Label product_id;
    @FXML
    Button cart_button;

    public void initialize()
    {


        product_name.setText(Product_Session.product_name);
        category.setText(Product_Session.category);
        price.setText(String.valueOf(Product_Session.price) + "L.E");
        product_id.setText(Product_Session.product_id);
        image_path.setImage(new Image(Product_Session.image_path));
        product_description.setText(Product_Session.description);



        if(Cart.check_if_exists(Integer.parseInt(product_id.getText()), User_Session.user_id)){
            cart_button.setText("Remove from Cart");
        }
        else{
            cart_button.setText("Add to Cart");
        }



    }


    public void Cart(ActionEvent actionEvent)
    {
        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Shopping_Cart.fxml");
    }

    public void Home(ActionEvent actionEvent)
    {
        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Client_Dashboard.fxml");
    }

    public void cart_actions()
    {
        if(Objects.equals(cart_button.getText(), "Remove from Cart")){
            Cart.delete_from_cart(new CartItem(User_Session.user_id, Integer.parseInt(product_id.getText())));
            cart_button.setText("Add to Cart");
        }
        else if(Objects.equals(cart_button.getText(), "Add to Cart"))
        {
            Cart.add_to_cart(new CartItem(User_Session.user_id, Integer.parseInt(product_id.getText())));
            cart_button.setText("Remove from Cart");
        }


    }

}
