package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.DashboardMapper;
import com.eksamengr2.alpha.data.DeleteProjectMapper;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    private DeleteProjectMapper deleteProjectMapper = new DeleteProjectMapper();
    private ProjectMapper projectMapper = new ProjectMapper();
    private Project projectz = new Project();
    private List<Project> projectsList = new ArrayList<>();
    private DashboardMapper dashboardMapper = new DashboardMapper();


    @GetMapping("/create_project")
    public String createProject(WebRequest request, Model model) throws Exception {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        System.out.println("usertype" + user.getUserType());
        model.addAttribute("pojotransfer", projectz);//todo den bliver ikke helt brugt???
        return  user.getUserType() + "/create_project2";
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
        //todo 1 error page.
        if (projectHandler1.createProjectInputDateCheck(project1) == 0) {
            return  user.getUserType() + "/create_project1_finishdateerror";
        }
        projectHandler1.createProjectInputNameCheck(project1, user);
        if (projectHandler1.createProjectInputNameCheck(project1, user) == 0) {
            return  user.getUserType() + "/create_project1_nameerror";
        } else {
            pm.createProject(project1);
            List<Project> projectsList = new DashboardMapper().getProjectByUser(user.getUserName());
            model.addAttribute("projects", projectsList);
            return  user.getUserType() + "/dashboard2";
        }
    }

    @GetMapping("/delete_project")
    public String delete_taskView(WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        //todo fix return
        return user.getUserType() + "/delete_project1";
    }

    @PostMapping("/delete_project")
    public String delete_task(WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        try {
//            int projectID = Integer.parseInt(request.getParameter("Project_ID"));
            int projectNo = Integer.parseInt(request.getParameter("Project_number"));
            Project project = new Project(projectNo);
            model.addAttribute("project", project);
            request.setAttribute("project", project, 1);
        } catch (Exception a) {
            return  user.getUserType() + "/delete_project_error_2";

        }

        return  user.getUserType() + "/delete_project_confirmation1";
    }

    @PostMapping("/delete_project_confirmation")
    public String delete_Project_confirmation(WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        Project project = (Project) request.getAttribute("project", 1);

        try {
            int i = projectMapper.gettaskForProject(project.getProjectId());
            System.out.println(i);
            if (i == 0) {
                int status1 = deleteProjectMapper.deleteProjectFromDBNoSubTasks(project.getProjectId());
                if (status1 == 0) {
                    return "delete_project_error_2";
                }
            } else {
                int status2 = deleteProjectMapper.deleteProjectFromDB(project.getProjectId());
                System.out.println(status2);
                if (status2 == 0) {
                    return "delete_project_error_2";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  user.getUserType() + "/dashboard2";

    }


    @GetMapping("dashboard")
    public String projectoverview(WebRequest request, Model model) throws Exception {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        //        int projectId =1;
        System.out.println("BAMBOOOOOLA!" + user);
        projectsList = dashboardMapper.getProjectByUser(user.getUserName());
        model.addAttribute("projects", projectsList);
        System.out.println("DashboarduserTestProjectList" + projectsList);

        return user.getUserType() + "/dashboard2";

    }

    @RequestMapping(value = "dashboard2", method = RequestMethod.POST, params = "refreshprojectlist")
    public String refreshList(WebRequest request, Model model) throws Exception {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        System.out.println("DashboarduserTestRequestMapping" + user);

        projectsList = dashboardMapper.getProjectByUser(user.getUserName());
        model.addAttribute("projects", projectsList);
        System.out.println("testing i refresh på dashboard html");
        return  user.getUserType() + "/dashboard2";

    }
}

