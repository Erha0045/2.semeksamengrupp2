package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



class TaskHandlerTest {


    //Denne skal laves med kontrolleret DB eller virker den ikke
    @Test
    @DisplayName("Updggggate")
    void userInput_FromEditTask_UpdateTaskInDB() throws SQLException {
        ArrayList<Task> mod = new ArrayList<>(); //Parameter
        ArrayList<Task> old = new ArrayList<>(); //Parameter
        ArrayList<Task> exp = new ArrayList<>(); //Return
        ArrayList<Task> actual; //Actual -resultat af test
        TaskHandler taskHandler = new TaskHandler();
         //
        //**************************************************************************************************************
        //                       TEST 1: StartDate i changed-->  OKAY
        //**************************************************************************************************************
        //Input (name, startDate, finishDate, duration, projectId, isSubTask, taskNo, lineCounter)

        mod.add(new Task("Taskname", LocalDate.of(1900,1,4),LocalDate.of(1900,1,10),7,0,"yes",(float) 0.0,0));
        old.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,10),10,0,"yes",(float) 0.0,0));

        //Expected
        exp.add(new Task("Taskname", LocalDate.of(1900,1,4),LocalDate.of(1900,1,10),7,0,"yes",(float) 0.0,0));

        //**************************************************************************************************************
        //                       TEST 2: FinishDate is changed--> OKAY
        //**************************************************************************************************************
        //(name, startDate, finishDate, duration, projectId, isSubTask, taskNo, lineCounter)
        //Input parameters
//        mod.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,2),2,0,"yes",(float) 0.0,0));
//        old.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,10),10,0,"yes",(float) 0.0,0));
//
//        //Expected
//        exp.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,2),2,0,"yes",(float) 0.0,0));

//        //**************************************************************************************************************
//        //                       TEST 3: duration i changed--> OKAY
//        //**************************************************************************************************************
//         //Input (name, startDate, finishDate, duration, projectId, isSubTask, taskNo, lineCounter)
//
//        mod.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,10),10,0,"yes",(float) 0.0,0));
//        old.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,1),1,0,"yes",(float) 0.0,0));
//
//        //Burde blive dette
//        exp.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,10),10,0,"yes",(float) 0.0,0));

        //**************************************************************************************************************
        //                       TEST 4: startDate and finishDate is changed--> OKAY
        //**************************************************************************************************************
         //Input (name, startDate, finishDate, duration, projectId, isSubTask, taskNo, lineCounter)

//        mod.add(new Task("Taskname", LocalDate.of(1900,1,2),LocalDate.of(1900,1,7),6,0,"yes",(float) 0.0,0));
//        old.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,5),5,0,"yes",(float) 0.0,0));
//
//        //Burde blive dette
//        exp.add(new Task("Taskname", LocalDate.of(1900,1,2),LocalDate.of(1900,1,7),6,0,"yes",(float) 0.0,0));

        //**************************************************************************************************************
        //                       TEST 5: finishDate and duration is changed-->OKAY
        //**************************************************************************************************************
        //Input (name, startDate, finishDate, duration, projectId, isSubTask, taskNo, lineCounter)

//        mod.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,7),7,0,"yes",(float) 0.0,0));
//        old.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,5),5,0,"yes",(float) 0.0,0));
//
//        //Burde blive dette
//        exp.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,7),7,0,"yes",(float) 0.0,0));

        //**************************************************************************************************************
        //                       TEST 6: startDate and duration is changed-->OKAY
        //**************************************************************************************************************
        //Input (name, startDate, finishDate, duration, projectId, isSubTask, taskNo, lineCounter)

//        mod.add(new Task("Taskname", LocalDate.of(1900,1,3),LocalDate.of(1900,1,7),5,0,"yes",(float) 0.0,0));
//        old.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,7),7,0,"yes",(float) 0.0,0));
//
//        //Burde blive dette
//        exp.add(new Task("Taskname", LocalDate.of(1900,1,3),LocalDate.of(1900,1,7),5,0,"yes",(float) 0.0,0));

        //**************************************************************************************************************
        //                       TEST 7: startDate, finishDate and duration is changed-->OKAY
        //**************************************************************************************************************
        //Input (name, startDate, finishDate, duration, projectId, isSubTask, taskNo, lineCounter)

//        mod.add(new Task("Taskname", LocalDate.of(1900,1,3),LocalDate.of(1900,1,7),5,0,"yes",(float) 0.0,0));
//        old.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,7),7,0,"yes",(float) 0.0,0));
//
//        //Burde blive dette
//        exp.add(new Task("Taskname", LocalDate.of(1900,1,3),LocalDate.of(1900,1,7),5,0,"yes",(float) 0.0,0));

        //**************************************************************************************************************
        //                       TEST 8: none is changed-->OKAY
        //**************************************************************************************************************
        //Input (name, startDate, finishDate, duration, projectId, isSubTask, taskNo, lineCounter)

