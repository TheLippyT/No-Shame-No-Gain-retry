package com.example.noshamenogain.Database;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.noshamenogain.Entity.Users;

public class UsersDAO extends SQLiteOpenHelper {
    //made into a constant to avoid rewriting them. to avoid types.

    private static final String DATABASE_NAME = "fitness.db";
    private static final int DATABASE_VERSION = 1;

    //Database objects for the Users table
    public static final String ID = "id";
    public static final String USERS_TABLE = "users";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_WEIGHT = "weight";
    public static final String USERS_HEIGHT = "height";
    public static final String USERS_BMI = "bmi";

    private static final String SQL_USERS_TABLE_QUERY = "CREATE TABLE users(id INTEGER PRIMARY KEY, username STRING NOT NULL UNIQUE, password STRING NOT NULL, weight DOUBLE, height DOUBLE, bmi DOUBLE)";


    public UsersDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void setDefaultCredentials() {
        Users users = findOneRecordByUsername("admin");
        if (users != null) {
            return;
        }
        //Set default username and password
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_USERNAME, "admin");
        contentValues.put(USERS_PASSWORD, "admin");
        insertOneRecord(USERS_TABLE, contentValues);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_USERS_TABLE_QUERY);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }//content value only for 1 record
    public long insertOneRecord(String tableName, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(tableName, null, contentValues);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }

    public Users findOneRecordByUsername(String username) {
        Users user = null;
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select * from %s", USERS_TABLE);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            user = new Users(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }
        db.close();
        return user;
    }
}
