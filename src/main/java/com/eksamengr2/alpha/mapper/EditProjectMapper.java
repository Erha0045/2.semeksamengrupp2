package com.eksamengr2.alpha.mapper;

import com.eksamengr2.alpha.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditProjectMapper {

    //Deklarationer
    public List<Task> getTaskForEditProject() throws SQLException {
        Task search1=null;

        Connection conn = null; //forbindelse
        ResultSet resultSet = null; //dataflow 1 linie ad gangen
        List<Task> arrDaters = new ArrayList<>();
        PreparedStatement preparedStatement=null;


        try {
            // 1) Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://den1.mysql1.gear.host:3306/alfasolutionsdb?autoReconnect=true&useSSL=false", "alfasolutionsdb", "Ga9Q_!89hJab");

            //2) Create a string that holds the query with ? as user input
            String sqlString = "SELECT * FROM task WHERE projectid=? ";

            //3) prepare the query
            preparedStatement= conn.prepareStatement(sqlString);

            //4 Indsætter værdier i placeholders '?'
            preparedStatement.setInt(1, 1); //TEST

            //5) excecuter sql statement
            resultSet = preparedStatement.executeQuery();

            //6) Fylder ArrayList med data fra mySQL database tabel "Person"
            while (resultSet.next()) //true så længe der er mere data i sql tabel
            {
                //BEAN Daters(String userName, LocalDate birthday, String profileText, int gender, String region, String pictureLink)
                //laver et object med en resultat række
                search1 = new Task(resultSet.getString("name"),
                        resultSet.getDate("startdate").toLocalDate(),
                        resultSet.getDate("finishdate").toLocalDate(),
                        resultSet.getInt("duration"),
                        resultSet.getInt("projectid"),
                        resultSet.getString("isSubTask"));

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
        for (Task a : arrDaters ) { System.out.println(a.getName() +" : "+ a.getProjectId()) ; }

        return arrDaters;
    }//metode
}