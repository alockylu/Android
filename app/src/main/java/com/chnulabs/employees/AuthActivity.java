package com.chnulabs.employees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chnulabs.employees.entities.Department;
import com.chnulabs.employees.entities.http.UserData;
import com.chnulabs.employees.http.HttpDataPoster;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    public void onLogIn (View view) {
        JSONObject data = new JSONObject();
        try {
            data.put("passwd", ((EditText) findViewById(R.id.auth_password_input)).getText());
            data.put("username", ((EditText) findViewById(R.id.auth_login_input)).getText());
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }

        String strToken = new HttpDataPoster(
                Department.API_URL + "?action=login", data).sendData();

        JSONObject tokenData = null;
        String token = "";
        try {
            System.out.println("strToken: " + strToken);
            tokenData = new JSONObject(strToken);
            System.out.println("TokenData: " + tokenData);
            token = tokenData.getString("token");
            System.out.println("Token: " + token);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }

        try {
            if (token.toLowerCase().equals("null")) {
                Toast.makeText(this, "Password is incorrect!", Toast.LENGTH_LONG).show();
            }
            else {
                UserData.token = token;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        catch (NullPointerException ex) {
            Toast.makeText(this, "Oops There was some exception, please, check stacktrace...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
