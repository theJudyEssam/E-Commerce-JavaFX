package com.example.ecommerce_java_proj.controllers;

import com.example.ecommerce_java_proj.backend.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Shopping_Cart {

    @FXML
    Label total_price;

    @FXML
    GridPane ProductGridPane;

    @FXML
    ScrollPane scrollP;



    // since i am just testing
    // ill use user 1 cart
    ArrayList<CartItem> user_cart = Cart.get_cart_items(User_Session.user_id);
    ArrayList<Product> products = Cart.convert_to_products(user_cart);

    public void initialize(){


        float sum = Cart.calculate_total(User_Session.user_id);

        total_price.setText(String.valueOf(sum));

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

                    // Create buttons for adding and subtracting quantity
                    Button addButton = new Button("+");
                    addButton.setFont(Font.font("Book Antiqua", 12));
                    addButton.setStyle("-fx-background-color: #DA498D; -fx-text-fill: white; -fx-padding: 3px;"); // Styling
                    int finalI = i;
                    addButton.setOnAction(e -> {
                        int new_quantity = Integer.parseInt(user_cart.get(finalI).quantity) + 1;
                        user_cart.get(finalI).quantity = String.valueOf(new_quantity);
                        Cart.editQuantity(User_Session.user_id, user_cart.get(finalI).product_id, new_quantity);

                        quantity.setText(String.valueOf(new_quantity));


                        float new_sum = Cart.calculate_total(User_Session.user_id);
                        total_price.setText(String.valueOf(new_sum));
                    });

                    Button subtractButton = new Button("-");
                    subtractButton.setFont(Font.font("Book Antiqua", 12));
                    subtractButton.setStyle("-fx-background-color: #FF8383; -fx-text-fill: white; -fx-padding: 3px;");

                    int finalI1 = i;
                    subtractButton.setOnAction(e -> {
                        int new_quantity = Integer.parseInt(user_cart.get(finalI).quantity) - 1 ;

                        if(new_quantity == 0){
                            Cart.delete_from_cart(new CartItem(User_Session.user_id, user_cart.get(finalI).product_id ));
                            SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Shopping_Cart.fxml");
                        }
                        else{
                            Cart.editQuantity(User_Session.user_id,user_cart.get(finalI1).product_id, new_quantity);
                            user_cart.get(finalI).quantity = String.valueOf(new_quantity);
                            quantity.setText(String.valueOf(new_quantity));

                            float newTotal = Cart.calculate_total(User_Session.user_id);
                            total_price.setText(String.valueOf(newTotal));
                        }

                        float new_sum = Cart.calculate_total(User_Session.user_id);
                        total_price.setText(String.valueOf(new_sum));

                    });

                    HBox quantityBox = new HBox(5); // Add spacing between elements
                    quantityBox.getChildren().addAll(subtractButton, quantity, addButton);
                    quantityBox.setAlignment(Pos.CENTER); // Align elements to the center

                    prod.getChildren().add(quantityBox);



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

    public void clear_cartos(){

        if (Cart.clear_cart(User_Session.user_id)){
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            //re-directs to the same page
            SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Shopping_Cart.fxml");

        }
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
        }
    }

    public void checkout(){


        String or = Cart.checkout(User_Session.user_id);
        if(or != "fail"){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Created Successfully");
            alert.setContentText(or);
            alert.showAndWait();
            Cart.clear_cart(User_Session.user_id);
            SceneManager.getInstance().switchScene("/com/example/ecommerce_java_proj/Shopping_Cart.fxml");

        }
        else{
            Alert alert  =new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
        }

        // Show the alert and wait for it to be closed
    }
}
