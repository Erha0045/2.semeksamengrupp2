package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OverviewMapper {

    public static ArrayList<Project> getProjectsFromDB() throws Exception {
        ResultSet rs = null;

        ArrayList<Project> arrProjects = new ArrayList<>();
        Project project = new Project();
        try {
            Connection con = DatabaseConnector.getConnection();
            String SQL = "SELECT * FROM project  ";
            PreparedStatement ps = con.prepareStatement(SQL);
            //5) excecuter sql statement
            rs = ps.executeQuery();

            while (rs.next()) //true så længe der er mere data i sql tabel
            {
                //laver et object med en resultat række
                project = new Project(
                        rs.getInt("idproject"),
                        rs.getString("name"),
                        rs.getString("ownername"),
                        rs.getDate("startdate").toLocalDate());

                //7) fylder ArrayList med data
                arrProjects.add(project);
            }


        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    return arrProjects;
    }
}

