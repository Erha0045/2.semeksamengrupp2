package com.eksamengr2.alpha.model;

import java.time.LocalDate;

public class Project {

    private String name;
    private String ownerName;
    private LocalDate startDate;

    public Project(String name, String ownerName, LocalDate startDate) {
        this.name = name;
        this.ownerName = ownerName;
        this.startDate = startDate;
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
