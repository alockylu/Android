package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chnulabs.employees.entities.Department;

public class DepartmentsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments_list);

        ListView departmentsList = findViewById(R.id.departments_list);

        ArrayAdapter<Department> departmentAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, Department.getDepartments());
        departmentsList.setAdapter(departmentAdapter);

        departmentsList.setOnItemClickListener((parent, view, position, id) -> {
            String department = parent.getItemAtPosition(position).toString();
            Intent intent = new Intent(DepartmentsListActivity.this, DepartmentActivity.class);
            intent.putExtra(DepartmentActivity.DEPARTMENT_NAME, department);
            startActivity(intent);
        });
    }
}