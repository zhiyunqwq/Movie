package com.example.app1;

public class Seat {
    private int seatID;//独特ID
    private String seatNumber;  // 座位号
    private boolean isBooked;   // 是否被预订
    private String bookedBy;    // 预订者身份
    private String movieID;    //电影编号

    // Seat 类的构造函数
    public Seat( int seatID,String seatNumber, boolean isBooked, String bookedBy,String movieID) {
        this.seatID = seatID;
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
        this.bookedBy = bookedBy;
        this.movieID = movieID;
    }
    //座位ID
    public String getSeatID() {
        return Integer.toString(seatID);
    }
    // 座位号的getter和setter
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    // 是否被预订的getter和setter
    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    // 预订者身份的getter和setter
    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID (String movieID) {
        this.movieID = movieID;
    }



}