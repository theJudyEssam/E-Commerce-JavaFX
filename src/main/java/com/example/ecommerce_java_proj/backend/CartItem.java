package com.example.ecommerce_java_proj.backend;

public class CartItem {
    public int user_id, product_id;
    public String quantity = "1";

    public CartItem(int user_id, int product_id)
    {
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public CartItem(int user_id, int product_id, String quantity){
        this.user_id = user_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }


    public Product get_details(){
        Product p = Product.get_one_product(product_id);
        return p;
    }
}
