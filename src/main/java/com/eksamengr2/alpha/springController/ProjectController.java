package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.ProjectMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.model.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@Controller
public class ProjectController {
    Project projectz = new Project();


    @GetMapping("/create_project")
    public String createProject(WebRequest request, Model model) throws Exception {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        System.out.println("usertype" + user.getUserType());
        model.addAttribute("pojotransfer", projectz);
        return user.getUserType() + "/" + user.getUserType() + "create_project1";
    }

    //      @RequestMapping(value="/create_project", method= RequestMethod.POST, params="getvalue")
    @PostMapping("/create_project")
    public String createNewUser2(Model model, WebRequest request,
                                 @RequestParam("projectName") String projectname,
                                 @RequestParam("startDate")
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startdato) throws Exception {
        model.addAttribute("pojotransfer", projectz);

        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        System.out.println("String fra felt " + projectname);
        System.out.println("LocalDate fra felt " + startdato);
//            System.out.println("String fra felt " + username );

        ProjectMapper pm = new ProjectMapper();
        Project project1 = new Project(projectname, user.getUserName(), startdato);
        pm.createProject(project1);

        return "create_project";
    }

}

