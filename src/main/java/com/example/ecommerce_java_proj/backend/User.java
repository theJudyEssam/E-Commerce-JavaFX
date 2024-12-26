package com.example.ecommerce_java_proj.backend;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class User {
    private String full_name, username, email, age, password, phone;

    public User(){
        System.out.println("I am in the default constructor of user!");
    }; //this is a default constructor.

    //this constructor will be called when registering
    public User(String full_name, String username, String email, String age, String password, String phone ) {
        this.full_name = full_name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.username = username;
    }

    //this is for log in
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    static boolean is_customer(){
        boolean customer = true;


        return customer;

    }

    public boolean log_in(){
        boolean logged_in = false;
        String checkUser = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = database_connection.getConnection()) {

            try (PreparedStatement checkUserQ = connection.prepareStatement(checkUser)) {
                checkUserQ.setString(1, username);
                checkUserQ.setString(2, password);

                try (ResultSet resultSet = checkUserQ.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Welcome Back!");
                        put_in_static(username);
                        logged_in = true;
                    }
                }
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        if(logged_in == false){
            System.out.println("Sorry, you are not logged in.");
        }
        return logged_in;

    }

    public boolean sign_up(){

        String checkUserSql = "SELECT * FROM users WHERE username = ?";
        String insertUserSql = "INSERT INTO users (username, password, age, phone_num, fullname, email) VALUES (?, ?, ?, ?, ?, ?)";
        boolean registered = false;

        try (Connection connection = database_connection.getConnection()) {

            try (PreparedStatement checkUserStmt = connection.prepareStatement(checkUserSql)) {
                checkUserStmt.setString(1, username);
                try (ResultSet resultSet = checkUserStmt.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("This user already exists!");
                        return false;
                    }
                }
            }


            try (PreparedStatement insertUserStmt = connection.prepareStatement(insertUserSql)) {
                insertUserStmt.setString(1, username);
                insertUserStmt.setString(2, password);
                insertUserStmt.setString(3, age);
                insertUserStmt.setString(4, phone);
                insertUserStmt.setString(5, full_name);
                insertUserStmt.setString(6, email);

                int rows = insertUserStmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("User registration successful!");
                    registered = true;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return registered;
    }


    public void put_in_static(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = database_connection.getConnection()) {
            try (PreparedStatement query = connection.prepareStatement(sql)) {
                query.setString(1, username);
                ResultSet resultSet = query.executeQuery();

                if (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String age = resultSet.getString("age");
                    String phoneNumber = resultSet.getString("phone_num");
                    String fullName = resultSet.getString("fullname");
                    int user_id = resultSet.getInt("user_id");
                    String username1 = resultSet.getString("username");
                    String password = resultSet.getString("password");

                    User_Session.email = email;
                    User_Session.age = age;
                    User_Session.phone = phoneNumber;
                    User_Session.full_name =fullName;
                    User_Session.user_id = user_id;
                    User_Session.username = username;
                    User_Session.password = password;

                } else {
                    System.out.println("No user found with username: " + username);
                }

            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }



    public boolean validates(){
        return false;
    }


}