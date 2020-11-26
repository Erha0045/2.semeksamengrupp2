//package com.eksamengr2.alpha.mapper;
//
//import com.eksamengr2.alpha.data.DatabaseConnector;
//import com.eksamengr2.alpha.model.User;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class RegistrationsMapper {
//    public void registerUser(User user) throws Exception {
//        try {
//            Connection con = DatabaseConnector.getConnection();
//            String SQL = "INSERT INTO userinfo (email, password) VALUES (?, ?)";
//            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, user.getEmail());
//            ps.setString(2, user.getPassword());
//            ps.executeUpdate();
//
//        } catch (SQLException ex) {
//            throw new Exception(ex.getMessage());
//        }
//    }
//}
