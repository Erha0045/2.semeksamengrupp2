package com.eksamengr2.alpha.springController;


import com.eksamengr2.alpha.data.DashboardMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {
    //    private ProjectRetrievingController projectRetrievingController = new ProjectRetrievingController();
    List<Project> projectsList = new ArrayList<>();
    DashboardMapper dashboardMapper = new DashboardMapper();
//    String dummyUsername = "jones";

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
    public String projectoverview(WebRequest request, Model model) throws Exception {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        //        int projectId =1;
        System.out.println("DashboarduserTest" + user);
        projectsList = dashboardMapper.getProjectByUser(user.getUserName());
        model.addAttribute("projects", projectsList);
        System.out.println("DashboarduserTestProjectList" + projectsList);

        return user.getUserType() + "/" + user.getUserType() + "dashboard2";
//        return "dashboard";

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
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        System.out.println("DashboarduserTestPostmapping" + user);
        String projectId = request.getParameter("projectId");
        return user.getUserType() + "/" + user.getUserType() + "dashboard2";

    }
//@PostMapping("dashboard" )
    @RequestMapping(value = "/dashboard", method = RequestMethod.POST, params = "refreshprojectlist")
    public String refreshList(WebRequest request, Model model) throws Exception {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        System.out.println("DashboarduserTestRequestMapping" + user);

        projectsList = dashboardMapper.getProjectByUser(user.getUserName());
        model.addAttribute("projects", projectsList);
        System.out.println("testing i refresh på dashboard html");
        return user.getUserType() + "/" + user.getUserType() + "dashboard2";


        //    @RequestMapping(value="/project_overview", method= RequestMethod.POST, params="knapnavn")
//    public String createNewUser2(@RequestParam("textFieldName") String variabelNavn)
//    {
//        System.out.println("Text fra felt " + variabelNavn);
//        return "project_overview";
//    }


    }
}