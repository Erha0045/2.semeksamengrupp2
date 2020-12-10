package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.data.EditProjectMapper;
import com.eksamengr2.alpha.model.Task;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskHandler1 {
    EditProjectMapper editProjectMapper = new EditProjectMapper();
    public static final double DEFAULT_WORKING_HOURS_DAY = 7.5;
    public static final double DEFAULT_PERSONS_ON_TASK = 1;

    public Task editTask(Task modifiedTask, Task oldTask) throws SQLException {
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

        //The new values which is for DB
        LocalDate startNew=null;
        LocalDate finishNew=null;
        int durationNew=0;
        int timeconsumptionNew=0;
        int personsOnTaskNew=0;
        double workingHoursDayNew=0.00;



        //**************************************************************************************
        //** The six values for a task: startDate, finishDate, duration noOfPersons,          **
        //** Timeconsumption and workingHoursDay interact with eachother                      **
        //** ONLY ONE VALUE CHANGED PER SESSION                                               **
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
            finishNew = startOld.plusDays(durationMod -1);
            workingHoursDayNew = workingHoursDayOld;
            timeconsumptionNew = timeconsumptionOld;
            if (durationNew!=0 && workingHoursDayOld!=0){
                personsOnTaskNew = (int) Math.ceil((double) timeconsumptionNew/(durationNew*workingHoursDayOld));
            }
        }

        double workingHoursDayOldCalculated=0;
        //5) PersonsOnTask is edited->
        if( personsOnTaskMod!=0){

            workingHoursDayNew =  Math.round(timeconsumptionOld /((double)durationOld*personsOnTaskOld)*100.00)/100d;
            durationNew = (int) Math.ceil(timeconsumptionOld/(personsOnTaskMod*workingHoursDayNew)) ;

            finishNew = startOld.plusDays(durationNew -1);
            personsOnTaskNew = personsOnTaskMod;
            startNew = startOld;
            timeconsumptionNew = timeconsumptionOld;
        }

        //6) WorkingHoursDay is edited->
        if( workingHoursDayMod!=0.0){
            startNew = startOld;
            workingHoursDayNew = workingHoursDayMod;
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

        //Change of taskNumber og alle eventuelt tilhørende subtask numre TODO

        String sqlString;
        //Updates DB with new TaskNo'(s)
        if (modifiedTask.getTaskNo() !=0.0) {
            System.out.println("Var inde i new taskNO if før sql generator"); //TIL TEST
            sqlString = updateTaskNos(oldTask.getProjectId(), oldTask.getTaskNo(), modifiedTask.getTaskNo(), oldTask.getIsSubTask());
            editProjectMapper.updateTaskNos(sqlString);
        }

        //Updates DB with task-attributes except for taskNo
        editProjectMapper.updateTask(newTask);  //TODO ikke testet

        return newTask; //BRUGES TIL TEST TODO fjern return og kør mappers direkte????????

    }//method


    /**This metod updates taskNo'ers by creating a SQL string
     *If taskNo is a overTask(Task) it will update all subTaskNo'ers
     * @param projectId
     * @param oldTaskNo
     * @param newTaskNo
     * @param isSubTask
     * @return
     */
    public String updateTaskNos(int projectId, double oldTaskNo, double newTaskNo, String isSubTask) {


        ArrayList<Task> listOld = new ArrayList<>();
        ArrayList<Task> listNew;
        EditProjectMapper editProjectMapper = new EditProjectMapper();
        oldTaskNo = Math.round(oldTaskNo * 100)/100d;
        newTaskNo = Math.round(newTaskNo * 100)/100d;

//        System.out.println("oldTaskNo: "+oldTaskNo); //14.2
//        System.out.println("newTaskNo: "+newTaskNo);  //13.2

        //Gets taskNo and all belonging subtask numbers
        if (isSubTask.equals("no")) { //it is an overTask
            listOld = editProjectMapper.get_idtasks_TaskNos(projectId, oldTaskNo);
        }

        //Get subTaskNo from DB
        if (isSubTask.equals("yes")) {
            listOld = editProjectMapper.getIdtasks_subTaskNo_FromTask(projectId, oldTaskNo);
        }
        System.out.println("listOld" + listOld);

        listNew = listOld;

        System.out.println("listNew efter = listOld"+listNew);

        /*  BEGIN WORK;START TRANSACTION;
            UPDATE alfasolutionsdb.task SET taskno = 99.1 WHERE idtask=19;
            UPDATE alfasolutionsdb.task SET taskno = 99.2 WHERE idtask=20;
            COMMIT;
            */
        //Strings variables to be used to create a sql-string for mapper
        String startString="BEGIN WORK; START TRANSACTION;";
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
                repeatString.append(" UPDATE alfasolutionsdb.task SET taskno =").append((Math.round(lineElment.getTaskNo() *100.00))/100d).append(" WHERE idtask=").append(lineElment.getIdtask()).append(";");
            }
        }
        else if(isSubTask.equals("yes")){ //subtask
            repeatString.append(" UPDATE alfasolutionsdb.task SET taskno =").append(Math.round((listNew.get(0).getTaskNo()) *100.00)/100d).append(" WHERE idtask=").append(listNew.get(0).getIdtask()).append(";");
        }
        //System.out.println(startString+repeatString+endString);

        return startString+repeatString+endString;
    }

    //TODO ADD_TASK ....SKAL OPDATERES SÅ DE NYE ATTRIBUTTEr KOMMER MED,
    /**Method prepares input data from Add_task page to be inserted to database
     *
     * @param task
     * @return
     */
    public Task AddTaskToDB(Task task) throws SQLException {
        System.out.println("AddTaskToDB" + task);

        //Local variables used to ease redaability of code
        LocalDate startDate = task.getStartDate();
        LocalDate finishDate= task.getFinishDate();
        int duration = task.getDuration();
        int timeConsumption = task.getTaskTimeconsumption();
        int personsOnTask= task.getNoOfPersons();
        double workingHoursDay=task.getWorkingHoursDay();
        double preTaskNo;


        //isSubTask value is set
        if (task.getSubTaskToName()==null){
            task.setIsSubTask("no");
        }
        else {
            task.setIsSubTask("yes");
        }

        //SubtaskNo value is set, only for subTask...task i allready given
        if (task.getIsSubTask().equals("yes")){ //task
            preTaskNo= editProjectMapper.getTaskNo(task.getProjectId(),task.getSubTaskToName());
            task.setTaskNo(preTaskNo + (task.getTaskNo()/100.00));
        }

        //finishDate is set, presuming duration is not filled
        if (task.getFinishDate()==null && task.getDuration() !=0){ //finish date is calculated
            task.setFinishDate(startDate.plusDays(duration-1));
        }

        //duration is set, presuming finishDate is not filled
        else if (task.getDuration()==0 && task.getFinishDate()!=null){ //duration is calculated
            duration =(int) ChronoUnit.DAYS.between(startDate,finishDate)+1;
            task.setDuration(duration);
        }
        //duration is set, presuming finishDate and duration is filled, removing possible entered error
        else if (task.getDuration()!=0 && task.getFinishDate()!=null){ //duration is calculated
            task.setDuration((int) ChronoUnit.DAYS.between(startDate,finishDate)+1);
        }


        //**********************************************************************************
        //*finishDate, duration, personsOnTask and workingHoursDay interact with eachother *
        // duration is rounded up where nessesary
        //**********************************************************************************

        //1) FinishDate or Duration entered
        if ((finishDate!=null || duration!=0) && personsOnTask==0 && workingHoursDay==0.0){
            System.out.println("noOfPersons: "+task.getNoOfPersons());
            System.out.println("duration: " + duration);
            task.setNoOfPersons(((int) (timeConsumption/(duration*DEFAULT_WORKING_HOURS_DAY)))+1);

            System.out.println("noOfPersons: "+task.getNoOfPersons());

            task.setWorkingHoursDay(DEFAULT_WORKING_HOURS_DAY);
        }

        //2 workingHoursDay entered
        if ((finishDate==null || duration==0) && personsOnTask!=0 && workingHoursDay==0.0d){
            task.setWorkingHoursDay(DEFAULT_WORKING_HOURS_DAY);
            task.setDuration((int) Math.ceil(timeConsumption/(personsOnTask*DEFAULT_WORKING_HOURS_DAY)));
        }

        //3) workingHoursDay entered
        if ((finishDate==null || duration==0) && personsOnTask==0 && workingHoursDay!=0.0d){
            task.setNoOfPersons((int)DEFAULT_PERSONS_ON_TASK);
            task.setDuration((int) Math.ceil(timeConsumption/(DEFAULT_PERSONS_ON_TASK*workingHoursDay)));
            task.setFinishDate(startDate.plusDays(task.getDuration()-1));
        }

        //4) FinishDate or Duration and personsOnTask entered
        if ((finishDate!=null || duration!=0) && personsOnTask!=0 && workingHoursDay==0.0d){
            System.out.println("task.getNoOfPersons: "+task.getWorkingHoursDay());
            task.setWorkingHoursDay(Math.round(100.00*timeConsumption/(duration*(double)personsOnTask))/100.00);
            System.out.println("task.getNoOfPersons: "+task.getWorkingHoursDay());
        }

        //5) FinishDate or Duration and workingHoursDay entered
        if ((finishDate!=null || duration!=0) && personsOnTask==0 && workingHoursDay!=0.0d){
            task.setNoOfPersons((int)Math.ceil(timeConsumption/(duration*workingHoursDay)));
        }

        //6)workingHoursDay and personsOnTask
        if ((finishDate==null || duration==0) && personsOnTask!=0 && workingHoursDay!=0.0d){
            task.setDuration((int) Math.ceil(timeConsumption/(personsOnTask*workingHoursDay)));
            task.setFinishDate(startDate.plusDays(task.getDuration()-1));
        }

        //7)All entered
        if ((finishDate!=null || duration!=0) && personsOnTask!=0 && workingHoursDay!=0.0d){
            task.setDuration((int) Math.ceil(timeConsumption/(personsOnTask*workingHoursDay)));
            task.setFinishDate(startDate.plusDays(task.getDuration()-1));
        }


         //8)None entered
        if ((finishDate==null || duration==0) && personsOnTask==0 && workingHoursDay==0.0d){
            task.setWorkingHoursDay(DEFAULT_WORKING_HOURS_DAY);
            task.setDuration((int) Math.ceil(timeConsumption/(DEFAULT_PERSONS_ON_TASK*DEFAULT_WORKING_HOURS_DAY)));
            task.setFinishDate(startDate.plusDays(task.getDuration()));
            task.setNoOfPersons((int) DEFAULT_PERSONS_ON_TASK);
        }
        System.out.println("finishdate efter:" + task.getFinishDate());
        //Inserts prepared data from dialogbox to DB
        editProjectMapper.createNewTask(task);

        return task; //Kun til test
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
