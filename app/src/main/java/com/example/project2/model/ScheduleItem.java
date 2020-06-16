package com.example.project2.model;

public class ScheduleItem {

    private String className;
    private String classType;
    private String professor;
    private String groups;
    private String day;
    private String time;
    private String classroom;

    public ScheduleItem(String className, String classType, String professor, String groups, String day, String time, String classroom) {
        this.className = className;
        this.classType = classType;
        this.professor = professor;
        this.groups = groups;
        this.day = day;
        this.time = time;
        this.classroom = classroom;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }


}