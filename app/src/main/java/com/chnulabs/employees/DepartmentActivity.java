package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chnulabs.employees.entities.Department;
import com.chnulabs.employees.entities.Employee;

public class DepartmentActivity extends AppCompatActivity {

    public static final String DEPARTMENT_NAME = "dep_name";

    private Department department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        Intent intent = getIntent();
        String depName = intent.getStringExtra(DEPARTMENT_NAME);
        department = Department.getBy(depName);

        EditText depNameEdit = findViewById(R.id.depNameEdit);
        depNameEdit.setText(department.getName());

        EditText totalEmployeesNumEdit = findViewById(R.id.totalEmployeesNum);
        totalEmployeesNumEdit.setText(String.valueOf(Employee.countEmployeesBy(department)));

        TextView depNameImageText = findViewById(R.id.depNameImageText);
        depNameImageText.setText(depName);

        TextView depIdImageText = findViewById(R.id.depIdImageText);
        depIdImageText.setText("Id: " + department.getId());

        if (department.isRemote()) {
            ((RadioButton)findViewById(R.id.remoteDepType)).setChecked(true);
        } else {
            ((RadioButton)findViewById(R.id.localDepType)).setChecked(true);
        }

        ((CheckBox)findViewById(R.id.trainees_check_box)).setChecked(department.hasTrainees());

        ((CheckBox)findViewById(R.id.invalids_check_box)).setChecked(department.hasInvalids());
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
}