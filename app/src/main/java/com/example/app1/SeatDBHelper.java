package com.example.app1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
            + COLUMN_IS_BOOKED + " INTEGER NOT NULL DEFAULT 0,"
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
    }

    // 更新座位预订状态的方法
    public void updateSeatBooking(String seatNumber, boolean isBooked,String movieId,String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_BOOKED, isBooked ? 1 : 0);
        values.put(COLUMN_BOOKED_BY,userName);
        db.update(TABLE_NAME, values, COLUMN_SEAT_NUMBER + " = ? AND " + COLUMN_MOVIE_ID + " = ?", new String[]{seatNumber, movieId});
    }

    // 获取座位信息的方法
    public List<Seat> getSeat(String movieID) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Seat> seats = new ArrayList<Seat>(); // 使用ArrayList并初始化
        String[] selectionArgs = {movieID};
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_MOVIE_ID + " = ?", selectionArgs, null, null, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        int seatID = cursor.getInt(cursor.getColumnIndex(COLUMN_SEAT_ID));
                        String seatNumber = cursor.getString(cursor.getColumnIndex(COLUMN_SEAT_NUMBER));
                        boolean isBooked = (cursor.getInt(cursor.getColumnIndex(COLUMN_IS_BOOKED)) == 1);
                        String bookedBy = cursor.getString(cursor.getColumnIndex(COLUMN_BOOKED_BY));
                        Seat seat = new Seat(seatID, seatNumber, isBooked, bookedBy, movieID);
                        seats.add(seat); // 将座位添加到列表中
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close(); // 确保在finally块中关闭cursor
            }
        }

        return seats; // 返回座位列表，如果为空则返回空列表
    }

}