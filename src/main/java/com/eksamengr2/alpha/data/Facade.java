package com.eksamengr2.alpha.data;

import com.eksamengr2.alpha.model.Task;

import java.sql.SQLException;
import java.util.List;

public class Facade implements FacadeTest {

    private EditProjectMapper editProjectMapper = new EditProjectMapper();


    public List<Task> getTaskForEditProject(int projectId) throws SQLException{
        return editProjectMapper.getTaskWithCounter(projectId);
    }


}
