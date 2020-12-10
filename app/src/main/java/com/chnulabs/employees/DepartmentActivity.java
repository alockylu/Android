package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
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
        int id = intent.getIntExtra(DEPARTMENT_ID, -1);
        department = Department.httpGetDepartment(id);

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

        SQLiteOpenHelper sqLiteOpenHelper = new EmployeesDatabaseHelper(this);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", ((TextView)findViewById(R.id.depNameEdit)).getText().toString());
        contentValues.put("isRemote", ((RadioButton)findViewById(R.id.remoteDepType)).isChecked());
        contentValues.put("hasTrainees", ((CheckBox)findViewById(R.id.trainees_check_box)).isChecked());
        contentValues.put("hasInvalids", ((CheckBox)findViewById(R.id.invalids_check_box)).isChecked());

        Intent intent = getIntent();
        int depId = intent.getIntExtra(DEPARTMENT_ID, -1);

        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            db.update("departments", contentValues, "id=?", new String[] {Integer.toString(depId)});
            db.close();
            NavUtils.navigateUpFromSameTask(this);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onBtnEmployeesListClick(View view) {
        Intent newIntent = new Intent(this, EmployeesListActivity.class);
        newIntent.putExtra(EmployeesListActivity.DEPARTMENT_ID, department.getId());
        startActivity(newIntent);
    }

    public void onDelete(View view) {
        Intent intent = getIntent();
        int depId = intent.getIntExtra(DEPARTMENT_ID, -1);
        Department.httpRemoveDepartment(depId);
        NavUtils.navigateUpFromSameTask(this);
    }
}