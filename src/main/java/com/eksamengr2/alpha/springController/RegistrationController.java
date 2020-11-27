package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.RegistrationsMapper;
import com.eksamengr2.alpha.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class RegistrationController {
        @GetMapping("/registration2")
        public String registrationpage(@ModelAttribute("user")User user, Model model) {
            model.addAttribute("user", user);
            return "registration2";
        }

    @PostMapping("/registration1")
    public String registerUser(@ModelAttribute("user") User user, WebRequest request, Model model) throws Exception {
        model.addAttribute("user", user);

        RegistrationsMapper registrationsMapper = new RegistrationsMapper();
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String email = request.getParameter("email");

        if (password1.equals(password2)) {
            user = new User(password1, email);

            setSessionInfo(request, user );
            registrationsMapper.registerUser(user);

            return "/edit_project";

        } else { // If passwords don't match, an exception is thrown
            throw new Exception("Adgangskode skal være éns.");
        }
    }
    private void setSessionInfo(WebRequest request, User user) {
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);

    }
}
