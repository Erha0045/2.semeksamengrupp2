package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.DashboardMapper;
import com.eksamengr2.alpha.data.DataFacadeImpl;
import com.eksamengr2.alpha.data.RegistrationsMapper;
import com.eksamengr2.alpha.model.LoginController;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.User;
import com.eksamengr2.alpha.service.UserHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FrontController {

    private LoginController loginController = new LoginController(new DataFacadeImpl());
    private UserHandler userHandler = new UserHandler();
    private String errorMsg;

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
        System.out.println(user.getUserName());
      if(user.getUserName()==null){
          errorMsg="no user exists with that name and that password";
          model.addAttribute("errorMsg",errorMsg);
          return "Login";
      }
        setSessionInfo(request, user);
        user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        System.out.println("usertype: " + user.getUserType());
        List<Project> projectsList = new DashboardMapper().getProjectByUser(user.getUserName());
        model.addAttribute("projects", projectsList);

        System.out.println("username, password: " + username + password);
        System.out.println(user);
        return user.getUserType() + "/dashboard2";


    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/";
    }

    private void setSessionInfo(WebRequest request, User user) {
        //sætter user og dater til være "in scope" for hele session, det vil sige, at vi kan hente dem i hele
        // sessionen - skal lige se om "user" kan undværes her? :S
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);

    }

    @GetMapping("/registration2")
    public String registrationpage(Model model) {
    model.addAttribute("errorMsg", errorMsg);
        return "registration2";
    }

    @PostMapping("registration2")
    public String registerUser(WebRequest request,Model model) throws Exception {

        RegistrationsMapper registrationsMapper = new RegistrationsMapper();
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String userName = request.getParameter("username");

        errorMsg=userHandler.CreateUserError(userName, password1, password2);
        model.addAttribute("errorMsg",errorMsg);
        if(!errorMsg.equals("")) {
            System.out.println(errorMsg);
            return "registration2";
        }

        User user = new User(password1, userName, "user");

        setSessionInfo(request, user);
        registrationsMapper.registerUser(user);

        return user.getUserType() + "/dashboard2";


    }
}





