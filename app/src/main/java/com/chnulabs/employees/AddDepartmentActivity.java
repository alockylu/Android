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

        EditText depNameEdit = findViewById(R.id.depNameEdit);

        SQLiteOpenHelper sqLiteOpenHelper = new EmployeesDatabaseHelper(this);

        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", depNameEdit.getText().toString());
            contentValues.put("isRemote", 0);
            contentValues.put("hasTrainees", 0);
            contentValues.put("hasInvalids", 0);
            db.insert("departments", null, contentValues);
            db.close();
            NavUtils.navigateUpFromSameTask(this);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}