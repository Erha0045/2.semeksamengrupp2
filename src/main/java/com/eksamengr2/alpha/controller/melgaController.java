package com.eksamengr2.alpha.controller;


import com.eksamengr2.alpha.mapper.EditProjectMapper;
import com.eksamengr2.alpha.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class melgaController {
    List<Task> tasks = new ArrayList();
    EditProjectMapper editProjectMapper = new EditProjectMapper();
    List<String> listTitler = Arrays.asList("Konge", "Prins", "Bonde"); //TODO skal skiftes med en søgning på projekt nummer + No overtask
    Task task1 = new Task();

    @GetMapping("add_task")
    public String add_task(Model model){


        //Person person = new Person();
        model.addAttribute("task", task1); //overføre model klasse

        //Indsætter værdier i dropbox muligheder
        model.addAttribute("listTitler", listTitler); //overføre liste til dropbox

        return "add_task";
    }

    @PostMapping("/add_task") // "/dropbox" skal være det samme som i <form method="post" action="/dropbox" <---
    public String dropBoxFecthValue(@ModelAttribute("task") Task task, Model model)
    {
        //System.out.println("printer person "+person.getTitel());
        if ( task.getName().equals("Konge"))
        {
            System.out.println("Der er valgt konge");
        }
        else if (task.getName().equals("Prins"))
        {
            System.out.println("Der er valgt Prins");
        }
        model.addAttribute("listTitler", listTitler); //overføre liste til dropbox

        return "add_task";
    }






    @GetMapping("edit_project")
    public String editProject(Model model) throws SQLException {

        int projectId =1; //TODO TEST SKAL HENTES FRA LINKVALG PÅ PROJECT OVERVIEW
        tasks = editProjectMapper.getTaskForEditProject(projectId);
        model.addAttribute("tasks",tasks);

        return "edit_project";
    }


}
