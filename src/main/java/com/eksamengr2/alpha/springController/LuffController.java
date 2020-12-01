package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.DeleteTaskMapper;
import com.eksamengr2.alpha.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;


@Controller
public class LuffController {
    DeleteTaskMapper deleteTaskMapper = new DeleteTaskMapper();

    @GetMapping("/delete_task")
    public String delete_taskView() {

        return "delete_task";
    }

    @PostMapping("/delete_task")
    public String delete_task(WebRequest request, Model model) {

        try {
//            int projectID = Integer.parseInt(request.getParameter("Project_ID"));
            double taskNo = Double.parseDouble(request.getParameter("Task_number"));
            Task task = new Task(taskNo);
            model.addAttribute("task", task);
            request.setAttribute("task", task, 1);
        } catch (Exception a) {
            return "delete_task_error_1";
        }

        return "delete_task_confirmation";
    }

    @PostMapping("/delete_task_confirmation")
    public String delete_task_confirmation(WebRequest request) {
        Task task = (Task) request.getAttribute("task", 1);

        try {
            int status = deleteTaskMapper.deleteTaskFromDB(task.getTaskNumber());
            if (status == 0) {
                return "delete_task_error_1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "delete_task";
    }


}
