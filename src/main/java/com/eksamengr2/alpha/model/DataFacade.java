package com.eksamengr2.alpha.model;


public interface DataFacade {

    public User login(String email, String password) throws LoginSampleException;
   public User createUser(User user) throws LoginSampleException;



}
