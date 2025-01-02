package com.example.ecommerce_java_proj.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

interface Operations{
    public default void calculate_price(int user_id){}
}

public class Order implements Operations {

    public static ArrayList<CartItem> o = new ArrayList<>();
    public static float order_sum;
    String orderP;

    public Order(int user_id){
        get_order(user_id);
        calculate_price(user_id);
        send_reciept();
    }


    public void get_order(int user_id){

        ArrayList<CartItem> items = Cart.get_cart_items(user_id);
        o.addAll(items);
    }


    public void calculate_price(int user_id){
        order_sum = Cart.calculate_total(user_id);
    }

    public String send_reciept(){
            // this will create an order and a file and save it in the user's files

        String folderPath = "Receipts";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Receipts folder created.");
            } else {
                System.out.println("Failed to create receipts folder.");
                orderP = "fail";
            }
        }

        StringBuilder s = OrderString();
        String st = s.toString();
        orderP= st;
        System.out.println(s.toString());

        String filename = folderPath + "/Reciept_" + System.currentTimeMillis() + ".txt";

        try {
            FileWriter reciept = new FileWriter(filename);
            reciept.write(s.toString());
            reciept.close();
            System.out.println("Receipt has been created successfully: " + filename);
        } catch (IOException e) {
            e.printStackTrace(); orderP = "fail";
        }

        return orderP;
    }


    public StringBuilder OrderString(){
        StringBuilder order = new StringBuilder();
        order.append("ORDER DETAILS : \n");
        for(int i = 0; i < o.size();i++){
            Product p = o.get(i).get_details();
            order.append(String.format("Order %d: %s Q: %s Price:%.2f \n", i + 1, p.product_name, o.get(i).quantity, p.price));
            order.append("-----------------------------------------------\n");
        }
        order.append(String.format("\nCalculated Total: %.2f \n", order_sum));
        order.append("Order was created successfully and should arrive in 2-3 non-business days \n " +
                "No need to tell us your address, we know. \n" +
                "WARNING: THE DELIVERYMAN IS NOT HUMAN, DO NOT TALK TO HIM, FOR HE IS NOT FOND OF YOUR KIND. ⊙.☉" +
                " \nThanks and can't wait for you next order ╰(*°▽°*)╯");

        return order;
    }





}
