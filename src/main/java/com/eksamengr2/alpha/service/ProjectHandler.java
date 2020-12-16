package com.eksamengr2.alpha.service;

import com.eksamengr2.alpha.data.ProjectMapper;
import com.eksamengr2.alpha.data.TaskMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.model.User;
import com.eksamengr2.alpha.springController.ProjectController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectHandler {
    ProjectMapper projectMapper = new ProjectMapper();


    public int createProjectInputDateCheck(Project project) {
        if (project.getDeadlineDate().isBefore(project.getStartDate())) {
            return 0;
        } else return 1;
    }

    public int projectTimeConsumptionSum(Project project) throws SQLException {
        TaskMapper taskMapper = new TaskMapper();
        ArrayList<Task> taskArrayList = taskMapper.findProjectTask(project);
        int timeConsumptionSum = 0;

        for (Task task : taskArrayList) {
            timeConsumptionSum += task.getTaskTimeconsumption();
        }
        return timeConsumptionSum;
    }

    public int createProjectInputNameCheck(Project project, User user) throws SQLException {
        ResultSet resultSet = projectMapper.getProjectNames(user);
        while (resultSet.next()) {
            String pname = resultSet.getString("projectname");
            if (project.getProjectName().equals(pname)) {
//                System.out.println("handlerprojectname " + project.getProjectName());
//                System.out.println("handler pname " + pname);
                return 0;
            }
        }
        return 1;
    }

    public String errorMessageCreateProject(Project project, User user) throws SQLException {

        String error = "";
        if (createProjectInputNameCheck(project, user) == 0) {
            error += " - The name you've entered already exists. please try another ";
        }
        if (createProjectInputDateCheck(project) == 0) {
            error += " - your start date needs to be before your Deadline date.";
        }
        return error;
    }
}
