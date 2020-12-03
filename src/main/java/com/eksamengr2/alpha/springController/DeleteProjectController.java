package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.DeleteProjectMapper;
import com.eksamengr2.alpha.model.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class DeleteProjectController {
    DeleteProjectMapper deleteProjectMapper= new DeleteProjectMapper();

    @GetMapping("/delete_project")
    public String delete_taskView() {

        return "delete_project";
    }

    @PostMapping("/delete_project")
    public String delete_task(WebRequest request, Model model) {

        try {
//            int projectID = Integer.parseInt(request.getParameter("Project_ID"));
            int projectNo = Integer.parseInt(request.getParameter("Project_number"));
            Project project = new Project(projectNo);
            model.addAttribute("project", project);
            request.setAttribute("project", project, 1);
        } catch (Exception a) {
            return "delete_project_error_1";
        }

        return "delete_project_confirmation";
    }

    @PostMapping("/delete_project_confirmation")
    public String delete_task_confirmation(WebRequest request) {
        Project project = (Project) request.getAttribute("project", 1);
        try {
            int status = deleteProjectMapper.deleteProjectFromDB(project.getProjectId());
            System.out.println(status);
            if (status == 0) {
                return "delete_task_error_1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "delete_project";
    }


}
