package com.eksamengr2.alpha.springController;

import com.eksamengr2.alpha.data.*;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.model.User;
import com.eksamengr2.alpha.service.TaskHandler;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private List<Task> tasksForProjectId = new ArrayList(); //Holds all tasks in a projectId
    private EditProjectMapper editProjectMapper = new EditProjectMapper();
    private TaskHandler taskController = new TaskHandler();
    private List<Task> listTitler;
    private ArrayList<Task> taskLine = new ArrayList<>();
    private static int projectId; //værdi hente fra URL
    private static double transferTaskNo=0.0;
    private Task POJO_Task = new Task();
    private TaskHandler taskHandler = new TaskHandler();
    private ArrayList<Task> arrTaskLine = new ArrayList<>();
    Task objTaskLine;
    private TaskHandler taskhandler = new TaskHandler();
    public static String errorString;
    private ProjectMapper projectMapper = new ProjectMapper();
    private  DeleteTaskMapper deleteTaskMapper = new DeleteTaskMapper();
    Project project = new Project();
    TaskMapper taskMapper = new TaskMapper();
    ArrayList<Project> ss;
    Facade facade = new Facade();



    @GetMapping("view_hours_per_day")
    public String hoursPerDay(Model model, WebRequest request) throws SQLException {

        //Get usertype for login session
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

       //Gets data from DB
//        ArrayList<Project> ss = taskHandler.hoursPerDayCalculation(projectId);  //no facade
        ss = facade.hoursPerDayCalculation(projectId);

        model.addAttribute("hours", ss);

        return user.getUserType() + "/view_hours_per_day";
    }


    @GetMapping("edit_task")
    public String edittask(Model model, WebRequest request) throws SQLException  {
        ArrayList<Task> taskLine = new ArrayList<>();

        //Get usertype for login session
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        //Populating task info line depending on active taskNo or not
        if (transferTaskNo==0.0){
            taskLine = taskHandler.ExampelForTaskLine();
        }else{
            objTaskLine = editProjectMapper.getTaskLine(projectId, transferTaskNo); //lige hentet
            arrTaskLine.clear();
            arrTaskLine.add(objTaskLine);
            taskLine=arrTaskLine;
        }

        //Transfer data to TaskNo-exampel
        model.addAttribute("taskLine", taskLine);

        //Get tasks-data from DB as ArrayList
//        tasksForProjectId = taskHandler.viewForEditProject(projectId);  //no facade
        tasksForProjectId = facade.viewForEditProject(projectId);

        //Round off
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }
        //Transfers task-data to table
        model.addAttribute("tasks", tasksForProjectId);

        //Transfers pojo info to textfields form
        model.addAttribute("task", POJO_Task);

        return  user.getUserType() + "/edit_task";
    }


    //Button "Get task/subTask" on edit_task page
    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="textFieldSubtaskNo")
    public String getTask(WebRequest request, Model model, @RequestParam("textFieldSubtaskNo") double taskOrSubTaskNo) throws SQLException {

        //Gets usertype for login session
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        //transfer Pojo to html
        model.addAttribute("task", POJO_Task);

        //Gets taskLine-data from DB when user push Button "Get task/subtask"
//        objTaskLine = editProjectMapper.getTaskLine(projectId, Math.round(taskOrSubTaskNo*100.00)/100.00d); //No facade
        objTaskLine = facade.getTaskLine(projectId,Math.round(taskOrSubTaskNo*100.00)/100.00d);
        arrTaskLine.clear();
        arrTaskLine.add(objTaskLine);

        //populating taskline in html
        model.addAttribute("taskLine", arrTaskLine);

        //Get tasks-data from DB as ArrayList
//        tasksForProjectId = taskHandler.viewForEditProject(projectId); //no facade
        tasksForProjectId = facade.viewForEditProject(projectId);

        //Round off...SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }

        //Transfers task-data to table
        model.addAttribute("tasks", tasksForProjectId);

        //Transfers taskNo from input to class attribute for sharing between @PostMappings
        transferTaskNo = Math.round(taskOrSubTaskNo*100.00)/100.00d;

       return user.getUserType() + "/edit_task";
    }


    //Button "Update task"
    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="updateTask")
    public String saveChangesToTask(WebRequest request, Model model, @ModelAttribute("task") Task task) throws SQLException {

        //Get usertype for login session
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        //Get the previus data for selected taskno/subTaskNo
        //Task oldTaskdata = editProjectMapper.getTaskLine(projectId,transferTaskNo);  //No facade
        Task oldTaskdata = facade.getTaskLine(projectId, transferTaskNo);

        //resets transferTaskNo
        transferTaskNo=0;

        //Sends old + edited object data to DB
        taskHandler.editTask(task,oldTaskdata);

        //Get tasks-data from DB as ArrayList
        tasksForProjectId.clear();
        //tasksForProjectId = taskHandler.viewForEditProject(projectId);  //Uden facade
        tasksForProjectId = facade.viewForEditProject(projectId);

        //Round off...SKAL NED I MAPPER
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }

        //Transfers task-data to table
        model.addAttribute("tasks", tasksForProjectId); //TODO noget galt med TaskNo er den delvis gamle data der indsættes

        if (transferTaskNo==0.0){
            taskLine = taskHandler.ExampelForTaskLine();
        }else{
            objTaskLine = editProjectMapper.getTaskLine(projectId, transferTaskNo); //lige hentet
            arrTaskLine.clear();
            arrTaskLine.add(objTaskLine);
            taskLine=arrTaskLine;
        }

        //Transfer data to TaskNo-exampel
        model.addAttribute("taskLine", taskLine);

        return  user.getUserType() + "/edit_task";
    }


    @GetMapping("add_task")
    public String add_task(WebRequest request, Model model) throws SQLException {
        //Gets usetype
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        model.addAttribute("errorMsg", errorString);

        //Gets names for tasks under projectId
        //listTitler = editProjectMapper.getTasksForAddTaskDropbox(projectId);  //no facade
        listTitler = facade.getTasksForAddTaskDropbox(projectId);

        //transfer Pojo to html
        model.addAttribute("task", POJO_Task);

        //Populate dropbox
        model.addAttribute("listTitler", listTitler);

        //Get tasks-data from DB as ArrayList
//        tasksForProjectId = taskHandler.viewForEditProject(projectId);  //No facade
        tasksForProjectId = facade.viewForEditProject(projectId);

        //Round off...SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }

        //Transfers task-data to table
        model.addAttribute("tasks", tasksForProjectId);

        return user.getUserType() + "/add_task";
    }


    //Button "Save task"
    @RequestMapping(value="/add_task", method= RequestMethod.POST, params="addtask")
    public String saveTask(WebRequest request, @ModelAttribute("task") Task task, Model model) throws SQLException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        //Setting attribute not included in input
        task.setProjectId(projectId);

        //Get project object
