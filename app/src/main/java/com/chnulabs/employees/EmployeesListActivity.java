package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EmployeesListActivity extends AppCompatActivity {

    public static final String DEPARTMENT_NAME = "employees_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_list);

        Intent intent = getIntent();
        String depName = intent.getStringExtra(DEPARTMENT_NAME);

        StringBuilder txtEmployees = new StringBuilder();
        Employee.getEmployees(depName).forEach(e -> txtEmployees.append(e.getFullName()).append("\n"));

        TextView textView = findViewById(R.id.employees_txt);
        textView.setText(txtEmployees);
    }


    public void onSendBtnClick(View view) {
        TextView textView = findViewById(R.id.employees_txt);

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, textView.getText());
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Employees List");
        startActivity(sendIntent);
    }
}