package com.example.project2.repository.web.model;

import com.google.gson.annotations.SerializedName;

public class ScheduleItemApiModel {
    @SerializedName("predmet")
    private String className;

    @SerializedName("tip")
    private String classType;

    @SerializedName("nastavnik")
    private String professor;

    @SerializedName("grupe")
    private String groups;

    @SerializedName("dan")
    private String day;

    @SerializedName("termin")
    private String time;

    @SerializedName("ucionica")
    private String classroom;

    public ScheduleItemApiModel(String className, String classType, String professor, String groups, String day, String time, String classroom) {
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

    public String getClassType() {
        return classType;
    }

    public String getProfessor() {
        return professor;
    }

    public String getGroups() {
        return groups;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getClassroom() {
        return classroom;
    }
}