//        Project project = projectMapper.getProjectFromId(projectId);  //no facade
        Project project = facade.getProjectFromId(projectId);


        //Handles errors for user input
        if (taskHandler.errorMessageTask(task,project).equals("")) {
            taskController.AddTaskToDB(task); //New task inserted to DB
        }
        else {
            //System.out.println(errorString = taskhandler.errorMessageTask(task, project));
            errorString = taskhandler.errorMessageTask(task, project);
            model.addAttribute("errorMsg", errorString);
        }

        //Gets names for tasks under projectId
//        listTitler = editProjectMapper.getTasksForAddTaskDropbox(projectId);  //facade
        listTitler = facade.getTasksForAddTaskDropbox(projectId);

        //transfer Pojo to html
        model.addAttribute("task", POJO_Task);

        //Populate dropbox
        model.addAttribute("listTitler", listTitler);

        //Get tasks-data from DB as ArrayList
//        tasksForProjectId = taskHandler.viewForEditProject(projectId); //no facade
        tasksForProjectId = facade.viewForEditProject(projectId);

        //Round off...SKAL NED I MAPPER
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }

        //Transfers task-data to table
        model.addAttribute("tasks", tasksForProjectId);

        return   user.getUserType() + "/add_task";
    }


    //Button "save subTask"
    @RequestMapping(value="/add_task", method= RequestMethod.POST, params="addsubtask")
    public String saveSubTask(WebRequest request, @ModelAttribute("task") Task task, Model model) throws SQLException {

        //Get usertype for user
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        //setting attribute not included in input //TODO HER??
        task.setProjectId(projectId);

        //testing insertet data
//        Project project = projectMapper.getProjectFromId(projectId);  //no facade
        Project project = facade.getProjectFromId(projectId);


        double overTaskNo = editProjectMapper.getTaskNo(projectId, task.getSubTaskToName()); //TODO HVORFOR VAR DEN HER
//        Task overTask = taskMapper.getTask(overTaskNo, projectId);  //no facade
        Task overTask = facade.getTask(overTaskNo, projectId);

        if (taskHandler.errorMessageSubtask(task,project, overTask).equals("")) {
            taskController.AddTaskToDB(task); //New task inserted to DB
        }
        else {
            errorString = taskhandler.errorMessageSubtask(task,project,overTask);
            model.addAttribute("errorMsg", errorString); //error msg send
        }

        //Gets names for tasks under projectId for view
//        listTitler = editProjectMapper.getTasksForAddTaskDropbox(projectId); //no facade
        listTitler = facade.getTasksForAddTaskDropbox(projectId);

        //transfer Pojo to html
        model.addAttribute("task", POJO_Task);

        //Populate dropbox
        model.addAttribute("listTitler", listTitler);

        //Get tasks-data from DB as ArrayList
//        tasksForProjectId = taskHandler.viewForEditProject(projectId); //no facade
        tasksForProjectId = facade.viewForEditProject(projectId);

        //Round off...SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }

        //Transfers task-data to table
        model.addAttribute("tasks", tasksForProjectId);

        return  user.getUserType() + "/add_task";
    }


    @RequestMapping(value="/add_task", method= RequestMethod.POST, params="cancel")
    public String cancelAddTask(WebRequest request, Model model, @ModelAttribute("task") Task task) throws SQLException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        //Opdates DB with new subTask
