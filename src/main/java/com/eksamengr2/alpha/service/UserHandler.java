package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.data.RegistrationsMapper;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.model.User;

import java.sql.SQLException;

public class UserHandler {
 private RegistrationsMapper registrationsMapper = new RegistrationsMapper();


    public boolean checkIfUsernameAlreadyExists(String userName) throws SQLException {


        return registrationsMapper.checkIfUserNameExists(userName);
    }

    public boolean checkIfPasswordsAreEqual(String pass1, String pass2){

        return pass1.equals(pass2);
    }

    public String CreateUserError(String username, String pass1, String pass2) throws SQLException {
        String error="";

        if(checkIfUsernameAlreadyExists(username)){
            error+="the chosen user name already exists, please try another";
        }

        if(!checkIfPasswordsAreEqual(pass1,pass2)){
            error+="  - your password entries didnt match, try again";
        }

        return error;

    }

}
