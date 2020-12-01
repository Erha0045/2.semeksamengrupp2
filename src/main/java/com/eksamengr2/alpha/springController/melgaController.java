package com.eksamengr2.alpha.springController;


import com.eksamengr2.alpha.data.EditProjectMapper;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.service.TaskHandler1;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class melgaController {
    List<Task> tasks = new ArrayList();
    EditProjectMapper editProjectMapper = new EditProjectMapper();
    TaskHandler1 taskController = new TaskHandler1();
    List<Task> listTitler;
    List<Task> taskLine = new ArrayList<>();
    private String projectName="Xxxxxxxx"; //TODO TEST SKAL HENTES FRA LINKVALG PÅ PROJECT OVERVIEW
    private int projectId =1; //TODO TEST SKAL HENTES FRA LINKVALG PÅ PROJECT OVERVIEW
    private float transferTaskNo; //TODO fjernet static

    ArrayList<Task> taskList = new ArrayList<>();
    Task POJO_Task = new Task();




    @GetMapping("edit_task")
    public String edittask(Model model) throws SQLException {
        List<Task> taskLine = new ArrayList<>();
//        //TEST henter taskLine udfra projectId og Task no VIRKER
//        editProjectMapper.getTaskLine(1, (float) 2.20);


        //Eksempel data transfer TODO skal nok flyttes væk fra her
        taskLine.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes",(float) 0.0,0));
        model.addAttribute("taskLine", taskLine);

        //Transfers ArrayList-data to table
        tasks = editProjectMapper.getTaskForEditProject(projectId);
        model.addAttribute("tasks", tasks);

        //Transfers data to Project header
        model.addAttribute("projectname","Projectname: "+projectName );

        //Transfers pojo info to textfields form
        model.addAttribute("task", POJO_Task);

        //TEST TEST DUMMY DATA
        model.addAttribute("test1", "Hejsa");



        TaskHandler1 taskHandler1 = new TaskHandler1();

        //Bliver kald når
        String sqlstring = taskHandler1.createsSqlStringForUpdatingTaskNo(1, (float)2.0, (float)3.0, "no");
        //System.out.println("SQL_String: " + sqlstring);

        editProjectMapper.updateTaskNos(sqlstring);

        return "edit_task";
    }

//    //Update the task edited
//        @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="updateTask")
//        public String saveChangesToTask(Model model,  @RequestParam("newTaskName") String newTaskName, @RequestParam("newTaskNo") float newTaskNo) throws SQLException {
//
//            //@RequestParam("newStartDate") LocalDate newStartDate,
//
//            List<Task> modifiedTaskList = new ArrayList<>();
//            model.addAttribute("taskLine", taskLine);
//
//            //Transfers pojo info to textfields form
//            model.addAttribute("task", task1);
//
//            //Transfers data to Project header
//            model.addAttribute("projectname","Projectname: "+projectName );
//
//            //Skal hente alle data fra de 5 tekst felter indsæt i arraylist
//            modifiedTaskList.add(new Task(newTaskName, LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes",newTaskNo,0));
//            System.out.println("Update task: " +modifiedTaskList);//TEST TEST
//        model.addAttribute("taskLine", taskLine);
//
//        //lav test print bare for at se hvad de indeholder når de er tomme
//        //Overføre arraylist til TaskController1
//        //Den skal analysere hvilket/hvilke felter der er opdateret
//        //lave en arraylist med hvor gammel og ny data kombineres
//
//
//        return "redirect:/edit_task";
//    }

    //Update the task edited
    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="updateTask")
    public String saveChangesToTask(Model model,
        @RequestParam("taskNo") float newTaskNo,
        @RequestParam("name") String newTaskName,
        @RequestParam(value = "startDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate newStartDate,
        @RequestParam(value = "finishDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate newFinishedDate,
        @RequestParam("duration") int newDuration) throws SQLException {

        ArrayList<Task> modifiedTaskList = new ArrayList<>();

        //Transfers pojo info to textfields form
        model.addAttribute("POJO_Task", POJO_Task);

        //Input values is inserted into an ArrayList TODO projectId er hardcodet ind
        modifiedTaskList.add(new Task(newTaskName,newStartDate ,newFinishedDate ,newDuration ,projectId,null, newTaskNo,-1));
        System.out.println("modifiedTask: " +modifiedTaskList);

        //Transfers data to Project header
        model.addAttribute("projectname","Projectname: "+projectId );

        //Henter oldtaskline
        EditProjectMapper editProjectMapper = new EditProjectMapper();
        ArrayList<Task> oldTaskdata = new ArrayList<>();

        oldTaskdata= editProjectMapper.getTaskLine(1,transferTaskNo);

        //Overføre arraylist til TaskController1
        TaskHandler1 taskHandler1 = new TaskHandler1();
        taskHandler1.UserInput_FromEditTask_PreparingObject_ForUpdateDB(modifiedTaskList,oldTaskdata);


        return "redirect:/edit_task";
    }

//    //Update the task edited
//    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="updateTask")
//    public String saveChangesToTask(Model model,
//                                    @RequestParam("newTaskNo") String newTaskNoString,
//                                    @RequestParam("newTaskName") String newTaskName,
//                                    @RequestParam("newStartDate") String newStartDateString,
//                                    @RequestParam("newFinishedDate") String newFinishedDateString,
//                                    @RequestParam("newDuration") String newDurationString) throws SQLException {
//
//        ArrayList<Task> modifiedTaskList = new ArrayList<>();
//
//        //Parse userinput from String to primitive type TODO i METODE
//        LocalDate newStartDate = LocalDate.parse(newStartDateString);
//        LocalDate newFinishDate = LocalDate.parse(newFinishedDateString);
//        float newTaskNo = Float.parseFloat(newTaskNoString);
//        int newDuration = Integer.parseInt(newDurationString);
//
//        //5 input values is inserted into an ArrayList
//        modifiedTaskList.add(new Task(newTaskName,newStartDate ,newFinishDate ,newDuration ,1,null, newTaskNo,-1));
//
//        //Transfers pojo info to textfields form
//        model.addAttribute("task", task1);
//
//        //Transfers data to Project header
//        model.addAttribute("projectname","Projectname: "+projectId );
//
//
//
//        //Henter oldtaskline
//        EditProjectMapper editProjectMapper = new EditProjectMapper();
//        ArrayList<Task> oldTaskdata = new ArrayList<>();
////        float transferTaskNo = (float) 2.2;
////        oldTaskdata= editProjectMapper.getTaskLine(1,transferTaskNo);
//
//        //Overføre arraylist til TaskController1
//        TaskController1 taskController1 = new TaskController1();
//        taskController1.UserInput_FromEditTask_PreparingObject_ForUpdateDB(modifiedTaskList,oldTaskdata);
//        //Den skal analysere hvilket/hvilke felter der er opdateret
//        //lave en arraylist med hvor gammel og ny data kombineres
//
//        return "redirect:/edit_task";
//    }

    //Gets "task/subTask"
    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="HentTaskSubTask")
    public String getTask(Model model, @RequestParam("textFieldSubtaskNo") float taskOrSubTaskNo) throws SQLException {

        //Transfers pojo info to textfields form
        model.addAttribute("task", POJO_Task);

        //Transfers data from input to class attribute for sharing between @PostMappings
        transferTaskNo = taskOrSubTaskNo;

        //TEST henter taskLine udfra projectId og Task no VIRKER
        taskLine = editProjectMapper.getTaskLine(1, (float) taskOrSubTaskNo);
        model.addAttribute("taskLine", taskLine);

        System.out.println("Trykket på knap---TaskOrSubTaskNo værdi: " + taskOrSubTaskNo);

        //Transfers data to Project header
        model.addAttribute("projectname","Projectname: "+projectName );

        return "edit_task";
    }

//    @PostMapping("/edit_task")
//    public String editTask(@ModelAttribute("task") Task task, Model model) throws SQLException {
//
//
//        //TODO PROJEKTID ER HARD CODET IND
////        taskList.add(new Task(task.getName(),task.getStartDate(),task.getFinishDate(),task.getDuration(),1,task.getIsSubTask(),task.getTaskNo(),0,task.getNewTaskName()));
//
////        //preparere input data så det kan indsættes i DB
////        ArrayList<Task> a =  taskController.UserInput_FromAddTaskPreparedToMySQL(taskList);
////
////        //Indsætter bearbejdet data fra dialogbox i DB
////        editProjectMapper.insertNewTaskIn_TaskTabel(a);
////
////        //model.addAttribute("listTitler", listTitler); //overføre liste til dropbox
//
//        return "edit_task"; //TODO skal returnere til edit_project siden eller????
//    }

    @GetMapping("add_task")
    public String add_task(Model model) throws SQLException {
        listTitler = editProjectMapper.getTasksForAddTaskDropbox(1); //TODO skal skiftes med en søgning på projekt nummer + No overtask

        //Person person = new Person();
        model.addAttribute("task", POJO_Task); //overføre model klasse

        //Indsætter værdier i dropbox muligheder
        model.addAttribute("listTitler", listTitler); //overføre liste til dropbox

        return "add_task";
    }

    @PostMapping("/add_task") // "/dropbox" skal være det samme som i <form method="post" action="/dropbox" <---
    public String dropBoxFecthValue(@ModelAttribute("task") Task task, Model model) throws SQLException {
        listTitler = editProjectMapper.getTasksForAddTaskDropbox(1); //TODO skal skiftes med en søgning på projekt nummer + No overtask
        //System.out.println("printer person "+person.getTitel());
        ArrayList<Task> taskList = new ArrayList<>();

        //TODO PROJEKTID ER HARD CODET IND
        //Henter data fra textfields på brugerflade
        taskList.add(new Task(task.getName(),task.getStartDate(),task.getFinishDate(),task.getDuration(),1,task.getIsSubTask(),task.getTaskNo(),0,task.getNewTaskName()));

        System.out.println(taskList);

        //preparere input data så det kan indsættes i DB
        ArrayList<Task> a =  taskController.UserInput_FromAddTaskPreparedToMySQL(taskList);

        //Indsætter bearbejdet data fra dialogbox i DB
        editProjectMapper.insertNewTaskIn_TaskTabel(a);

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
