package com.example.ecommerce_java_proj.backend;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Product {
   public String product_id, product_name, image_path, description, category;
   public float price;

    public Product(String product_id,String product_name, String image_path, String description, String category, float price){
        this.product_id = product_id;
        this.product_name = product_name;
        this.image_path = image_path;
        this.description = description;
        this.category = category;
        this.price = price;
    };
    public Product(String product_name, String image_path, String description, String category, float price){
        this.product_name = product_name;
        this.image_path = image_path;
        this.description =description;
        this.category = category;
        this.price = price;
       product_id =  make_product();
       System.out.println(product_id);

    }

    public String make_product(){
        String id = null;
        String query = "INSERT into product (product_name, image_path, description, category, price) VALUES (?,?,?,?,?)";
        String query1 = "SELECT * FROM product WHERE product_name = ?";

        try (Connection connection = database_connection.getConnection()) {
            try (PreparedStatement s1 = connection.prepareStatement(query1)){
                s1.setString(1, product_name);
                try (ResultSet resultSet = s1.executeQuery()) {
                    if (resultSet.next()) {
                        return "exists";
                    }
                }
            }

            try (PreparedStatement s2 = connection.prepareStatement(query)){

                s2.setString(1, product_name);
                s2.setString(2, image_path);
                s2.setString(3, description);
                s2.setString(4, category);
                s2.setFloat(5, price);
                System.out.println("I am in the inserting phase");

                int rows = s2.executeUpdate();
                if (rows > 0) {
                    System.out.println("Product Added Successfully");
                    String query2 = "SELECT * FROM product WHERE product_name = ?";
                    try (PreparedStatement s3 = connection.prepareStatement(query2)) {
                        s3.setString(1, product_name);
                        ResultSet resultSet = s3.executeQuery();
                        if (resultSet.next()) {
                            id = resultSet.getString("product_id");
                            System.out.println("Added Product of id "+id);
                        }
                    }
                }
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    public static ArrayList<Product> get_products() {
        String query = "SELECT * FROM product";
        ArrayList<Product> products = new ArrayList<>();


        try (Connection connection = database_connection.getConnection();
             PreparedStatement stat = connection.prepareStatement(query);
        )
        {
            ResultSet resultSet = stat.executeQuery();
            while (resultSet.next()) {
                String p_name = resultSet.getString("product_name");
                String p_id = resultSet.getString("product_id");
                String p_path = resultSet.getString("image_path");
                String p_desc = resultSet.getString("description");
                String p_category = resultSet.getString("category");
                float price= resultSet.getFloat("price");

                Product new_product = new Product(p_id, p_name, p_path, p_desc, p_category, price);
                products.add(new_product);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public static Product get_one_product(int id){
        String query = "SELECT * FROM product WHERE product_id = ?";
        Product new_product = null;
        try(Connection connection = database_connection.getConnection();
            PreparedStatement stat = connection.prepareStatement(query)
        )
        {
           stat.setInt(1, id);
           ResultSet resultSet = stat.executeQuery();
           if(resultSet.next()){
               String p_name = resultSet.getString("product_name");
               String p_id = resultSet.getString("product_id");
               String p_path = resultSet.getString("image_path");
               String p_desc = resultSet.getString("description");
               String p_category = resultSet.getString("category");
               float price= resultSet.getFloat("price");

               new_product = new Product(p_id, p_name, p_path, p_desc, p_category, price);

           }

        }
        catch (SQLException e){
            e.printStackTrace();
        }


        return new_product;

    }

    public static boolean delete_product(int id){
        String query = "DELETE FROM product WHERE product_id = ?";
        boolean deleted = false;

        try(Connection connection = database_connection.getConnection();
            PreparedStatement stat = connection.prepareStatement(query)
        )
        {
            stat.setInt(1, id);
            int rows = stat.executeUpdate();
            if(rows > 0){
                deleted = true;
                System.out.println("Product Deleted Successfully");
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }



        return deleted; //called by retailer
    }

    public boolean edit_product(Product p){
        return false;
    }


}
