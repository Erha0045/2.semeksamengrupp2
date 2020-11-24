package com.eksamengr2.alpha.domain;

import org.apache.tomcat.jni.User;

public interface DataFacade {

    public User login(String userName, String password) throws LoginSampleException;
    public User createUser(User user) throws LoginSampleException;

}
