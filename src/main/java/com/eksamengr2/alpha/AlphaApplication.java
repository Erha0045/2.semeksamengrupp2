package com.eksamengr2.alpha;

import com.eksamengr2.alpha.data.ProjectMapper;
import com.eksamengr2.alpha.data.RegistrationsMapper;
import com.eksamengr2.alpha.model.Project;
import com.eksamengr2.alpha.model.Task;
import com.eksamengr2.alpha.model.User;
import com.eksamengr2.alpha.service.TaskHandler1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class AlphaApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AlphaApplication.class, args);

    TaskHandler1 taskHandler1 = new TaskHandler1();
        Task old = new Task("Petersen", LocalDate.of(2020,12,02) ,
        LocalDate.of(2020,12,04), 3,
        1, "yes", 1.35,
        100, 5, 7.25, "KØKKEN");

        Task mod = new Task("", null,
                LocalDate.of(2020,12,04), 0,
                0, "", 0.0,
                0, 0, 0.0, "");



    taskHandler1.UserInput_FromEditTask_UpdateTaskInDB2(mod, old);

    }


}
