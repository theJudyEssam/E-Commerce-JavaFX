package com.example.ecommerce_java_proj.backend;

interface Operations{
}


public class Customer extends User implements Operations  {


    public static void add(int product_id, int user_id){
        //adds the product to the user's shopping cart
        CartItem cartItem = new CartItem(user_id, product_id);
        Cart.add_to_cart(cartItem);
    }

    public static void delete(int product_id, int user_id){
        CartItem cartItem = new CartItem(user_id, product_id);
        Cart.delete_from_cart(cartItem);
    }

    //elsraha lesa deh ya3ny
    public static void edit(String quantity, String product_id, String user_id){
        //edits
    }


}
