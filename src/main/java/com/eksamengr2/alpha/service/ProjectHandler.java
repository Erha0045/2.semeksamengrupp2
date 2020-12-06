package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.data.ProjectMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.User;
import com.eksamengr2.alpha.springController.ProjectController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectHandler {
    ProjectMapper projectMapper = new ProjectMapper();

    ProjectController projectController = new ProjectController();

    public int createProjectInputDateCheck(Project project) {
        if (project.getDeadlineDate().isBefore(project.getStartDate())) {
            return 0;
        } else return 1;
    }

    public int createProjectInputNameCheck(Project project, User user) throws SQLException {
        ResultSet resultSet = projectMapper.getProjectNames(user);
        while (resultSet.next()) {
            String pname = resultSet.getString("projectname");
            if (project.getProjectName().equals(pname)) {
                System.out.println("handlerprojectname " + project.getProjectName());
                System.out.println("handler pname " + pname);
                return 0;
            }
        }
        return 1;
    }
}
