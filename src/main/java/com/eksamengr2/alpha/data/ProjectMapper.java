package com.eksamengr2.alpha.data;


import com.eksamengr2.alpha.model.Project;

import java.sql.*;

public class ProjectMapper {

        public void createProject(Project project) throws Exception {
            try {
                Connection con = DatabaseConnector.getConnection();
                String SQL = "INSERT INTO Project (name, ownername, startdate) VALUES (?, ?, ?)";

//                java.sql.Date sqlStartDate = java.sql.Date.valueOf(project.getStartDate());
                Date sqlStartDate = Date.valueOf(project.getStartDate());

                PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, project.getProjectName());
                ps.setString(2, project.getOwnerName());
                ps.setDate(3,sqlStartDate);
                ps.executeUpdate();
//                ResultSet ids = ps.getGeneratedKeys();
//                ids.next();
//                int id = ids.getInt(1);
//                project.setId(id);
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
}
