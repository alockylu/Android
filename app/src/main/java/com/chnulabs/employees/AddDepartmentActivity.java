package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
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

public class AddDepartmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);
    }

    public void onAddDepartmentBtnClick(View view) {
        Department dep = new Department(
                ((EditText)findViewById(R.id.depNameEdit)).getText().toString(),
                0,
                true, true, true
        );
        dep.httpAddDepartment();
        NavUtils.navigateUpFromSameTask(this);
    }
}