//        taskController.AddTaskToDB(task); // no facade
        facade.AddTaskToDB(task);

        //Gets names for tasks under projectId for view
//        listTitler = editProjectMapper.getTasksForAddTaskDropbox(projectId); //no facade
        listTitler = facade.getTasksForAddTaskDropbox(projectId);

        //transfer Pojo to html
        model.addAttribute("task", POJO_Task);

        //Populate dropbox
        model.addAttribute("listTitler", listTitler);

        //Get tasks-data from DB as ArrayList
//        tasksForProjectId = taskHandler.viewForEditProject(projectId); //no facade
        tasksForProjectId = facade.viewForEditProject(projectId);

        //Round off...SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }

        //Transfers task-data to table
        model.addAttribute("tasks", tasksForProjectId);

        return  user.getUserType() + "/edit_project";
    }



    @GetMapping("edit_project")
    public String editProject(WebRequest request ,Model model, @RequestParam("projectId") int urlProjectId) throws SQLException {

        //get usertype for login session
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        //Overføre værdi fra url
        projectId =urlProjectId;



        //FacadeTest facadeTest = new Facade();
//        tasksForProjectId = taskHandler.viewForEditProject(projectId);  //no facade
        tasksForProjectId = facade.getTaskForEditProject(projectId);

        // ArrayList<Task> taskNoRounded = new ArrayList<>();
        model.addAttribute("projectID", projectId);

        //Afrunder double SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100.0)/100.0);
        }

        //Transfer data to tabel
        model.addAttribute("tasks", tasksForProjectId);

        return  user.getUserType() + "/edit_project";
    }


    @GetMapping("/delete_task")
    public String delete_taskView(WebRequest request , Model model) throws SQLException {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        project.setProjectId(projectId);

//        tasksForProjectId = taskHandler.viewForEditProject(projectId); //facade
        tasksForProjectId = facade.viewForEditProject(projectId);

        //Transfer data to html
        model.addAttribute("tasks", tasksForProjectId);
        model.addAttribute("projectId", projectId);

        return  user.getUserType() + "/delete_task1";
    }


    @PostMapping("/delete_task")
    public String delete_task(WebRequest request, Model model) {
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        try {
            double taskNo = Double.parseDouble(request.getParameter("Task_number"));
            Task task = new Task(taskNo);
            model.addAttribute("task", task);
            request.setAttribute("task", task, 1);
        } catch (Exception a) {
            return user.getUserType() + "/delete_task_error_1";
        }
        return  user.getUserType() +  "/delete_task_confirmation1";
    }


    @PostMapping("/delete_task_confirmation")
    public String delete_task_confirmation(WebRequest request,Model model) throws SQLException {
        Task task = (Task) request.getAttribute("task", 1);
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        project.setProjectId(projectId);
        try {
            if (deleteTaskMapper.deleteTaskFromDB(task.getTaskNumber(),projectId) == 0) {
                return  user.getUserType() +"/delete_task_error_1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tasksForProjectId = taskHandler.viewForEditProject(projectId);
        model.addAttribute("tasks", tasksForProjectId);
        model.addAttribute("projectId", projectId);

        return  user.getUserType() + "/edit_project";
    }

}
