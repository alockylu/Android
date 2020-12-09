package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.chnulabs.employees.database.EmployeesDatabaseHelper;
import com.chnulabs.employees.entities.Department;

public class EmployeesListActivity extends AppCompatActivity {

    public static final String DEPARTMENT_ID = "dep_id";
    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_list);

        Intent intent = getIntent();
        int depId = intent.getIntExtra(DEPARTMENT_ID, -1);
        Department department = Department.getBy(depId);

        ListView employeesListView = findViewById(R.id.employeesList);
        SimpleCursorAdapter adapter = getAdapterFromDbQuery(depId);
        if (adapter != null) {
            employeesListView.setAdapter(adapter);
        }

    }

    private SimpleCursorAdapter getAdapterFromDbQuery(int depId) {

        SimpleCursorAdapter cursorAdapter = null;
        SQLiteOpenHelper sqLiteOpenHelper = new EmployeesDatabaseHelper(this);
        try {
            sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();

            cursor = sqLiteDatabase.rawQuery("select e.id as _id, e.name as name from employees e " +
                    "inner join departments d on e.dep_id = d.id " +
                    "where e.dep_id = ?;", new String[] {Integer.toString(depId)});

            cursorAdapter = new SimpleCursorAdapter(
                    this, android.R.layout.simple_list_item_1, cursor,
                    new String[] {"name"}, new int[] {android.R.id.text1}, 0
            );

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }
        return cursorAdapter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        sqLiteDatabase.close();
    }


    public void onSendBtnClick(View view) {
//        TextView textView = findViewById(R.id.text);

    }
}