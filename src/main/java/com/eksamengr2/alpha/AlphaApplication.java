package com.eksamengr2.alpha;

import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.service.TaskHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class AlphaApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AlphaApplication.class, args);

//        TaskHandler taskHandler = new TaskHandler();
//        ArrayList<Task> arr = new ArrayList<>();
//        arr.add(new Task(LocalDate.of(2020,01,01), LocalDate.of(2020,01,8),1.0 ));
//        arr.add(new Task(LocalDate.of(2020,01,02), LocalDate.of(2020,01,5),2.0 ));
//
//        taskHandler.createFullSubTaskList(arr);



//        Project project = new Project(LocalDate.of(2020,1,1),LocalDate.of(2020,1,8),0.0);
//
//        taskHandler.createMasterList(project);

    }


}
