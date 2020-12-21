package com.eksamengr2.alpha.model;

import com.eksamengr2.alpha.data.Facade;

public class LoginController {//(EB)

    // facade to datasource layer
    private Facade facade = null;

    public LoginController(Facade facade) {
        this.facade = facade;
    }

    public User login(String userName, String password) throws Throwable {
        return facade.login(userName, password);
    }


}
