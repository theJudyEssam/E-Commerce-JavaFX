package com.example.ecommerce_java_proj.controllers;

import com.example.ecommerce_java_proj.backend.Product;
import com.example.ecommerce_java_proj.backend.Product_Session;
import com.example.ecommerce_java_proj.backend.SceneManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Retailer_Page {

    @FXML
    ScrollPane scrollP;
    @FXML
    GridPane ProductGridPane;

    ArrayList <Product> products = Product.get_products();

    public void initialize(){
        scrollP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollP.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollP.setFitToWidth(true);  // Fit width to scrollpane
        scrollP.setFitToHeight(true);  // Fit height to scrollpane


        Task<Void> loadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                int column = 0;
                int row = 0;

                for (int i = 0; i < products.size(); i++) {
                    // I steal the function from the client dashboard
                    VBox prod = createProduct(products.get(i).product_id, products.get(i).product_name, products.get(i).price, products.get(i).image_path);

                    int finalColumn = column;
                    int finalRow = row;

                    // why? because in JavaFX you much load everything on the Main thread.
                    Platform.runLater(() -> {
                        try {
                            System.out.println("Adding product to GridPane");
                            ProductGridPane.add(prod, finalColumn, finalRow);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                    column++;
                    if (column == 4) {
                        column = 0;
                        row++;
                    }
                }
                return null;
            }

        };


        Thread loadProducts = new Thread(loadTask);
        loadProducts.setDaemon(true);
        loadProducts.start();


    }


    public static VBox createProduct(String id, String name, double price, String imagePath) {
        VBox productBox = new VBox();
        productBox.setStyle("-fx-alignment: center; -fx-spacing: 5; -fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1;");


        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Book Antiqua", 12)); // Set font and size
        nameLabel.setTextFill(Color.web("#b3029b")); // Set text color


        Label priceLabel = new Label(String.valueOf(price) + " L.E");
        priceLabel.setFont(Font.font("Book Antiqua", 12)); // Set font and size
        priceLabel.setTextFill(Color.BLACK); // Set text color (you can change this)


        Label idLabel = new Label("#" + String.valueOf(id));
        idLabel.setFont(Font.font("Book Antiqua", 12)); // Set font and size
        idLabel.setTextFill(Color.BLACK);


        ImageView imageView = new ImageView();
        imageView.setImage(new Image(imagePath));
        imageView.setFitWidth(100); // Adjust as needed
        imageView.setFitHeight(100); // Adjust as needed
        imageView.setPreserveRatio(true);

        Button b = new Button("View Product");
        b.setStyle("-fx-background-color: #A294F9;");




        b.setOnAction(e ->{
            // this is a very lousy approach, but it is what it is.

            Product p = Product.get_one_product(Integer.parseInt(id));

            Product_Session.product_name = p.product_name;
            Product_Session.price = p.price;
            Product_Session.description = p.description;
            Product_Session.category = p.category;
            Product_Session.product_id = p.product_id;
            Product_Session.image_path = p.image_path;

            SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Retailer_Product_Page.fxml");

        });

        productBox.getChildren().addAll(imageView, idLabel, nameLabel, priceLabel, b);
        return productBox;
    }




    public void Add_Product(){
        try{
            SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/New_Product.fxml");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void log_out(ActionEvent actionEvent){
        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Log_IN.fxml");

    }



}
