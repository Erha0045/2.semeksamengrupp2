package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.ProjectMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.time.LocalDate;

@Controller
public class ProjectController {
    Project projectz = new Project();


    @GetMapping("/create_project")
    public String createProject(WebRequest request, Model model) throws Exception {
//        model.addAttribute("search", search); //overføre model klasse
//        Daters dater1 = (Daters) request.getAttribute("dater", WebRequest.SCOPE_SESSION);

//        return  dater1.getUserType() +"/search";
        model.addAttribute("pojotransfer",projectz);
        return "create_project";
    }

        @RequestMapping(value="/create_project", method= RequestMethod.POST, params="getvalue")
        public String createNewUser2(Model model,
//                todo husk at ændre navne fra name->projectname, ownername->username
                @RequestParam("projectName") String projectname,
                @RequestParam("ownerName") String username,
                @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate startdato) throws Exception {
            model.addAttribute("pojotransfer",projectz);

            System.out.println("String fra felt " + projectname );
            System.out.println("LocalDate fra felt " + startdato );
            System.out.println("String fra felt " + username );

            ProjectMapper pm = new ProjectMapper();

        Project project1 = new Project(projectname, username, startdato);
                pm.createProject(project1);

            return "create_project";
        }

    }

