package com.eksamengr2.alpha.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Task {
    private String name; //taskname
    @DateTimeFormat(pattern = "yyyy-MM-dd") //for at matche formattering fra bruger flade
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishDate;
    private int duration;
    private int projectId;
    private String isSubTask;
    private double taskNo;
    private int lineCounter;
    private String NewTaskName;
    private int idtask;
    private double taskNumber; //TODO skal fjernes
    private int  taskTimeconsumption; //the total time for one subTask
    private int noOfPersons;
    private double workingHoursDay;
    private String subTaskToName;

    public Task(LocalDate startDate, LocalDate finishDate, int duration,
                int projectId, String isSubTask, double taskNo, String newTaskName,
                int taskTimeconsumption, int noOfPersons, double workingHoursDay, String subTaskToName) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.duration = duration;
        this.projectId = projectId;
        this.isSubTask = isSubTask;
        this.taskNo = taskNo;
        NewTaskName = newTaskName;
        this.taskTimeconsumption = taskTimeconsumption;
        this.noOfPersons = noOfPersons;
        this.workingHoursDay = workingHoursDay;
        this.subTaskToName = subTaskToName;
    }

    public Task(double taskNo, int idtask) {
        this.taskNo = taskNo;
        this.idtask = idtask;
    }

    public Task(String name, LocalDate startDate, LocalDate finishDate, int duration, int projectId, String isSubTask, double taskNo, int lineCounter, String newTaskName, int idtask) {
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.duration = duration;
        this.projectId = projectId;
        this.isSubTask = isSubTask;
        this.taskNo = taskNo;
        this.lineCounter = lineCounter;
        NewTaskName = newTaskName;
        this.idtask = idtask;
    }

    public Task(String name, LocalDate startDate, LocalDate finishDate, int duration, int projectId, String isSubTask, double taskNo, int lineCounter, String NewTaskName) {
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
    public Task(String name, LocalDate startDate, LocalDate finishDate, int duration, int projectId, String isSubTask, double taskNo, int lineCounter) {
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
    //luff constructor
    public Task(double taskNumber){
        this.taskNumber=taskNumber;
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
                ", idtask=" + idtask +
                ", taskNumber=" + taskNumber +
                ", taskTimeconsumption=" + taskTimeconsumption +
                ", noOfPersons=" + noOfPersons +
                ", workingHoursDay=" + workingHoursDay +
                ", subTaskTo='" + subTaskToName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (duration != task.duration) return false;
        if (projectId != task.projectId) return false;
        if (Double.compare(task.taskNo, taskNo) != 0) return false;
        if (lineCounter != task.lineCounter) return false;
        if (idtask != task.idtask) return false;
        if (Double.compare(task.taskNumber, taskNumber) != 0) return false;
        if (taskTimeconsumption != task.taskTimeconsumption) return false;
        if (noOfPersons != task.noOfPersons) return false;
        if (Double.compare(task.workingHoursDay, workingHoursDay) != 0) return false;
        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        if (startDate != null ? !startDate.equals(task.startDate) : task.startDate != null) return false;
        if (finishDate != null ? !finishDate.equals(task.finishDate) : task.finishDate != null) return false;
        if (isSubTask != null ? !isSubTask.equals(task.isSubTask) : task.isSubTask != null) return false;
        if (NewTaskName != null ? !NewTaskName.equals(task.NewTaskName) : task.NewTaskName != null) return false;
        return subTaskToName != null ? subTaskToName.equals(task.subTaskToName) : task.subTaskToName == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (finishDate != null ? finishDate.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + projectId;
        result = 31 * result + (isSubTask != null ? isSubTask.hashCode() : 0);
        temp = Double.doubleToLongBits(taskNo);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + lineCounter;
        result = 31 * result + (NewTaskName != null ? NewTaskName.hashCode() : 0);
        result = 31 * result + idtask;
        temp = Double.doubleToLongBits(taskNumber);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + taskTimeconsumption;
        result = 31 * result + noOfPersons;
        temp = Double.doubleToLongBits(workingHoursDay);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (subTaskToName != null ? subTaskToName.hashCode() : 0);
        return result;
    }

    //    @Override
//    public int hashCode() {
//        int result;
//        long temp;
//        result = name != null ? name.hashCode() : 0;
//        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
//        result = 31 * result + (finishDate != null ? finishDate.hashCode() : 0);
//        result = 31 * result + duration;
//        result = 31 * result + projectId;
//        result = 31 * result + (isSubTask != null ? isSubTask.hashCode() : 0);
//        result = 31 * result + (taskNo != +0.0f ? Float.floatToIntBits(taskNo) : 0);
//        result = 31 * result + lineCounter;
//        result = 31 * result + (NewTaskName != null ? NewTaskName.hashCode() : 0);
//        result = 31 * result + idtask;
//        temp = Double.doubleToLongBits(taskNumber);
//        result = 31 * result + (int) (temp ^ (temp >>> 32));
//        return result;
//    }

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


    public boolean isSubTask() {
        return isSubTask.equals("yes");
    }

    public void setIsSubTask(String isSubTask) {
        this.isSubTask = isSubTask;
    }

    public double getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(double taskNo) {
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

    public double getTaskNumber() {
        return Math.round(taskNumber*100)/100d;
    }

    public void setLineCounter(int lineCounter) {
        this.lineCounter = lineCounter;
    }

    public int getIdtask() {
        return idtask;
    }

    public void setIdtask(int idtask) {
        this.idtask = idtask;
    }

    public void setTaskNumber(double taskNumber) {
        this.taskNumber = taskNumber;
    }

    public int getTaskTimeconsumption() {
        return taskTimeconsumption;
    }

    public void setTaskTimeconsumption(int taskTimeconsumption) {
        this.taskTimeconsumption = taskTimeconsumption;
    }

    public int getNoOfPersons() {
        return noOfPersons;
    }

    public void setNoOfPersons(int noOfPersons) {
        this.noOfPersons = noOfPersons;
    }

    public double getWorkingHoursDay() {
        return workingHoursDay;
    }

    public void setWorkingHoursDay(double workingHoursDay) {
        this.workingHoursDay = workingHoursDay;
    }

    public String getSubTaskToName() {
        return subTaskToName;
    }

    public void setSubTaskToName(String subTaskToName) {
        this.subTaskToName = subTaskToName;
    }
}
