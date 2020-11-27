package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.DataFacadeImpl;
import com.eksamengr2.alpha.model.LoginController;
import com.eksamengr2.alpha.model.LoginSampleException;
import com.eksamengr2.alpha.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
@Controller
public class LogController {

        private LoginController loginController = new LoginController(new DataFacadeImpl());

        @GetMapping("/login")
        public String home() {
            return "login";
        }

        @PostMapping("/login")
        public String login(WebRequest request) throws SQLException, LoginSampleException {
            //henter userName og password fra loginpage textfelter
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            //delegate work + data to login controller
            User user = loginController.login(email, password);
            setSessionInfo(request, user);

            return "owerview";
//            return "login" + user;
        }

        @GetMapping("/logout")
        public String logout(WebRequest request) {

            // nok lidt dårlig måde at "stoppe" en session på
            Object o = new Object();
            request.setAttribute("user", o, WebRequest.SCOPE_SESSION);
            return "index";
        }
        private void setSessionInfo(WebRequest request, User user) {
            //sætter user og dater til være "in scope" for hele session, det vil sige, at vi kan hente dem i hele
            // sessionen - skal lige se om "user" kan undværes her? :S
            request.setAttribute("user", user, WebRequest.SCOPE_SESSION);

        }


    }


