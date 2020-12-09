package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.DeleteTaskMapper;
import com.eksamengr2.alpha.data.EditProjectMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class DeleteTaskController {
  private  DeleteTaskMapper deleteTaskMapper = new DeleteTaskMapper();
  private EditProjectMapper editProjectMapper = new EditProjectMapper();
    private List<Task> tasksForProjectId = new ArrayList();
    private int projectId;
    Project project = new Project();

    @GetMapping("/delete_task")
    public String delete_taskView(WebRequest request ,@RequestParam("projectId") int urlProjectId, Model model) throws SQLException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        projectId=urlProjectId;
        project.setProjectId(projectId);
        tasksForProjectId=editProjectMapper.getTaskForEditProject(projectId);
        model.addAttribute("tasks", tasksForProjectId);
        model.addAttribute("projectId", projectId);
//        System.out.println("project id delete task Controller : " + tasksForProjectId.get(0).getProjectId());
        System.out.println("LuffController user : " + user);
        return  "user/" + user.getUserType() + "delete_task1";
    }
// user.getUserType() + "/" + user.getUserType() + "create_project1";
    @PostMapping("/delete_task")
    public String delete_task(WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        try {
            System.out.println("delete task post mapping project id : " + projectId);
            double taskNo = Double.parseDouble(request.getParameter("Task_number"));
            Task task = new Task(taskNo);
            model.addAttribute("task", task);
            request.setAttribute("task", task, 1);
        } catch (Exception a) {
            System.out.println("jeg skrev ikke noget ind");
            return "user/" + user.getUserType() + "delete_task_error_1";
        }

        return "user/" + user.getUserType() +  "delete_task_confirmation1";
    }

    @PostMapping("/delete_task_confirmation")
    public String delete_task_confirmation(WebRequest request) {
        Task task = (Task) request.getAttribute("task", 1);
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        try {
            if (deleteTaskMapper.deleteTaskFromDB(task.getTaskNumber(),projectId) == 0) {
                return "user/" + user.getUserType() +"delete_task_error_1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/edit_project?projectId=" + projectId;
    }


}
