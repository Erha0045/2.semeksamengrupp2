package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.data.EditProjectMapper;
import com.eksamengr2.alpha.model.Task;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskHandler1 {


    public ArrayList<Task> UserInput_FromEditTask_PreparingObject_ForUpdateDB(ArrayList<Task> modifiedTask, ArrayList<Task> oldTask){

        ArrayList<Task> newList =new ArrayList<>();

        //The tree values for a task, startDate, finishDate and duration interact with eachother

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


        //initialiser med værdier da get eller ikke kan bruges
        newList.add(new Task("",LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),0,0,"maybe",(float) 0.0,0));

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


//        //Change of taskNumber og alle eventuelt tilhørende subtask numre TODO
        changeTaskNo(1, (float)2.0, (float)3.0, "yes");
        //TODO DUMMY
        newList.get(0).setTaskNo((float)9.99);

        System.out.println("new List"+newList);
        return newList;
    }
    //TODO
    private void changeTaskNo(int projectId, float oldTaskNo, float newTaskNo, String isSubTask) {
        ArrayList<Task> list = new ArrayList<>();
        EditProjectMapper editProjectMapper = new EditProjectMapper();

        String subString1="INSERT INTO task (idtask, taskno) VALUES ";
        String subString2="";
        String subString3="";
        String subString4 = "ON DUPLICATE KEY UPDATE taskno = VALUES(taskno);";

        if(isSubTask.equals("no")){
            //hent idtask + taskno
            list = editProjectMapper.get_idtask_TaskNo(projectId,oldTaskNo);
            /* DETTE ER STRENG DER SKAL LAVES
            INSERT INTO task
                    (idtask, taskno)
            VALUES
                    (19, 2.27),
                    (20, 2.27)
            ON DUPLICATE KEY UPDATE
            taskno = VALUES(taskno);
            */


            for (Task a: list ) {

            subString2 += "(" + a.getIdtask() +","+ a.getTaskNo()+ "),"; //BEMÆRK KOMMA SKAL IKKE VÆRE PÅ SIDSTE KAN FJERNE DET EFTER???


            }
            //create SQL string med forløkke
            //uddate alle relevante i mapper
        }

        if(isSubTask.equals("yes")){



        }





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
