package com.eksamengr2.alpha.service;

//import com.eksamengr2.alpha.model.Project;
//import com.eksamengr2.alpha.model.Task;
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
    Task tActual;//Actual -resultat af test
    Task told; //parameter
    Task tmod; //parameter
    Task texp; //Expected
    TaskHandler taskHandler = new TaskHandler();

    //*********************************************
    //** JUnit edit Task test
    //*********************************************

    @Test //OKAY 19-12
    @DisplayName("TEST 1: StartDate i changed")
    void EditTask1() throws SQLException {

        //Parameters
        tmod=new Task("", LocalDate.of(1900,1,3),null,0,0,"", 0.0,0);
        told=new Task("Taskname", LocalDate.of(1900,1,1),LocalDate.of(1900,1,10),10,0,"yes",(float) 0.0,0);

        //Expected
        texp=new Task("Taskname", LocalDate.of(1900,1,3),LocalDate.of(1900,1,10),8,0,"yes",(float) 0.0,0);

        //assert
        tActual = taskHandler.editTask(tmod, told);
        assertEquals(texp, tActual);
    }


    @Test//OKAY 19-12
    @DisplayName("TEST 2: TimeConsumption is changed")
    void EditTask2() throws SQLException {

        //Input parameters
        tmod=new Task("", null, null, 0,  0, "", 0.0, 100, 0, 0.0, "");
        told=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,4), 4,  0, "yes", 0.0, 80, 4, 10.0, "");

        //Expected
        texp=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,4), 4,  0, "yes", 0.0, 100, 3, 10.0, "");
        //assert
        tActual = taskHandler.editTask(tmod, told);
        assertEquals(texp, tActual);

    }

    @Test//OKAY 19-12
    @DisplayName("TEST 3: FinishDate is changed")
    void EditTask3() throws SQLException {

        //Input parameters
        tmod=new Task("", null, LocalDate.of(2020,1,6), 0,  0, "", 0.0, 0, 0, 0.0, "");
        told=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,4), 4,  0, "yes", 0.0, 560, 4, 10.0, "");

        //Expected
        texp=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,6), 6,  0, "yes", 0.0, 560, 10, 10.0, "");

        //assert
        tActual = taskHandler.editTask(tmod, told);
        assertEquals(texp, tActual);

        //assert
        tActual = taskHandler.editTask(tmod, told);
        assertEquals(texp, tActual);

    }


    @Test//OKAY 19-12
    @DisplayName("TEST 4: duration i changed")
    void EditTask4() throws SQLException {

        //Input parameters
        tmod=new Task("", null, null, 10,  0, "", 0.0, 0, 0, 0.0, "");
        told=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,10), 10,  0, "yes", 0.0, 844, 4, 7.5, "");

        //Expected
        texp=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,10), 10,  0, "yes", 0.0, 844, 12, 7.5, "");

        //assert
        tActual = taskHandler.editTask(tmod, told);
        assertEquals(texp, tActual);

    }


    @Test//OKAY 19-12
    @DisplayName("TEST 5: noOfPerson i changed")
    void EditTask5() throws SQLException {

        //Input parameters
        tmod=new Task("", null, null, 0,  0, "", 0.0, 0, 10, 0.0, "");
        told=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,11), 11,  0, "yes", 0.0, 741, 10, 7.5, "");

        //Expected
        texp=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,10), 10,  0, "yes", 0.0, 741, 10, 7.41, "");

        //assert
        tActual = taskHandler.editTask(tmod, told);
        assertEquals(texp, tActual);
    }


    @Test//OKAY 19-12
    @DisplayName("TEST 6: workingHoursDay i changed")
    void EditTask6() throws SQLException {

        //Input parameters
        tmod=new Task("", null, null, 0,  0, "", 0.0, 0, 0, 6.0, "");
        told=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,11), 11,  0, "yes", 0.0, 654, 8, 7.5, "");

        //Expected
        texp=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,11), 11,  0, "yes", 0.0, 654, 10, 6.0, "");

        //assert
        tActual = taskHandler.editTask(tmod, told);
        assertEquals(texp, tActual);
    }


    @Test//OKAY 19-12
    @DisplayName("TEST 6: none i changed")
    void EditTask7() throws SQLException {

        //Input parameters
        tmod=new Task("", null, null, 0,  0, "", 0.0, 0, 0, 0.0, "");
        told=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,11), 11,  0, "yes", 0.0, 654, 10, 6.0, "");

        //Expected
        texp=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,11), 11,  0, "yes", 0.0, 654, 10, 6.0, "");

        //assert
        tActual = taskHandler.editTask(tmod, told);
        assertEquals(texp, tActual);
    }


    @Test //OKAY 19-12
        @DisplayName("TEST 7: name is changed")
    void EditTask9() throws SQLException {

            //Input parameters
            tmod=new Task("Task", null, null, 0,  0, "", 0.0, 0, 0, 0.0, "");
            told=new Task("", LocalDate.of(2020,1,1), LocalDate.of(2020,1,11), 11,  0, "yes", 0.0, 654, 10, 6.0, "");

            //Expected
            texp=new Task("Task", LocalDate.of(2020,1,1), LocalDate.of(2020,1,11), 11,  0, "yes", 0.0, 654, 10, 6.0, "");

            //assert
            tActual = taskHandler.editTask(tmod, told);
            assertEquals(texp, tActual);
    }


    @ParameterizedTest
    @CsvSource({
            "1, 9, 10.0, no,BEGIN WORK; START TRANSACTION; UPDATE alfasolutionsdb.task SET taskno =10.0 WHERE idtask=1; UPDATE alfasolutionsdb.task SET taskno =10.2 WHERE idtask=2; UPDATE alfasolutionsdb.task SET taskno =10.1 WHERE idtask=3; UPDATE alfasolutionsdb.task SET taskno =10.20 WHERE idtask=4; UPDATE alfasolutionsdb.task SET taskno =10.17 WHERE idtask=5;COMMIT;"
//            "14, 2.6, 1.2, yes, BEGIN WORK; START TRANSACTION; UPDATE alfasolutionsdb.task SET taskno =1.2 WHERE idtask=26; COMMIT;",
//            "14, 2.0, 2.2, yes, BEGIN WORK; START TRANSACTION; UPDATE alfasolutionsdb.task SET taskno =2.2 WHERE idtask=32; COMMIT;"
    })
    void createsSqlStringForUpdatingTaskNo(int projectId, float oldTaskNo, float newTaskNo, String isSubTask, String expected) {
        TaskHandler taskHandler = new TaskHandler();
        String actual = taskHandler.updateTaskNos(projectId, oldTaskNo, newTaskNo, isSubTask);

        assertEquals(expected,actual);
    }


    //*********************************************
    //** JUnit edit subTask
    //*********************************************

    @Test//OKAY 19-12
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

    @Test//OKAY 19-12
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

    @Test//OKAY 19-12
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

    @Test//OKAY 19-12
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

    @Test//OKAY 19-12
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

    @Test//OKAY 19-12
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

    @Test//OKAY19-12
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

    @Test//OKAY 19-12
    @DisplayName("TEST changed task taskno-> sub taskNo")
    void updateTaskNos() {
        TaskHandler taskHandler = new TaskHandler();
        int projectId=1;
        double oldTaskNo=8.00;
        double newTaskNo=9.00;
        String isSubtask="no";
        String expected="BEGIN WORK; START TRANSACTION; UPDATE alfasolutionsdb.task SET taskno =9.0 WHERE idtask=6;" +
                                                      " UPDATE alfasolutionsdb.task SET taskno =9.25 WHERE idtask=7;" +
                                                      " UPDATE alfasolutionsdb.task SET taskno =9.3 WHERE idtask=8;" +
                                                      " UPDATE alfasolutionsdb.task SET taskno =9.3 WHERE idtask=9; COMMIT;";

        String actual=  taskHandler.updateTaskNos(projectId,oldTaskNo,newTaskNo,isSubtask);

        assertEquals(expected, actual);
    }


    //*********************************************
    //** JUnit add a new Task
    //*********************************************

    @Test//OKAY 19-12
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

    @Test//OKAY 19-12
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

    @Test //OKAY 19-12
    @DisplayName("TEST 1: subTask: Finishdate entered")
    void addTaskToDB1() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("HaveTest", LocalDate.of(2020,12,1),
                LocalDate.of(2021,1,15), 0, 1, "",
                14, 0, 0,
                1500, 0, 0.0, "HAVE");

        //Expected result
        Task exp = new Task("HaveTest", LocalDate.of(2020,12,1),
                LocalDate.of(2021,1,15), 46, 1, "yes",
                7.14, 0, 0,
                1500, 5, 7.5, "HAVE");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test //OKAY 19-12
    @DisplayName("TEST 1a: subTask: Duration entered")
    void addTaskToDB1a() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("HaveTest1a", LocalDate.of(2020,12,1),
                null, 15, 1, "",
                10.0, 0, 0,
                1000, 0, 0.0, "HAVE");

        //Expected result
        Task exp = new Task("HaveTest1a", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,15), 15, 1, "yes",
                7.10, 0, 0,
                1000, 9, 7.5, "HAVE");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test //OKAY19-12
    @DisplayName("TEST 2: subTask: personOnTask entered")
    void addTaskToDB2() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("HaveTest2", LocalDate.of(2020,12,1),
                null, 0, 1, "",
                13.0, 0, 0,
                1000, 9, 0.0, "HAVE");

        //Expected result
        Task exp = new Task("HaveTest2", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,15), 15, 1, "yes",
                7.13, 0, 0,
                1000, 9, 7.5, "HAVE");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test//OKAY19-12
    @DisplayName("TEST 3: subTask: workingHoursDay entered")
    void addTaskToDB3() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("HaveTest3", LocalDate.of(2020,12,1),
                null, 0, 1, "",
                12.0, 0, 0,
                1000, 0, 7.5, "HAVE");

        //Expected result
        Task exp = new Task("HaveTest3", LocalDate.of(2020,12,1),
                LocalDate.of(2021,4,13), 134, 1, "yes",
                7.12, 0, 0,
                1000, 1, 7.5, "HAVE");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test//OKAY19-12
    @DisplayName("TEST 4: subTask: finishDate and personOnTask entered")
    void addTaskToDB4() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("HaveTest4", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,15), 0, 1, "",
                11.0, 0, 0,
                1000, 9, 0.0, "HAVE");

        //Expected result
        Task exp = new Task("HaveTest4", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,15), 15, 1, "yes",
                7.11, 0, 0,
                1000, 9, 7.41, "HAVE");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test//OKAY19-12
    @DisplayName("TEST 5: subTask: finishDate duration and workingHoursDay entered")
    void addTaskToDB5() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("HaveTest5", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,15), 14, 1, "",
                50.0, 0, 0,
                1000, 0, 10.0, "HAVE");

        //Expected result
        Task exp = new Task("HaveTest5", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,15), 15, 1, "yes",
                7.50, 0, 0,
                1000, 8, 10.0, "HAVE");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test//OKAY19-12
    @DisplayName("TEST 6: subTask: personOnTask and workingHoursDay entered")
    void addTaskToDB6() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("HaveTest6", LocalDate.of(2020,12,1),
                null, 0, 1, "",
                26.0, 0, 0,
                1000, 6, 8.0, "HAVE");

        //Expected result
        Task exp = new Task("HaveTest6", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,21), 21, 1, "yes",
                7.26, 0, 0,
                1000, 6, 8.0, "HAVE");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test//OKAY19-12
    @DisplayName("TEST 7: subTask: All entered")
    void addTaskToDB7() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("HaveTest7", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,14), 14, 1, "",
                36.0, 0, 0,
                1000, 6, 8.0, "HAVE");

        //Expected result
        Task exp = new Task("HaveTest7", LocalDate.of(2020,12,1),
                LocalDate.of(2020,12,21), 21, 1, "yes",
                7.36, 0, 0,
                1000, 6, 8.0, "HAVE");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    @Test//OKAY19-12
    @DisplayName("TEST 8: subTask: none entered")
    void addTaskToDB8() throws SQLException {
        TaskHandler taskHandler = new TaskHandler();

        //parameter- subTask
        Task input = new Task("HaveTest8", LocalDate.of(2020,12,1),
                null, 0, 1, "",
                37.0, 0, 0,
                1000, 0, 0.0, "HAVE");

        //Expected result
        Task exp = new Task("HaveTest8", LocalDate.of(2020,12,1),
                LocalDate.of(2021,4,14), 134, 1, "yes",
                7.37, 0, 0,
                1000, 1, 7.5, "HAVE");

        //Actual result
        Task actual = taskHandler.AddTaskToDB(input);

        //Assert
        assertEquals(exp, actual);


    }

    //*********************************************
    //** JUnit Time calculation
    //*********************************************

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