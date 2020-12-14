package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.DeleteProjectMapper;
import com.eksamengr2.alpha.data.EditProjectMapper;
import com.eksamengr2.alpha.data.ProjectMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
public class DeleteProjectController {
    private DeleteProjectMapper deleteProjectMapper = new DeleteProjectMapper();
    private ProjectMapper projectMapper = new ProjectMapper();

    @GetMapping("/delete_project")
    public String delete_taskView(WebRequest request) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
    //todo fix return
        return "user/" + user.getUserType() + "delete_project1";
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
            return  "user/" + user.getUserType() + "delete_project_error_1";

        }

        return "user/" + user.getUserType() + "delete_project_confirmation1";
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
                    return "user/userdelete_project_error_2";
                }
            } else {
                int status2 = deleteProjectMapper.deleteProjectFromDB(project.getProjectId());
                System.out.println(status2);
                if (status2 == 0) {
                    return "user/userdelete_project_error_2";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  "user/" + user.getUserType() + "delete_project1";

    }


}
