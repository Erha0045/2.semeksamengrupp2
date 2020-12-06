package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.ProjectMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.User;
import com.eksamengr2.alpha.service.ProjectHandler;
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
        model.addAttribute("pojotransfer", projectz);//todo den bliver ikke helt brugt???
        return user.getUserType() + "/" + user.getUserType() + "create_project1";
    }

    //      @RequestMapping(value="/create_project", method= RequestMethod.POST, params="getvalue")
    @PostMapping("/create_project")

    public String createNewUser2(Model model, WebRequest request,
                                 @RequestParam("projectName") String projectname,
                                 @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startdate,
                                 @RequestParam("deadlineDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadlinedate) throws Exception {
        model.addAttribute("pojotransfer", projectz);

        ProjectHandler projectHandler1 = new ProjectHandler();
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        System.out.println("String fra felt " + projectname);
        System.out.println("LocalDate fra felt " + startdate);
        System.out.println("LocalDate fra felt " + deadlinedate);

        ProjectMapper pm = new ProjectMapper();
        Project project1 = new Project(projectname, user.getUserName(), startdate, deadlinedate);
        projectHandler1.createProjectInputDateCheck(project1);
        if (projectHandler1.createProjectInputDateCheck(project1) == 0) {
            return user.getUserType() + "/" + user.getUserType() + "create_project1_finishdateerror";
        }
        projectHandler1.createProjectInputNameCheck(project1, user);
        if (projectHandler1.createProjectInputNameCheck(project1, user) == 0){
            return user.getUserType() + "/" + user.getUserType() + "create_project1_nameerror";
        }
         else {
            pm.createProject(project1);

            return "create_project";
        }
    }
}

