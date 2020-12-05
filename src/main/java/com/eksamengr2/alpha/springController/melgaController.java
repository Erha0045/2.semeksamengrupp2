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
    private double transferTaskNo; //TODO fjernet static
    private Task POJO_Task = new Task();
    private ArrayList<Task> modifiedTaskList = new ArrayList<>();
    private TaskHandler1 taskHandler1 = new TaskHandler1();



    @GetMapping("edit_task")
    public String edittask(Model model) throws SQLException  {

        //Eksempel data transfer TODO skal nok flyttes væk fra her
        taskLine.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes",(float) 0.0,0));
        model.addAttribute("taskLine", taskLine);

        //Get tasks from DB
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

        //TEST TEST DUMMY DATA
        model.addAttribute("test1", "Hejsa");

        return "edit_task";
    }


    //Button "Update task"
    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="updateTask")
    public String saveChangesToTask(Model model,
        @RequestParam("taskNo") double newTaskNo,
        @RequestParam("name") String newTaskName,
        @RequestParam(value = "startDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate newStartDate,
        @RequestParam(value = "finishDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate newFinishedDate,
        @RequestParam("duration") int newDuration) throws SQLException {

        //Transfer to task-info-line
        taskLine.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes",0.0,0));
        model.addAttribute("taskLine", taskLine);

        //Transfers pojo info to textfields form
        model.addAttribute("task", POJO_Task);

        //Transfers data to Project header
        model.addAttribute("projectname","Projectname: "+projectId );

        //Transfers ArrayList-data to table
        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);

        //Afrunder double SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100.0)/100.0);
        }
        model.addAttribute("tasks", tasksForProjectId);

        //Input values is inserted into an ArrayList //TODO lav til et object??
        modifiedTaskList.add(new Task(newTaskName,newStartDate ,newFinishedDate ,newDuration ,projectId,null, newTaskNo,-1));
        System.out.println("modifiedTask: " +modifiedTaskList);

        //Henter oldtaskline
        ArrayList<Task> oldTaskdata = editProjectMapper.getTaskLine(projectId,transferTaskNo);

        //Updater DB Overføre arraylist til TaskController1
        taskHandler1.UserInput_FromEditTask_UpdateTaskInDB(modifiedTaskList,oldTaskdata);

        //Transfers ArrayList-data to table after update of DB
        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
        model.addAttribute("tasks", tasksForProjectId);

        //transfer ArrayList-data to table after update of DB
        taskLine.clear();
        taskLine.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes", 0.0,0));
        model.addAttribute("taskLine", taskLine);

        return "/edit_task"; //TODO afslut test
//        return "redirect:/edit_task";
    }


    //Button "Get task/subTask"
    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="HentTaskSubTask")
    public String getTask(Model model, @RequestParam("textFieldSubtaskNo") double taskOrSubTaskNo) throws SQLException {

        //Transfers ArrayList-data to table
        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);

        //Afrunder double SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }

        model.addAttribute("tasks", tasksForProjectId);

        //Transfers pojo info to textfields form
        model.addAttribute("task", POJO_Task);

        //Transfers data from input to class attribute for sharing between @PostMappings
        transferTaskNo = Math.round(taskOrSubTaskNo*100)/100d;

        
        //TEST henter taskLine udfra projectId og Task no VIRKER
        taskLine = editProjectMapper.getTaskLine(projectId, taskOrSubTaskNo);
        model.addAttribute("taskLine", taskLine);

        System.out.println("Trykket på knap---TaskOrSubTaskNo værdi: " + taskOrSubTaskNo);

        //Transfers data to Project header
        model.addAttribute("projectname","Projectname: "+projectName );

        return "edit_task";
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


    //@PostMapping("/add_task")
    //task saved
    @RequestMapping(value="/add_task", method= RequestMethod.POST, params="addtask")
    public String dropBoxFecthValue(@ModelAttribute("task") Task task, Model model) throws SQLException {

        ArrayList<Task> taskList = new ArrayList<>();

        listTitler = editProjectMapper.getTasksForAddTaskDropbox(projectId); //TODO skal skiftes med en søgning på projekt nummer + No overtask


        //TODO PROJEKTID ER HARD CODET IND
        //Henter data fra textfields på brugerflade
        taskList.add(new Task(task.getName(),task.getStartDate(),task.getFinishDate(),task.getDuration(),projectId,task.getIsSubTask(),task.getTaskNo(),0,task.getNewTaskName()));

        System.out.println(taskList);

        //preparere input data så det kan indsættes i DB
        ArrayList<Task> a =  taskController.UserInput_FromAddTaskPreparedToMySQL(taskList);

        //Indsætter bearbejdet data fra dialogbox i DB
        editProjectMapper.insertNewTaskIn_TaskTabel(a);

        model.addAttribute("listTitler", listTitler); //overføre liste til dropbox

        return "add_task"; //TODO skal returnere til edit_project siden eller????
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
