package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardMapper {//(MS)

    /**Gets prjects data by ownername
     *
     * @param ownerName
     * @return
     * @throws Exception
     */
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
                search = new Project(
                        rs.getInt("idproject"),
                        rs.getString("projectname"),
                        rs.getString ("ownername"),
                        rs.getDate("startdate").toLocalDate(),
                        rs.getDate("deadlinedate").toLocalDate());

                //7) fylder ArrayList med data
                projectList.add(search);
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



