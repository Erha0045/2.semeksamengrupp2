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

    //returns true if name exists in DB
    public boolean checkTaskNameExistsInDB(Task task) throws SQLException {
        Connection con = DatabaseConnector.getConnection();
        String SQL = "select * FROM task WHERE  name =? AND projectid=?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setString(1, task.getName());
        ps.setInt(2, task.getProjectId());
        ResultSet resultSet = ps.executeQuery();
        return resultSet.next();
    }
    public boolean checkTaskNoExistsInDB(Task task) throws SQLException {
        Connection con = DatabaseConnector.getConnection();
        String SQL = "select * FROM task WHERE  taskno=? AND projectid= ?;";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setDouble(1, task.getTaskNo());
        ps.setInt(2, task.getProjectId());
        ResultSet resultSet = ps.executeQuery();
        return resultSet.next();
    }
    // potentiel løsning for mig og erhan.
    public boolean checkSubTaskNoExistsInDB(Task task,double overTaskNo) throws SQLException {
        Connection con = DatabaseConnector.getConnection();
        String SQL = "select * FROM task WHERE  taskno=? AND isSubTask='yes' and projectid= ?;";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setDouble(1, task.getTaskNo()/100.00+ overTaskNo);
        ps.setInt(2, task.getProjectId());
        ResultSet resultSet = ps.executeQuery();
        return resultSet.next();
    }

    public Task getTask(double overTaskNo,int projectID) throws SQLException {
        Task task = new Task();

        Connection con = DatabaseConnector.getConnection();
        String SQL = "select startdate, finishdate, taskno FROM task WHERE  taskno=? and projectid= ?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setDouble(1, overTaskNo);
        ps.setInt(2, projectID);

        ResultSet resultSet = ps.executeQuery();

        while(resultSet.next()){
             task = new Task(resultSet.getDouble("taskno"),resultSet.getDate("startdate").toLocalDate(),resultSet.getDate("finishdate").toLocalDate());
            return task;
        }

       return task;
    }
}

