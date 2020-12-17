package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.User;

import java.sql.*;

public class RegistrationsMapper {
    public void registerUser(User user) throws Exception {
        try {
            Connection con = DatabaseConnector.getConnection();
            String SQL = "INSERT INTO userinfo (username, password) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public boolean checkIfUserNameExists(String userName) throws SQLException {

        Connection con = DatabaseConnector.getConnection();
        String SQL = "select username from userinfo where username=?";
        PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, userName);
        ResultSet resultSet = ps.executeQuery();

        return resultSet.next();
    }
}
