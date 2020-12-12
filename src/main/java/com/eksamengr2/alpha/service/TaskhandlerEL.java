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

        if (task.getFinishDate() != null) {
            if (task.getStartDate().isEqual(task.getFinishDate())) {
                return true;
            } else return task.getStartDate().isBefore(task.getFinishDate());
        }
        else return true;

    }

    public boolean subTaskDatesWithInTaskDates(Task task, Task overTask) {
        if (task.getFinishDate() != null) {
            if (task.getStartDate().isBefore(overTask.getStartDate())) {
                return false;
            } else if (task.getStartDate().isAfter(overTask.getFinishDate())) {
                return false;
            } else if (task.getFinishDate().isAfter(overTask.getFinishDate())) {
                return false;
            } else return !task.getFinishDate().isBefore(overTask.getStartDate());
        } else if (task.getStartDate().isBefore(overTask.getStartDate())) {
            return false;
        } else return (!task.getStartDate().isAfter(overTask.getFinishDate()));
    }

    // subtask+task
    public boolean taskDatesAreWithinProjectDatesCheck(Task task, Project project) {

        if (task.getFinishDate() != null) {

            if (task.getStartDate().isBefore(project.getStartDate())) {
                return false;
            } else if (task.getStartDate().isAfter(project.getDeadlineDate())) {
                return false;
            } else if (task.getFinishDate().isBefore(project.getStartDate())) {
                return false;
            } else return !task.getFinishDate().isAfter(project.getDeadlineDate());
        } else if (task.getStartDate().isBefore(project.getStartDate())) {
            return false;
        } else return (!task.getStartDate().isAfter(project.getDeadlineDate()));
    }


    //unused og nok ligegyldig.
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
        int timeConsumptionSum = 0;

        for (Task value : taskArrayList) {
            timeConsumptionSum += value.getTaskTimeconsumption();
        }
        return timeConsumptionSum;
    }

    public int projectTimeConsumptionSum(Project project) throws SQLException {
        TaskMapper taskMapper = new TaskMapper();
        ArrayList<Task> taskArrayList = taskMapper.findProjectTask(project);
        int timeConsumptionSum = 0;

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
    public boolean checkForNullValue(Task task) {
        if (task.getDuration() == 0 && task.getNoOfPersons() == 0 && task.getWorkingHoursDay() == 0) {
            return false;
        } else if ((task.getDuration() == 0 && task.getNoOfPersons() == 0) ||
                (task.getDuration() == 0 && task.getWorkingHoursDay() == 0) ||
                (task.getWorkingHoursDay() == 0 && task.getNoOfPersons() == 0)) {
            return false;
        }
        return true;
    }

    public boolean checkForNullValueFinishDateDuration(Task task) {

        if (task.getFinishDate() == null && task.getDuration() == 0) {
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

    public boolean checkSubTaskNoExistsInDB(Task task, double overTaskNo) throws SQLException {
        TaskMapper taskMapper = new TaskMapper();
        return taskMapper.checkSubTaskNoExistsInDB(task, overTaskNo);
    }

    public String errorMessageTask(Task task, Project project) throws SQLException {
        String error = "";
        if (checkTaskName(task)) {
            error += " \n -  the chosen name is already in use!";
        }
        if (checkTaskNo(task)) {
            error += " \n -  the chosen number is already in use!";
        }
        if (!checkForNullValueFinishDateDuration(task)) {
            error += " \n - You need to enter either a finish date or a Duration! ";
        }
        if (!taskStartDateBeforeFinishCheck(task)) {
            error += " \n -  finishdate is before startdate!";
        }
        if (!taskDatesAreWithinProjectDatesCheck(task, project)) {
            error += " \n -  taskdate needs to be within project scope!";
        }
        return error;
    }

    public String errorMessageSubtask(Task task, Project project, Task overTask) throws SQLException {

        String error = "";
        if (checkTaskName(task)) {
//            System.out.println("the chosen name is already in use!");
            error += " - the chosen name is already in use!";
        }
        if (checkSubTaskNoExistsInDB(task, overTask.getTaskNo())) {
//            System.out.println("the chosen number is already in use!");
            error += " \n -  the chosen number is already in use!";
        }
        if (!checkForNullValueFinishDateDuration(task)) {
            error += " \n - You need to enter either a finish date or a Duration! ";
        }
        if (!taskStartDateBeforeFinishCheck(task)) {
//            System.out.println("finishdate is before startdate!");
            error += "\n - finishdate is before startdate!";
        }
        if (!taskDatesAreWithinProjectDatesCheck(task, project)) {
//            System.out.println("taskdate needs to be within project scope!");
            error += "\n -  taskdate needs to be within project scope!";
        }
        if (!subTaskDatesWithInTaskDates(task, overTask)) {
            error += "\n - SubTaskDates need to be within Task scope";
        }
        return error;

        //else if (!createTaskTotalWorkTCalculation(task)) {
        // System.out.println("working hours total is less than task time consumption");
        //return "working hours total is less than task time consumption";
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
