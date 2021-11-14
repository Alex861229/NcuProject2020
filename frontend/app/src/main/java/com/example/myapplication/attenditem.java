package com.example.myapplication;


public class attenditem {
    private String CourseID;
    private String AttendanceDate;
    private String AttendanceRate;

    public attenditem(String CourseID, String AttendanceDate, String AttendanceRate) {
        this.CourseID = CourseID;
        this.AttendanceDate = AttendanceDate;
        this.AttendanceRate = AttendanceRate;
    }



    public String getCourseID() {
        return CourseID;
    }

    public String getDate() {
        return AttendanceDate;
    }

    public String getRate() {
        return AttendanceRate;
    }

}
