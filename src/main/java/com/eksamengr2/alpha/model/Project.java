package com.eksamengr2.alpha.model;

import java.time.LocalDate;

public class Project {

    private int projectId ;
    private String name;
    private String ownerName;
    private LocalDate startDate;

    public Project(String name, String ownerName, LocalDate startDate) {
        this.name = name;
        this.ownerName = ownerName;
        this.startDate = startDate;
    }

    public Project(int projectId, String name, String ownerName, LocalDate startDate) {
        this.projectId = projectId;
        this.name = name;
        this.ownerName = ownerName;
        this.startDate = startDate;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Project() {
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
