package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.model.Task;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskController1 {


    //TODO tager en arraylist som input måske et object istedet??
    //TODO behøver vel ikke smide dem ind enkeltvis, kun dem der skal findes
    public ArrayList<Task> UserInput_FromAddTaskPreparedToMySQL(ArrayList<Task> inputListAddTask){
        ArrayList<Task> listForMySQLUpdate = new ArrayList<>();

        //initialsier variable
        String name = inputListAddTask.get(0).getNewTaskName();
        LocalDate startDate = inputListAddTask.get(0).getStartDate();
        int projectId = inputListAddTask.get(0).getProjectId();
        float taskNo = inputListAddTask.get(0).getTaskNo();
        String NewTaskName=inputListAddTask.get(0).getNewTaskName();
        int lineCounter=0;

        //Deklarer variable
        LocalDate finishDate=null;
        int duration=-1;
        String isSubTask="";



        //Finds value for isSubTask
        if (inputListAddTask.get(0).getName().equals("No overtask")){
            isSubTask = "no";
        }
        else {
            isSubTask = "yes";
        }

        if (inputListAddTask.get(0).getFinishDate()==null){
            //så skal finishdate beregnes med duration
            finishDate = startDate.plusDays(inputListAddTask.get(0).getDuration());
            duration = inputListAddTask.get(0).getDuration();
        }
        else {
            finishDate = inputListAddTask.get(0).getFinishDate();
            duration = (int) ChronoUnit.DAYS.between(startDate,finishDate);
        }

        //Finder duration hvis startdate og slutdate er valgt
//        if (inputListAddTask.get(0).getFinishDate()!=)



        //TODO projectId bliver hardcodet ind
        listForMySQLUpdate.add(new Task(name,startDate,finishDate,duration,1,isSubTask,taskNo,0,"")); //skal indsætte her
        System.out.println("Dette er print fra TaskController: "+listForMySQLUpdate);

        return listForMySQLUpdate;
    }
}
