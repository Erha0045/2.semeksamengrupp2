package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.data.EditProjectMapper;
import com.eksamengr2.alpha.model.Task;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskHandler1 {
    EditProjectMapper editProjectMapper = new EditProjectMapper();

    public ArrayList<Task> UpdateTaskInDB(Task modifiedTask, Task oldTask) throws SQLException {
        Task newTask = oldTask;
        System.out.println("modified TH: " + modifiedTask);
        System.out.println("old TH: " + oldTask);

        //*********************************************************
        //Using local variables to make the code more readable
        //*********************************************************
        //The old values, the original once
        LocalDate startOld =oldTask.getStartDate();
        LocalDate finishOld = oldTask.getFinishDate();
        int durationOld = oldTask.getDuration();
        int timeconsumptionOld = oldTask.getTaskTimeconsumption();
        int personsOnTaskOld = oldTask.getNoOfPersons();
        double workingHoursDayOld = oldTask.getWorkingHoursDay();

        //The values the user have changed (modified)->(mod)
        LocalDate startMod = modifiedTask.getStartDate();
        LocalDate finishMod = modifiedTask.getFinishDate();
        int durationMod = modifiedTask.getDuration();
        int timeconsumptionMod = modifiedTask.getTaskTimeconsumption();
        int personsOnTaskMod = modifiedTask.getNoOfPersons();
        double workingHoursDayMod = modifiedTask.getWorkingHoursDay();

        //The new values
        LocalDate startNew=null;
        LocalDate finishNew=null;
        int durationNew=0;
        int timeconsumptionNew=0;
        int personsOnTaskNew=0;
        double workingHoursDayNew=0.0;




        //**************************************************************************************
        //** The six values for a task: startDate, finishDate, duration noOfPersons,          **
        //** Timeconsumption and workingHoursDay interact with eachother                      **
        //** KUN EN VÆRDI ÆNDRES AF GANGEN                                                    **
        //**************************************************************************************

        //1) startDate is edited-> durationNew = startMod - finishOld
        if( startMod !=null){
            durationNew = (int) ChronoUnit.DAYS.between(startMod,finishOld) +1;
            startNew = startMod;
            finishNew = finishOld;
            timeconsumptionNew = timeconsumptionOld;
            if (durationNew!=0 && workingHoursDayOld!=0){
                personsOnTaskNew = (int) Math.ceil((double) timeconsumptionOld/(durationNew*workingHoursDayOld));
            }
            workingHoursDayNew = workingHoursDayOld;
        }

        //2 TimeConsumption is edited
        if (timeconsumptionMod!=0){
            durationNew = durationOld;
            startNew = startOld;
            finishNew = finishOld;
            workingHoursDayNew = workingHoursDayOld;
            timeconsumptionNew = timeconsumptionMod;
            if (durationNew!=0 && workingHoursDayOld!=0){
                personsOnTaskNew = (int) Math.ceil((double) timeconsumptionNew/(durationNew*workingHoursDayOld));
            }

        }

        //3) FinishDate is edited->
        if( finishMod!=null){
            durationNew = (int) ChronoUnit.DAYS.between(startOld,finishMod) +1;
            startNew = startOld;
            finishNew = finishMod;
            workingHoursDayNew = workingHoursDayOld;
            timeconsumptionNew = timeconsumptionOld;
            if (durationNew!=0 && workingHoursDayOld!=0){
                personsOnTaskNew = (int) Math.ceil((double) timeconsumptionNew/(durationNew*workingHoursDayOld));
            }
        }

        //4) Duration is edited->
        if(durationMod!=0){
            durationNew = durationMod;
            startNew = startOld;
            finishNew = startOld.plusDays(durationMod +1);
            workingHoursDayNew = workingHoursDayOld;
            timeconsumptionNew = timeconsumptionOld;
            if (durationNew!=0 && workingHoursDayOld!=0){
                personsOnTaskNew = (int) Math.ceil((double) timeconsumptionNew/(durationNew*workingHoursDayOld));
            }
        }

        //5) PersonsOnTask is edited->
        if( personsOnTaskMod!=0){
            personsOnTaskNew = personsOnTaskMod;
            startNew = startOld;
            workingHoursDayNew = workingHoursDayOld;
            timeconsumptionNew = timeconsumptionOld;
            durationNew = (int) Math.ceil(timeconsumptionOld/(personsOnTaskNew*workingHoursDayOld)) ;
            finishNew = startOld.plusDays(durationNew +1);
        }

        //6) WorkingHoursDay is edited->
        if( workingHoursDayMod!=0.0){

            startNew = startOld;
            workingHoursDayNew = workingHoursDayOld;
            timeconsumptionNew = timeconsumptionOld;
            durationNew = durationOld;
            finishNew = finishOld;
            personsOnTaskNew = (int) Math.ceil(timeconsumptionOld/(durationOld*workingHoursDayNew));
        }

        //6) none is changed
        if(startMod==null && timeconsumptionMod==0 && finishMod==null && durationMod==0 && personsOnTaskMod==0 && workingHoursDayMod==0.0){
            startNew = startOld;
            workingHoursDayNew = workingHoursDayOld;
            timeconsumptionNew = timeconsumptionOld;
            durationNew = durationOld;
            finishNew = finishOld;
            personsOnTaskNew = personsOnTaskOld;
        }

        //7 name is changed
        if (!modifiedTask.getName().equals("")){
            newTask.setName(modifiedTask.getName());
        }
        else {
            newTask.setName(oldTask.getName());
        }



            //MAN KUNNE MÅSKE LADE BRUGER UDVÆLGE 2 UDVALGTE???

        //Populater new task object
        newTask.setDuration(durationNew);
        newTask.setStartDate(startNew);
        newTask.setFinishDate(finishNew);
        newTask.setNoOfPersons(personsOnTaskNew);
        newTask.setWorkingHoursDay(workingHoursDayNew);
        newTask.setTaskTimeconsumption(timeconsumptionNew);

        //**********************************************************************************
        //**********************************************************************************

        //Change of taskNumber og alle eventuelt tilhørende subtask numre TODO

        String sqlString;
        //Updates DB with new TaskNo'(s)
        if (modifiedTask.getTaskNo() !=0.0) {
            System.out.println("Var inde i new taskNO sql generator");
            sqlString = UpdateTaskNos(oldTask.getProjectId(), oldTask.getTaskNo(), modifiedTask.getTaskNo(), oldTask.getIsSubTask());
            editProjectMapper.updateTaskNos(sqlString);
        }

        //Updates DB with task-attributes except for taskNo
        editProjectMapper.updateTask(newTask);  //TODO ikke opdateret

//        return newList; //BRUGES TIL TEST TODO fjern return og kør mappers direkte????????
        return null;
    }//method

//    public ArrayList<Task> UserInput_FromEditTask_UpdateTaskInDB(ArrayList<Task> modifiedTask, ArrayList<Task> oldTask) throws SQLException {
//        LocalDate startOld;
//        LocalDate finishOld;
//        LocalDate startMod;
//        LocalDate finishMod;
//
//        //*********************************************************
//        //Using local variables to make the code more readable
//        //*********************************************************
//
//        //The values the user did not change, keeping (old)
//       // if (oldTask.get(0).getStartDate()!=null && oldTask.get(0).getFinishDate()!=null ) {
//            startOld = oldTask.get(0).getStartDate();
//            finishOld = oldTask.get(0).getFinishDate();
//        //}
//       // else {
//       //     startOld = null;
//       //     finishOld = null;
//        //}
//        int durationOld = oldTask.get(0).getDuration();
//
//        //The values the user have changed (modified)->(mod)
//        startMod = modifiedTask.get(0).getStartDate();
//        finishMod = modifiedTask.get(0).getFinishDate();
//        int durationMod = modifiedTask.get(0).getDuration();
//
//        //The new values used to update the DB (new)
//        LocalDate startNew=null; //= returnList.get(0).getStartDate();
//        LocalDate finishNew=null;// = returnList.get(0).getFinishDate();
//        int durationNew=0; //  = returnList.get(0).getDuration();
//
//        //Transfer data from the old(DB-values) to new list
//        ArrayList<Task> newList = oldTask;
//
//        //initialiser med værdier da get ellers ikke kan bruges
//        //newList.add(new Task("",LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"maybe",(float) 0.0,0));
//
//        //**************************************************************************************
//        //The tree values for a task, startDate, finishDate and duration interact with eachother
//        //**************************************************************************************
//        //1) startDate is changed-> durationNew = startMod - finishOld
//        if( startMod !=null && finishMod==null && durationMod==0){
//            durationNew = (int) ChronoUnit.DAYS.between(startMod,finishOld) +1;
//            startNew = startMod;
//            finishNew = finishOld;
//        }
//
//        //2) FinishDate is changed->
//        if( startMod ==null && finishMod!=null && durationMod==0){
//            durationNew = (int) ChronoUnit.DAYS.between(startOld,finishMod) +1;
//            startNew = startOld;
//            finishNew = finishMod;
//        }
//
//        //3) Duration is changed->
//        if( startMod ==null && finishMod==null && durationMod!=0){
//            durationNew = durationMod;
//            startNew = startOld;
//            finishNew = startOld.plusDays(durationMod +1);
//        }
//
//        //4) StartDate and FinishedDate is changed->
//        if( startMod !=null && finishMod!=null && durationMod==0){
//            durationNew = (int) ChronoUnit.DAYS.between(startMod,finishMod) +1;;
//            startNew = startMod;
//            finishNew = finishMod;
//        }
//
//        //5) FinishedDate and Duration is changed->
//        if( startMod ==null && finishMod!=null && durationMod!=0){
//            durationNew = durationMod;
//            startNew = finishMod.minusDays((long)durationMod-1);
//            finishNew = finishMod;
//        }
//
//        //6) StartDate and Duration is changed->
//        if( startMod !=null && finishMod==null && durationMod!=0){
//            durationNew = durationMod;
//            startNew = startMod;
//            finishNew = startMod.plusDays((long)durationMod+1);
//        }
//
//        //7) StartDate, finishDate and Duration is changed-> TODO NÅR DE IKKE PASSER SAMMEN så regnes duration automatisk??
//        if( startMod !=null && finishMod!=null && durationMod!=0){
//            durationNew = (int) ChronoUnit.DAYS.between(startMod,finishMod)+1;
//            startNew = startMod;
//            finishNew = finishMod;
//        }
//
//        //8) none is changed->
//        if( startMod ==null && finishMod==null && durationMod==0){
//            durationNew = durationOld;
//            startNew = startOld;
//            finishNew = finishOld;
//        }
//        //**********************************************************************************
//        //**********************************************************************************
//
//        //fill newArraylist with values from duration, startDate and finishDate
//        newList.get(0).setDuration(durationNew);
//        newList.get(0).setStartDate(startNew);
//        newList.get(0).setFinishDate(finishNew);
//
//        //giving "name" a value depending on users choice
//        if (modifiedTask.get(0).getName().equals("")){
//            newList.get(0).setName(oldTask.get(0).getName());
//        }
//        else {
//            newList.get(0).setName(modifiedTask.get(0).getName());
//        }
//
//        //giving attributes the old value,  since they are not in UI choise TODO SKULLE VÆRE OVERFLØDIG
//        newList.get(0).setProjectId(oldTask.get(0).getProjectId()); //"projectId"
//        newList.get(0).setIsSubTask(oldTask.get(0).getIsSubTask()); //isSubTask
//        newList.get(0).setLineCounter(oldTask.get(0).getLineCounter()); //lineCounter
//        newList.get(0).setName(oldTask.get(0).getName()); //TaskName
//
//        //System.out.println("new List Before method: "+newList);
//        //Change of taskNumber og alle eventuelt tilhørende subtask numre TODO
//        int projectId = oldTask.get(0).getProjectId();
//        double modifiedTaskNo = modifiedTask.get(0).getTaskNo();
//        double oldTaskNo = oldTask.get(0).getTaskNo();
//        String isSubTask = oldTask.get(0).getIsSubTask();
//
//        //Updates DB with new TaskNo'(s)
//        String sqlString = createsSqlStringForUpdatingTaskNo(projectId, oldTaskNo, modifiedTaskNo, isSubTask);
//        editProjectMapper.updateTaskNos(sqlString);
//
//        //Updates DB with task-attributes except for taskNo
//        editProjectMapper.updateTask(newList);
//
//        return newList; //BRUGES TIL TEST TODO fjern return og kør mappers direkte????????
//    }//method


    /**This metod updates taskNo'ers by creating a SQL string
     *If taskNo is a overTask(Task) it will update all subTaskNo'ers
     * @param projectId
     * @param oldTaskNo
     * @param newTaskNo
     * @param isSubTask
     * @return
     */
    public String UpdateTaskNos(int projectId, double oldTaskNo, double newTaskNo, String isSubTask) {
        ArrayList<Task> listOld = new ArrayList<>();
        ArrayList<Task> listNew;
        EditProjectMapper editProjectMapper = new EditProjectMapper();
        oldTaskNo = Math.round(oldTaskNo * 100)/100d;
        newTaskNo = Math.round(newTaskNo * 100)/100d;


        //Gets taskNo and all belonging subtask numbers
        if (isSubTask.equals("no")) { //it is a overTask ()Task
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
        double diffBetweenOldAndNewTaskNo = Math.round((newTaskNo - oldTaskNo) *100) / 100d;

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
        newTaskFromInput.setName(newTaskFromInput.getName());

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
        editProjectMapper.createNewTask(newTaskFromInput); //TODO DENNE MAPPER ER IKKE OPDATERET

        return newTaskFromInput; //Kun til test
    }

    /**Exampel text of a task for the UI
     *
     * @return
     */
    public ArrayList<Task> ExampelForTaskLine(){
        ArrayList<Task> returnList = new ArrayList<>();
        Task taskExampel = new Task ("Taskname", LocalDate.of(1900,1,1), LocalDate.of(1900,1,1),
        0, 0, "String isSubTask",0.0d, 0, 0, 0, 0, 0.0d, "String subTaskToName");

        returnList.add(taskExampel);
        return returnList;
    }
}
