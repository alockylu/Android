package com.chnulabs.employees.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.chnulabs.employees.entities.Department;
import com.chnulabs.employees.entities.Employee;

public class EmployeesDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "employees.db";
    public static final int DB_VERSION = 2;

    public EmployeesDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createTableQuery = "create table departments (\n" +
                                        "id          integer     PRIMARY KEY AUTOINCREMENT, \n" +
                                        "name        text        NOT NULL, \n" +
                                        "isRemote    boolean, \n" +
                                        "hasTrainees boolean, \n" +
                                        "hasInvalids boolean);";
        db.execSQL(createTableQuery);
        updateSchema(db, 0);
        populateDB(db);
    }

    private void updateSchema(SQLiteDatabase db, int oldV) {
        if (oldV < 2) {
            db.execSQL("CREATE TABLE employees (" +
                            " id        integer     PRIMARY KEY AUTOINCREMENT," +
                            " name      TEXT(30)    NOT NULL," +
                            " dep_id    integer     REFERENCES departments (id) ON DELETE RESTRICT " +
                            "                                                   ON UPDATE RESTRICT );");
            populateEmployees(db);
        }
    }

    private void populateDB(SQLiteDatabase db) {
        populateDepartments(db);
        populateEmployees(db);
    }

    private void populateDepartments(SQLiteDatabase db) {
        Department.getDepartments().forEach(department -> insertRowToDepartment(db, department));
    }

    private void insertRowToDepartment(SQLiteDatabase db, Department department) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", department.getId());
        contentValues.put("name", department.getName());
        contentValues.put("isRemote", department.isRemote());
        contentValues.put("hasTrainees", department.hasTrainees());
        contentValues.put("hasInvalids", department.hasInvalids());
        db.insert("departments", null, contentValues);
    }

    private void populateEmployees(SQLiteDatabase db) {
        Employee.getEmployees().forEach(employee -> insertRowToEmployee(db, employee));
    }

    private void insertRowToEmployee(SQLiteDatabase db, Employee employee) {
        db.execSQL("INSERT INTO employees (name, dep_id) " +
                    " select '" + employee.getFullName() + "', id " +
                    " from departments " +
                    " where name ='"+ employee.getDepartment() + "';");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateSchema(db, oldVersion);
    }
}
