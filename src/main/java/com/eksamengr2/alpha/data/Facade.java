package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.model.User;
import com.eksamengr2.alpha.service.ProjectHandler;
import com.eksamengr2.alpha.service.TaskHandler;
import com.eksamengr2.alpha.service.UserHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Facade{//(TL, MS, EB, TM)

    private EditProjectMapper editProjectMapper = new EditProjectMapper();
    private TaskHandler taskHandler = new TaskHandler();
    private Project project = new Project();
    private DashboardMapper dashboardMapper = new DashboardMapper();
    private ProjectHandler projectHandler = new ProjectHandler();
    private TaskHandler taskController = new TaskHandler();
    private TaskMapper taskMapper = new TaskMapper();
    private ProjectMapper projectMapper = new ProjectMapper();
    private UserMapper userMapper = new UserMapper();
    private DeleteProjectMapper deleteProjectMapper = new DeleteProjectMapper();
    private UserHandler userHandler = new UserHandler();

    public String createUserError(String userName, String password1, String password2) throws SQLException {
        return userHandler.createUserError(userName, password1, password2);
    }

    public int deleteProjectFromDB(int projectId) throws Exception {
        return deleteProjectMapper.deleteProjectFromDB(projectId);
    }

    public int deleteProjectFromDBNoSubTasks(int projectId) throws SQLException {
        return deleteProjectMapper.deleteProjectFromDBNoSubTasks(projectId);
    }

    public int gettaskForProject(int projectId) throws SQLException {
        return projectMapper.gettaskForProject(projectId);
    }

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

//    public List<Task> getTaskForEditProject(int projectId) throws SQLException{
//        return editProjectMapper.getTaskWithCounter(projectId); //TODO IKKE ENS
//    }

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
