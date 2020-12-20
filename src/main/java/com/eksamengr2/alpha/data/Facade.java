package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.model.User;
import com.eksamengr2.alpha.service.ProjectHandler;
import com.eksamengr2.alpha.service.TaskHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Facade{          //implements FacadeTest {

    private EditProjectMapper editProjectMapper = new EditProjectMapper();
    private TaskHandler taskHandler = new TaskHandler();
    private Project project = new Project();
    private DashboardMapper dashboardMapper = new DashboardMapper();
    private ProjectHandler projectHandler = new ProjectHandler();
    private TaskHandler taskController = new TaskHandler();
    private TaskMapper taskMapper = new TaskMapper();
    private ProjectMapper projectMapper = new ProjectMapper();
    private UserMapper userMapper = new UserMapper();

    public  List<Project> getProjectByUser(String userName) throws Exception {
        return dashboardMapper.getProjectByUser(userName);
    }

    public String errorMessageCreateProject(Project project1, User user) throws SQLException {
        return projectHandler.errorMessageCreateProject(project1, user);
    }

    public Task AddTaskToDB(Task task) throws SQLException {
        return taskController.AddTaskToDB(task);
    }

    public Task getTask(double overTaskNo, int projectId) throws SQLException {
        return taskMapper.getTask(overTaskNo, projectId);
    }


    public Project getProjectFromId(int projectId) throws  SQLException{
        return projectMapper.getProjectFromId(projectId);
    }

    public ArrayList<Project> hoursPerDayCalculation(int projectId) throws SQLException {
        return taskHandler.hoursPerDayCalculation(projectId);
    }

    public List<Task> getTasksForAddTaskDropbox(int projectId) throws SQLException {
        return editProjectMapper.getTasksForAddTaskDropbox(projectId);
    }

    public List<Task> getTaskForEditProject(int projectId) throws SQLException{
        return editProjectMapper.getTaskWithCounter(projectId);
    }

    public ArrayList<Task> viewForEditProject(int projectId) throws SQLException {
        return taskHandler.viewForEditProject(projectId);
    }

    public Task getTaskLine(int projectId,double transferTaskNo) throws SQLException {
        return editProjectMapper.getTaskLine(projectId, transferTaskNo);
    }

    public User login(String username, String password) throws Throwable {
        return userMapper.login(username, password);
    }
}
