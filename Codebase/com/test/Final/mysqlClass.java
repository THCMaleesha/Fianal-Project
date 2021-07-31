package com.test.Final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//create a class to set the database connection of the system with MYSQL,using phpMyAdmin & jdbc connector

public class mysqlClass {

    static Connection connection;

    public static Connection getConnection() throws Exception{

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/customer+management+system","root","");
            //System.out.println("Connected");
            return connection;
    }
}
