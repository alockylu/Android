package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chnulabs.employees.http.HttpDataGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        Intent intent = getIntent();
        int userId = intent.getIntExtra(UserListActivity.USER_ID,0);
        boolean completed = intent.getBooleanExtra(UserListActivity.TODO_STATUS, true);

        ListView listView = (ListView) findViewById(R.id.toDoList);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getToDoNamesData(userId, completed)
        );
        listView.setAdapter(adapter);
    }

    private ArrayList<String> getToDoNamesData(int userId, boolean completed){
        ArrayList<String> arr = new ArrayList<>();
        String res = new HttpDataGetter(
                "https://jsonplaceholder.typicode.com/users/" + userId +
                        "/todos?completed=" + completed
        ).getData();

        try{
            JSONArray jsonArray = new JSONArray(res);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                arr.add(obj.getString("title"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return arr;
    }
}