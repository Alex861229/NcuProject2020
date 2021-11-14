package com.example.myapplication;

public class Nameitem {
    private String CourseID;
    private String created_at;
    private String Total;

    public Nameitem(String CourseID, String created_at, String Total) {
        this.CourseID = CourseID;
        this.created_at = created_at;
        this.Total = Total;
    }



    public String getCourseID() {
        return CourseID;
    }

    public String getcreated_at() {
        return created_at;
    }

    public String getTotal() {
        return Total;
    }
}
