package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskHandlerTest1 {
    TaskHandler taskhandler = new TaskHandler();
    Task task = new Task();
    Project project = new Project();

    @Test
    @DisplayName("Test 0 - true test, ingen intentionelle fejl.")
    void createTaskInputChecksIfTaskDatesAreWithinProjectDates() {


        task.setStartDate(LocalDate.of(2020, 1, 1));
        task.setFinishDate(LocalDate.of(2020, 2, 1));
        project.setStartDate(LocalDate.of(2020, 1, 1));
        project.setDeadlineDate(LocalDate.of(2020, 2, 1));

        Boolean svar0 = taskhandler.taskDatesAreWithinProjectDatesCheck(task, project);

        assertTrue(svar0);
    }


    @Test
    @DisplayName("Test 1 -  Tester hvis task.startDate.isBefore(project.getStartDate)")
    void createTaskInputChecksIfTaskDatesAreWithinProjectDates1() {
        task.setStartDate(LocalDate.of(2019, 1, 1));
        task.setFinishDate(LocalDate.of(2020, 2, 1));
        project.setStartDate(LocalDate.of(2020, 1, 1));
        project.setDeadlineDate(LocalDate.of(2020, 2, 1));

        Boolean svar = taskhandler.taskDatesAreWithinProjectDatesCheck(task, project);

        assertFalse(svar);
    }


    @Test
    @DisplayName("Test 2 -Tester hvis task.startDate.isBefore(project.getStartDate)")
    void createTaskInputChecksIfTaskDatesAreWithinProjectDates2() {
        task.setStartDate(LocalDate.of(2021, 1, 1));
        task.setFinishDate(LocalDate.of(2020, 2, 1));
        project.setStartDate(LocalDate.of(2020, 1, 1));
        project.setDeadlineDate(LocalDate.of(2020, 2, 1));

        Boolean svar2 = taskhandler.taskDatesAreWithinProjectDatesCheck(task, project);

        assertFalse(svar2);
    }


    @Test
    @DisplayName("test 3 - tester hvis : task.finishDate.isBefore(project.getStartDate)")
    void createTaskInputChecksIfTaskDatesAreWithinProjectDates3() {
        task.setStartDate(LocalDate.of(2020, 1, 1));
        task.setFinishDate(LocalDate.of(2019, 2, 1));
        project.setStartDate(LocalDate.of(2020, 1, 1));
        project.setDeadlineDate(LocalDate.of(2020, 2, 1));

        Boolean svar3 = taskhandler.taskDatesAreWithinProjectDatesCheck(task, project);

        assertFalse(svar3);
    }


    @Test
    @DisplayName("Test 4 -  tester, hvis task.finishDate.isAfter(project.deadlineDate)")
    void createTaskInputChecksIfTaskDatesAreWithinProjectDates4() {
        task.setStartDate(LocalDate.of(2020, 1, 1));
        task.setFinishDate(LocalDate.of(2020, 3, 1));
        project.setStartDate(LocalDate.of(2020, 1, 1));
        project.setDeadlineDate(LocalDate.of(2020, 2, 1));

        boolean svar4 = taskhandler.taskDatesAreWithinProjectDatesCheck(task, project);
        assertFalse(svar4);
    }


    @Test
    void createTaskInputDateCheck() {
        Task task = new Task();
        task.setStartDate(LocalDate.of(2020, 1, 1));
        task.setFinishDate(LocalDate.of(2019, 1, 1));
        assertFalse(taskhandler.taskStartDateBeforeFinishCheck(task));
    }


    @Test
    void CreateTaskInputChecksIfDurationIsOverFinishDateMinusStartdate() {
        Task task = new Task();

        task.setDuration(3);
        task.setStartDate(LocalDate.of(2020, 1, 1));
        task.setFinishDate(LocalDate.of(2020, 1, 2));

        assertFalse(taskhandler.durationIsOverFinishDateMinusStartdateCheck(task));
    }


    @Test
    @DisplayName("check if name exists on projectId")
    void checkTaskName() throws SQLException {
        //arrange

        Task task = new Task();
        task.setName("HAVE");
        task.setProjectId(1);
        //act
        boolean actual = taskhandler.checkTaskName(task);
        //assert
        assertTrue(actual);

    }


    @Test
    @DisplayName("check if taskno exists on projectId")
    void checkTaskNo() throws SQLException {
        //arrange

        Task task = new Task();
        task.setTaskNo(1);
        task.setProjectId(1);
        //act
        boolean actual = taskhandler.checkTaskNo(task);
        //assert
        assertFalse(actual);

    }

}