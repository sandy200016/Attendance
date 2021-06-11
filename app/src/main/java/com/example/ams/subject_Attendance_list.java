package com.example.ams;

public class subject_Attendance_list {
    String Name;
    int Present,Absent,Score_subject;

    public subject_Attendance_list(String name, int present, int absent,int Score_subject) {
        Name = name;
        Present = present;
        Absent = absent;
        this.Score_subject=Score_subject;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPresent() {
        return Present;
    }

    public void setPresent(int present) {

        Present = present;
    }

    public int getAbsent() {
        return Absent;
    }

    public void setAbsent(int absent) {

        Absent = absent;
    }
    public void setScore_subject(int Score_subject){
        this.Score_subject=Score_subject;
    }
    public int getScore_subject(){
        return Score_subject;
    }

}
