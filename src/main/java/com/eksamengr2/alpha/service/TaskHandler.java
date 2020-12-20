package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.data.EditProjectMapper;
import com.eksamengr2.alpha.data.ProjectMapper;
import com.eksamengr2.alpha.data.TaskMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.springController.TaskController;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskHandler {
    EditProjectMapper editProjectMapper = new EditProjectMapper();
    public static final double DEFAULT_WORKING_HOURS_DAY = 7.5;
    public static final double DEFAULT_PERSONS_ON_TASK = 1;

    /**Prepares data from input to be inserted into DB
     *
     * @param modifiedTask attributes user want edited
     * @param oldTask attributes from before editing
     * @return is only for JUnit testing
     * @throws SQLException
     */
    public Task editTask(Task modifiedTask, Task oldTask) throws SQLException {
        Task newTask = oldTask;
        String sqlString;

//        System.out.println("mod: " + modifiedTask);
//        System.out.println("old: " + oldTask);


        System.out.println("Old isSubTask: " + oldTask.getIsSubTask());

        //************************************************************************
        //Using local variables to make the code more readable and to have to sets
        //************************************************************************
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
        //** ONLY ONE VALUE CHANGED PER BUTTON CLICK                                          **
        //**************************************************************************************

        //1) startDate is edited
        if( startMod !=null){
            System.out.println("Var i 1");
            durationNew = (int) ChronoUnit.DAYS.between(startMod,finishOld) +1;
            startNew = startMod;
            finishNew = finishOld;
            timeconsumptionNew = timeconsumptionOld;
            if (durationNew!=0 && workingHoursDayOld!=0){
                personsOnTaskNew = (int) Math.ceil((double) timeconsumptionOld/(durationNew*workingHoursDayOld));
            }
            workingHoursDayNew = workingHoursDayOld;
        }


        //3) FinishDate is edited
        if( finishMod!=null){
            System.out.println("Var i 3");
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
            System.out.println("Var i 4");
            durationNew = durationMod;
            startNew = startOld;
            finishNew = startOld.plusDays(durationMod -1);
            workingHoursDayNew = workingHoursDayOld;
            timeconsumptionNew = timeconsumptionOld;
            if (durationNew!=0 && workingHoursDayOld!=0){
                personsOnTaskNew = (int) Math.ceil((double) timeconsumptionNew/(durationNew*workingHoursDayOld));
            }
        }

        //ONLY subtask
        if (oldTask.getIsSubTask().equals("yes")) {

            //2 TimeConsumption is edited
            if (timeconsumptionMod != 0) {
                System.out.println("Var i 2");
                durationNew = durationOld;
                startNew = startOld;
                finishNew = finishOld;
                workingHoursDayNew = workingHoursDayOld;
                timeconsumptionNew = timeconsumptionMod;
                if (durationNew != 0 && workingHoursDayOld != 0) {
                    personsOnTaskNew = (int) Math.ceil((double) timeconsumptionNew / (durationNew * workingHoursDayOld));
                }
            }

            //5) PersonsOnTask is edited->
            if (personsOnTaskMod != 0) {
                System.out.println("Var i 5");
                durationNew = (int) Math.ceil(timeconsumptionOld / (personsOnTaskMod * workingHoursDayOld));
                finishNew = startOld.plusDays(durationNew - 1);
                workingHoursDayNew = Math.round(timeconsumptionOld /
                        ((double) durationNew * personsOnTaskOld) * 100.00) / 100d;
                personsOnTaskNew = personsOnTaskMod;
                startNew = startOld;
                timeconsumptionNew = timeconsumptionOld;
            }

            //6) WorkingHoursDay is edited->
            if (workingHoursDayMod != 0.0) {
                System.out.println("Var i 6");
                startNew = startOld;
                workingHoursDayNew = workingHoursDayMod;
                timeconsumptionNew = timeconsumptionOld;
                durationNew = durationOld;
                finishNew = finishOld;
                personsOnTaskNew = (int) Math.ceil(timeconsumptionOld / (durationOld * workingHoursDayNew));
            }

            //6) none is changed
            if (startMod == null && timeconsumptionMod == 0 && finishMod == null && durationMod == 0 && personsOnTaskMod == 0 && workingHoursDayMod == 0.0 ) {
                System.out.println("Var i 6");
                startNew = startOld;
                workingHoursDayNew = workingHoursDayOld;
                timeconsumptionNew = timeconsumptionOld;
                durationNew = durationOld;
                finishNew = finishOld;
                personsOnTaskNew = personsOnTaskOld;
            }
        }//if SUBTASK finish

        //7) name is changed
        if (!modifiedTask.getName().equals("")){
            System.out.println("Var i 7");
            newTask.setName(modifiedTask.getName());
        }
        else {
            newTask.setName(oldTask.getName());
        }

        //Populater new task object
        newTask.setDuration(durationNew);
        newTask.setStartDate(startNew);
        newTask.setFinishDate(finishNew);
        newTask.setNoOfPersons(personsOnTaskNew);
        newTask.setWorkingHoursDay(workingHoursDayNew);
        newTask.setTaskTimeconsumption(timeconsumptionNew);

        //Updates DB with new TaskNo'(s)
        if (modifiedTask.getTaskNo() !=0.0) {
            sqlString = updateTaskNos(oldTask.getProjectId(), oldTask.getTaskNo(), modifiedTask.getTaskNo(), oldTask.getIsSubTask());
            editProjectMapper.updateTaskNos(sqlString);
        }

        //Updates DB with task-attributes except for taskNo
        editProjectMapper.updateTask(newTask);

        return newTask; //Only for TEST

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

        //Gets taskNo and all belonging subtask numbers
        if (isSubTask.equals("no")) { //it is an overTask
            listOld = editProjectMapper.get_idtasks_TaskNos(projectId, oldTaskNo);
        }

        //Get subTaskNo from DB
        if (isSubTask.equals("yes")) {
            listOld = editProjectMapper.getIdtasks_subTaskNo_FromTask(projectId, oldTaskNo);
        }

        //Populates ArrayList
        listNew = listOld;


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

        double diffBetweenOldAndNewTaskNo = Math.round((newTaskNo - oldTaskNo) *100) / 100d;

        //Add the difference between oldTaskNo and newtaskNo to all new taskNo values
        for (Task lineElement: listNew) {
            lineElement.setTaskNo(lineElement.getTaskNo()+diffBetweenOldAndNewTaskNo);
        }

        //Fills taskNo to the composed sqlString-substring with new values
        if(isSubTask.equals("no")){ //task with subtasks
            for (Task lineElment: listNew ) {
                repeatString.append(" UPDATE alfasolutionsdb.task SET taskno =").append((Math.round(lineElment.getTaskNo() *100.00))/100d).append(" WHERE idtask=").append(lineElment.getIdtask()).append(";");
            }
        }
        else if(isSubTask.equals("yes")){ //subtask
            repeatString.append(" UPDATE alfasolutionsdb.task SET taskno =").append(Math.round((listNew.get(0).getTaskNo()) *100.00)/100d).append(" WHERE idtask=").append(listNew.get(0).getIdtask()).append(";");
        }

        return startString+repeatString+endString;
    }


    /**Method prepares input data from Add_task page to be inserted to database
     *
     * @param task
     * @return
     */
    public Task AddTaskToDB(Task task) throws SQLException {

        //Local variables used to ease redaability of code and to work with two sets of variables
        LocalDate startDate = task.getStartDate();
        LocalDate finishDate= task.getFinishDate();
        int duration = task.getDuration();
        int timeConsumption = task.getTaskTimeconsumption();
        int personsOnTask= task.getNoOfPersons();
        double workingHoursDay=task.getWorkingHoursDay();
        double preTaskNo;

        //values are set depending if it is subTask or Task
        if (Objects.isNull(task.getSubTaskToName()) || task.getSubTaskToName().isBlank()){ //task
            task.setIsSubTask("no");
            task.setNoOfPersons(0);
            task.setWorkingHoursDay(0.0);
        }
        else { //subTask
            task.setIsSubTask("yes");
        }

        //SubtaskNo value is set, only for subTask...task i allready given
        if (task.getIsSubTask().equals("yes")){ //task
            preTaskNo= editProjectMapper.getTaskNo(task.getProjectId(),task.getSubTaskToName());
            task.setTaskNo(preTaskNo + (task.getTaskNo()/100.00));
        }

        //finishDate is set, presuming duration is filled
        if (task.getFinishDate()==null && task.getDuration() !=0){ //finish date is calculated
            task.setFinishDate(startDate.plusDays(duration-1));
        }

        //duration is set, presuming finishDate is filled
        else if (task.getDuration()==0 && task.getFinishDate()!=null){ //duration is calculated
            task.setDuration((int) ChronoUnit.DAYS.between(startDate,finishDate)+1);
        }
        //duration is set, presuming finishDate and duration is filled, overriding possible duration error
        else if (task.getDuration()!=0 && task.getFinishDate()!=null){ //duration is calculated
            task.setDuration((int) ChronoUnit.DAYS.between(startDate,finishDate)+1);
        }

        //**********************************************************************************
        //For subtask
        //finishDate, duration, personsOnTask and workingHoursDay interact with eachother *
        // duration is rounded up where nessesary
        //**********************************************************************************

        if (task.getIsSubTask().equals("yes")) {

            //1) FinishDate or Duration entered
            if ((finishDate != null || duration != 0) && personsOnTask == 0 && workingHoursDay == 0.0) {
                System.out.println("Var i 1");
                task.setNoOfPersons(((int) (timeConsumption / (task.getDuration() * DEFAULT_WORKING_HOURS_DAY))) + 1);
                task.setWorkingHoursDay(DEFAULT_WORKING_HOURS_DAY);
            }

            //2 workingHoursDay entered
            if (finishDate == null && duration == 0 && personsOnTask != 0 && workingHoursDay == 0.0d) {
                System.out.println("Var i 2");
                task.setWorkingHoursDay(DEFAULT_WORKING_HOURS_DAY);
                task.setDuration((int) Math.ceil(timeConsumption / (task.getNoOfPersons() * DEFAULT_WORKING_HOURS_DAY)));
                task.setFinishDate(startDate.plusDays(task.getDuration() - 1));
            }

            //3) workingHoursDay entered
            if (finishDate == null && duration == 0 && personsOnTask == 0 && workingHoursDay != 0.0d) {
                System.out.println("Var i 3");
                task.setNoOfPersons((int) DEFAULT_PERSONS_ON_TASK);
                task.setDuration((int) Math.ceil((double) timeConsumption / (DEFAULT_PERSONS_ON_TASK * workingHoursDay)));
                task.setFinishDate(startDate.plusDays(task.getDuration() - 1));
            }

            //4) FinishDate or Duration and personsOnTask entered
            if ((finishDate != null || duration != 0) && personsOnTask != 0 && workingHoursDay == 0.0d) {
                System.out.println("Var i 4");
                task.setWorkingHoursDay(Math.round(100.00 * timeConsumption / (task.getDuration() * (double) personsOnTask)) / 100.00);
            }

            //5) FinishDate or Duration and workingHoursDay entered
            if ((finishDate != null || duration != 0) && personsOnTask == 0 && workingHoursDay != 0.0d) {
                System.out.println("Var i 5");
                task.setNoOfPersons((int) Math.ceil(timeConsumption / (duration * workingHoursDay)));
            }

            //6)workingHoursDay and personsOnTask
            if (finishDate == null && duration == 0 && personsOnTask != 0 && workingHoursDay != 0.0d) {
                System.out.println("Var i 6");
                task.setDuration((int) Math.ceil(timeConsumption / (personsOnTask * workingHoursDay)));
                task.setFinishDate(startDate.plusDays(task.getDuration() - 1));
            }

            //7)All entered
            if ((finishDate != null || duration != 0) && personsOnTask != 0 && workingHoursDay != 0.0d) {
                System.out.println("Var i 7");
                task.setDuration((int) Math.ceil(timeConsumption / (personsOnTask * workingHoursDay)));
                task.setFinishDate(startDate.plusDays(task.getDuration() - 1));
            }


            //8)None entered
            if (finishDate == null && duration == 0 && personsOnTask == 0 && workingHoursDay == 0.0d) {
                System.out.println("Var i 8");
                task.setWorkingHoursDay(DEFAULT_WORKING_HOURS_DAY);
                task.setDuration((int) Math.ceil(timeConsumption / (DEFAULT_PERSONS_ON_TASK * DEFAULT_WORKING_HOURS_DAY)));
                task.setFinishDate(startDate.plusDays(task.getDuration()));
                task.setNoOfPersons((int) DEFAULT_PERSONS_ON_TASK);
            }

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

    public ArrayList<Task> viewForEditProject(int projectId) throws SQLException {

//        ArrayList<Task> viewList = new ArrayList<>();

        ArrayList<Task> bigList = (ArrayList<Task>) editProjectMapper.getTaskWithCounter(projectId);
        List<Task> smallList = getTaskNoAndSumOfTimeConsumptionPerTask(projectId);

        for (Task small: smallList     ) {

            for (int i=0; i<bigList.size();i++)
            if (bigList.get(i).getTaskNo()==small.getTaskNo()){

                bigList.get(i).setTaskTimeconsumption(small.getTaskTimeconsumption());

            }
        }
        return bigList;
    }

    public ArrayList<Task> getTaskNoAndSumOfTimeConsumptionPerTask(int projetcId) throws SQLException {
        TaskMapper taskMapper = new TaskMapper();
        ArrayList<Task> list = new ArrayList<>();
        Task taskSum = new Task(); //Constructor Task[(SumTaskTimeConsumption, taskNo)]

        ArrayList<Task> taskNoList = taskMapper.getAllTaskNoForProject(projetcId);

        int counter=0;
        for (Task t: taskNoList) {
            //Makes a arrylist with TaskNo + sumOf(TaskTimeConsumption)
            taskSum = taskMapper.getSumOfTaskTimeConsumptionForTask(projetcId,t.getTaskNo());
            list.add(new Task(taskSum.getTaskTimeconsumption(), taskNoList.get(counter).getTaskNo()));

            counter++;
        }
        return list;
    }


    //***************************************************************************
    //***************************************************************************
    //***                   TIME CALCULATION                                  ***
    //***************************************************************************
    //***************************************************************************

    /**
     *
     * @param projectId
     * @return
     * @throws SQLException
     */
    public ArrayList<Project> hoursPerDayCalculation(int projectId) throws SQLException {
        ProjectMapper projectMapper = new ProjectMapper();
        TaskMapper taskMapper = new TaskMapper();
        ArrayList<Project> resultList = new ArrayList<>();

        //Gets project metadate from DB
        Project project = projectMapper.getProjectFromId(projectId);

        //Creates the masterList with metadata
        ArrayList<Project> masterList= createFullMasterList(project);

        //Gets all subtasks start, finish and hours worked per day from DB
        ArrayList<Task> subTaskList = taskMapper.getStartFinishWorkingHoursDayForSubTask(projectId);

        //Creates fullsubtaskList [date, hoursWorked] for all subtasks and days in one ArrayList
        ArrayList<Task> fullSubList = createFullSubTaskList(subTaskList);

        //populate masterList with values from subTaskList
        resultList = joinArrayListValues(masterList, fullSubList);

        return resultList;
    }


    /**Creates masterList for tabel fill with hours worked per day
     *
     * @param project
     * @return
     */
    public ArrayList<Project> createFullMasterList(Project project) {
        //System.out.println("FULL MASTERLIST");
        ArrayList<Project> masterList = new ArrayList<>();
        LocalDate startDate = project.getStartDate();
        LocalDate finishdate = project.getDeadlineDate();

        long i = 0;
        for (LocalDate tempDate = startDate; tempDate.isBefore(finishdate.plusDays(1)); tempDate = startDate.plusDays(i) ){

            masterList.add(new Project(tempDate,null,0.0));
//            System.out.println("tempDate" + tempDate + " time: " + project.getTimeProject() );
            i++;
        }

        return masterList;
    }


    /**Filles arrayList between two input dates
     *   [01, 04]
     *   [01,  value]
     *   [02, value]
     *   [03, value]
     *   [04, value]
     *
     *
     * @param subTaskList
     * @return
     */
    public ArrayList<Task> createFullSubTaskList(ArrayList<Task> subTaskList) {
        ArrayList<Task> subFullList = new ArrayList<>();

        for (int y = 0; y < subTaskList.size(); y++) {

            long i = 0;
            for (LocalDate tempDate = subTaskList.get(y).getStartDate(); tempDate.isBefore(subTaskList.get(y).getFinishDate().plusDays(1)); tempDate = subTaskList.get(y).getStartDate().plusDays(i)) {

                subFullList.add(new Task(tempDate, null, subTaskList.get(y).getWorkingHoursDay()));
                i++;
            }
        }
        return subFullList;
    }


    /**Add the values from one arraylist to another where they have same dates
     *
     * @param masterList
     * @param subTaskList
     * @return
     */
    public ArrayList<Project> joinArrayListValues(ArrayList<Project> masterList, ArrayList<Task> subTaskList) {
            ArrayList<Project> returnList = masterList;
//        System.out.println("subTaskList: " + subTaskList);
            //int index=0;
        int g=0;
        for (Project tempMaster: masterList) {

            for (Task tempSub : subTaskList ) {
                //adds the workingHours from subs to masterList
                //Hvis sub har samme
                if (tempMaster.getStartDate().isEqual(tempSub.getStartDate())){
                    tempMaster.setTimeProject(tempMaster.getTimeProject()+tempSub.getWorkingHoursDay());
                    returnList.get(g).setTimeProject(tempMaster.getTimeProject());
                }

            }
            g++;
        }
        return returnList;
    }

    //***************************************************************************
    //***************************************************************************
    //***               INPUT CHECK                                           ***
    //***************************************************************************
    //***************************************************************************

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

    //Checks if overTask is choosen before create new subtask
    public boolean checkSubTaskChooseTaskName(Task task){
        return task.getSubTaskToName().equals("No overtask");
    }

    //Add task error handling
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

    //Add subtask error handling
    public String errorMessageSubtask(Task task, Project project, Task overTask) throws SQLException {

        String error = "";
        if (checkTaskName(task)) {
            error += " - the chosen name is already in use!";
        }
        if (checkSubTaskNoExistsInDB(task, overTask.getTaskNo())) {
            error += " \n -  the chosen number is already in use!";
        }
        if (!checkForNullValueFinishDateDuration(task)) {
            error += " \n - You need to enter either a finish date or a Duration! ";
        }
        if (!taskStartDateBeforeFinishCheck(task)) {
            error += "\n - finishdate is before startdate!";
        }
        if (!taskDatesAreWithinProjectDatesCheck(task, project)) {
            error += "\n -  taskdate needs to be within project scope!";
        }

        if (checkSubTaskChooseTaskName(task)){
            error += "\n - Taskname need to be choosen";
        }

        if (!subTaskDatesWithInTaskDates(task, overTask)) {
            error += "\n - SubTaskDates need to be within Task scope";
        }

        return error;
    }
}
