package com.eksamengr2.alpha.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteProjectMapper {

    /**Deletes project from DB
     *
     * @param projectNumber
     * @return
     * @throws Exception
     */
    public int deleteProjectFromDB(int projectNumber) throws Exception {

        Connection con = DatabaseConnector.getConnection();


        String SQL = "DELETE project, task \n" +
                "        FROM project\n" +
                "        INNER JOIN task ON project.idproject = task.projectid\n" +
                "        WHERE idproject = ?;";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1, projectNumber);


        //hvis executeUpdate returnere 0, er intet ændret, altså den kan ikke finde en task med nummeret.
       int numberOfRowCounts = ps.executeUpdate();

        return numberOfRowCounts;
    }

    public int deleteProjectFromDBNoSubTasks(int projectNumber) throws SQLException {

        Connection con = DatabaseConnector.getConnection();

        String SQL ="delete project from project where idproject=?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1, projectNumber);

        int numberOfRowCounts = ps.executeUpdate();

        return numberOfRowCounts;
    }

}