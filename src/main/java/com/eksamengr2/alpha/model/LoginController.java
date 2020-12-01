package com.eksamengr2.alpha.model;

public class LoginController {

    // facade to datasource layer
    private DataFacade facade = null;

    public LoginController(DataFacade facade) {
        this.facade = facade;
    }

    public User login(String userName, String password) throws LoginSampleException {
        return facade.login(userName, password);
    }


}
