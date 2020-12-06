package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.EditProjectMapper;
import com.eksamengr2.alpha.data.Facade;
import com.eksamengr2.alpha.data.FacadeTest;
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

    private List<Task> tasksForProjectId = new ArrayList(); //Holds all tasks in a projectId
    private EditProjectMapper editProjectMapper = new EditProjectMapper();
    private TaskHandler1 taskController = new TaskHandler1();
    private List<Task> listTitler;
    private List<Task> taskLine = new ArrayList<>();
    private String projectName="Xxxxxxxx"; //TODO TEST SKAL HENTES FRA LINKVALG PÅ PROJECT OVERVIEW
    private static int projectId; //værdi hente fra URL
    private double transferTaskNo;
    private Task POJO_Task = new Task();
    private ArrayList<Task> modifiedTaskList = new ArrayList<>();
    private TaskHandler1 taskHandler1 = new TaskHandler1();
    private ArrayList<Task> arrTaskLine = new ArrayList<>();




    @GetMapping("edit_task")
    public String edittask(Model model) throws SQLException  {
        //taskLine.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes",(float) 0.0,0));

       //Transfer data to TaskNo-exampel
        model.addAttribute("taskLine", taskHandler1.ExampelForTaskLine());

        //Get tasks from DB as ArrayList
        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);

        //Afrunder double SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }
        //Transfers task-data to table
        model.addAttribute("tasks", tasksForProjectId);

        //Transfers data to Project header
        model.addAttribute("projectname","Projectname: "+projectName );

        //Transfers pojo info to textfields form
        model.addAttribute("task", POJO_Task);

        //Transfer data to exampel line
        model.addAttribute("taskLine", taskHandler1.ExampelForTaskLine());

        return "edit_task";
    }


    //Button "Get task/subTask"
    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="getTaskSubTask")
    public String getTask(Model model, @RequestParam("textFieldSubtaskNo") double taskOrSubTaskNo) throws SQLException {
        Task objTaskLine;

        //Transfers pojo info to textfields form
        model.addAttribute("task", POJO_Task);

        //Transfer data to TaskNo exampel
        model.addAttribute("taskLine", editProjectMapper.getTaskLine(projectId,Math.round(taskOrSubTaskNo*100)/100d));

        //Transfer data from ArrayList to tabel after roundofing double taskNo
        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);

        for (int i=0; i<tasksForProjectId.size(); i++ ) { //Afrunder double TODO SKAL i metode
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }
        model.addAttribute("tasks", tasksForProjectId);

        //Transfers data from input to class attribute for sharing between @PostMappings
        transferTaskNo = Math.round(taskOrSubTaskNo*100)/100d;

        //Gets taskLinePOJO-data from DB when user push Button TODO GRIMT
        objTaskLine = editProjectMapper.getTaskLine(projectId, transferTaskNo);
        arrTaskLine.add(objTaskLine);
        model.addAttribute("taskLine", arrTaskLine);

        //Transfers data to Project header TODO skal hentes fra database
        model.addAttribute("projectname","Projectname: "+projectName );

        //1)Udfyld tabel
        //2) exampel line udfyldes med dummy
        //3) Bruger henter task udfra taskNo
        //4) exampel line udfyldes med DB data
        //5) Bruger udfylder redigeringsfelter
        //6) Bruger sender ønske til System
        //7a) System tjekker input data
        //7b) System tager Hvis okay; System opdater DB
        //8) System opdater brugers tabel

        return "edit_task";
    }


    //Button "Update task"
    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="updateTask")
    public String saveChangesToTask(Model model, @ModelAttribute("task") Task task,
                                    @RequestParam("taskNo") double newTaskNo,
                                    @RequestParam("name") String name,
                                    @RequestParam(value = "startDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate newStartDate,
                                    @RequestParam(value = "finishDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate newFinishedDate,
                                    @RequestParam("duration") int duration,
                                    @RequestParam("taskTimeconsumption") int taskTimeconsumption,
                                    @RequestParam("noOfPersons") int noOfPersons,
                                    @RequestParam("workingHoursDay") double newDuration) throws SQLException {

        //1Transfer projectname to Headline in UI
        //TODO

        //2Transfer data to TaskNo-exampel TODO for GRIMT
        Task objTaskLine = editProjectMapper.getTaskLine(projectId, transferTaskNo);
        arrTaskLine.add(objTaskLine);
        model.addAttribute("taskLine", arrTaskLine);

        //3 Transfer data from ArrayList to tabel after roundofing double taskNo
        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
        for (int i=0; i<tasksForProjectId.size(); i++ ) { //Afrunder double TODO SKAL i metode
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }
        model.addAttribute("tasks", tasksForProjectId);

        //5 Send inputs to checkmetod, to evaluate input
        //TODO

        //5a Get old data for taskNo
        Task oldTaskdata = editProjectMapper.getTaskLine(projectId,transferTaskNo);
        System.out.println("Oldtask: "+oldTaskdata); //TEST
        System.out.println("modTask: " +task);




        //1 transfer headline projectname
        //2 transfer TaskNo-example data til UI
        //3 transfer arraylist til tabel
        //4 er bare task-------Hent input fra textfelter til object
        //5 Send input til en check metode
        //5a Hvis OKAY Hent old data for taskLine til object
        //6a Send old. modified object til SQL generator
        //5b Hvis IK OKAY send besked til bruger

//        //Transfer to task-info-line
//        taskLine.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes",0.0,0));
//        model.addAttribute("taskLine", taskLine);
//
//        //Transfers pojo info to textfields form
//        model.addAttribute("task", POJO_Task);
//
//        //Transfers data to Project header
//        model.addAttribute("projectname","Projectname: "+projectId );
//
//        //Transfers ArrayList-data to table
//        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
//
//        //Afrunder double SKAL NED I MAPPER TODO eller??
//        for (int i=0; i<tasksForProjectId.size(); i++ ) {
//            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100.0)/100.0);
//        }
//        model.addAttribute("tasks", tasksForProjectId);
//
//        //Input values is inserted into an ArrayList //TODO lav til et object??
//        modifiedTaskList.add(new Task(newTaskName,newStartDate ,newFinishedDate ,newDuration ,projectId,null, newTaskNo,-1));
//        System.out.println("modifiedTask: " +modifiedTaskList);
//
//        //Henter oldtaskline
//        //ArrayList<Task> oldTaskdata = editProjectMapper.getTaskLine(projectId,transferTaskNo); TODO
//
//        //Updater DB Overføre arraylist til TaskController1
//        // taskHandler1.UserInput_FromEditTask_UpdateTaskInDB(modifiedTaskList,oldTaskdata); TODO
//
//        //Transfers ArrayList-data to table after update of DB
//        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
//        model.addAttribute("tasks", tasksForProjectId);
//
//        //transfer ArrayList-data to table after update of DB
//        taskLine.clear();
//        taskLine.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes", 0.0,0));
//        model.addAttribute("taskLine", taskLine);

        return "/edit_task"; //TODO afslut test
//        return "redirect:/edit_task";
    }




    @GetMapping("add_task")
    public String add_task(Model model) throws SQLException {
        listTitler = editProjectMapper.getTasksForAddTaskDropbox(projectId); //TODO skal skiftes med en søgning på projekt nummer + No overtask

        //Person person = new Person();
        model.addAttribute("task", POJO_Task); //overføre model klasse

        //Indsætter værdier i dropbox muligheder
        model.addAttribute("listTitler", listTitler); //overføre liste til dropbox

        return "add_task";
    }


    //Button "save Task"
    @RequestMapping(value="/add_task", method= RequestMethod.POST, params="addtask")
    public String dropBoxFecthValue(@ModelAttribute("task") Task task, Model model) throws SQLException {

        //Getting and inserting data for dropbox
        listTitler = editProjectMapper.getTasksForAddTaskDropbox(projectId);
        model.addAttribute("listTitler", listTitler);

        //Preparing input data så det kan indsættes i DB
        taskController.UserInput_FromAddTaskPreparedToMySQL(task);

        return "add_task";
    }


    @RequestMapping(value="/add_task", method= RequestMethod.POST, params="cancel")
    public String cancelAddTask(Model model) throws SQLException {

        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
        //Afrunder double SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }

        model.addAttribute("tasks", tasksForProjectId);


        return "edit_project";
    }

    //Facade facade = new Facade();

    @GetMapping("edit_project")
    public String editProject(Model model, @RequestParam("projectId") int urlProjectId) throws SQLException {

        //Overføre værdi fra url
        projectId =urlProjectId;

       // tasksForProjectId = facade.getTaskForEditProject(projectId);

        FacadeTest facadeTest = new Facade();
        tasksForProjectId = facadeTest.getTaskForEditProject(projectId);
        ArrayList<Task> taskNoRounded = new ArrayList<>();

        //Afrunder double SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100.0)/100.0);
        }

        model.addAttribute("tasks", tasksForProjectId);

        return "edit_project";
    }
}
