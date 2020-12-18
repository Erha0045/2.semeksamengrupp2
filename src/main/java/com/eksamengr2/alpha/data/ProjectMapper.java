package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.User;
import java.sql.*;

public class ProjectMapper {

        /**Data inserted for new project
         *
         * @param project
         * @throws Exception
         */
        public void createProject(Project project) throws Exception {
            try {
                Connection con = DatabaseConnector.getConnection();
                String SQL = "INSERT INTO Project (projectname, ownername, startdate, deadlinedate) VALUES (?, ?, ?, ?)";

                Date sqlStartDate = Date.valueOf(project.getStartDate());
                Date sqlDeadlineDate = Date.valueOf(project.getDeadlineDate());


                PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, project.getProjectName());
                ps.setString(2, project.getOwnerName());
                ps.setDate(3,sqlStartDate);
                ps.setDate(4,sqlDeadlineDate);

                ps.executeUpdate();
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }


        /**Gets project metadata by ownername
     *
     * @param user
     * @return
     * @throws SQLException
     */
        public ResultSet getProjectNames(User user) throws SQLException {
            Connection con = DatabaseConnector.getConnection();

            String SQL = "SELECT * FROM project "
                    + "WHERE ownername=?";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserName());

            ResultSet resultSet = ps.executeQuery();
            return resultSet;

        }


        /**Gets project by projectId
         *
         * @param projectid
         * @return
         * @throws SQLException
         */
        public Project getProjectFromId(int projectid)  {
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


        /**Get task data by projectId
     *
     * @param projectid
     * @return
     * @throws SQLException
     */
        public int gettaskForProject(int projectid) throws SQLException {
            Connection con = DatabaseConnector.getConnection();
            String SQL = "select * from task where projectid=?";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, projectid);

            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                return 1;
            }else return 0;
        }

}
