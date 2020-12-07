package com.chnulabs.employees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.widget.ShareActionProvider;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chnulabs.employees.database.EmployeesDatabaseHelper;
import com.chnulabs.employees.entities.Department;

import java.util.ArrayList;

public class DepartmentsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments_list);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ListView departmentsList = findViewById(R.id.departments_list);

        ArrayAdapter<Department> departmentAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getDataFromDB()
        );

        departmentsList.setAdapter(departmentAdapter);

        departmentsList.setOnItemClickListener((parent, view, position, id) -> {
            Department department = (Department) parent.getItemAtPosition(position);
            Intent intent = new Intent(DepartmentsListActivity.this, DepartmentActivity.class);
            intent.putExtra(DepartmentActivity.DEPARTMENT_ID, department.getId());
            System.out.println(department.getId());
            startActivity(intent);
        });
    }

    private ArrayList<Department> getDataFromDB() {
        ArrayList<Department> departments = new ArrayList<>();

        SQLiteOpenHelper sqLiteOpenHelper = new EmployeesDatabaseHelper(this);

        try (SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
             Cursor cursor = db.query("departments", new String[]{"name", "id", "isRemote", "hasTrainees", "hasInvalids"},
                null, null, null, null, "id")) {
            while (cursor.moveToNext()) {
                departments.add(
                        new Department(
                                cursor.getString(0),
                                cursor.getInt(1),
                                cursor.getInt(2) == 1,
                                cursor.getInt(3) == 1,
                                cursor.getInt(4) == 1
                        ));
                System.out.println(cursor.getString(0));
            }

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }
        return departments;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.departments_menu, menu);

        StringBuilder departmentsText = new StringBuilder();

        for (Department department : Department.getDepartments()) {
            departmentsText.append(department.getName()).append('\n');
        }

        MenuItem menuItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT, departmentsText.toString());
        shareActionProvider.setShareIntent(intent);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_add_department) {
            startActivity(new Intent(this, AddDepartmentActivity.class));
        }
        return super.onOptionsItemSelected(menuItem);
    }

}