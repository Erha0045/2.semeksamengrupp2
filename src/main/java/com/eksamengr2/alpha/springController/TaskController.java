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
    private String projectName="Xxxxxxxx"; //TODO TEST SKAL HENTES FRA LINKVALG PÅ PROJECT OVERVIEW
    private static int projectId; //værdi hente fra URL
    private static double transferTaskNo=0.0;
    private Task POJO_Task = new Task();
    private ArrayList<Task> modifiedTaskList = new ArrayList<>();
    private TaskHandler taskHandler = new TaskHandler();
    private ArrayList<Task> arrTaskLine = new ArrayList<>();
    Task objTaskLine;


    private TaskHandler taskhandler = new TaskHandler();
    public static String errorString;
    private ProjectMapper projectMapper = new ProjectMapper();
    //fra deleteTaskController
    private  DeleteTaskMapper deleteTaskMapper = new DeleteTaskMapper();
//    private EditProjectMapper editProjectMapper = new EditProjectMapper();
//    private List<Task> tasksForProjectId = new ArrayList();
//    private int projectId;
    Project project = new Project();


//    @GetMapping("edit_task")
//    public String edittask(Model model) throws SQLException  {
//        //taskLine.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes",(float) 0.0,0));
//
//       //Transfer data to TaskNo-exampel
//        model.addAttribute("taskLine", taskHandler.ExampelForTaskLine());
//
//        //Get tasks-data from DB as ArrayList
//        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
//
//        //Round off...SKAL NED I MAPPER TODO eller??
//        for (int i=0; i<tasksForProjectId.size(); i++ ) {
//            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
//        }
//        //Transfers task-data to table
//        model.addAttribute("tasks", tasksForProjectId);
//
//        //Transfers data to Project header
//        model.addAttribute("projectname","Projectname: "+projectName );
//
//        //Transfers pojo info to textfields form
//        model.addAttribute("task", POJO_Task);
//
//        //Transfer data to exampel line
//        model.addAttribute("taskLine", taskHandler.ExampelForTaskLine());
//
//        return "edit_task";
//    }
//
//
//    //Button "Get task/subTask"
//    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="getTaskSubTask")
//    public String getTask(Model model, @RequestParam("textFieldSubtaskNo") double taskOrSubTaskNo) throws SQLException {
//        Task objTaskLine;
//
//        //Transfers data to Project header TODO skal hentes fra database
//        //model.addAttribute("projectname","Projectname: "+projectName );
//
//        //Transfers taskNo from input to class attribute for sharing between @PostMappings
//        transferTaskNo = Math.round(taskOrSubTaskNo*100.00)/100.00d; //TODO ÆNDRET HER IKKE TESTET
//
//        //Transfers pojo info to textfields form
//        model.addAttribute("task", POJO_Task);
//
//        //Transfer data to TaskNo exampel
//        model.addAttribute("taskLine", editProjectMapper.getTaskLine(projectId,Math.round(taskOrSubTaskNo*100)/100d));
//
//        //Transfer data from ArrayList to tabel after roundofing double taskNo
//        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
//
//        for (int i=0; i<tasksForProjectId.size(); i++ ) { //Afrunder double TODO SKAL i metode
//            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
//        }
//        model.addAttribute("tasks", tasksForProjectId); //tabel insert
//
//        //Gets taskLinePOJO-data from DB when user push Button TODO GRIMT
//        objTaskLine = editProjectMapper.getTaskLine(projectId, transferTaskNo); //lige hentet
//        arrTaskLine.add(objTaskLine);
//        model.addAttribute("taskLine", arrTaskLine);
//
//
//
//        //1)Udfyld tabel
//        //2) exampel line udfyldes med dummy
//        //3) Bruger henter task udfra taskNo
//        //4) exampel line udfyldes med DB data
//        //5) Bruger udfylder redigeringsfelter
//        //6) Bruger sender ønske til System
//        //7a) System tjekker input data
//        //7b) System tager Hvis okay; System opdater DB
//        //8) System opdater brugers tabel
//
//        return "edit_task";
//    }
//
//
//    //Button "Update task"
//    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="updateTask")
//    public String saveChangesToTask(Model model, @ModelAttribute("task") Task task,
//                                    @RequestParam("taskNo") double newTaskNo,
//                                    @RequestParam("name") String name,
//                                    @RequestParam(value = "startDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate newStartDate,
//                                    @RequestParam(value = "finishDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate newFinishedDate,
//                                    @RequestParam("duration") int duration,
//                                    @RequestParam("taskTimeconsumption") int taskTimeconsumption,
//                                    @RequestParam("noOfPersons") int noOfPersons,
//                                    @RequestParam("workingHoursDay") double newDuration) throws SQLException {
//
//        //1Transfer projectname to Headline in UI
//        //TODO
//
////        //2Transfer data to TaskNo-exampel TODO for GRIMT
////        Task objTaskLine = editProjectMapper.getTaskLine(projectId, transferTaskNo);
////        arrTaskLine.add(objTaskLine);
////        model.addAttribute("taskLine", arrTaskLine);
////
////        //3 Transfer data from ArrayList to tabel after rounding double taskNo TODO hvorfor ikke efter data er opdateret
////        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
////        for (int i=0; i<tasksForProjectId.size(); i++ ) { //Afrunder double TODO SKAL i metode
////            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
////        }
////        model.addAttribute("tasks", tasksForProjectId);
//
//        //5 Send inputs to checkmetod, to evaluate input
//        //TODO
//
//        //5a Get old data for taskNo
//        Task oldTaskdata = editProjectMapper.getTaskLine(projectId,transferTaskNo);
//
//        //6a Sends old + modified object to SQL generator which then sends data to DB
//        taskHandler.editTask(task,oldTaskdata);
//        transferTaskNo = task.getTaskNo()==0.0?0.0:task.getTaskNo(); //TODO IKKE TESTET
//
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
//
//
//
//        //1 transfer headline projectname
//        //2 transfer TaskNo-example data til UI --Måske ikke
//        //3 transfer arraylist til tabel
//        //4 er bare task-------Hent input fra textfelter til object
//        //5 Send input til en check metode
//        //5a Hvis OKAY Hent old data for taskLine til object
//        //6a Send old + modified object til SQL generator --> mapper
//        //5b Hvis IK OKAY send besked til bruger
//
////        //Transfer to task-info-line
////        taskLine.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes",0.0,0));
////        model.addAttribute("taskLine", taskLine);
////
////        //Transfers pojo info to textfields form
////        model.addAttribute("task", POJO_Task);
////
////        //Transfers data to Project header
////        model.addAttribute("projectname","Projectname: "+projectId );
////
////        //Transfers ArrayList-data to table
////        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
////
////        //Afrunder double SKAL NED I MAPPER TODO eller??
////        for (int i=0; i<tasksForProjectId.size(); i++ ) {
////            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100.0)/100.0);
////        }
////        model.addAttribute("tasks", tasksForProjectId);
////
////        //Input values is inserted into an ArrayList //TODO lav til et object??
////        modifiedTaskList.add(new Task(newTaskName,newStartDate ,newFinishedDate ,newDuration ,projectId,null, newTaskNo,-1));
////        System.out.println("modifiedTask: " +modifiedTaskList);
////
////        //Henter oldtaskline
////        //ArrayList<Task> oldTaskdata = editProjectMapper.getTaskLine(projectId,transferTaskNo); TODO
////
////        //Updater DB Overføre arraylist til TaskController1
////        // taskHandler.UserInput_FromEditTask_UpdateTaskInDB(modifiedTaskList,oldTaskdata); TODO
////
////        //Transfers ArrayList-data to table after update of DB
////        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
////        model.addAttribute("tasks", tasksForProjectId);
////
////        //transfer ArrayList-data to table after update of DB
////        taskLine.clear();
////        taskLine.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes", 0.0,0));
////        model.addAttribute("taskLine", taskLine);
//
//        //return "/edit_task"; //TODO afslut test
//        return "redirect:/edit_task";
//    }
//

    @GetMapping("edit_task")
    public String edittask(Model model, WebRequest request) throws SQLException  {
        ArrayList<Task> taskLine = new ArrayList<>();
        System.out.println("I getmapping: " + transferTaskNo);

        if (transferTaskNo==0.0){
            taskLine = taskHandler.ExampelForTaskLine();
        }else{
            //Hvis transferTaskNo er != 0.0 så hentes taskLine fra database
            System.out.println("I ELSE**************************************************");
            System.out.println("*********************************************************");
            System.out.println("Før objTaskLine: " + objTaskLine);
            System.out.println("projectId før: "+ projectId);
            System.out.println("transferTaskNo før: "+ transferTaskNo);

            objTaskLine = editProjectMapper.getTaskLine(projectId, transferTaskNo); //lige hentet
            System.out.println("Efter objTaskLine: " + objTaskLine);
            System.out.println("Før arrTaskLine: " + objTaskLine);
            arrTaskLine.clear();
            System.out.println("Efter arrTaskLine: " + objTaskLine);
            arrTaskLine.add(objTaskLine);
            taskLine=arrTaskLine;
            System.out.println("taskLine"+taskLine);
        }

        //Transfer data to TaskNo-exampel
        model.addAttribute("taskLine", taskLine);

        //Get tasks-data from DB as ArrayList
        System.out.println("projectId: " + projectId);
        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);

        //Round off...SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }
        //Transfers task-data to table
        model.addAttribute("tasks", tasksForProjectId);

        //Transfers data to Project header
        model.addAttribute("projectname","Projectname: "+projectName );

        //Transfers pojo info to textfields form
        model.addAttribute("task", POJO_Task);
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
//        return "edit_task";
        return  user.getUserType() + "/edit_task";
    }


    //Button "Get task/subTask"
    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="getTaskSubTask")
    public String getTask(WebRequest request, Model model, @RequestParam("textFieldSubtaskNo") double taskOrSubTaskNo) throws SQLException {



        //Transfers taskNo from input to class attribute for sharing between @PostMappings
        transferTaskNo = Math.round(taskOrSubTaskNo*100.00)/100.00d; //TODO ÆNDRET HER IKKE TESTET

        //Transfers pojo info to textfields form
//        model.addAttribute("task", POJO_Task);

        //Transfer data to TaskNo exampel
       //TODO virker vist?? model.addAttribute("taskLine", editProjectMapper.getTaskLine(projectId,Math.round(taskOrSubTaskNo*100)/100d));

        //Transfer data from ArrayList to tabel after roundofing double taskNo
  //      tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);

//        for (int i=0; i<tasksForProjectId.size(); i++ ) { //Afrunder double TODO SKAL i metode
//            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
//        }
//        model.addAttribute("tasks", tasksForProjectId); //tabel insert

        //Gets taskLinePOJO-data from DB when user push Button TODO GRIMT
//        objTaskLine = editProjectMapper.getTaskLine(projectId, transferTaskNo); //lige hentet
//        arrTaskLine.add(objTaskLine);
//        model.addAttribute("taskLine", arrTaskLine);



        //1)Udfyld tabel
        //2) exampel line udfyldes med dummy
        //3) Bruger henter task udfra taskNo
        //4) exampel line udfyldes med DB data
        //5) Bruger udfylder redigeringsfelter
        //6) Bruger sender ønske til System
        //7a) System tjekker input data
        //7b) System tager Hvis okay; System opdater DB
        //8) System opdater brugers tabel

        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

       return user.getUserType() + "/edit_task";
        //return  "redirect:/user/" + user.getUserType() + "edit_task";
    }



    //Button "Update task" //TODO bruges @RequestParam eller ej?
    @RequestMapping(value="/edit_task", method= RequestMethod.POST, params="updateTask")
    public String saveChangesToTask(WebRequest request, Model model, @ModelAttribute("task") Task task,
                                    @RequestParam("taskNo") double newTaskNo,
                                    @RequestParam("name") String name,
                                    @RequestParam(value = "startDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate newStartDate,
                                    @RequestParam(value = "finishDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate newFinishedDate,
                                    @RequestParam("duration") int duration,
                                    @RequestParam("taskTimeconsumption") int taskTimeconsumption,
                                    @RequestParam("noOfPersons") int noOfPersons,
                                    @RequestParam("workingHoursDay") double newDuration) throws SQLException {

        System.out.println("newTaskNo: " + newTaskNo);
       // System.out.println("name: " + name);

        //1Transfer projectname to Headline in UI
        //TODO

//        //2Transfer data to TaskNo-exampel TODO for GRIMT
//        Task objTaskLine = editProjectMapper.getTaskLine(projectId, transferTaskNo);
//        arrTaskLine.add(objTaskLine);
//        model.addAttribute("taskLine", arrTaskLine);
//
//        //3 Transfer data from ArrayList to tabel after rounding double taskNo TODO hvorfor ikke efter data er opdateret
//        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
//        for (int i=0; i<tasksForProjectId.size(); i++ ) { //Afrunder double TODO SKAL i metode
//            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
//        }
//        model.addAttribute("tasks", tasksForProjectId);

        //5 Send inputs to checkmetod, to evaluate input
        //TODO

        //5a Get old data for taskNo
        Task oldTaskdata = editProjectMapper.getTaskLine(projectId,transferTaskNo);
        model.addAttribute("projectID", projectId);
        //6a Sends old + modified object to SQL generator which then sends data to DB
        taskHandler.editTask(task,oldTaskdata);
       // transferTaskNo = task.getTaskNo()==0.0?0.0:task.getTaskNo(); //TODO IKKE TESTET
//        if (!Objects.isNull(newTaskNo)){transferTaskNo=newTaskNo;};
//        try {
//            // Using Thread.sleep() we can add delay in our
//            // application in a millisecond time. For the example
//            // below the program will take a deep breath for one
//            // second before continue to print the next value of
//            // the loop.
//            Thread.sleep(5000);
//
//            // The Thread.sleep() need to be executed inside a
//            // try-catch block and we need to catch the
//            // InterruptedException.
//        } catch (InterruptedException ie) {
//            ie.printStackTrace();
//        }


        //Transfers ArrayList-data to table
//        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);

        //Afrunder double SKAL NED I MAPPER TODO eller??
//        for (int i=0; i<tasksForProjectId.size(); i++ ) {
//            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100.0)/100.0);
//        }
//        model.addAttribute("tasks", tasksForProjectId);




        //1 transfer headline projectname
        //2 transfer TaskNo-example data til UI --Måske ikke
        //3 transfer arraylist til tabel
        //4 er bare task-------Hent input fra textfelter til object
        //5 Send input til en check metode
        //5a Hvis OKAY Hent old data for taskLine til object
        //6a Send old + modified object til SQL generator --> mapper
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
//        // taskHandler.UserInput_FromEditTask_UpdateTaskInDB(modifiedTaskList,oldTaskdata); TODO
//
//        //Transfers ArrayList-data to table after update of DB
//        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
//        model.addAttribute("tasks", tasksForProjectId);
//
//        //transfer ArrayList-data to table after update of DB
//        taskLine.clear();
//        taskLine.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"yes", 0.0,0));
//        model.addAttribute("taskLine", taskLine);

        //return "/edit_task"; //TODO afslut test
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        return  user.getUserType() + "/edit_task";
        //return  "redirect:/user/" + user.getUserType() + "edit_task";
       // return "redirect:/edit_task";
    }




    @GetMapping("add_task")
    public String add_task(WebRequest request, Model model) throws SQLException {
        System.out.println("add_task getMapping");
        listTitler = editProjectMapper.getTasksForAddTaskDropbox(projectId); //TODO skal skiftes med en søgning på projekt nummer + No overtask


        //Person person = new Person();
        model.addAttribute("task", POJO_Task); //overføre model klasse

        //Indsætter værdier i dropbox muligheder
        model.addAttribute("listTitler", listTitler); //overføre liste til dropbox
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        //return "add_task";
        return user.getUserType() + "/add_task";
    }


//todo Error page til add tasks/subtasks - addtask + errormessage(s)
    //Button "save Task"
    @RequestMapping(value="/add_task", method= RequestMethod.POST, params="addtask")
    public String saveTask(WebRequest request, @ModelAttribute("task") Task task, Model model) throws SQLException {
        //setting attribute not included in input
        task.setProjectId(projectId);

        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

        //TODO kontrol af indtastet data
        Project project = projectMapper.getProjectFromId(projectId);
        errorString = taskhandler.errorMessageTask(task, project);
        System.out.println(errorString);
        model.addAttribute("errorString", errorString);
//        if (!errorString.equals("")) return user.getUserType() + "/add_task_error1";


        //New task inserted to DB
        taskController.AddTaskToDB(task); //TODO METODE ER IKKE OPDATET
        return   user.getUserType() + "/add_task";
    }



    //Button "save subTask"
    @RequestMapping(value="/add_task", method= RequestMethod.POST, params="addsubtask")
    public String saveSubTask(WebRequest request, @ModelAttribute("task") Task task, Model model) throws SQLException {

        //Getting and inserting data for dropbox
        listTitler = editProjectMapper.getTasksForAddTaskDropbox(projectId);
        model.addAttribute("listTitler", listTitler);

        //setting attribute not included in input
        task.setProjectId(projectId);
       //skal vel rykkes op i toppen af klassen?
        TaskMapper taskMapper = new TaskMapper();

        //testing insertet data
        Project project = projectMapper.getProjectFromId(projectId);
        //
        double overTaskNo = editProjectMapper.getTaskNo(projectId, task.getSubTaskToName());
        Task overTask = taskMapper.getTask(overTaskNo, projectId);

        String errorString = taskhandler.errorMessageSubtask(task, project, overTask);

        model.addAttribute("errorString", errorString);
        System.out.println(errorString);
//todo
//         if (!errorString.equals(""))return "add_task_error_page1";

        //New task inserted to DB
        taskController.AddTaskToDB(task); //TODO METODE ER IKKE OPDATET
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        return  user.getUserType() + "/add_task";
//        return "redirect:/add_task"; //TODO
    }


    @RequestMapping(value="/add_task", method= RequestMethod.POST, params="cancel")
    public String cancelAddTask(WebRequest request, Model model) throws SQLException {

        tasksForProjectId = editProjectMapper.getTaskForEditProject(projectId);
        //Afrunder double SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100)/100d);
        }

        model.addAttribute("tasks", tasksForProjectId);

        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
//        return "edit_project";
        return  user.getUserType() + "/edit_project";
    }

    //Facade facade = new Facade();

    @GetMapping("edit_project")
    public String editProject(WebRequest request ,Model model, @RequestParam("projectId") int urlProjectId) throws SQLException {

        //Overføre værdi fra url
        projectId =urlProjectId;

       // tasksForProjectId = facade.getTaskForEditProject(projectId);

        FacadeTest facadeTest = new Facade();
        tasksForProjectId = facadeTest.getTaskForEditProject(projectId);
        ArrayList<Task> taskNoRounded = new ArrayList<>();
        model.addAttribute("projectID", projectId);
        //Afrunder double SKAL NED I MAPPER TODO eller??
        for (int i=0; i<tasksForProjectId.size(); i++ ) {
            tasksForProjectId.get(i).setTaskNo(Math.round(tasksForProjectId.get(i).getTaskNo()*100.0)/100.0);
        }

        model.addAttribute("tasks", tasksForProjectId);
        User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
//        return "edit_project";
        return  user.getUserType() + "/edit_project";
    }

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
        return  user.getUserType() + "/delete_task1";
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
        tasksForProjectId=editProjectMapper.getTaskForEditProject(projectId);
        model.addAttribute("tasks", tasksForProjectId);
        model.addAttribute("projectId", projectId);

        return  user.getUserType() + "/edit_project";
//        ?projectId=" + projectId;
        //        return "redirect:/edit_project?projectId=" + projectId;
    }

}
