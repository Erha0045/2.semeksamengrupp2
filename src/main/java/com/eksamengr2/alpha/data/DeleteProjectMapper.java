package com.eksamengr2.alpha.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeleteProjectMapper {
//    String SQL = "SELECT * FROM project\n" +
//            "INNER JOIN task\n" +
//            "ON projectid = idproject\n" +
//            "where idproject=?;";

    public int deleteProjectFromDB(int projectNumber) throws Exception {

        Connection con = DatabaseConnector.getConnection();
//        SET SQL_SAFE_UPDATES = 0;
//        DELETE project,task
//        FROM project
//        INNER JOIN task ON project.idproject = task.projectid
//        WHERE idproject=18;

        //deleter ikke pt, nu selecter vi bare, "det" som ska deletes.
        String SQL = "DELETE project, task\n" +
        "FROM project\n" +
        "INNER JOIN task ON project.idproject = task.projectid\n" +
        "WHERE idproject = ?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1, projectNumber);


        //hvis executeUpdate returnere 0, er intet ændret, altså den kan ikke finde en task med nummeret.
       int numberOfRowCounts = ps.executeUpdate();
//        ResultSet resultSet = ps.executeQuery();
//
//        int rc = 0;
//        while (resultSet.next()) {
//            rc++;
//        }
//        System.out.println(rc);

        return numberOfRowCounts;
    }
}