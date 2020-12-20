package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserMapper {

    /**Gets user from if username and password is correct
     *
     * @param username
     * @param password
     * @return

     */
    public User login(String username, String password) throws Throwable {
        try {
            Connection con = DatabaseConnector.getConnection();
            String SQL = "SELECT username, usertype FROM userinfo "
                    + "WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String userType = rs.getString("usertype");
                User user = new User(password, username, userType);
                return user;
            } else {
                User user = new User();
                return user;
            }
        } catch (Exception ex) {
            throw ex.getCause();
        }
    }
}
