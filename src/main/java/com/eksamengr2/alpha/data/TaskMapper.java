package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskMapper {

    /** Return StartDate, FinishDate and workingHoursDay*noOfPersons for all subtasks in a project
     *
     * @param
     * @return  List(startdate, finishdate, workinghoursday)
     * @throws SQLException
     */
    public ArrayList<Task> getStartFinishWorkingHoursDayForSubTask(int projectId) throws SQLException {
        ArrayList<Task> list = new ArrayList<>();
        Task task;


        try {
            //Creates connection
            Connection con = DatabaseConnector.getConnection();

            String SQL = "SELECT startdate, finishdate, noOfPersons* workinghoursday as workinghoursday FROM alfasolutionsdb.task WHERE isSubTask='yes' AND projectid=?;";

            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, projectId);


            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                task = new Task("",
                        resultSet.getDate("startdate").toLocalDate(),
                        resultSet.getDate("finishdate").toLocalDate(),
                        0,
                        0,
                        "",
                        0.0,
                        0,
                        0,
                        0,
                        0,
                        Math.round(resultSet.getDouble("workinghoursday") * 100.00) / 100.00d,
                        "");

                list.add(task);
            }
        }catch (SQLException ex) {
            System.out.println("get_idtask_TaskNo: " + ex);
        }
        return list;
    }

    /** Return all TaskNo(overtask) for project
     *
     * @param
     * @return  The sum of all tasktimeconsumption under one Task(overtask)
     * @throws SQLException
     */
    public ArrayList<Task> getAllTaskNoForProject(int projectId) throws SQLException {
        Task task = new Task();
        ArrayList<Task> listTaskTimeSum = new ArrayList<>();

        try {
            Connection con = DatabaseConnector.getConnection();

            String SQL = "SELECT taskno FROM alfasolutionsdb.task where projectid=? AND isSubTask='no';";

            PreparedStatement ps = con.prepareStatement(SQL);

            ps.setInt(1, projectId);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                task = new Task("",
                        null,
                        null,
                        0,
                        0,
                        "",
                        Math.round(resultSet.getDouble("taskno") * 100.00) / 100.00d,
                        0,
                        0,
                        0,
                        0,
                        0.0,
                        "");

                listTaskTimeSum.add(task);
            }

        }catch (SQLException ex) {
            System.out.println("" + ex);
        }
        return listTaskTimeSum;
    }

    /** return sum of tasktimeconsumption for one Task
     *
     * @param projectId
     * @param taskNo
     * @return
     * @throws SQLException
     */
    public Task getSumOfTaskTimeConsumptionForTask(int projectId, double taskNo) throws SQLException {
        Task task = new Task();

        try {
            Connection con = DatabaseConnector.getConnection();

            String SQL = "SELECT taskno, SUM(tasktimeconsumption) as tasktimeconsumption FROM alfasolutionsdb.task where projectid=? AND taskno>=? AND taskno <?+1;";

            PreparedStatement ps = con.prepareStatement(SQL);

            ps.setInt(1, projectId);
            ps.setDouble(2, Math.round(taskNo * 100.00) / 100d);
            ps.setDouble(3, Math.round(taskNo * 100.00) / 100d);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                task = new Task("",
                        null,
                        null,
                        0,
                        0,
                        "",
                        Math.round(resultSet.getDouble("taskno") * 100.00) / 100.00d,
                        0,
                        0,
                        resultSet.getInt("tasktimeconsumption"),
                        0,
                        0.0,
                        "");

                //listTaskTimeSum.add(task);
            }
        }catch (SQLException ex) {
            System.out.println("getSumOfTaskTimeConsumptionForTask" + ex);
        }

        return task;
    }

//    public ArrayList<Task> findTasksSubTasks(Task task) throws SQLException {
//        ArrayList<Task> taskArrayList = new ArrayList<>();
//
//        try {
//            Connection con = DatabaseConnector.getConnection();
//
//            String SQL = "select tasktimeconsumption FROM task WHERE taskno >= ? AND taskno < ? and projectid= ?";
//            PreparedStatement ps = con.prepareStatement(SQL);
//            ps.setDouble(1, task.getTaskNo());
//            ps.setDouble(2, task.getTaskNo() + 1);
//            ps.setInt(3, task.getProjectId());
//            ResultSet resultSet = ps.executeQuery();
//            while (resultSet.next()) {
//                Task task1 = new Task();
//                task1.setTaskTimeconsumption(resultSet.getInt("tasktimeconsumption"));
//                taskArrayList.add(task1);
//            }
//        }catch (SQLException ex) {
//            System.out.println("findTasksSubTasks: " + ex);
//        }
//        return taskArrayList;
//    }

    public ArrayList<Task> findProjectTasksTimeConsumption(Project project) throws SQLException {

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

