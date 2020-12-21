package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProjectHandlerTest { //(TL, EB)


    @Test
    void createProjectInputDateCheck() {
        //arrange
        ProjectHandler projectHandler = new ProjectHandler();
        LocalDate startDate = LocalDate.of(2012, 02, 20);
        LocalDate finisDate = LocalDate.of(2019, 02, 20);
        //act
        Project project = new Project();
        project.setStartDate(startDate);
        project.setDeadlineDate(finisDate);
        int result = projectHandler.createProjectInputDateCheck(project);

        //assert
        assertEquals(1, result);
    }

    @Test
    void createProjectInputNameCheck() throws SQLException {
        //arrange
        ProjectHandler projectHandler = new ProjectHandler();
        Project project = new Project();
        project.setProjectName("test2");
        User user = new User("luff");

        //act
        int result = projectHandler.createProjectInputNameCheck(project, user);

        //assert
        assertEquals(0, result);
    }
}