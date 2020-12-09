package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onBtnShowDepartmentsClick(View view) {
        Intent intent = new Intent(this, DepartmentsListActivity.class);
        startActivity(intent);
    }


    public void onGetJsonBtnClick(View view) {
        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
    }
}