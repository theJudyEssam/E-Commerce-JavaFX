package com.example.ecommerce_java_proj.backend;



public class Retailer extends User  {

    public static void add(String product_name, float price, String image_path, String description, String category){
        Product p = new Product(product_name, image_path, description, category, price);
    }

    public static void delete(int product_id){
        Product.delete_product(product_id);
    }

    public static void edit(String product_name, int price, String image_path, String description, String category){

    }


}
