package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonGetterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_getter);

        Intent intent = getIntent();
        int postId = intent.getIntExtra(UserListActivity.USER_ID,0);

        String res = new HttpDataGetter(
                "http://jsonplaceholder.typicode.com/posts/" + postId
        ).getData();

        try{
            JSONObject obj = new JSONObject(res);

//            EditText txtUser = findViewById(R.id.txtUser);
//            txtUser.setText(obj.getString("userId"));

            EditText txtId = findViewById(R.id.txtId);
            txtId.setText(obj.getString("id"));

            EditText txtTheme = findViewById(R.id.txtTheme);
            txtTheme.setText(obj.getString("title"));

            EditText txtText = findViewById(R.id.txtText);
            txtText.setText(obj.getString("body"));

            String user_id = obj.getString("userId");
            String resUser = new HttpDataGetter(
                    "http://jsonplaceholder.typicode.com/users/" + user_id
            ).getData();

            JSONObject objUser = new JSONObject(resUser);
            EditText txtUser = findViewById(R.id.txtUser);
            txtUser.setText(objUser.getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}