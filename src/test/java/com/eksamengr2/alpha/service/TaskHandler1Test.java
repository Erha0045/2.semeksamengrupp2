package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Hejsa")
class TaskHandler1Test {



    @Test
    @DisplayName("Updggggate")
    void userInput_FromEditTask_UpdateTaskInDB() throws SQLException {
        ArrayList<Task> mod = new ArrayList<>(); //Parameter
        ArrayList<Task> old = new ArrayList<>(); //Parameter
        ArrayList<Task> exp = new ArrayList<>(); //Return
        ArrayList<Task> actual; //Actual -resultat af test
        TaskHandler1 taskHandler1 = new TaskHandler1();
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

//        actual = taskHandler1.UserInput_FromEditTask_UpdateTaskInDB(mod, old);
//        assertEquals(exp, actual);

    }


    @ParameterizedTest
    @CsvSource({
            "14, 14, 10.0, no,BEGIN WORK; START TRANSACTION; UPDATE alfasolutionsdb.task SET taskno =10.0 WHERE idtask=54;UPDATE alfasolutionsdb.task SET taskno =10.2 WHERE idtask=55; COMMIT;"
//            "14, 2.6, 1.2, yes, BEGIN WORK; START TRANSACTION; UPDATE alfasolutionsdb.task SET taskno =1.2 WHERE idtask=26; COMMIT;",
//            "14, 2.0, 2.2, yes, BEGIN WORK; START TRANSACTION; UPDATE alfasolutionsdb.task SET taskno =2.2 WHERE idtask=32; COMMIT;"
    })
    void createsSqlStringForUpdatingTaskNo(int projectId, float oldTaskNo, float newTaskNo, String isSubTask, String expected) {
        TaskHandler1 taskHandler1 = new TaskHandler1();
        String actual = taskHandler1.UpdateTaskNos(projectId, oldTaskNo, newTaskNo, isSubTask);

        assertEquals(expected,actual);

//        System.out.println("Print fra test: " + projectId +" " + oldTaskNo +" "+ newTaskNo +" "+ isSubTask);

    }

    @Test
    void testtest(){

        System.out.println("testtest");
    }
}