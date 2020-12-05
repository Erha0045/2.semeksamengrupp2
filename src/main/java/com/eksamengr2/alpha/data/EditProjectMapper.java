package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditProjectMapper {

    public void updateTask(ArrayList<Task> taskArrayList) throws SQLException {
        Connection conn=DatabaseConnector.getConnection();

        PreparedStatement preparedStatement = null; //stored procedure

        try {
            //1) Create a string that holds the query with ? as user input
            String sqlString = "UPDATE alfasolutionsdb.task SET projectid=? ,taskno =? ,name=?, startdate=?, finishdate=?, duration=?, isSubTask=? WHERE idtask=?;";

            //2) prepare the query
            preparedStatement = conn.prepareStatement(sqlString);

            //Convert Localdate to mysql date
            java.sql.Date sqlStartDate = java.sql.Date.valueOf(taskArrayList.get(0).getStartDate());
            java.sql.Date sqlFinishDate = java.sql.Date.valueOf(taskArrayList.get(0).getFinishDate());

            //3) Bind the values to the parameteres
            preparedStatement.setString(3, taskArrayList.get(0).getName());
            preparedStatement.setDate(4, sqlStartDate);
            preparedStatement.setDate(5, sqlFinishDate);
            preparedStatement.setInt(6, taskArrayList.get(0).getDuration());
            preparedStatement.setInt(1, taskArrayList.get(0).getProjectId());
            preparedStatement.setString(7, taskArrayList.get(0).getIsSubTask());
            preparedStatement.setDouble(2, taskArrayList.get(0).getTaskNo());
            preparedStatement.setInt(8, taskArrayList.get(0).getIdtask());

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


                task = new Task(Math.round((resultSet.getFloat("taskno")*100.0)/100.0),
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


                task = new Task(Math.round((resultSet.getDouble("taskno")*100.0)/100.0),
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

    public ArrayList<Task> getTaskLine(int projectId, double taskNo) throws SQLException {
        Task search1=null;
        Task task = new Task();

        Connection conn = null; //forbindelse
        ResultSet resultSet = null; //dataflow 1 linie ad gangen
        ArrayList<Task> arrDaters = new ArrayList<>();
        PreparedStatement preparedStatement=null;


        try {
            // 1) Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://den1.mysql3.gear.host/alfasolutionsdb?autoReconnect=true&useSSL=false", "alfasolutionsdb", "Ga9Q_!89hJab");

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
            int lineCounter=0;
            while (resultSet.next()) //true så længe der er mere data i sql tabel
            {
                ++lineCounter; //yderste kolonne i tabel 1,2,3 ect i edit_project
                //laver et object med en resultat række
                search1 = new Task(resultSet.getString("name"),
                        resultSet.getDate("startdate").toLocalDate(),
                        resultSet.getDate("finishdate").toLocalDate(),
                        resultSet.getInt("duration"),
                        resultSet.getInt("projectid"),
                        resultSet.getString("isSubTask"),
                        Math.round((resultSet.getDouble("taskno")*100)/100d),
                        lineCounter);

                //7) fylder ArrayList med data
                arrDaters.add(search1);
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
        for (Task a : arrDaters ) { System.out.println(a.getName() +" taskno: "+a.getTaskNo()) ; }

        return arrDaters;
    }//Method

    /**Modtager data fra addTask og indsætter i tabellen "task"
     *
     * @param arr ArrayList udfyld af UI
     * @throws SQLException
     */
    public void insertNewTaskIn_TaskTabel(ArrayList<Task> arr) throws SQLException {
            Connection conn=DatabaseConnector.getConnection();

            PreparedStatement preparedStatement = null; //stored procedure

            try {
            //1) Create a string that holds the query with ? as user input
            String sqlString = "INSERT INTO task(projectid, taskno, name, startdate, finishdate, duration, isSubTask) VALUES(?,?,?,?,?,?,?);";

            //2) prepare the query
            preparedStatement = conn.prepareStatement(sqlString);

            //Convert Localdate to mysql date
            java.sql.Date sqlStartDate = java.sql.Date.valueOf(arr.get(0).getStartDate());
            java.sql.Date sqlFinishDate = java.sql.Date.valueOf(arr.get(0).getFinishDate());

            //3) Bind the values to the parameteres
            preparedStatement.setString(3, arr.get(0).getName());
            preparedStatement.setDate(4, sqlStartDate);
            preparedStatement.setDate(5, sqlFinishDate);
            preparedStatement.setInt(6, arr.get(0).getDuration());
            preparedStatement.setInt(1, arr.get(0).getProjectId());
            preparedStatement.setString(7, arr.get(0).getIsSubTask());
            preparedStatement.setDouble(2, arr.get(0).getTaskNo());

            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
//        finally //kode der skal køres selvom den bugger (lukke connection)
//        {
//            if (preparedStatement !=null )
//                preparedStatement.close();
//
//            if (conn !=null)
//                conn.close();
//        }
    }//Method

    //TODO har to med samme

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

    //TODO har to mapper med samme formål
    public List<Task> getTaskForEditProject(int projectId) throws SQLException {
        Task search1=null;
        Task task = new Task();

        Connection conn = null; //forbindelse
        ResultSet resultSet = null; //dataflow 1 linie ad gangen
        List<Task> arrDaters = new ArrayList<>();
        PreparedStatement preparedStatement=null;


        try {
            // 1) Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://den1.mysql3.gear.host/alfasolutionsdb?autoReconnect=true&useSSL=false", "alfasolutionsdb", "Ga9Q_!89hJab");

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
                search1 = new Task(resultSet.getString("name"),
                        resultSet.getDate("startdate").toLocalDate(),
                        resultSet.getDate("finishdate").toLocalDate(),
                        resultSet.getInt("duration"),
                        resultSet.getInt("projectid"),
                        resultSet.getString("isSubTask"),
                        resultSet.getFloat("taskno"),
                        lineCounter);

                //7) fylder ArrayList med data
                arrDaters.add(search1);
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
        //for (Task a : arrDaters ) { System.out.println(a.getName() +" taskno: "+a.getTaskNo()) ; }

        return arrDaters;
    }//Method
}
