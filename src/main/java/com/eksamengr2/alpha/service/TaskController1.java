package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.model.Task;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskController1 {


    public ArrayList<Task> UserInput_FromEditTask_PreparingObject_ForUpdateDB(ArrayList<Task> modifiedTask, ArrayList<Task> oldTask){
        ArrayList<Task> returnList = new ArrayList<>();
        //modifiedTask.get(0).getName().equals("")? System.out.println("") :System.out.println("name");;


        //Den skal analysere hvilket/hvilke felter der er opdateret
        //lave en arraylist med hvor gammel og ny data kombineres
        System.out.println("modifiedTask: " +modifiedTask);
        System.out.println("oldTask: " +oldTask);

        return modifiedTask;
    }

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
