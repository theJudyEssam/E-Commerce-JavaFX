package com.example.ecommerce_java_proj.backend;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cart {

    public static boolean add_to_cart(CartItem cartitem){
        boolean added = false;
        String query = "INSERT into shopping_cart VALUES (?,?,?)";

        try(Connection connection = database_connection.getConnection();
            PreparedStatement stat = connection.prepareStatement(query))
        {
            stat.setInt(1, cartitem.user_id);
            stat.setInt(2, cartitem.product_id);
            stat.setInt(3, 1); // the default val for the quantity is one
            int row = stat.executeUpdate();
            if(row > 0){
                System.out.println("Added successfully");
                added = true;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static ArrayList<CartItem> get_cart_items(int user_id){
        ArrayList<CartItem> cart = new ArrayList<>();
        String query = "SELECT * FROM shopping_cart WHERE user_id = ?";

        try(Connection connection = database_connection.getConnection();
            PreparedStatement q = connection.prepareStatement(query))
        {
            q.setInt(1, user_id);
            ResultSet resultSet = q.executeQuery();
            while (resultSet.next())
            {
                int userID = resultSet.getInt("user_id");
                int productID = resultSet.getInt("product_id");
                String quantity = resultSet.getString("quantity");

                CartItem cartItem = new CartItem(userID, productID, quantity);
                cart.add(cartItem);
                System.out.println("success");
            }


        }
        catch(SQLException e){
            System.out.println(e.getMessage());

        }


        return cart;
    }

    public static boolean delete_from_cart(CartItem cartItem){
        boolean removed = false;
        String query = "delete from shopping_cart where user_id = ? and product_id = ?";

        try(Connection connection = database_connection.getConnection();
            PreparedStatement stat = connection.prepareStatement(query))
        {
            stat.setInt(1, cartItem.user_id);
            stat.setInt(2, cartItem.product_id);

            int rows = stat.executeUpdate();
            if (rows > 0){
                System.out.println("Successfully removed from cart");
                removed = true;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return removed;
    }

    public static ArrayList<Product>  convert_to_products(ArrayList<CartItem> cart){
        ArrayList<Product> p = new ArrayList<>();
        for (CartItem item : cart){
            Product pro = item.get_details();
            p.add(pro);
        }

        return p;
    }

    public static float calculate_total(int user_id){
        ArrayList<CartItem> cart = get_cart_items(user_id);
        float sum = 0;

        for(int i =0; i < cart.size();i++){
            int product_id = cart.get(i).product_id;
            Product p = Product.get_one_product(product_id);
            sum += p.price * Integer.parseInt(cart.get(i).quantity);
        }

        return sum;
    }

    public static boolean check_if_exists(int product_id, int user_id){
        String query = "SELECT * from shopping_cart WHERE product_id = ? and user_id = ?";

        try(Connection connection = database_connection.getConnection();
            PreparedStatement stat = connection.prepareStatement(query)){

            stat.setInt(1, product_id);
            stat.setInt(2, user_id);
            ResultSet rows = stat.executeQuery();

            if(rows.next()){
                System.out.println("This product exists in the cart");
                return true;
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());

        }

        return false;
    }

    public static boolean clear_cart(int user_id){
        String query = "DELETE FROM shopping_cart WHERE user_id = ?";
        boolean deleted =false;

        try(Connection connection = database_connection.getConnection();
            PreparedStatement stat = connection.prepareStatement(query)
        ){
            stat.setInt(1, user_id);
            int rows = stat.executeUpdate();
            if(rows > 0){
                deleted = true;
                System.out.println("Cleared Cart");
            }


        }
        catch(SQLException e ){
            System.out.println(e.getMessage());

        }


        return deleted;
    }

    public static String checkout(int user_id){
        Order o = new Order(user_id);
        return o.orderP;
        //khalas keda mesh me7taga a3ml haga tanya, the Order class figures everything out
    }


    public static boolean editQuantity(int user_id,int product_id, int new_quantity){
        boolean edited = false;
        String query = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? and product_id = ?";

        try(Connection connection = database_connection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setInt(1, new_quantity);
            statement.setInt(2, user_id);
            statement.setInt(3, product_id);
            int rows = statement.executeUpdate();
            if(rows > 0){
                edited = true;
                System.out.println("Edited.");
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return false;
    }

}
