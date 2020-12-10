package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.chnulabs.employees.http.HttpDataGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    public static final java.lang.String USER_ID = "user_id";
    public static final java.lang.String TODO_STATUS = "todo_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ListView listView = (ListView) findViewById(R.id.toDoList);
        final ArrayAdapter<User> adapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_1,
                getListData()
        );
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = (User) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(UserListActivity.this, ToDoListActivity.class);
                intent.putExtra(UserListActivity.USER_ID, user.id);
                intent.putExtra(UserListActivity.TODO_STATUS, ((RadioButton)findViewById(R.id.completedRadioButton)).isChecked());
                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(listener);
    }

    private ArrayList<User> getListData(){
        ArrayList<User> arr = new ArrayList<>();
        java.lang.String res = new HttpDataGetter(
                "https://jsonplaceholder.typicode.com/users/"
        ).getData();
        try{
            JSONArray jsonArray = new JSONArray(res);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                arr.add(
                        new User(
                                obj.getInt("id"),
                                obj.getString("name")
                        )
                );
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return arr;
    }
}

class User {
    public int id;
    public java.lang.String name;

    public User(int id, java.lang.String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public java.lang.String toString() {
        return name;
    }
}