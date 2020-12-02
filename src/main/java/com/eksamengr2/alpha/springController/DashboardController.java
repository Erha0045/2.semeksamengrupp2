package com.eksamengr2.alpha.springController;


import com.eksamengr2.alpha.data.OverviewMapper;
import com.eksamengr2.alpha.model.Project;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {
    //    private ProjectRetrievingController projectRetrievingController = new ProjectRetrievingController();
    List<Project> projectsList = new ArrayList<>();
    OverviewMapper overviewMapper = new OverviewMapper();
    String dummyUsername = "jones";

//    @GetMapping("project_overview")
//    public String projectList(){
//
//        return "project_overview";
//    }

//    @GetMapping("project_overview")
//    public String project_overview()
//    {
//        return "project_overview";
//    }


    @GetMapping("dashboard")
    public String projectoverview(Model model) throws Exception {
        //        int projectId =1;
        projectsList = overviewMapper.getProjectByUser(dummyUsername);
        model.addAttribute("projects", projectsList);

        return "dashboard";
    }

//@GetMapping("/login")
//
//
//
//    @PostMapping("/login")
//public String (WebRequest request) throws Exception{
//        String username= request.getParameter("username");
//    User user = loginController.login(username, password);
//    setSessionInfo(request, user);
//}


    @PostMapping("dashboard")
    public String projectoverview(WebRequest request) {
        String projectId = request.getParameter("projectId");
        return "dashboard";

    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.POST, params = "refreshprojectlist")
    public String refreshList(Model model) throws Exception {

        projectsList = overviewMapper.getProjectByUser(dummyUsername);
        model.addAttribute("projects", projectsList);
        System.out.println("testing i refresh");
        return "dashboard";


        //    @RequestMapping(value="/project_overview", method= RequestMethod.POST, params="knapnavn")
//    public String createNewUser2(@RequestParam("textFieldName") String variabelNavn)
//    {
//        System.out.println("Text fra felt " + variabelNavn);
//        return "project_overview";
//    }


    }
}