package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.data.EditProjectMapper;
import com.eksamengr2.alpha.model.Task;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskHandler1 {
    EditProjectMapper editProjectMapper = new EditProjectMapper();


    public ArrayList<Task> UserInput_FromEditTask_UpdateTaskInDB(ArrayList<Task> modifiedTask, ArrayList<Task> oldTask) throws SQLException {
        LocalDate startOld;
        LocalDate finishOld;
        LocalDate startMod;
        LocalDate finishMod;

        //*********************************************************
        //Using local variables to make the code more readable
        //*********************************************************

        //The values the user did not change, keeping (old)
       // if (oldTask.get(0).getStartDate()!=null && oldTask.get(0).getFinishDate()!=null ) {
            startOld = oldTask.get(0).getStartDate();
            finishOld = oldTask.get(0).getFinishDate();
        //}
       // else {
       //     startOld = null;
       //     finishOld = null;
        //}
        int durationOld = oldTask.get(0).getDuration();

        //The values the user have changed (modified)->(mod)
        startMod = modifiedTask.get(0).getStartDate();
        finishMod = modifiedTask.get(0).getFinishDate();
        int durationMod = modifiedTask.get(0).getDuration();

        //The new values used to update the DB (new)
        LocalDate startNew=null; //= returnList.get(0).getStartDate();
        LocalDate finishNew=null;// = returnList.get(0).getFinishDate();
        int durationNew=0; //  = returnList.get(0).getDuration();

        //Transfer data from the old(DB-values) to new list
        ArrayList<Task> newList = oldTask;

        //initialiser med værdier da get ellers ikke kan bruges
        //newList.add(new Task("",LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"maybe",(float) 0.0,0));

        //**************************************************************************************
        //The tree values for a task, startDate, finishDate and duration interact with eachother
        //**************************************************************************************
        //1) startDate is changed-> durationNew = startMod - finishOld
        if( startMod !=null && finishMod==null && durationMod==0){
            durationNew = (int) ChronoUnit.DAYS.between(startMod,finishOld) +1;
            startNew = startMod;
            finishNew = finishOld;
        }

        //2) FinishDate is changed->
        if( startMod ==null && finishMod!=null && durationMod==0){
            durationNew = (int) ChronoUnit.DAYS.between(startOld,finishMod) +1;
            startNew = startOld;
            finishNew = finishMod;
        }

        //3) Duration is changed->
        if( startMod ==null && finishMod==null && durationMod!=0){
            durationNew = durationMod;
            startNew = startOld;
            finishNew = startOld.plusDays(durationMod +1);
        }

        //4) StartDate and FinishedDate is changed->
        if( startMod !=null && finishMod!=null && durationMod==0){
            durationNew = (int) ChronoUnit.DAYS.between(startMod,finishMod) +1;;
            startNew = startMod;
            finishNew = finishMod;
        }

        //5) FinishedDate and Duration is changed->
        if( startMod ==null && finishMod!=null && durationMod!=0){
            durationNew = durationMod;
            startNew = finishMod.minusDays((long)durationMod-1);
            finishNew = finishMod;
        }

        //6) StartDate and Duration is changed->
        if( startMod !=null && finishMod==null && durationMod!=0){
            durationNew = durationMod;
            startNew = startMod;
            finishNew = startMod.plusDays((long)durationMod+1);
        }

        //7) StartDate, finishDate and Duration is changed-> TODO NÅR DE IKKE PASSER SAMMEN så regnes duration automatisk??
        if( startMod !=null && finishMod!=null && durationMod!=0){
            durationNew = (int) ChronoUnit.DAYS.between(startMod,finishMod)+1;
            startNew = startMod;
            finishNew = finishMod;
        }

        //8) none is changed->
        if( startMod ==null && finishMod==null && durationMod==0){
            durationNew = durationOld;
            startNew = startOld;
            finishNew = finishOld;
        }
        //**********************************************************************************
        //**********************************************************************************

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

        //giving attributes the old value,  since they are not in UI choise TODO SKULLE VÆRE OVERFLØDIG
        newList.get(0).setProjectId(oldTask.get(0).getProjectId()); //"projectId"
        newList.get(0).setIsSubTask(oldTask.get(0).getIsSubTask()); //isSubTask
        newList.get(0).setLineCounter(oldTask.get(0).getLineCounter()); //lineCounter
        newList.get(0).setNewTaskName(oldTask.get(0).getNewTaskName()); //NewTaskName

        //System.out.println("new List Before method: "+newList);
        //Change of taskNumber og alle eventuelt tilhørende subtask numre TODO
        int projectId = oldTask.get(0).getProjectId();
        double modifiedTaskNo = modifiedTask.get(0).getTaskNo();
        double oldTaskNo = oldTask.get(0).getTaskNo();
        String isSubTask = oldTask.get(0).getIsSubTask();

        //Updates DB with new TaskNo'(s)
        String sqlString = createsSqlStringForUpdatingTaskNo(projectId, oldTaskNo, modifiedTaskNo, isSubTask);
        editProjectMapper.updateTaskNos(sqlString);

        //Updates DB with task-attributes except for taskNo
        editProjectMapper.updateTask(newList);

        return newList; //BRUGES TIL TEST TODO fjern return og kør mappers direkte????????
    }//method


    //TODO kan vel fjerne if'erne og vel kun en repeatString da for løkke automatisk kun udskifter de valgte????
    public String createsSqlStringForUpdatingTaskNo(int projectId, double oldTaskNo, double newTaskNo, String isSubTask) {
        ArrayList<Task> listOld = new ArrayList<>();
        ArrayList<Task> listNew;
        EditProjectMapper editProjectMapper = new EditProjectMapper();
        oldTaskNo = Math.round(oldTaskNo * 100.0)/100.0;
        newTaskNo = Math.round(newTaskNo * 100.0)/100.0;


        //Gets taskNo and all belonging subtask numbers
        if (isSubTask.equals("no")) { //it is a Task
            listOld = editProjectMapper.get_idtasks_TaskNos(projectId, oldTaskNo);
        }

        //Get subTaskNo
        if (isSubTask.equals("yes")) { //it is a subTask
            listOld = editProjectMapper.getIdtasks_subTaskNo_FromTask(projectId, oldTaskNo);
        }

        listNew = listOld;

        System.out.println("listNew efter = listOld"+listNew);

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

        //Difference between new and old taskNo FEJL differenc 0.04999999999952 = 0.05  ->((int) ((value + 0.005f) * 100)) / 100f skal tjekkes
//        float diffBetweenOldAndNewTaskNo = (float)Math.round((newTaskNo - oldTaskNo) *100.0f) / 100.0f;
        double diffBetweenOldAndNewTaskNo = Math.round((newTaskNo - oldTaskNo) *100.0) / 100.0; //TODO SKULLE IKKE VÆRE NØDVENDIG er afrundet allerede?

        System.out.println("Before add: " + listNew );
        //Add the difference between oldTaskNo and newtaskNo to all new taskNo values
        for (Task lineElement: listNew) {
            lineElement.setTaskNo(lineElement.getTaskNo()+diffBetweenOldAndNewTaskNo);
        }
        System.out.println("After add: " + listNew );

        //Fills taskNo to the composed sqlString-substring with new values
        if(isSubTask.equals("no")){ //task with subtasks
            for (Task lineElment: listNew ) {
                repeatString.append("UPDATE alfasolutionsdb.task SET taskno =").append(Math.round((lineElment.getTaskNo()) *100.0) / 100.0).append(" WHERE idtask=").append(lineElment.getIdtask()).append(";");
            }
        }
        else if(isSubTask.equals("yes")){ //subtask
            repeatString.append("UPDATE alfasolutionsdb.task SET taskno =").append(Math.round((listNew.get(0).getTaskNo()) *100.0) / 100.0).append(" WHERE idtask=").append(listNew.get(0).getIdtask()).append(";");
        }
        //System.out.println(startString+repeatString+endString);

        return startString+repeatString+endString;
    }

    /**Method prepares input data from Add_task page to be inserted to database
     *
     * @param newTaskFromInput
     * @return
     */
    public Task UserInput_FromAddTaskPreparedToMySQL(Task newTaskFromInput) throws SQLException {

         //Variables used to ease redaability of code
         LocalDate startDate = newTaskFromInput.getStartDate();
         LocalDate finishDate= newTaskFromInput.getFinishDate();
         int duration = newTaskFromInput.getDuration();

         //Inserts new name
        newTaskFromInput.setName(newTaskFromInput.getNewTaskName());

        //Finds value for isSubTask
        if (newTaskFromInput.getSubTaskToName().equals("No overtask")){
            newTaskFromInput.setIsSubTask("no");
        }
        else {
            newTaskFromInput.setIsSubTask("yes");
        }

        if (newTaskFromInput.getFinishDate()==null){
            //finish date is calculated
            newTaskFromInput.setFinishDate(startDate.plusDays(duration-1));

        }
        else {
            //duration is calculated
            newTaskFromInput.setDuration((int) ChronoUnit.DAYS.between(startDate,finishDate)+1);
        }

        if (newTaskFromInput.getDuration() == 0){
            newTaskFromInput.setDuration((int) ChronoUnit.DAYS.between(startDate, finishDate)+1);

        }

        System.out.println("Dette er print fra TaskController: "+newTaskFromInput);

        //Inserts prepared data from dialogbox to DB
        editProjectMapper.insertNewTaskIn_TaskTabel(newTaskFromInput);

        return newTaskFromInput; //Kun til test
    }
}
