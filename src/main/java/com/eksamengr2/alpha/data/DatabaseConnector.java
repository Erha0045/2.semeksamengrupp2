package com.eksamengr2.alpha.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;

public class DatabaseConnector{


    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";
    private static Connection con;

    private DatabaseConnector(){
    }

    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD);
            } catch (SQLException ex) {
                //Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Vi fik ikke connection=" + ex.getMessage());
            }
        }
        return con;
    }
}
