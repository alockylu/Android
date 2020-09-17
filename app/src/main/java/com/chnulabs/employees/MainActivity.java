package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnClick(View view) {
        Spinner spinner = findViewById(R.id.dep_spinner);
        String department = (String) spinner.getSelectedItem();

        Intent listIntent = new Intent(this, EmployeesListActivity.class);
        listIntent.putExtra(EmployeesListActivity.DEPARTMENT_NAME, department);
        startActivity(listIntent);
    }
}