package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chnulabs.employees.entities.Department;

public class AddDepartmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);
    }

    public void addDepartment(View view) {
        EditText depIdEdit = findViewById(R.id.depIdEdit);
        EditText depNameEdit = findViewById(R.id.depNameEdit);
        Department.addDepartment(new Department(
                depNameEdit.getText().toString(),
                Integer.parseInt(depIdEdit.getText().toString()),
                false, false, false)
        );
        NavUtils.navigateUpFromSameTask(this);
    }
}