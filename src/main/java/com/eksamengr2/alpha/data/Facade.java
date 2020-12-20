package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.model.User;
import com.eksamengr2.alpha.service.TaskHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Facade{          //implements FacadeTest {

    private EditProjectMapper editProjectMapper = new EditProjectMapper();
    private TaskHandler taskHandler = new TaskHandler();
    private Project project = new Project();

    public  List<Project> getProjectByUser(String userName){
        return getProjectByUser(userName);
    }

    public String errorMessageCreateProject(Project project1, User user){
        return errorMessageCreateProject(project1, user);
    }

    public Task AddTaskToDB(Task task){
        return AddTaskToDB(task);
    }

    public Task getTask(double overTaskNo, int projectId){
        return getTask(overTaskNo, projectId);
    }


    public Project getProjectFromId(int projectId){
        return getProjectFromId(projectId);
    }

    public ArrayList<Project> hoursPerDayCalculation(int projectId){
        return hoursPerDayCalculation(projectId);
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
}
