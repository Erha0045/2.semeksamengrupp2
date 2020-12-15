package com.eksamengr2.alpha.service;

import java.sql.SQLException;

class TaskHandlerTestLuff {

//    Task(LocalDate startDate, LocalDate finishDate, int duration,
//         int projectId, String isSubTask, double taskNo, String newTaskName,
//         int taskTimeconsumption, int noOfPersons, double workingHoursDay, String subTaskToName)

    private final TaskHandler taskHandler = new TaskHandler();
    @org.junit.jupiter.api.Test
    void addUserDurationZero() throws SQLException {
//
//        // tester hvis, duration=0;
//        Task addTask = new Task(LocalDate.of(1900,1,4),LocalDate.of(1900,1,10),0,1,"yes",10,"testtask",30,2,8,"que?");
//        Task expReturnTask = new Task(LocalDate.of(1900,1,4),LocalDate.of(1900,1,10),7,1,"yes",10,"testtask",30,2,8,"que?");
//       // hvorfor for får tasks et "newname" isetdet for bare et name???
//        expReturnTask.setName("testtask");
//        Task taskThats_beenThrough_userInput_fromAddTaskPreparedToMySQ_L = taskHandler.UserInput_FromAddTaskPreparedToMySQL(addTask);
//        assertEquals(expReturnTask, taskThats_beenThrough_userInput_fromAddTaskPreparedToMySQ_L);
//        System.out.println("start duration=0");
//        System.out.println("expected duration ="+expReturnTask.getDuration() );
    }
    @org.junit.jupiter.api.Test
    void addUserNoDeadline() throws SQLException {

//        // tester hvis, ingen slutdato er sat
//        Task addTask = new       Task(LocalDate.of(1900,1,4),null,7,1,"yes",10,"testtask",30,2,8,"que?");
//        Task expReturnTask = new Task(LocalDate.of(1900,1,4),LocalDate.of(1900,1,10),7,1,"yes",10,"testtask",30,2,8,"que?");
//        // hvorfor for får tasks et "newname" isetdet for bare et name???
//        expReturnTask.setName("testtask");
//        Task taskThats_beenThrough_userInput_fromAddTaskPreparedToMySQ_L = taskHandler.UserInput_FromAddTaskPreparedToMySQL(addTask);
//        assertEquals(expReturnTask, taskThats_beenThrough_userInput_fromAddTaskPreparedToMySQ_L);
//        System.out.println("finishDate: Null");
//        System.out.println("expected finishDate ="+expReturnTask.getFinishDate() );
    }

//    @org.junit.jupiter.api.Test
//    void ThirdTest_for_addTask() throws SQLException {
//
//        // tester hvis, duration=0;
//        Task addTask = new       Task(LocalDate.of(1900,1,4),LocalDate.of(1900,1,10),7,1,"no",10,"testtask",30,2,8,"que?");
//        Task expReturnTask = new Task(LocalDate.of(1900,1,4),LocalDate.of(1900,1,10),7,1,"yes",10,"testtask",30,2,8,"que?");
//        // hvorfor for får tasks et "newname" isetdet for bare et name???
//        expReturnTask.setName("testtask");
//        Task taskThats_beenThrough_userInput_fromAddTaskPreparedToMySQ_L = taskHandler.UserInput_FromAddTaskPreparedToMySQL(addTask);
//        assertEquals(expReturnTask, taskThats_beenThrough_userInput_fromAddTaskPreparedToMySQ_L);
//        System.out.println("Start is not sub task");
//        System.out.println("expected is subtask="+expReturnTask.getIsSubTask() );
//    }
}