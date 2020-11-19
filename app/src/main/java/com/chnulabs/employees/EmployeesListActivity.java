package com.chnulabs.employees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.chnulabs.employees.entities.Employee;

public class EmployeesListActivity extends AppCompatActivity {

    public static final String DEPARTMENT_NAME = "employees_list";

    private float textSize;

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

        textSize = textView.getTextSize();

        if (savedInstanceState != null) {
            textSize = savedInstanceState.getFloat("textSize");
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("textSize", textSize);
    }

    public void onSendBtnClick(View view) {
        TextView textView = findViewById(R.id.employees_txt);

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, textView.getText());
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Employees List");

        startActivity(sendIntent);
    }

    public void onFontBtnClick(View view) {
        textSize *= 1.1;
        ((TextView)findViewById(R.id.employees_txt)).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }
}