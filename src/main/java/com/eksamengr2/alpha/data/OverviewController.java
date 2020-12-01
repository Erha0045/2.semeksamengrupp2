package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OverviewController {
    List<Project> projects = new ArrayList<>();

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

    @GetMapping("project_overview")
    public String projectoverview (Model model) throws Exception {

//        int projectId =1;
       projects = OverviewMapper.getProjectsFromDB( );
        model.addAttribute("projects", projects);

        return "project_overview";
    }


    @RequestMapping(value="/project_overview", method= RequestMethod.POST, params="knapnavn")
    public String createNewUser2(@RequestParam("textFieldName") String variabelNavn)
    {
        System.out.println("Text fra felt " + variabelNavn);
        return "project_overview";
    }




}
