package com.eksamengr2.alpha.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Project {

    private int projectId ;
    private String projectName;
    private String ownerName;
    @DateTimeFormat(pattern = "yyyy-MM-dd") //for at matche formattering fra bruger flade
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd") //for at matche formattering fra bruger flade
    private LocalDate deadlineDate;
    private double timeProject;

    public Project()  {
    }

    public Project(LocalDate startDate, LocalDate deadlineDate, double timeProject) {
        this.startDate = startDate;
        this.deadlineDate = deadlineDate;
        this.timeProject = timeProject;
    }

    public Project(int projectId){
        this.projectId=projectId;
    }

    public Project(String projectName, String ownerName, LocalDate startDate) {
        this.projectName = projectName;
        this.ownerName = ownerName;
        this.startDate = startDate;
    }

    public Project(int projectId, String projectName, String ownerName, LocalDate startDate) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.ownerName = ownerName;
        this.startDate = startDate;
    }

    public Project(String projectName, LocalDate startDate, LocalDate deadlineDate) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.deadlineDate = deadlineDate;
    }

    public Project(String projectName, String ownerName, LocalDate startDate, LocalDate deadlineDate) {
        this.projectName = projectName;
        this.ownerName = ownerName;
        this.startDate = startDate;
        this.deadlineDate = deadlineDate;
    }

    public Project(int projectId, String projectName, String ownerName, LocalDate startDate, LocalDate deadlineDate) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.ownerName = ownerName;
        this.startDate = startDate;
        this.deadlineDate = deadlineDate;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public double getTimeProject() {
        return timeProject;
    }

    public void setTimeProject(double timeProject) {
        this.timeProject = timeProject;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", startDate=" + startDate +
                ", deadlineDate=" + deadlineDate +
                ", timeProject=" + timeProject +
                '}';
    }
}
