package com.eksamengr2.alpha.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Task {
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishDate;
    private int duration;
    private int projectId;
    private String isSubTask;
    private float taskNo;
    private int lineCounter;
    private String NewTaskName;

    public Task(String name, LocalDate startDate, LocalDate finishDate, int duration, int projectId, String isSubTask, float taskNo, int lineCounter, String NewTaskName) {
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.duration = duration;
        this.projectId = projectId;
        this.isSubTask = isSubTask;
        this.taskNo = taskNo;
        this.lineCounter = lineCounter;
        this.NewTaskName = NewTaskName;
    }

    //bruges til måske edit_project
    public Task(String name, LocalDate startDate, LocalDate finishDate, int duration, int projectId, String isSubTask, float taskNo, int lineCounter) {
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.duration = duration;
        this.projectId = projectId;
        this.isSubTask = isSubTask;
        this.taskNo = taskNo;
        this.lineCounter=lineCounter;
    }

    public Task(String name) {
        this.name = name;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", duration=" + duration +
                ", projectId=" + projectId +
                ", isSubTask='" + isSubTask + '\'' +
                ", taskNo=" + taskNo +
                ", lineCounter=" + lineCounter +
                ", NewTaskName='" + NewTaskName + '\'' +
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

    public float getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(float taskNo) {
        this.taskNo = taskNo;
    }

    public int getLineCounter() {
        return lineCounter;
    }

    public String getNewTaskName() {
        return NewTaskName;
    }

    public void setNewTaskName(String newTaskName) {
        NewTaskName = newTaskName;
    }
}
