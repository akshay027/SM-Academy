package com.sm_academy.Activity.LearnerActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sm_academy.API.RetrofitAPI;


import com.sm_academy.ModelClass.DashBoard.DashBoardResponse;
import com.sm_academy.ModelClass.DashBoard.DashBoardSections;
import com.sm_academy.R;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SecondHomeDashBoardFragment extends Fragment {

    HashMap hashMap = new HashMap();
    ArrayList<String> nameList;
    ArrayList<ArrayList<String>> nodes = new ArrayList<ArrayList<String>>();
    Collection c;
    ArrayList arrayList = new ArrayList();

    public static SecondHomeDashBoardFragment newInstance() {
        SecondHomeDashBoardFragment fragment = new SecondHomeDashBoardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_home_dash_board, container, false);


        //RecyclerView my_recycler_view = view.findViewById(R.id.my_recycler_view);

        //my_recycler_view.setHasFixedSize(true);

        //RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this,arrayList);

        // my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // my_recycler_view.setAdapter(adapter);
      // dashBoardDetails();
        return view;
    }
/*
    private void dashBoardDetails() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. ");

                RetrofitAPI.getInstance(getActivity()).getApi().getSeconddashBoardDetails(new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject object, Response response) {
                        try {
                            if (response.getStatus() == 200) {
                                UIUtil.stopProgressDialog(getActivity());
                                JSONObject responseDataObj = new JSONObject(response.getBody().toString());
                                Log.e("aaaaaaaa", "===" + object);
                                JSONObject obj = responseDataObj.getJSONObject("sections");
                                //JSONArray responseArray = responseDataObj.getJSONArray("sections");
                                for (int i = 0; i < obj.length(); i++) {

                                    Iterator keys = obj.keys();
                                    while (keys.hasNext()) {
                                        // Loop to get the dynamic key
                                        String currentDynamicKey = (String) keys.next();
                                        // Get the value of the dynamic key
                                        JSONArray currentDynamicValue = obj.getJSONArray(currentDynamicKey);
                                        int jsonArraySize = currentDynamicValue.length();
                                        if (jsonArraySize > 0) {
                                            for (int ii = 0; ii < jsonArraySize; ii++) {
                                                // NameList ArrayList<String> declared globally
                                                nameList = new ArrayList<String>();
                                                if (ii == 0) {
                                                    JSONObject nameObj = currentDynamicValue.getJSONObject(ii);
                                                    String name = nameObj.getString("titleized_name");
                                                    System.out.print("Name = " + name);
                                                    // Store name in an array list
                                                    nameList.add(name);
                                                }
                                            }
                                        }
                                        nodes.add(nameList);
                                    }
                                }
                                Log.e("node", "===" + nodes);
                                Log.e("node", "===" + nameList);


                                // bindData(object.getSections());
                               *//* //bindData(object.getSections());
                               // JSONObject json = null;
                                String string = "sections"; // The String which Need To Be Converted
                                JsonObject convertedObject = new Gson().fromJson(string, JsonObject.class);

                                Log.e("json", "" + convertedObject);
                                JSONArray categories = new JSONArray();
                                // JSONArray categories;
                                //categories = json.getJSONArray("section");
                         *//**//*       for (int i = 0; i < convertedObject.size(); i++) {
                                    try {
                                        categories =  convertedObject.get(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Log.e("categories222", "" + categories);*//*

                                //  Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                UIUtil.stopProgressDialog(getActivity());
                                Toast.makeText(getActivity(), "No Data Available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        UIUtil.stopProgressDialog(getActivity());
                        Toast.makeText(getActivity(), "Internal Server Error", Toast.LENGTH_SHORT).show();

                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void bindData(DashBoardSections dashBoardSections) {


        // ArrayList arrayList=new ArrayList<>();
        c = Collections.singleton(dashBoardSections.toString());

        hashMap.put("abc", dashBoardSections);
        Object[] keys = hashMap.keySet().toArray();
        for (int i = 0; i < hashMap.size(); i++) {
            arrayList.addAll(Collections.singleton(hashMap.get(keys[i])));
        }
        Log.e("aaaaaaa", "" + arrayList);
        Log.e("categories222", "" + c.size());
        Log.e("hashMap", "" + hashMap.size());
    }

}
