package com.example.ecommerce_java_proj.controllers;
import com.example.ecommerce_java_proj.backend.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.concurrent.Task;
import javax.swing.*;
import java.util.ArrayList;


public class Client_Dashboard {


    // this is the FXML elements that i am about to manipulate muhahahahaha
    @FXML
    GridPane ProductGridPane;

    @FXML
    ScrollPane scrollP;

    ArrayList<Product> products = Product.get_products();
    // This class create the product pane itself

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

            SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Product_Page.fxml");

        });

        productBox.getChildren().addAll(imageView, idLabel, nameLabel, priceLabel, b);
        return productBox;
    }

    public void initialize() {

        scrollP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollP.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollP.setFitToWidth(true);  // Fit width to scrollpane
        scrollP.setFitToHeight(true);  // Fit height to scrollpane


        Task<Void> loadProductsTask = new Task<>() {
            // 'Tis an anonymous class

            // ok so the call() method must be implemented
            protected Void call() throws Exception{

//                ArrayList<Product> products = Product.get_products();

                int column = 0;
                int row = 0;

                for (Product product : products) {
                    VBox prod = createProduct(product.product_id, product.product_name, product.price, product.image_path);
                    prod.setAlignment(Pos.CENTER);

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

        loadProductsTask.setOnFailed(event -> {
            System.out.println("Something bad occurred");
            Throwable exception = loadProductsTask.getException();
            exception.printStackTrace();
        });

        loadProductsTask.setOnSucceeded(event -> {
            System.out.println("Successfully loaded");
        });


        Thread loadProducts = new Thread(loadProductsTask);
        loadProducts.setDaemon(true);
        loadProducts.start();



    }

    public void Cart(ActionEvent actionEvent){
        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Shopping_Cart.fxml");
    }

    public void Account(ActionEvent actionEvent){
        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Account_Page.fxml");
    }

}
