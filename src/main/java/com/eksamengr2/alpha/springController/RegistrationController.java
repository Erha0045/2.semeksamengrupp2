/*
package com.eksamengr2.alpha.controller;

import com.eksamengr2.alpha.mapper.RegistrationsMapper;
import com.eksamengr2.alpha.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;

@Controller
public class RegistrationController {

    @PostMapping("/register_user")
    public String registerUser(@ModelAttribute("user") User user, WebRequest request, Model model) throws Exception {
        model.addAttribute("user", user);

        RegistrationsMapper registrationsMapper = new RegistrationsMapper();
        String userName = request.getParameter("userName");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String email = request.getParameter("email");

        if (password1.equals(password2)) {
            user = new User(userName, password1, email);



            RegistrationMapper registrationMapper = new RegistrationMapper();

            setSessionInfo(request, users, dater1);
            registrationMapper.registerUser(users, dater1);



            return users.getUserType() + "/registrationindex";

        } else { // If passwords don't match, an exception is thrown
            throw new Exception("Adgangskode skal være éns.");
        }
    }
    private void setSessionInfo(WebRequest request, User user) {
        //sætter user og dater til være "in scope" for hele session, det vil sige, at vi kan hente dem i hele
        // sessionen - skal lige se om "user" kan undværes her? :S
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);

    }
}
*/
