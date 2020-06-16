package com.example.project2.model;

public class ScheduleItemFilter {
    private String classNameOrProfessor;
    private String day;
    private String group;

    public ScheduleItemFilter(String classNameOrProfessor, String day, String group) {
        this.classNameOrProfessor = classNameOrProfessor;
        this.day = day;
        this.group = group;
    }

    public ScheduleItemFilter(){

    }

    public String getClassNameOrProfessor() {
        return classNameOrProfessor;
    }

    public void setClassNameOrProfessor(String classNameOrProfessor) {
        this.classNameOrProfessor = classNameOrProfessor;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
