package com.eksamengr2.alpha.springController;


import com.eksamengr2.alpha.data.EditProjectMapper;
import com.eksamengr2.alpha.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class melgaController {
    List<Task> tasks = new ArrayList();
    EditProjectMapper editProjectMapper = new EditProjectMapper();
    List<Task> listTitler;
    Task task1 = new Task();

    @GetMapping("add_task")
    public String add_task(Model model) throws SQLException {
        listTitler = editProjectMapper.getTasksForAddTaskDropbox(1); //TODO skal skiftes med en søgning på projekt nummer + No overtask

        //Person person = new Person();
        model.addAttribute("task", task1); //overføre model klasse

        //Indsætter værdier i dropbox muligheder
        model.addAttribute("listTitler", listTitler); //overføre liste til dropbox

        return "add_task";
    }

    @PostMapping("/add_task") // "/dropbox" skal være det samme som i <form method="post" action="/dropbox" <---
    public String dropBoxFecthValue(@ModelAttribute("task") Task task, Model model) throws SQLException {
        listTitler = editProjectMapper.getTasksForAddTaskDropbox(1); //TODO skal skiftes med en søgning på projekt nummer + No overtask
        //System.out.println("printer person "+person.getTitel());
        List<Task> dtoTaskList = new ArrayList<>();

        //LocalDate startdate = LocalDate.parse(task.getStartDate())

        dtoTaskList.add(new Task(task.getName(),task.getStartDate(),task.getFinishDate(),task.getDuration(),1,task.getIsSubTask(),task.getTaskNo(),0,task.getNewTaskName()));
        System.out.println(dtoTaskList);

        model.addAttribute("listTitler", listTitler); //overføre liste til dropbox

        return "add_task"; //TODO skal returnere til edit_project siden eller????
    }






    @GetMapping("edit_project")
    public String editProject(Model model) throws SQLException {

        int projectId =1; //TODO TEST SKAL HENTES FRA LINKVALG PÅ PROJECT OVERVIEW
        tasks = editProjectMapper.getTaskForEditProject(projectId);
        model.addAttribute("tasks",tasks);

        return "edit_project";
    }


}
