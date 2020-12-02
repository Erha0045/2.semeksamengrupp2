package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.LoginSampleException;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OverviewMapper {

//    public static ArrayList<Project> getProjectsFromDB() throws Exception {
//        ResultSet rs = null;
//
//        ArrayList<Project> arrProjects = new ArrayList<>();
//        Project project = new Project();
//        try {
//            Connection con = DatabaseConnector.getConnection();
//            String SQL = "SELECT * FROM project  ";
//            PreparedStatement ps = con.prepareStatement(SQL);
//            //5) excecuter sql statement
//            rs = ps.executeQuery();
//
//            while (rs.next()) //true så længe der er mere data i sql tabel
//            {
//                //laver et object med en resultat række
//                project = new Project(
//                        rs.getInt("idproject"),
//                        rs.getString("name"),
//                        rs.getString("ownername"),
//                        rs.getDate("startdate").toLocalDate());
//
//                //7) fylder ArrayList med data
//                arrProjects.add(project);
//            }
//
//
//        } catch (SQLException ex) {
//            throw new Exception(ex.getMessage());
//        }
//    return arrProjects;
//    }

//    public User fetchUserToRetrieveProject(String username) throws LoginSampleException {
//        try {
//            Connection con = DatabaseConnector.getConnection();
//            String SQL = "SELECT username=? FROM userinfo ";
//
//            PreparedStatement ps = con.prepareStatement(SQL);
//            ps.setString(1, username);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                User user = new User(username);
//                return user;
//            } else {
//                throw new LoginSampleException("Could not validate user");
//            }
//        } catch (SQLException ex) {
//            throw new LoginSampleException(ex.getMessage());
//        }
//    }

    public List<Project> getProjectByUser(String ownerName) throws Exception {
        Project search = null;
        Project project = new Project();
        List<Project> projectList = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement ps=null;
        try {
            Connection con = DatabaseConnector.getConnection();
            String SQL = "SELECT * FROM project "
                    + "WHERE ownername=?";
            ps = con.prepareStatement(SQL);
            ps.setString(1, ownerName);

            rs = ps.executeQuery();
            while (rs.next()) {
//                Project mapperProject = new Project();
                search = new Project(rs.getInt("idproject"),
                        rs.getString("projectname"),
                        rs.getString ("ownername"),
                        rs.getDate("startdate").toLocalDate());


                //7) fylder ArrayList med data
                projectList.add(search);
//
//            } else {
//                throw new Exception("Could not finde project");
           }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());

        } finally //kode der skal køres selvom den bugger (lukke connection)
        {
            if (ps != null)
                ps.close();


        }
        return projectList;
    }
}



