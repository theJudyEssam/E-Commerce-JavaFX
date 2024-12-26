package com.example.ecommerce_java_proj.controllers;

import com.example.ecommerce_java_proj.backend.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Shopping_Cart {

    @FXML
    GridPane ProductGridPane;

    @FXML
    ScrollPane scrollP;
    // since i am just testing
    // ill use user 1 cart
    ArrayList<CartItem> user_cart = Cart.get_cart_items(User_Session.user_id);
    ArrayList<Product> products = Cart.convert_to_products(user_cart);

    public void initialize(){


        scrollP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollP.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollP.setFitToWidth(true);  // Fit width to scrollpane
        scrollP.setFitToHeight(true);

        Task<Void> loadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                int column = 0;
                int row = 0;

                for (int i = 0; i < products.size(); i++) {
                    // I steal the function from the client dashboard
                    VBox prod = Client_Dashboard.createProduct(products.get(i).product_id, products.get(i).product_name, products.get(i).price, products.get(i).image_path);

                    Label quantity = new Label(user_cart.get(i).quantity);
                    quantity.setFont(Font.font("Book Antiqua", 12)); // Set font and size
                    quantity.setTextFill(Color.BLACK);



                    prod.getChildren().add(quantity);
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


        Thread loadProducts = new Thread(loadTask);
        loadProducts.setDaemon(true);
        loadProducts.start();

    }



    public void Home(){

    // i will actually re-use the create product method from the Client_Dashboard controller :p

    SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Client_Dashboard.fxml");
}
    public void Account(){
        SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Account_Page.fxml");
    }
}
