package com.eksamengr2.alpha.model;

import java.time.LocalDate;

public class Task {
    private String name;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int duration;
    private int projectId;
    private String isSubTask;

    public Task(String name, LocalDate startDate, LocalDate finishDate, int duration, int projectId, String isSubTask) {
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.duration = duration;
        this.projectId = projectId;
        this.isSubTask = isSubTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", duration=" + duration +
                ", projectId=" + projectId +
                ", isSubTask=" + isSubTask +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getIsSubTask() {
        return isSubTask;
    }

    public void setIsSubTask(String isSubTask) {
        this.isSubTask = isSubTask;
    }
}
