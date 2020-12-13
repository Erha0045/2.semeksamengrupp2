package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditProjectMapper {

    public double getTaskNo(int projectId, String name) {
        double returnTaskNo=0.0;

        try {
            Connection con = DatabaseConnector.getConnection();

            String SQL = "SELECT taskno FROM task WHERE projectid=? AND name=? AND isSubTask='no';";

            PreparedStatement ps = con.prepareStatement(SQL);

            ps.setInt(1, projectId);
            ps.setString(2, name);


            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) //true så længe der er mere data i sql tabel
            {
                returnTaskNo = (Math.round((resultSet.getDouble("taskno")*100.00))/100.00d);
            }
        } catch (SQLException ex) {
            System.out.println("get_idtask_TaskNo: " + ex);
        }
            System.out.println("Hentet taskNo: " +returnTaskNo);

        return returnTaskNo;
    }

    //TODO ER IKKE TJEKKET
    public void updateTask(Task task) throws SQLException {
        Connection conn=DatabaseConnector.getConnection();

        PreparedStatement preparedStatement = null; //stored procedure

        try {
            //1) Create a string that holds the query with ? as user input
            String sqlString = "UPDATE alfasolutionsdb.task SET projectid=? ,taskno =? ,name=?, startdate=?, finishdate=?, duration=?, isSubTask=?, tasktimeconsumption=?,noOfPersons=?, workinghoursday=? WHERE idtask=?;";

            //2) prepare the query
            preparedStatement = conn.prepareStatement(sqlString);

            //Convert Localdate to mysql date
            java.sql.Date sqlStartDate = java.sql.Date.valueOf(task.getStartDate());
            java.sql.Date sqlFinishDate = java.sql.Date.valueOf(task.getFinishDate());

            //3) Bind the values to the parameteres
            preparedStatement.setInt(1, task.getProjectId());
            preparedStatement.setDouble(2, Math.round(task.getTaskNo()*100)/100d);
            preparedStatement.setString(3, task.getName());
            preparedStatement.setDate(4, sqlStartDate);
            preparedStatement.setDate(5, sqlFinishDate);
            preparedStatement.setInt(6, task.getDuration());
            preparedStatement.setString(7, task.getIsSubTask());
            preparedStatement.setInt(8, task.getTaskTimeconsumption());
            preparedStatement.setInt(9, task.getNoOfPersons());
            preparedStatement.setDouble(10, Math.round(task.getWorkingHoursDay()*100)/100d);
            preparedStatement.setInt(11, task.getIdtask());

            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }//Method


    public void updateTaskNos(String SqlUpdateString) throws SQLException {
        Connection conn=DatabaseConnector.getConnection();
        conn.setAutoCommit(false);

        try {
            //1) prepare the query
            Statement statement = conn.createStatement();
            statement.executeUpdate(SqlUpdateString);

            conn.commit();
        }
        catch (Exception e)
        {
            conn.rollback();
            System.err.println(e.getMessage());
        }
    }//Method


    public ArrayList<Task> getIdtasks_subTaskNo_FromTask(int projectId, double taskNo) {
        ArrayList<Task> list = new ArrayList<>();
        Task task;
        try {
            Connection con = DatabaseConnector.getConnection();

            String SQL = "SELECT idtask, taskno FROM task WHERE projectid=? AND taskno=?;";

            PreparedStatement ps = con.prepareStatement(SQL);

            ps.setInt(1, projectId);
            ps.setDouble(2, taskNo);


            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) //true så længe der er mere data i sql tabel
            {
                //laver et object med en resultat række


                task = new Task((Math.round((resultSet.getDouble("taskno")*100.00))/100.00d),
                        resultSet.getInt("idtask"));

                //7) fylder ArrayList med data
                //System.out.println("taskid: "+task.getIdtask());

                list.add(new Task(task.getTaskNo(),task.getIdtask()));
            }
        } catch (SQLException ex) {
            System.out.println("get_idtask_TaskNo: " + ex);
        }
//        for (Task a: list) {
//            System.out.println(a);
//
//        }

        return list;
    }


    public ArrayList<Task> get_idtasks_TaskNos(int projectId, double taskNo) {
        ArrayList<Task> list = new ArrayList<>();
        Task task;
        try {
            Connection con = DatabaseConnector.getConnection();

            String SQL = "SELECT idtask, taskno FROM task WHERE projectid=? AND taskno>=? and taskno<?+1;";

            PreparedStatement ps = con.prepareStatement(SQL);

            ps.setInt(1, projectId);
            ps.setDouble(2, taskNo);
            ps.setDouble(3, taskNo);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) //true så længe der er mere data i sql tabel
            {
                //laver et object med en resultat række


                task = new Task((Math.round((resultSet.getDouble("taskno")*100.00))/100.00d),
                        resultSet.getInt("idtask"));

                //7) fylder ArrayList med data
                //System.out.println("taskid: "+task.getIdtask());

                list.add(new Task(task.getTaskNo(),task.getIdtask()));
            }
        } catch (SQLException ex) {
            System.out.println("get_idtask_TaskNo: " + ex);
        }