//        mod.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,7),7,0,"yes",(float) 0.0,0));
//        old.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,7),7,0,"yes",(float) 0.0,0));
//
//        //Burde blive dette
//        exp.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,7),7,0,"yes",(float) 0.0,0));

        //**************************************************************************************************************
        //                       TEST 9: name is changed-->OKAY
        //**************************************************************************************************************
        //Input (name, startDate, finishDate, duration, projectId, isSubTask, taskNo, lineCounter)

//        mod.add(new Task("newname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,7),7,0,"yes",(float) 0.0,0));
//        old.add(new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,7),7,0,"yes",(float) 0.0,0));
//
//        //Burde blive dette
//        exp.add(new Task("newname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,7),7,0,"yes",(float) 0.0,0));

//        actual = taskHandler.UserInput_FromEditTask_UpdateTaskInDB(mod, old);
//        assertEquals(exp, actual);

    }


    @ParameterizedTest
    @CsvSource({
            "14, 14, 10.0, no,BEGIN WORK; START TRANSACTION; UPDATE alfasolutionsdb.task SET taskno =10.0 WHERE idtask=54;UPDATE alfasolutionsdb.task SET taskno =10.2 WHERE idtask=55; COMMIT;"
//            "14, 2.6, 1.2, yes, BEGIN WORK; START TRANSACTION; UPDATE alfasolutionsdb.task SET taskno =1.2 WHERE idtask=26; COMMIT;",
//            "14, 2.0, 2.2, yes, BEGIN WORK; START TRANSACTION; UPDATE alfasolutionsdb.task SET taskno =2.2 WHERE idtask=32; COMMIT;"
    })
    void createsSqlStringForUpdatingTaskNo(int projectId, float oldTaskNo, float newTaskNo, String isSubTask, String expected) {
        TaskHandler taskHandler = new TaskHandler();
        String actual = taskHandler.updateTaskNos(projectId, oldTaskNo, newTaskNo, isSubTask);

        assertEquals(expected,actual);

//        System.out.println("Print fra test: " + projectId +" " + oldTaskNo +" "+ newTaskNo +" "+ isSubTask);

    }
    //TODO disse skal rettes til
    @Test
    @DisplayName("TEST 1: StartDate i changed")
    void editTask1() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //Parameters1
        Task old = new Task("Taskname", LocalDate.of(2020,1,2),
                LocalDate.of(2020,1,4), 3, 1, "yes",
                1.4, 0, 74,
                100, 4, 10, "");

        //Parameters2
        Task mod = new Task("", LocalDate.of(2020,1,1),
                null, 0, 0, "",
                0.0, 0, 0,
                0, 0, 0.0, "");

        //Expected result
        Task exp = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,4), 4, 1, "yes",
                1.4, 0, 74,
                100, 3, 10, "");

        Task actual = taskHandler.editTask(mod, old);

        //Assert
        assertEquals(exp, actual);


    }

    @Test
    @DisplayName("TEST 2: TimeConsumption is changed")
    void editTask2() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //Parameters1
        Task old = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,4), 4, 1, "yes",
                1.4, 0, 74,
                100, 3, 10, "");

        //Parameters2
        Task mod = new Task("", null,
                null, 0, 0, "",
                0.0, 0, 0,
                50, 0, 0.0, "");

        //Expected result
        Task exp = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,4), 4, 1, "yes",
                1.4, 0, 74,
                50, 2, 10, "");

        Task actual = taskHandler.editTask(mod, old);

        //Assert
        assertEquals(exp, actual);


    }

    @Test
    @DisplayName("TEST 3: FinishDate is changed")
    void editTask3() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //Parameters1
        Task old = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,11), 11, 1, "yes",
                1.4, 0, 74,
                560, 6, 10, "");

        //Parameters2
        Task mod = new Task("", null,
                LocalDate.of(2020,1,6), 0, 0, "",
                0.0, 0, 0,
                0, 0, 0.0, "");

        //Expected result
        Task exp = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,6), 6, 1, "yes",
                1.4, 0, 74,
                560, 10, 10, "");

        Task actual = taskHandler.editTask(mod, old);

        //Assert
        assertEquals(exp, actual);
    }

    @Test
    @DisplayName("TEST 4: Duration is changed")
    void editTask4() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //Parameters1
        Task old = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,13), 13, 1, "yes",
                1.4, 0, 74,
                844, 9, 7.5, "");

        //Parameters2
        Task mod = new Task("", null,
                null, 10, 0, "",
                0.0, 0, 0,
                0, 0, 0.0, "");

        //Expected result
        Task exp = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,10), 10, 1, "yes",
                1.4, 0, 74,
                844, 12, 7.5, "");

        Task actual = taskHandler.editTask(mod, old);

        //Assert
        assertEquals(exp, actual);
    }

    @Test
    @DisplayName("TEST 5: personsOnTask is changed")
    void editTask5() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //Parameters1
        Task old = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,20), 20, 1, "yes",
                1.4, 0, 74,
                741, 5, 7.0, "");

        //Parameters2
        Task mod = new Task("", null,
                null, 0, 0, "",
                0.0, 0, 0,
                0, 10, 0.0, ""); //noOfPersons er afrundet??? når man regner modsat??

        //Expected result
        Task exp = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,10), 10, 1, "yes",
                1.4, 0, 74,
                741, 10, 7.41, "");

        //Actual result
        Task actual = taskHandler.editTask(mod, old);

        //Assert
        assertEquals(exp, actual);
    }

    @Test
    @DisplayName("TEST 6: workingHoursDay is changed")
    void editTask6() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //Parameters1
        Task old = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,11), 11, 1, "yes",
                1.4, 0, 74,
                654, 8, 7.25, "");

        //Parameters2
        Task mod = new Task("", null,
                null, 0, 0, "",
                0.0, 0, 0,
                0, 0, 6, ""); //noOfPersons er afrundet??? når man regner modsat??

        //Expected result
        Task exp = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,11), 11, 1, "yes",
                1.4, 0, 74,
                654, 10, 6.0, "");

        //Actual result
        Task actual = taskHandler.editTask(mod, old);

        //Assert
        assertEquals(exp, actual);
    }

    @Test
    @DisplayName("TEST 7: nothing is changed")
    void editTask7() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //Parameters1
        Task old = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,11), 11, 1, "yes",
                1.4, 0, 74,
                654, 8, 7.25, "");

        //Parameters2
        Task mod = new Task("", null,
                null, 0, 0, "",
                0.0, 0, 0,
                0, 0, 0, ""); //noOfPersons er afrundet??? når man regner modsat??

        //Expected result
        Task exp = new Task("Taskname", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,11), 11, 1, "yes",
                1.4, 0, 74,
                654, 8, 7.25, "");

        //Actual result
        Task actual = taskHandler.editTask(mod, old);

        //Assert
        assertEquals(exp, actual);
    }

    @Test
    void updateTaskNos() {
        TaskHandler taskHandler = new TaskHandler();
        int projectId=1;
        double oldTaskNo=14.00;
        double newTaskNo=15.00;
        String isSubtask="no";
        String expected="BEGIN WORK; START TRANSACTION; UPDATE alfasolutionsdb.task SET taskno =15.0 WHERE idtask=54;" +
                        " UPDATE alfasolutionsdb.task SET taskno =15.25 WHERE idtask=55;" +
                        " UPDATE alfasolutionsdb.task SET taskno =15.3 WHERE idtask=56; COMMIT;";

        String actual=  taskHandler.updateTaskNos(projectId,oldTaskNo,newTaskNo,isSubtask);

        assertEquals(expected, actual);

    }


    //*********************************************
    //** JUnit add a new Task
    //*********************************************

    @Test
    @DisplayName("TEST a: TASK duration entered")
    void addTaskToDBa() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter Task
        Task input = new Task("BAD", LocalDate.of(2020,1,1),
                null, 15, 1, "",
                6.0, 0, 0,
                0, 0, 0.0, "");

        //Expected result
        Task exp = new Task("BAD", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,15), 15, 1, "no",
                6.0, 0, 0,
                0, 0, 0.0, "");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);
    }

    @Test
    @DisplayName("TEST b: TASK finishDate entered")
    void addTaskToDBb() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter Task
        Task input = new Task("BAD", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,1), 1, 1, "",
                16.0, 0, 0,
                0, 0, 0.0, "");

        //Expected result
        Task exp = new Task("BAD", LocalDate.of(2020,1,1),
                LocalDate.of(2020,1,1), 1, 1, "no",
                16.0, 0, 0,
                0, 0, 0.0, "");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);
    }







    //*********************************************
    //** JUnit add a new subTask
    //*********************************************


    @Test
    @DisplayName("TEST 1: subTask: Finishdate entered")
    void addTaskToDB1() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("BAD_sub2", LocalDate.of(2020,12,1),
                LocalDate.of(2021,1,15), 0, 1, "",
                14, 0, 0,
                1500, 0, 0.0, "BAD77");

        //Expected result
        Task exp = new Task("BAD_sub2", LocalDate.of(2020,12,1),
                LocalDate.of(2021,1,15), 46, 1, "yes",
                6.14, 0, 0,
                1500, 5, 7.5, "BAD77");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test
    @DisplayName("TEST 1a: subTask: Duration entered")
    void addTaskToDB1a() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("BAD_sub3", LocalDate.of(2020,12,1),
                null, 15, 1, "",
                10.0, 0, 0,
                1000, 0, 0.0, "BAD77");

        //Expected result
        Task exp = new Task("BAD_sub3", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,15), 15, 1, "yes",
                6.10, 0, 0,
                1000, 9, 7.5, "BAD77");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test
    @DisplayName("TEST 2: subTask: personOnTask entered")
    void addTaskToDB2() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("BAD_sub2", LocalDate.of(2020,12,1),
                null, 0, 1, "",
                13.0, 0, 0,
                1000, 9, 8.4, "BAD77");

        //Expected result
        Task exp = new Task("BAD_sub2", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,14), 14, 1, "yes",
                6.13, 0, 0,
                1000, 9, 8.4, "BAD77");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test
    @DisplayName("TEST 3: subTask: workingHoursDay entered")
    void addTaskToDB3() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("BAD_sub3", LocalDate.of(2020,12,1),
                null, 0, 1, "",
                12.0, 0, 0,
                1000, 0, 7.5, "BAD77");

        //Expected result
        Task exp = new Task("BAD_sub3", LocalDate.of(2020,12,1),
                LocalDate.of(2021,4,13), 134, 1, "yes",
                6.12, 0, 0,
                1000, 1, 7.5, "BAD77");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test
    @DisplayName("TEST 4: subTask: finishDate and personOnTask entered")
    void addTaskToDB4() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("BAD_sub4", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,15), 0, 1, "",
                11.0, 0, 0,
                1000, 9, 0.0, "BAD77");

        //Expected result
        Task exp = new Task("BAD_sub4", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,15), 15, 1, "yes",
                6.11, 0, 0,
                1000, 9, 7.41, "BAD77");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test
    @DisplayName("TEST 5: subTask: finishDate duration and personOnTask entered")
    void addTaskToDB5() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("BAD_sub5", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,15), 14, 1, "",
                50.0, 0, 0,
                1000, 0, 0.0, "BAD77");

        //Expected result
        Task exp = new Task("BAD_sub5", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,15), 15, 1, "yes",
                6.50, 0, 0,
                1000, 10, 7.50, "BAD77");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test
    @DisplayName("TEST 6: subTask: personOnTask and workingHoursDay entered")
    void addTaskToDB6() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("BAD_sub6", LocalDate.of(2020,12,1),
                null, 0, 1, "",
                26.0, 0, 0,
                1000, 6, 8.0, "BAD77");

        //Expected result
        Task exp = new Task("BAD_sub6", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,21), 21, 1, "yes",
                6.26, 0, 0,
                1000, 6, 8.0, "BAD77");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test
    @DisplayName("TEST 7: subTask: All entered")
    void addTaskToDB7() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("BAD_sub7", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,14), 14, 1, "",
                36.0, 0, 0,
                1000, 6, 8.0, "BAD77");

        //Expected result
        Task exp = new Task("BAD_sub7", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,21), 21, 1, "yes",
                6.36, 0, 0,
                1000, 6, 8.0, "BAD77");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test
    @DisplayName("TEST 8: subTask: none entered")
    void addTaskToDB8() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("BAD_sub8", LocalDate.of(2020,12,1),
                null, 0, 1, "",
                37.0, 0, 0,
                1000, 0, 0.0, "BAD77");

        //Expected result
        Task exp = new Task("BAD_sub8", LocalDate.of(2020,12,1),
                LocalDate.of(2021,4,14), 134, 1, "yes",
                6.37, 0, 0,
                1000, 1, 7.5, "BAD77");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test
    void joinArrayListValues() {
        TaskHandler taskHandler = new TaskHandler();

        //Parameter master
        Project project = new Project(LocalDate.of(2020,1,1),LocalDate.of(2020,1,8),0.0);
        ArrayList<Project> masterList = taskHandler.createFullMasterList(project);

        //Parameter sub685
        ArrayList<Task> sublist1 = new ArrayList<>();
        sublist1.add(new Task(LocalDate.of(2020,01,01), LocalDate.of(2020,01,6),1.0 ));
        sublist1.add(new Task(LocalDate.of(2020,01,02), LocalDate.of(2020,01,5),2.0 ));

        ArrayList<Task> sublist = taskHandler.createFullSubTaskList(sublist1);

        //Expected
        ArrayList<Project> expected = new ArrayList<>();

        //Actual
        ArrayList<Project> actual = taskHandler.joinArrayListValues(masterList, sublist);

        //assert test
        assertEquals(expected, actual);


    }
}