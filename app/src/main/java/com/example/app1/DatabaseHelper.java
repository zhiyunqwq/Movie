package com.example.app1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_EMAIL + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // 插入数据的方法
    public boolean insertData(String username, String password,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL,email);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID
        };

        String selection = COLUMN_USERNAME + "=?" + " AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }

    public boolean PwdChange_Data(String username, String email, String newPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID
        };

        // 查询数据库以验证用户名和邮箱是否匹配
        String selection = COLUMN_USERNAME + "=?" + " AND " + COLUMN_EMAIL + "=?";
        String[] selectionArgs = {username, email};

        // 检查用户名和邮箱是否匹配
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        // 如果找到匹配的用户名和邮箱
        if (cursorCount > 0) {
            // 打开可写的数据库
            db = this.getWritableDatabase();

            // 准备更新密码的SQL语句
            String updateQuery = "UPDATE " + TABLE_USERS + " SET " + COLUMN_PASSWORD + " = ? " +
                    "WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_EMAIL + " = ?";

            // 使用SQL语句更新密码
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PASSWORD, newPassword);

            // 执行更新操作
            long result = db.update(TABLE_USERS, contentValues, COLUMN_USERNAME + "=? AND " + COLUMN_EMAIL + "=?",
                    new String[] {username, email});

            // 关闭数据库
            db.close();

            // 如果更新行数大于0，则表示密码更新成功
            return result > 0;
        }

        // 如果没有找到匹配的用户名和邮箱，返回false
        return false;
    }
    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_ID }; // 假设用户表的主键是 COLUMN_USER_ID
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };
        Cursor cursor = null;
        boolean exists = false;

        try {
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            exists = cursor.getCount() > 0; // 如果游标中有数据，说明用户名已存在
        } finally {
            if (cursor != null) {
                cursor.close(); // 关闭游标释放资源
            }
            db.close(); // 关闭数据库连接
        }

        return exists;
    }

}