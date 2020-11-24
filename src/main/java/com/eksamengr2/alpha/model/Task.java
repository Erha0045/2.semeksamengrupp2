package com.eksamengr2.alpha.model;

import java.time.LocalDate;

public class Task {
    private String name;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int duration;
    private int projectId;

    public Task(String name, LocalDate startDate, LocalDate finishDate, int duration, int projectId) {
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.duration = duration;
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", duration=" + duration +
                ", projectId=" + projectId +
                '}';
    }
}
