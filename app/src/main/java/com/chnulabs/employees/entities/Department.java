package com.chnulabs.employees.entities;

import androidx.annotation.NonNull;

import com.chnulabs.employees.http.HttpDataGetter;
import com.chnulabs.employees.http.HttpDataPoster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Department {

    private String name;
    private int id;
    private boolean isRemote;
    private boolean hasTrainees;
    private boolean hasInvalids;

    public Department(String name, int id, boolean isRemote, boolean hasTrainees, boolean hasInvalids) {
        this.name = name;
        this.id = id;
        this.isRemote = isRemote;
        this.hasTrainees = hasTrainees;
        this.hasInvalids = hasInvalids;
    }

    public String getName() {
        return name;
    }

    public boolean hasTrainees() {
        return hasTrainees;
    }

    public boolean hasInvalids() {
        return hasInvalids;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public int getId() {
        return id;
    }

    public static final String API_URL = "http://10.0.2.2:8888/api/";

    public static ArrayList<Department> httpGetDepartments() {
        ArrayList<Department> departments = new ArrayList<>();
        String response = new HttpDataGetter(API_URL + "?action=get_departments_list").getData();

        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Department dep = new Department(
                        obj.getString("name"),
                        obj.getInt("id"),
                        obj.getInt("isRemote") == 1,
                        obj.getInt("hasTrainees") == 1,
                        obj.getInt("hasInvalids") == 1
                );
                departments.add(dep);
            }
        } catch (JSONException jex) {
            jex.printStackTrace();
        }
        return  departments;
    }

    public static Department httpGetDepartment(Integer id) {
        Department department = null;
        String response = new HttpDataGetter(API_URL + "?action=get_department&dep_id=" + id).getData();
        try {
            JSONObject obj = new JSONObject(response);
            department = new Department(
                    obj.getString("name"),
                    obj.getInt("id"),
                    obj.getInt("isRemote") == 1,
                    obj.getInt("hasTrainees") == 1,
                    obj.getInt("hasInvalids") == 1
            );
        }
        catch (JSONException jex) {
            jex.printStackTrace();
        }

        return department;
    }

    public void httpAddDepartment() {

        JSONObject data = new JSONObject();
        try {
            data.put("name", name);
            data.put("isRemote", isRemote);
            data.put("hasTrainees", hasTrainees);
            data.put("hasInvalids", hasInvalids);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }

        String res = new HttpDataPoster(API_URL + "?action=add_dep", data).sendData();
        System.out.println(res);
    }

    public static void httpRemoveDepartment(int id) {
        JSONObject data = new JSONObject();
        try {
            data.put("id", id);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        String res = new HttpDataPoster(API_URL + "?action=remove_dep", data).sendData();
        System.out.println(res);
    }

    private static final List<Department> departments = new ArrayList<>(Arrays.asList(
            new Department("Sales", 3, false, true, false),
            new Department("Marketing", 1, true, false, false),
            new Department("Software Development", 2, true, true, true),
            new Department("Logistics", 4, false, false, true)
    ));

    public static List<Department> getDepartments() {
        return departments;
    }


    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public static Department getBy(Integer id) {
        return departments.stream().filter(dep -> dep.id == id).findAny().orElse(null);
    }

}
