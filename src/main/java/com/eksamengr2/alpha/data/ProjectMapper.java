package com.eksamengr2.alpha.data;


import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.User;

import java.sql.*;
import java.util.Set;

public class ProjectMapper {

        public void createProject(Project project) throws Exception {
            try {
                Connection con = DatabaseConnector.getConnection();
                String SQL = "INSERT INTO Project (projectname, ownername, startdate, deadlinedate) VALUES (?, ?, ?, ?)";

//                java.sql.Date sqlStartDate = java.sql.Date.valueOf(project.getStartDate());
                Date sqlStartDate = Date.valueOf(project.getStartDate());
                Date sqlDeadlineDate = Date.valueOf(project.getDeadlineDate());


                PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, project.getProjectName());
                ps.setString(2, project.getOwnerName());
                ps.setDate(3,sqlStartDate);
                ps.setDate(4,sqlDeadlineDate);

                ps.executeUpdate();
//                ResultSet ids = ps.getGeneratedKeys();
//                ids.next();
//                int id = ids.getInt(1);
//                project.setId(id);
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }

        public ResultSet getProjectNames(User user) throws SQLException {
            Connection con = DatabaseConnector.getConnection();

            String SQL = "SELECT * FROM project "
                    + "WHERE ownername=?";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserName());

            ResultSet resultSet = ps.executeQuery();
            return resultSet;

        }

        public Project getProjectFromId(int projectid) throws SQLException {
            Project project = null;
            try {
                Connection con = DatabaseConnector.getConnection();
                String SQL = "SELECT * FROM project "
                        + "WHERE idproject=?";
                PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, projectid);

                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                     project = new Project(projectid,
                            resultSet.getString("projectname"),
                            resultSet.getString("ownername"),
                            resultSet.getDate("startdate").toLocalDate(),
                            resultSet.getDate("deadlinedate").toLocalDate());
                    return project;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return project;
        }

}
