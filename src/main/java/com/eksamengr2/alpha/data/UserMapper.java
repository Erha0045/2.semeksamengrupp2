package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.LoginSampleException;
import com.eksamengr2.alpha.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public User login(String username, String password) throws LoginSampleException {
        try {
            Connection con = DatabaseConnector.getConnection();
            String SQL = "SELECT username FROM userinfo "
                    + "WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(username,password);
                return user;
            } else {
                throw new LoginSampleException("Could not validate user");
            }
        } catch (SQLException ex) {
            throw new LoginSampleException(ex.getMessage());
        }
    }
}
