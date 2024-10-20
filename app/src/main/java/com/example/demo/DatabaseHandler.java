package com.example.demo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Objects;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UsersManagement";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Users";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_USERS_table = String.format("CREATE TABLE %s(%s TEXT PRIMARY KEY, %s TEXT)", TABLE_NAME, KEY_EMAIL, KEY_PASSWORD);
        sqLiteDatabase.execSQL(create_USERS_table);
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, "user1@gmail.com");
        values.put(KEY_PASSWORD, "12345"); // Nên mã hóa mật khẩu trước khi lưu trữ
        sqLiteDatabase.insert(TABLE_NAME, null, values);

        values.put(KEY_EMAIL, "user2@gmail.com");
        values.put(KEY_PASSWORD, "123456"); // Nên mã hóa mật khẩu trước khi lưu trữ
        sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_USERS_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        sqLiteDatabase.execSQL(drop_USERS_table);

        onCreate(sqLiteDatabase);
    }
    public boolean checkExistedUser(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME, null, KEY_EMAIL + " = ? AND " + KEY_PASSWORD + " = ? ",
                    new String[]{user.getEmail(), user.getPassword()}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                return true;
            }
        } catch (Exception e) {
            Log.e("ERROR","Error1");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return false;
    }
}
