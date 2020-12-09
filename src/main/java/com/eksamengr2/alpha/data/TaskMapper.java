package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskMapper {

    public ArrayList<Task> findTasksSubTasks(Task task) throws SQLException {
        Connection con = DatabaseConnector.getConnection();
        ArrayList<Task> taskArrayList = new ArrayList<>();
        String SQL = "select tasktimeconsumption FROM task WHERE taskno >= ? AND taskno < ? and projectid= ?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setDouble(1, task.getTaskNo());
        ps.setDouble(2, task.getTaskNo() + 1);
        ps.setInt(3, task.getProjectId());
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            Task task1 = new Task();
            task1.setTaskTimeconsumption(resultSet.getInt("tasktimeconsumption"));
            taskArrayList.add(task1);
        }

        return taskArrayList;
    }

    public ArrayList<Task> findProjectTask(Project project) throws SQLException {

        Connection con = DatabaseConnector.getConnection();
        ArrayList<Task> taskArrayList = new ArrayList<>();
        String SQL = "select tasktimeconsumption FROM task WHERE  projectid= ?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1, project.getProjectId());
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            Task task1 = new Task();
            task1.setTaskTimeconsumption(resultSet.getInt("tasktimeconsumption"));
            taskArrayList.add(task1);
        }

        return taskArrayList;
    }
}
