package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HandlesDTO_FromUserInputForms{


    //TODO tager en arraylist som input måske et object istedet??
    //TODO behøver vel ikke smide dem ind enkeltvis, kun dem der skal findes
    public ArrayList<Task> UserInputDTO_FromAddTaskPreparedToMySQL(ArrayList<Task> inputListAddTask){
        ArrayList<Task> listForMySQLUpdate = new ArrayList<>();
        String name = inputListAddTask.get(0).getNewTaskName();
        LocalDate startDate = inputListAddTask.get(0).getStartDate();
        int projectId = inputListAddTask.get(0).getProjectId();
        float taskNo = inputListAddTask.get(0).getTaskNo();
        String NewTaskName=inputListAddTask.get(0).getNewTaskName();
        LocalDate finishDate;
        int duration;
        String isSubTask;
        int lineCounter=0;


        //Finds value for isSubTask
        if (inputListAddTask.get(0).getName().equals("No overtask")){
            isSubTask = "no";
        }
        else {
            isSubTask = "yes";
        }

        if (inputListAddTask.get(0).getFinishDate()==null){
            //så skal den beregnes med duration
        }
        else {
            finishDate = inputListAddTask.get(0).getFinishDate();
        }

        //Skla lave if for duration

        listForMySQLUpdate.add(new Task()); //skal indsætte her


        return listForMySQLUpdate;
    }
}
