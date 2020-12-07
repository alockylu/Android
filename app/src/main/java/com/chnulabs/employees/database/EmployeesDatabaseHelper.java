package com.chnulabs.employees.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.chnulabs.employees.entities.Department;

public class EmployeesDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "employees.db";
    public static final int DB_VERSION = 1;

    public EmployeesDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        final String createTableQuery = "create table departments (\n" +
//                                        "id          integer     PRIMARY KEY AUTOINCREMENT, \n" +
//                                        "name        text        NOT NULL, \n" +
//                                        "isRemote    boolean, \n" +
//                                        "hasTrainees boolean, \n" +
//                                        "hasInvalids boolean);";
//        db.execSQL(createTableQuery);
//        populateDB(db);
    }

    private void populateDB(SQLiteDatabase db) {
        Department.getDepartments().forEach(department -> insertRow(db, department));
    }

    private void insertRow(SQLiteDatabase db, Department department) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", department.getId());
        contentValues.put("name", department.getName());
        contentValues.put("isRemote", department.isRemote());
        contentValues.put("hasTrainees", department.hasTrainees());
        contentValues.put("hasInvalids", department.hasInvalids());
        db.insert("departments", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
