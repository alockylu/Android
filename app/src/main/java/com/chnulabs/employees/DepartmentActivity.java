package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chnulabs.employees.database.EmployeesDatabaseHelper;
import com.chnulabs.employees.entities.Department;
import com.chnulabs.employees.entities.Employee;

public class DepartmentActivity extends AppCompatActivity {

    public static final String DEPARTMENT_ID = "dep_id";

    private Department department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department2);

        Intent intent = getIntent();
        int departmentId = intent.getIntExtra(DEPARTMENT_ID, -1);

        SQLiteOpenHelper sqLiteOpenHelper = new EmployeesDatabaseHelper(this);

        try (SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
             Cursor cursor = db.query("departments", new String[]{"name", "id", "isRemote", "hasTrainees", "hasInvalids"},
                     "id=?", new String[]{Integer.toString(departmentId)},
                     null, null, null)) {
            while (cursor.moveToNext()) {
                department = new Department(
                        cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getInt(2) == 1,
                        cursor.getInt(3) == 1,
                        cursor.getInt(4) == 1
                );
                System.out.println(cursor.getString(0));
            }

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }

        EditText depNameEdit = findViewById(R.id.depNameEdit);
        depNameEdit.setText(department.getName());

        EditText totalEmployeesNumEdit = findViewById(R.id.totalEmployeesEdit);
        totalEmployeesNumEdit.setText(String.valueOf(Employee.countEmployeesBy(department)));

        TextView depNameImageText = findViewById(R.id.depNameImageText);
        depNameImageText.setText(department.getName());

        TextView depIdImageText = findViewById(R.id.depIdImageText);
        depIdImageText.setText("Id: " + department.getId());

        if (department.isRemote()) {
            ((RadioButton) findViewById(R.id.remoteDepType)).setChecked(true);
        } else {
            ((RadioButton) findViewById(R.id.localDepType)).setChecked(true);
        }

        ((CheckBox) findViewById(R.id.trainees_check_box)).setChecked(department.hasTrainees());

        ((CheckBox) findViewById(R.id.invalids_check_box)).setChecked(department.hasInvalids());
    }

    public void onOkBtnClick(View view) {

        String message = "Department: " + department.getName() + '\n' +
                "Id: " + department.getId() + '\n' +
                "Total employees: " + Employee.countEmployeesBy(department) + '\n' +
                "Type: " + (department.isRemote() ? "Remote" : "Local") + '\n' +
                (department.hasTrainees() ? "Has trainees!!" : "No trainees =(") + '\n' +
                (department.hasInvalids() ? "Has invalids" : "No invalids in department");

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void onBtnEmployeesListClick(View view) {
        Intent localIntent = getIntent();
        Integer departmentId = localIntent.getIntExtra(DEPARTMENT_ID, -1);

        Intent newIntent = new Intent(this, EmployeesListActivity.class);
        newIntent.putExtra(EmployeesListActivity.DEPARTMENT_ID, departmentId);
        startActivity(newIntent);
    }
}