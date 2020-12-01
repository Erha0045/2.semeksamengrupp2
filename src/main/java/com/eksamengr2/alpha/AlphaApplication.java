package com.eksamengr2.alpha;

import com.eksamengr2.alpha.data.ProjectMapper;
import com.eksamengr2.alpha.data.RegistrationsMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class AlphaApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AlphaApplication.class, args);

//        RegistrationsMapper reg = new RegistrationsMapper();

//        //Dummy data
//        User user1 = new User("pascal", "qwas@mail.com");
//        reg.registerUser(user1);

    }


}
