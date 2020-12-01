package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.data.EditProjectMapper;
import com.eksamengr2.alpha.model.Task;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskHandler1 {


    public ArrayList<Task> UserInput_FromEditTask_PreparingObject_ForUpdateDB(ArrayList<Task> modifiedTask, ArrayList<Task> oldTask){

        ArrayList<Task> newList =new ArrayList<>();

        //The values the user did not change (old)
        LocalDate startOld = oldTask.get(0).getStartDate();
        LocalDate finishOld = oldTask.get(0).getFinishDate();
        int durationOld = oldTask.get(0).getDuration();

        //The values the user have changed (modified)->(mod)
        LocalDate startMod = modifiedTask.get(0).getStartDate();
        LocalDate finishMod = modifiedTask.get(0).getFinishDate();
        int durationMod = modifiedTask.get(0).getDuration();

        //The new values used to update the DB (new)
        LocalDate startNew=null; //= returnList.get(0).getStartDate();
        LocalDate finishNew=null;// = returnList.get(0).getFinishDate();
        int durationNew=0; //  = returnList.get(0).getDuration();

        //initialiser med værdier da get ellers ikke kan bruges
        newList.add(new Task("",LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"maybe",(float) 0.0,0));

        //The tree values for a task, startDate, finishDate and duration interact with eachother

        //1) startDate is changed-> durationNew = startMod - finishOld
        if( startMod !=null && finishMod==null && durationMod==0){
            durationNew = (int) ChronoUnit.DAYS.between(startMod,finishOld);
            startNew = startMod;
            finishNew = finishOld;
        }

        //2) FinishDate is changed->
        if( startMod ==null && finishMod!=null && durationMod==0){
            durationNew = (int) ChronoUnit.DAYS.between(startOld,finishMod);
            startNew = startOld;
            finishNew = finishMod;
        }

        //3) Duration is changed->
        if( startMod ==null && finishMod==null && durationMod!=0){
            durationNew = durationMod;
            startNew = startOld;
            finishNew = startOld.plusDays((long)durationMod );
        }

        //4) StartDate and FinishedDate is changed->
        if( startMod !=null && finishMod!=null && durationMod==0){
            durationNew = (int) ChronoUnit.DAYS.between(startMod,finishMod);;
            startNew = startMod;
            finishNew = finishMod;
        }

        //5) FinishedDate and Duration is changed->
        if( startMod ==null && finishMod!=null && durationMod!=0){
            durationNew = durationMod;
            startNew = finishMod.minusDays((long)durationMod);
            finishNew = finishMod;
        }

        //6) StartDate and Duration is changed->
        if( startMod !=null && finishMod==null && durationMod!=0){
            durationNew = durationMod;
            startNew = startMod;
            finishNew = startMod.plusDays((long)durationMod);
        }

        //7) StartDate, finishDate and Duration is changed->
        if( startMod !=null && finishMod!=null && durationMod!=0){
            durationNew = (int) ChronoUnit.DAYS.between(startMod,finishMod);
            startNew = startMod;
            finishNew = finishMod;
        }

        //8) none is changed->
        if( startMod ==null && finishMod==null && durationMod==0){
            durationNew = durationOld;
            startNew = startOld;
            finishNew = finishOld;
        }

        //fill newArraylist with values from duration, startDate and finishDate
        newList.get(0).setDuration(durationNew);
        newList.get(0).setStartDate(startNew);
        newList.get(0).setFinishDate(finishNew);


        //giving "name" a value depending on users choice
        if (modifiedTask.get(0).getName().equals("")){
            newList.get(0).setName(oldTask.get(0).getName());
        }
        else {
            newList.get(0).setName(modifiedTask.get(0).getName());
        }

        //giving attributes the old value,  since they are not in UI choise
        newList.get(0).setProjectId(oldTask.get(0).getProjectId()); //"projectId"
        newList.get(0).setIsSubTask(oldTask.get(0).getIsSubTask()); //isSubTask
        newList.get(0).setLineCounter(oldTask.get(0).getLineCounter()); //lineCounter
        newList.get(0).setNewTaskName(oldTask.get(0).getNewTaskName()); //NewTaskName

        System.out.println("new List Before method: "+newList);
        //Change of taskNumber og alle eventuelt tilhørende subtask numre TODO
        createsSqlStringForUpdatingTaskNo(1, (float)2.0, (float)3.0, "yes");

        newList.get(0).setTaskNo((float)9.99); //TODO DUMMY

        System.out.println("new List after method:  "+newList);

        //TODO fjern return og kør mappers direkte
        return newList;
    }//method


    //TODO kan vel fjerne if'erne og vel kun en repeatString da for løkke automatisk kun udskifter de valgte????
    public String createsSqlStringForUpdatingTaskNo(int projectId, float oldTaskNo, float newTaskNo, String isSubTask) {

        EditProjectMapper editProjectMapper = new EditProjectMapper();
        ArrayList<Task> listOld = editProjectMapper.get_idtasks_TaskNos(projectId,oldTaskNo); //Get a 2 colum list with idtask and OLDtaskno from DB
        ArrayList<Task> listNew = listOld;

        /*  BEGIN WORK;START TRANSACTION;
            UPDATE alfasolutionsdb.task SET taskno = 99.1 WHERE idtask=19;
            UPDATE alfasolutionsdb.task SET taskno = 99.2 WHERE idtask=20;
            COMMIT;
            */
        //Strings variables to be used to create a sql-string for mapper
        String startString="BEGIN WORK; START TRANSACTION; ";
        StringBuilder repeatString= new StringBuilder();
        String endString=" COMMIT;";
        String sqlString="";

        //Difference between new and old taskNo
        float diffBetweenOldAndNewTaskNo = newTaskNo - oldTaskNo;

        System.out.println("Before add: " + listNew );
        //Add the difference between oldTaskNo and newtaskNo to all new taskNo values
        for (Task lineElement: listNew) {
            lineElement.setTaskNo(lineElement.getTaskNo()+diffBetweenOldAndNewTaskNo);
        }
        System.out.println("After add: " + listNew );

        //Fills taskNo to the sqlString-substring with new values
        if(isSubTask.equals("no")){ //task with subtasks
//            UPDATE alfasolutionsdb.task SET taskno = 99.1 WHERE idtask=19;
            for (Task lineElment: listNew ) { //TODO den fordobler vist string ved hver iteration??
                repeatString.append(" UPDATE alfasolutionsdb.task SET taskno =").append(lineElment.getTaskNo()).append(" WHERE idtask=").append(lineElment.getIdtask()).append(";");
            }
        }

        //composing repeatString for subtask
        if(isSubTask.equals("yes")){ //subtask
            repeatString.append("UPDATE alfasolutionsdb.task SET taskno =").append(listNew.get(0).getTaskNo()).append("WHERE idtask=").append(listNew.get(0).getIdtask()).append(";");
        }


        System.out.println(sqlString);

        return sqlString=startString+repeatString+endString;
    }


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
