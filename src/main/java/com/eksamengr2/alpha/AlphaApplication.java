package com.eksamengr2.alpha;

import com.eksamengr2.alpha.mapper.RegistrationsMapper;
import com.eksamengr2.alpha.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlphaApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AlphaApplication.class, args);

        RegistrationsMapper reg = new RegistrationsMapper();

        //Dummy data
        User user2 = new User("limbo", "jal@mail.com");
        reg.registerUser(user2);
    }


}
