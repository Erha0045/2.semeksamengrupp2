package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserHandlerTest {

    UserHandler userHandler = new UserHandler();

    @Test
    void checkIfUsernameAlreadyExists() throws SQLException {
        String navn = "luff";
        boolean actual = userHandler.checkIfUsernameAlreadyExists(navn);

        assertTrue(actual);

    }

    @Test
    void checkIfPasswordsAreEqual() {
        String pass1 = "hej";
        String pass2 = "hej";
        boolean actual = userHandler.checkIfPasswordsAreEqual(pass1, pass2);
        assertTrue(actual);
    }

    @Test
    void createUserError() throws SQLException {
        String navn = "luff";
        String pass1 = "hej";
        String pass2 = "hej";

       String error = userHandler.CreateUserError(navn,pass1,pass2);
        System.out.println(error);

       assertEquals(error,"the chosen user name already exists, please try another");


    }
}