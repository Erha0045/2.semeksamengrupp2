package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskhandlerELTest {
    TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
    Task task = new Task();
    Project project = new Project();

    @Test
    @DisplayName("Test 0 - true test, ingen intentionelle fejl.")
    void createTaskInputChecksIfTaskDatesAreWithinProjectDates() {


        task.setStartDate(LocalDate.of(2020, 1, 1));
        task.setFinishDate(LocalDate.of(2020, 2, 1));
        project.setStartDate(LocalDate.of(2020, 1, 1));
        project.setDeadlineDate(LocalDate.of(2020, 2, 1));

        Boolean svar0 = taskhandlerEL.taskDatesAreWithinProjectDatesCheck(task, project);

        assertTrue(svar0);
    }
    @Test
    @DisplayName( "Test 1 -  Tester hvis task.startDate.isBefore(project.getStartDate)")
    void createTaskInputChecksIfTaskDatesAreWithinProjectDates1() {
//        TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
//        Task task = new Task();
//        Project project = new Project();

        task.setStartDate(LocalDate.of(2019, 1, 1));
        task.setFinishDate(LocalDate.of(2020, 2, 1));
        project.setStartDate(LocalDate.of(2020, 1, 1));
        project.setDeadlineDate(LocalDate.of(2020, 2, 1));

        Boolean svar = taskhandlerEL.taskDatesAreWithinProjectDatesCheck(task, project);

        assertFalse(svar);
    }
    @Test
    @DisplayName("Test 2 -Tester hvis task.startDate.isBefore(project.getStartDate)")
    void createTaskInputChecksIfTaskDatesAreWithinProjectDates2() {
//        TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
//        Task task = new Task();
//        Project project = new Project();
//        //test 2
        // tester hvis : task.startDate.isAfter(project.deadlineDate)
        task.setStartDate(LocalDate.of(2021, 1, 1));
        task.setFinishDate(LocalDate.of(2020, 2, 1));
        project.setStartDate(LocalDate.of(2020, 1, 1));
        project.setDeadlineDate(LocalDate.of(2020, 2, 1));

        Boolean svar2 = taskhandlerEL.taskDatesAreWithinProjectDatesCheck(task, project);

        assertFalse(svar2);
    }
    @Test
    @DisplayName("test 3 - tester hvis : task.finishDate.isBefore(project.getStartDate)")
    void createTaskInputChecksIfTaskDatesAreWithinProjectDates3() {
//        TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
//        Task task = new Task();
//        Project project = new Project();

        task.setStartDate(LocalDate.of(2020, 1, 1));
        task.setFinishDate(LocalDate.of(2019, 2, 1));
        project.setStartDate(LocalDate.of(2020, 1, 1));
        project.setDeadlineDate(LocalDate.of(2020, 2, 1));

        Boolean svar3 = taskhandlerEL.taskDatesAreWithinProjectDatesCheck(task, project);

        assertFalse(svar3);
    }
    @Test
    @DisplayName("Test 4 -  tester, hvis task.finishDate.isAfter(project.deadlineDate)")
    void createTaskInputChecksIfTaskDatesAreWithinProjectDates4() {
//        TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
//        Task task = new Task();
//        Project project = new Project();

        task.setStartDate(LocalDate.of(2020, 1, 1));
        task.setFinishDate(LocalDate.of(2020, 3, 1));
        project.setStartDate(LocalDate.of(2020, 1, 1));
        project.setDeadlineDate(LocalDate.of(2020, 2, 1));

        boolean svar4 = taskhandlerEL.taskDatesAreWithinProjectDatesCheck(task, project);
        assertFalse(svar4);
    }

    @Test
    void createTaskInputDateCheck(){
        //test1
        // tester hvis : task.FinishDate.isBefore(task.startDate)
        TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
        Task task = new Task();
        task.setStartDate(LocalDate.of(2020, 1, 1));
        task.setFinishDate(LocalDate.of(2019, 1, 1));
        assertFalse(taskhandlerEL.taskStartDateBeforeFinishCheck(task));
    }

    @Test

    void CreateTaskInputChecksIfDurationIsOverFinishDateMinusStartdate(){
        TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
        Task task = new Task();

        task.setDuration(3);
        task.setStartDate(LocalDate.of(2020,1,1));
        task.setFinishDate(LocalDate.of(2020,1,2));

        assertFalse(taskhandlerEL.durationIsOverFinishDateMinusStartdateCheck(task));
        //assertTrue(taskhandlerEL.CreateTaskInputChecksIfDurationIsOverFinishDateMinusStartdate(task));

    }
//todo husk at teste
//
//    @Test
//    @DisplayName("check for null value if all three variable are 0")
//    void checkForNullValue() {
//        //arrange
//        TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
//        Task task = new Task();
//        task.setDuration(0);
//        task.setNoOfPersons(0);
//        task.setWorkingHoursDay(0);
//
//        //act
//        String fejl = taskhandlerEL.checkForNullValue(task);
//
//        //assert
//        assertEquals("MINDST TO VARIABLE SKAL INDTASTES ",fejl);
//    }
//    @Test
//    @DisplayName("check for null value if two variables are 0")
//    void checkForNullValue1() {
//        //arrange
//        TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
//        Task task = new Task();
//        task.setDuration(1);
//        task.setNoOfPersons(0);
//        task.setWorkingHoursDay(0);
//
//        //act
//        String fejl = taskhandlerEL.checkForNullValue(task);
//
//        //assert
//        assertEquals("FEJL I INDTASTNING",fejl);
//    }

    @Test
    @DisplayName("check if name exists on projectId")
    void checkTaskName() throws SQLException {
            //arrange
        TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
        Task task = new Task();
        task.setName("blah");
        task.setProjectId(34);
            //act
        boolean actual = taskhandlerEL.checkTaskName(task);
            //assert
        assertTrue(actual);

    }
    @Test
    @DisplayName("check if taskno exists on projectId")
    void checkTaskNo() throws SQLException {
            //arrange
        TaskhandlerEL taskhandlerEL = new TaskhandlerEL();
        Task task = new Task();
        task.setTaskNo(1);
        task.setProjectId(34);
            //act
        boolean actual = taskhandlerEL.checkTaskNo(task);
            //assert
        assertTrue(actual);

    }
}