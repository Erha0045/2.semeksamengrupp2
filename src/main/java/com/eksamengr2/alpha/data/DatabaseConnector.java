package com.eksamengr2.alpha.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private static String user;
    private static String password;
    private static String url;
    private static Connection con = null;

    public static Connection getConnection() {
        if (con != null) return con;
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            //Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Vi fik ikke connection=" + ex.getMessage());
        }
        return con;
    }
}


//    private static final String URL = "";
//    private static final String USER = "";
//    private static final String PASSWORD = "";
//    private static Connection con;
//
//    private DatabaseConnector(){
//    }
//
//    public static Connection getConnection() {
//        if (con == null) {
//            try {
//                con = DriverManager.getConnection(
//                        URL,
//                        USER,
//                        PASSWORD);
//            } catch (SQLException ex) {
//                //Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
//                System.out.println("Vi fik ikke connection=" + ex.getMessage());
//            }
//        }
//        return con;
//    }
