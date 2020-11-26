package com.chnulabs.employees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.widget.ShareActionProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chnulabs.employees.entities.Department;

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
                this, android.R.layout.simple_list_item_1, Department.getDepartments());
        departmentsList.setAdapter(departmentAdapter);

        departmentsList.setOnItemClickListener((parent, view, position, id) -> {
            String department = parent.getItemAtPosition(position).toString();
            Intent intent = new Intent(DepartmentsListActivity.this, DepartmentActivity.class);
            intent.putExtra(DepartmentActivity.DEPARTMENT_NAME, department);
            startActivity(intent);
        });
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