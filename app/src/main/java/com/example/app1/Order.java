package com.example.app1;

public class Order {
        private int id;
        private String seatNumber; // 座位号
        private String bookedBy;
        private String movieTitle; // 电影名称
    public Order( int id,String seatNumber, String bookedBy,String movieTitle) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.bookedBy = bookedBy;
        this.movieTitle = movieTitle;
    }
    //座位ID
    public String getSeatID() {
        return Integer.toString(id);
    }
    // 座位号的getter和setter
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    // 预订者身份的getter和setter
    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieID (String movieTitle) {
        this.movieTitle = movieTitle;
    }

}
