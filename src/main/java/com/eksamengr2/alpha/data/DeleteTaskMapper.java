package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//luff
public class DeleteTaskMapper {
    //TODO addere project, så den faktisk kun sletter, de tasks fra "current project" hvordan? ska det bare skrives ind,
    //todo som tal på samme måde som task no?
    //project id hardcodet til 1
    int projectID = 1;
    // virker med project id, skal bare ah et projectID fra et eller andet sted.
    public int deleteTaskFromDB(Double taskNo) throws Exception {

        if (taskNo % 1 == 0.0) {

            Connection con = DatabaseConnector.getConnection();
//            "DELETE FROM testtask WHERE taskno >= ? AND taskno < ?";
            // "DELETE  FROM task WHERE taskno >= ? AND taskno < ? and projectid= ?";

            String SQL = "DELETE  FROM testtask WHERE taskno >= ? AND taskno < ? and projectid= ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setDouble(1, taskNo);
            ps.setDouble(2, taskNo + 1);
            ps.setInt(3,projectID);

            //hvis executeUpdate returnere 0, er intet ændret, altså den kan ikke finde en task med nummeret.
            int numberOfUpdatedRows = ps.executeUpdate();
            System.out.println(numberOfUpdatedRows);
            // con.close();
            return numberOfUpdatedRows;
        } else {
//            "DELETE FROM testtask WHERE taskno = ?";
            //"DELETE FROM task WHERE taskno=? and projectID=?"
            Connection con = DatabaseConnector.getConnection();
            String SQL = "DELETE FROM testtask WHERE taskno=? and projectid=?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setDouble(1, taskNo);
            ps.setInt(2,projectID);
            int numberOfUpdatedRows = ps.executeUpdate();
            System.out.println(numberOfUpdatedRows);
            // con.close();
            return numberOfUpdatedRows;
        }
    }

    //hvis det skal være object orienteret?

    public void deleteTaskFromDB(Task task) throws Exception {

        try {
            Connection con = DatabaseConnector.getConnection();

            if (task.isSubTask()) {
                String SQL = "DELETE FROM testtask WHERE taskno = ?";
                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setDouble(1, task.getTaskNo());
                ps.executeUpdate();
            } else {
                String SQL = "DELETE FROM testtask WHERE taskno >= ? AND taskno < ?";
                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setDouble(1, task.getTaskNo());
                ps.setDouble(2, task.getTaskNo() + 1);
                ps.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}





