package com.example.ecommerce_java_proj.controllers;

import com.example.ecommerce_java_proj.backend.SceneManager;

public class Retailer_Page {

    public void Add_Product(){
        try{
            SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/New_Product.fxml");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
