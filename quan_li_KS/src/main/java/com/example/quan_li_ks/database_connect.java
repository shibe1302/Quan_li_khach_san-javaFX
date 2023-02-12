package com.example.quan_li_ks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database_connect {

    public static Connection connectDatabase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/login-sign-up-ks","root","");

            return connection;
        }catch (SQLException q){
            q.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}

