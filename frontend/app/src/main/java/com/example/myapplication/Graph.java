package com.example.myapplication;

public class Graph {
        private String AttendanceDate;
        private String AttendanceRate;

        public Graph(String AttendanceDate, String AttendanceRate) {
            this.AttendanceDate = AttendanceDate;
            this.AttendanceRate = AttendanceRate;
        }




        public String getDate() {
            return AttendanceDate;
        }

        public String getRate() {
            return AttendanceRate;
        }

    }


