package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.DataFacade;
import com.eksamengr2.alpha.model.LoginSampleException;
import com.eksamengr2.alpha.model.User;

public class DataFacadeImpl implements DataFacade {
   private UserMapper userMapper = new UserMapper();


    public User login(String username, String password) throws LoginSampleException {
        return userMapper.login(username, password);
    }


    public User createUser(User user)  {
        return user;
    }
}

