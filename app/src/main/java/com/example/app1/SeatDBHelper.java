package com.example.app1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SeatDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "seat_reservation.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "seats";
    public static final String COLUMN_SEAT_ID = "seat_id";
    public static final String COLUMN_SEAT_NUMBER = "seat_number";
    public static final String COLUMN_IS_BOOKED = "is_booked";
    public static final String COLUMN_BOOKED_BY = "booked_by";
    public static final String COLUMN_MOVIE_ID = "movie_id";

    private static final String CREATE_TABLE_SEATS = "CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_SEAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SEAT_NUMBER + " TEXT NOT NULL,"
            + COLUMN_IS_BOOKED + " INTEGER NOT NULL DEFAULT 0,"  // 使用整数存储布尔值，0 表示未预订，1 表示已预订
            + COLUMN_BOOKED_BY + " TEXT,"
            + COLUMN_MOVIE_ID + " TEXT"+")";

    public SeatDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SEATS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // 用于插入新座位的方法
    public void addSeat(String seatNumber, String bookedBy,String movieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SEAT_NUMBER, seatNumber);
        values.put(COLUMN_BOOKED_BY, bookedBy);
        values.put(COLUMN_MOVIE_ID,movieId);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // 更新座位预订状态的方法
    public void updateSeatBooking(String seatNumber, boolean isBooked,String movieId,String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_BOOKED, isBooked ? 1 : 0);
        values.put(COLUMN_BOOKED_BY,userName);
        db.update(TABLE_NAME, values, COLUMN_SEAT_NUMBER + " = ? AND " + COLUMN_MOVIE_ID + " = ?", new String[]{seatNumber, movieId});
        db.close();
    }

    // 获取座位信息的方法
    public Seat getSeat(String MovieID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,  COLUMN_MOVIE_ID+ " = ?", new String[]{String.valueOf(MovieID)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int seatID = cursor.getInt(cursor.getColumnIndex(COLUMN_SEAT_ID));
            @SuppressLint("Range") String SeatNumber = cursor.getString(cursor.getColumnIndex(COLUMN_SEAT_NUMBER));
            @SuppressLint("Range") boolean isBooked = (cursor.getInt(cursor.getColumnIndex(COLUMN_IS_BOOKED))==1);
            @SuppressLint("Range") String bookedBy = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKED_BY));
            @SuppressLint("Range") String  movieID = cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_ID));
            Seat seat = new Seat(seatID,SeatNumber, isBooked, bookedBy, movieID);
            cursor.close();
            return seat;
        }
        return null;
    }

}