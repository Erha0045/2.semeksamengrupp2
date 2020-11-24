package com.eksamengr2.alpha.model;

import java.time.LocalDate;

public class SubTask {

    private String name;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int duration;
    private int projectId;
    private int taskId;

    public SubTask(String name, LocalDate startDate, LocalDate finishDate, int duration, int projectId, int taskId) {
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.duration = duration;
        this.projectId = projectId;
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", duration=" + duration +
                ", projectId=" + projectId +
                ", taskId=" + taskId +
                '}';
    }
}
