package com.example.ams;

public class subjectAttendenceData {
    private String Subject_name;
    private int Present, Absent;

    public subjectAttendenceData(String Subject_name, int Present, int Absent) {
        this.Subject_name = Subject_name;
        this.Present = Present;
        this.Absent = Absent;

    }

    public String getSubject() {

        return Subject_name;
    }

    public int getPresentValue() {
        return Present;
    }

    public int getAbsentValue() {

        return Absent;
    }

    public void setSubject_name(String Subject_name) {

        this.Subject_name = Subject_name;
    }

    public void setPresent(int Present) {

        this.Present = Present;
    }

    public void setAbsent(int Absent) {
        this.Absent = Absent;
    }
}