//        for (Task a: list) {
//            System.out.println(a);
//
//        }

        return list;
    }


    public Task getTaskLine(int projectId, double taskNo) throws SQLException {
        Task taskLine = new Task();
        ResultSet resultSet = null; //dataflow 1 linie ad gangen
        PreparedStatement preparedStatement=null;
        System.out.println("fra mapper projectId: "+projectId);
        System.out.println("fra mapper taskNo: "+ taskNo);

        try {
            Connection conn = DatabaseConnector.getConnection();
            // 1) Connect to the database

            //2) Create a string that holds the query with ? as user input
            String sqlString = "SELECT * FROM alfasolutionsdb.task WHERE projectid=? AND taskno=?;";

            //3) prepare the query
            preparedStatement= conn.prepareStatement(sqlString);

            //4 Indsætter værdier i placeholders '?'
            preparedStatement.setInt(1, projectId);
            preparedStatement.setDouble(2, Math.round(taskNo*100)/100d);

            //5) excecuter sql statement
            resultSet = preparedStatement.executeQuery();

            //6) Fylder ArrayList med data fra mySQL database tabel "Person"

            while (resultSet.next()) //true så længe der er mere data i sql tabel
            {
                //laver et object med en resultat række
                taskLine = new Task(resultSet.getString("name"),
                                    resultSet.getDate("startdate").toLocalDate(),
                                    resultSet.getDate("finishdate").toLocalDate(),
                                    resultSet.getInt("duration"),
                                    resultSet.getInt("projectid"),
                                    resultSet.getString("isSubTask"),
                  Math.round(resultSet.getDouble("taskno")*100)/100d,
                                    0,
                        resultSet.getInt("idtask"),
                                    resultSet.getInt("tasktimeconsumption"),
                                    resultSet.getInt("noofpersons"),
                        Math.round(resultSet.getDouble("workinghoursday")*100)/100d,
                        "");
                        //resultSet.getString("subtasktoname"));
            }
        }//try
        catch (Exception e) {
            System.err.println(e.getMessage());

        }
        System.out.println("Task i mapper: " + taskLine);

        return taskLine;
    }//Method


    //TODO ER IKKE TJEKKET
    /**Modtager data fra addTask og indsætter i tabellen "task"
     *
     * @param newTask Task()
     * @throws SQLException
     */
    public void createNewTask(Task newTask) throws SQLException {
        Connection conn=DatabaseConnector.getConnection();

        PreparedStatement preparedStatement = null; //stored procedure

        try {
            //1) Create a string that holds the query with ? as user input
            String sqlString = "INSERT INTO task(projectid, taskno, name, startdate, finishdate, duration, isSubTask, tasktimeconsumption, noOfPersons, workinghoursday) VALUES(?,?,?,?,?,?,?,?,?,?);";

            //2) prepare the query
            preparedStatement = conn.prepareStatement(sqlString);

            //Convert Localdate to mysql date
            java.sql.Date sqlStartDate = java.sql.Date.valueOf(newTask.getStartDate());
            java.sql.Date sqlFinishDate = java.sql.Date.valueOf(newTask.getFinishDate());

            //3) Bind the values to the parameteres
            preparedStatement.setInt(1, newTask.getProjectId());
            preparedStatement.setDouble(2, Math.round(newTask.getTaskNo()*100)/100d);
            preparedStatement.setString(3, newTask.getName());
            preparedStatement.setDate(4, sqlStartDate);
            preparedStatement.setDate(5, sqlFinishDate);
            preparedStatement.setInt(6, newTask.getDuration());
            preparedStatement.setString(7, newTask.getIsSubTask());
            preparedStatement.setInt(8, newTask.getTaskTimeconsumption());
            preparedStatement.setInt(9, newTask.getNoOfPersons());
            preparedStatement.setDouble(10, Math.round(newTask.getWorkingHoursDay()*10)/10d);

            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }//Method


    /**Gets names from all Tasks belonging to projectId
     *
     * @param projectId
     * @return
     * @throws SQLException
     */
    public List<Task> getTasksForAddTaskDropbox(int projectId) throws SQLException  {
        Task task=new Task("No overtask");


        Connection conn = null; //forbindelse
        ResultSet resultSet = null; //dataflow 1 linie ad gangen
        List<Task> arrDaters = new ArrayList<>();
        PreparedStatement preparedStatement=null;


        try {
            // 1) Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://den1.mysql3.gear.host/alfasolutionsdb?autoReconnect=true&useSSL=false", "alfasolutionsdb", "Ga9Q_!89hJab");

            //2) Create a string that holds the query with ? as user input
            String sqlString =  "SELECT task.name FROM alfasolutionsdb.task\n" +
                                "WHERE projectid=? AND issubtask = 'no'\n" +
                                "ORDER BY name;";

            //3) prepare the query
            preparedStatement= conn.prepareStatement(sqlString);

            //4 Indsætter værdier i placeholders '?'
            preparedStatement.setInt(1, projectId);

            //5) excecuter sql statement
            resultSet = preparedStatement.executeQuery();

            //6) Fylder ArrayList med data fra mySQL database tabel "Person"

            //Inserts first default choise for dropbox
            arrDaters.add(task);

            //inserts values for return array
            while (resultSet.next()) //true så længe der er mere data i sql tabel
            {
                //laver et object med en resultat række
                task = new Task(resultSet.getString("name"));

                //7) fylder ArrayList med data
                arrDaters.add(task);
            }
            //return arrResultList;
        }//try
        catch (Exception e) {
            System.err.println(e.getMessage());

        } finally //kode der skal køres selvom den bugger (lukke connection)
        {
            if (preparedStatement != null)
                preparedStatement.close();

            if (conn != null)
                conn.close();
        }
        //TEST PRINT
        //for(Task a : arrDaters ) { System.out.println(a.getName()); }

        return arrDaters;
    }//Method


    //TODO ER IKKE TESTET efter connector er rettet
    public List<Task> getTaskForEditProject(int projectId) throws SQLException {
        Task task1=null;
        Connection conn = DatabaseConnector.getConnection();


//        Connection conn = null; //forbindelse
        ResultSet resultSet = null; //dataflow 1 linie ad gangen
        List<Task> listTasks = new ArrayList<>();
        PreparedStatement preparedStatement=null;


        try {
            // 1) Connect to the database

//            conn = DriverManager.getConnection("jdbc:mysql://den1.mysql3.gear.host/alfasolutionsdb?autoReconnect=true&useSSL=false", "alfasolutionsdb", "Ga9Q_!89hJab");

            //2) Create a string that holds the query with ? as user input
            String sqlString = "SELECT * FROM alfasolutionsdb.task WHERE projectid=? ORDER BY taskno;";

            //3) prepare the query
            preparedStatement= conn.prepareStatement(sqlString);

            //4 Indsætter værdier i placeholders '?'
            preparedStatement.setInt(1, projectId);

            //5) excecuter sql statement
            resultSet = preparedStatement.executeQuery();

            //6) Fylder ArrayList med data fra mySQL database tabel "Person"
            int lineCounter=0;
            while (resultSet.next()) //true så længe der er mere data i sql tabel
            {
                ++lineCounter; //yderste kolonne i tabel 1,2,3 ect i edit_project
                //laver et object med en resultat række
                task1 = new Task(resultSet.getString("name"),
                        resultSet.getDate("startdate").toLocalDate(),
                        resultSet.getDate("finishdate").toLocalDate(),
                        resultSet.getInt("duration"),
                        resultSet.getInt("projectid"),
                        resultSet.getString("isSubTask"),
                        Math.round(resultSet.getDouble("taskno")*100.00)/100.00d,
                        lineCounter,
                        resultSet.getInt("idtask"),
                        resultSet.getInt("tasktimeconsumption"),
                        resultSet.getInt("noOfPersons"),
                        Math.round(resultSet.getDouble("workinghoursday")*100.00)/100.00d,
                        "");
                        //resultSet.getString("subtasktoname"));

                //7) fylder ArrayList med data
                listTasks.add(task1);
            }
            //return arrResultList;
        }//try
        catch (Exception e) {
            System.err.println(e.getMessage());

        }
//        finally //kode der skal køres selvom den bugger (lukke connection)
//        {
//            if (preparedStatement != null)
//                preparedStatement.close();
//
//            if (conn != null)
//                conn.close();
//        }
        //TEST PRINT
        //for (Task a : listTasks ) { System.out.println(a.getName() +" taskno: "+a.getTaskNo() + "working hours day" + a.getWorkingHoursDay()) ; }

        return listTasks;
    }//Method
}
