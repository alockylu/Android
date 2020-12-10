package com.chnulabs.employees.http;

import com.chnulabs.employees.entities.http.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HttpDataPoster implements Runnable {

    private String url;
    private String data;
    private JSONObject sendData;

    public HttpDataPoster(String url, JSONObject sendData) {
        this.url = url;
        this.sendData = sendData;

        try {
            this.sendData.put("token", UserData.token);
        }
        catch (JSONException ex ) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

            con.setRequestProperty("Accept","application/json");
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(sendData.toString().getBytes(StandardCharsets.UTF_8));
            os.close();

            InputStream response = con.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            data = s.hasNext() ? s.next() : "";
        }
        catch (Exception ex) {
            data = ex.getMessage();
            ex.printStackTrace();
        }
    }

    public String sendData() {
        Thread thread = new Thread(this);
        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return data;
    }

}
