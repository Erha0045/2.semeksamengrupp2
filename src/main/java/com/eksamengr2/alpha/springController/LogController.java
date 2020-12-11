package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.DashboardMapper;
import com.eksamengr2.alpha.data.DataFacadeImpl;
import com.eksamengr2.alpha.model.LoginController;
import com.eksamengr2.alpha.model.LoginSampleException;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class LogController {

        private LoginController loginController = new LoginController(new DataFacadeImpl());

        @GetMapping("/login")
        public String home() {
            return "login";
        }

        @PostMapping("/login")
        public String login(WebRequest request, Model model) throws Exception {
            //henter userName og password fra loginpage textfelter
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            //delegate work + data to login controller
            User user = loginController.login(username, password);
            setSessionInfo(request, user);
            user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
            System.out.println("usertype: "+user.getUserType());
            List<Project> projectsList = new DashboardMapper().getProjectByUser(user.getUserName());
            model.addAttribute("projects", projectsList);

            System.out.println("username, password: " + username + password);
            System.out.println(user);
            return user.getUserType() + "/"+ user.getUserType() + "dashboard2";

//            Project projectz = new Project();
//            model.addAttribute("pojotransfer",projectz);
//            return user.getUserType() + "/"+ user.getUserType() + "create_project1";


        }

//        @GetMapping("/logout")
//        public String logout(WebRequest request) {
//            // nok lidt dårlig måde at "stoppe" en session på
//            Object o = new Object();
//            request.setAttribute("user", o, WebRequest.SCOPE_SESSION);
//            return "index";
//        }

        @GetMapping("/logout")
        public String logout(HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/";
     }

        private void setSessionInfo(WebRequest request, User user) {
            //sætter user og dater til være "in scope" for hele session, det vil sige, at vi kan hente dem i hele
            // sessionen - skal lige se om "user" kan undværes her? :S
            request.setAttribute("user", user, WebRequest.SCOPE_SESSION);

        }


    }


