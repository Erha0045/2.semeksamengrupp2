package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.data.TaskMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TaskhandlerEL {

    //subtask + task
    public boolean taskStartDateBeforeFinishCheck(Task task) {
        System.out.println(task.getFinishDate());
        if (task.getFinishDate()!=null) {
            return task.getStartDate().isBefore(task.getFinishDate());
        }else {
            return true;
        }
    }

    // subtask+task
    public boolean taskDatesAreWithinProjectDatesCheck(Task task, Project project) {

        if (task.getFinishDate()==null){
            return true;
        }else if (task.getStartDate().isBefore(project.getStartDate())) {
            return false;
        } else if (task.getStartDate().isAfter(project.getDeadlineDate())) {
            //task.getStartDate().isAfter(project.getDeadlineDate());
            return false;
        } else if (task.getFinishDate().isBefore(project.getStartDate())) {
            //task.getFinishDate().isBefore(project.getStartDate());
            return false;
        } else return !task.getFinishDate().isAfter(project.getDeadlineDate());

    }

    //subtask + task
    public boolean durationIsOverFinishDateMinusStartdateCheck(Task task) {

        return task.getDuration() == ChronoUnit.DAYS.between(task.getStartDate(), task.getFinishDate()) + 1;
    }

    public boolean createTaskTotalWorkTCalculation(Task task) {
        double totalWorkTimeHours = task.getDuration() * task.getNoOfPersons() * task.getWorkingHoursDay();

        return task.getTaskTimeconsumption() < totalWorkTimeHours;

    }

    //skal bruge heltal, men er vel ikke et problem?
    public int taskTimeConsumptionSum(Task task) throws SQLException {
        TaskMapper taskMapper = new TaskMapper();
       ArrayList<Task> taskArrayList = taskMapper.findTasksSubTasks(task);
        int timeConsumptionSum=0;

        for (Task value : taskArrayList) {
            timeConsumptionSum += value.getTaskTimeconsumption();
        }
        return timeConsumptionSum;
    }

    public int projectTimeConsumptionSum(Project project) throws SQLException {
        TaskMapper taskMapper = new TaskMapper();
        ArrayList<Task> taskArrayList = taskMapper.findProjectTask(project);
        int timeConsumptionSum=0;

        for (Task task : taskArrayList) {
            timeConsumptionSum += task.getTaskTimeconsumption();
        }
        return timeConsumptionSum;
    }

    //TODO muligvis dobbeltgænger?? possible 4 på krav listen?
    public Task createTaskTimeConsumption(Task task) {
        //
        if (task.getWorkingHoursDay() == 0) {
            task.setWorkingHoursDay((double) task.getTaskTimeconsumption() / task.getNoOfPersons() / task.getDuration());
        }
        if (task.getNoOfPersons() == 0) {

            double noOfPerson = (double) task.getTaskTimeconsumption() / task.getDuration() / task.getWorkingHoursDay();
            int noOfPersonInt = (int) Math.round(noOfPerson);
            task.setNoOfPersons(noOfPersonInt);
        }
        if (task.getDuration() == 0) {

            double duration = (double) task.getTaskTimeconsumption() / task.getNoOfPersons() / task.getWorkingHoursDay();
            int durationInt = (int) Math.round(duration);
            task.setDuration(durationInt);

        }
        return task;

    }
    //     Duration       Persons     WorkHours
    public boolean  checkForNullValue(Task task){
        if (task.getDuration() == 0 && task.getNoOfPersons() == 0 && task.getWorkingHoursDay() == 0){
                return false;
        }else if ((task.getDuration() == 0 && task.getNoOfPersons() == 0)||
                (task.getDuration() == 0 && task.getWorkingHoursDay() == 0)||
                (task.getWorkingHoursDay() == 0 && task.getNoOfPersons() == 0)){
            return false;
        }
        return true;
    }

    //if returns true it exists in DB
    public boolean checkTaskName(Task task) throws SQLException {
        TaskMapper taskMapper = new TaskMapper();
        return taskMapper.checkTaskNameExistsInDB(task);
    }
    public boolean checkTaskNo(Task task) throws SQLException {
        TaskMapper taskMapper = new TaskMapper();
        return taskMapper.checkTaskNoExistsInDB(task);
    }

    public String errorMessageTask(Task task, Project project) throws SQLException {
        if (checkTaskName(task)){
            return   "the chosen name is already in use!";
        }else if (checkTaskNo(task)){
            return  "the chosen number is already in use!";
        }else if (taskStartDateBeforeFinishCheck(task)){
            return  "finishdate is before startdate!";
        }else if (taskDatesAreWithinProjectDatesCheck(task, project)){
            return "taskdate needs to be within project scope!";
        }else
        return "";
    }

    public String errorMessageSubtask(Task task, Project project) throws SQLException {

        if (checkTaskName(task)){
            System.out.println("the chosen name is already in use!");
            return   "the chosen name is already in use!";
            //todo taskno!!
//        }else if (checkTaskNo(task)){
//            System.out.println("the chosen number is already in use!");
//            return  "the chosen number is already in use!";
        }else if (!taskStartDateBeforeFinishCheck(task)){
            System.out.println("finishdate is before startdate!");
            return  "finishdate is before startdate!";
        }else if (!taskDatesAreWithinProjectDatesCheck(task, project)){
            System.out.println("taskdate needs to be within project scope!");
            return "taskdate needs to be within project scope!";
        }else if (!checkForNullValue(task)){
            System.out.println("atleast one of (Persons on subtask:) or (Working hours/day/person:) needs to be typed! ");
            return "atleast two variables needs to be typed! ";
        }else if (!createTaskTotalWorkTCalculation(task)) {
            System.out.println("working hours total is less than task time consumption");
            return "working hours total is less than task time consumption";

        }else return "";
    }

//luff erhan test ?
//        DeleteTaskMapper deleteTaskMapper = new DeleteTaskMapper();
//        Task task = new Task();
//        task.setTaskNo(8.04);
//        task.setProjectId(1);
//        boolean svar = deleteTaskMapper.checkIfTaskNoExistsAddTask(task);
//
//        System.out.println("svar på testen :" +svar);

    //luff erhan test 2
//      TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
//       Task task = new Task();
//        task.setDuration(10);
//        task.setNoOfPersons(2);
//        task.setTaskTimeconsumption(100);
//        Task task1 = taskhandlerEL.createTaskTimeConsumption(task);
//
//        System.out.println(task1);


    // consumptionSumTest både til project og task.
//        TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
//        Project project = new Project();
////        project.setProjectId(1);
//        Task task = new Task();
//        task.setProjectId(1);
//        task.setTaskNo(1);
//        int answer =  taskhandlerEL.taskTimeConsumptionSum(task);
//        int answer1 =  taskhandlerEL.projectTimeConsumptionSum(project);
//        System.out.println(answer);


    //   100*         10       2        ?
    //   100          10        ?       5
    //  100            ?        2       5
// timeConsumption,Duration,Persons,HoursPerDay
//    public boolean createTaskInputCheckIfTaskNoExists(Task task) throws SQLException {
//
//        deleteTaskMapper.checkIfTaskNoExistsBeforeDelete(task);
//
//        return deleteTaskMapper.checkIfTaskNoExistsBeforeDelete(task);
//    }




}
