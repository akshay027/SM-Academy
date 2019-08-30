package com.sm_academy.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Adapters.CompletedSessionAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.LiveSession.CompletedSession;
import com.sm_academy.ModelClass.LiveSession.LiveSessionResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedSessionFragment extends Fragment {
    private CompletedSessionAdapter completedSessionAdapter;
    private ListView listviewdefaulter;
    private EditText etSearchdefaulters;
    private Handler handler;
    private String pos;
    public static final int TIME_OUT = 1000;
    private ArrayList<CompletedSession> completedSessionArrayList, searchArrayList;
    private TextView tv_nodata;
    private LinearLayout searchView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public CompletedSessionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_session, container, false);
        listviewdefaulter = (ListView) view.findViewById(R.id.lvCompletedSession);

        completedSessionArrayList = new ArrayList<>();
        searchArrayList = new ArrayList<>();
        searchView = view.findViewById(R.id.searchView);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        //etSearchdefaulters = (EditText) view.findViewById(R.id.etSearchdefaulters);
        completedSessionAdapter = new CompletedSessionAdapter(getActivity(), completedSessionArrayList);
        // LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        etSearchdefaulters = view.findViewById(R.id.etSearchdefaulters);

        handler = new Handler();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etSearchdefaulters.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return false;
            }
        });
        getSessionData();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSessionData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        etSearchdefaulters.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() > 0) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            filterSearch(s.toString());
                        }
                    }, TIME_OUT);
                } else {
                    if (searchArrayList.size() > 0) {
                        completedSessionArrayList.clear();
                        completedSessionArrayList.addAll(searchArrayList);
                        completedSessionAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void filterSearch(String search) {
        try {
            completedSessionArrayList.clear();
            for (int i = 0; i < searchArrayList.size(); i++) {
                CompletedSession defaulterDatamodel = searchArrayList.get(i);
                if (defaulterDatamodel.getSectionName().toLowerCase().contains(search.toLowerCase()) ||
                        defaulterDatamodel.getTimings().toLowerCase().contains(search.toLowerCase()) ||
                        defaulterDatamodel.getCourseName().toLowerCase().contains(search.toLowerCase()) ||
                        defaulterDatamodel.getBatchName().toLowerCase().contains(search.toLowerCase()) ||
                        defaulterDatamodel.getSectionTopicTopic().toLowerCase().contains(search.toLowerCase()) ||
                        defaulterDatamodel.getSessionDateReadableFormat().toLowerCase().contains(search.toLowerCase())) {
                    completedSessionArrayList.add(defaulterDatamodel);
                }
            }
            if (completedSessionArrayList.size() <= 0) {
                etSearchdefaulters.setError("No Record found");
            }
            completedSessionAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSessionData() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
                //Map<String, String> params = new HashMap<String, String>();
                //params.put("auth_token", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_TOKEN));

                RetrofitAPI.getInstance(getActivity()).getApi().getSessionDetails(new Callback<LiveSessionResponse>() {
                    @Override
                    public void success(LiveSessionResponse defaulterResponseModel, Response response) {
                        if (response.getStatus() == Constants.SUCCESS) {
                            try {
                                UIUtil.stopProgressDialog(getActivity());
                                Log.e("jsonObject", "jsonObject --- " + defaulterResponseModel.toString());
                                searchArrayList.clear();
                                completedSessionArrayList.clear();
                                searchArrayList.addAll(defaulterResponseModel.getCompletedLiveSessions());
                                completedSessionArrayList.addAll(defaulterResponseModel.getCompletedLiveSessions());
                                listviewdefaulter.setAdapter(completedSessionAdapter);
                                completedSessionAdapter.notifyDataSetChanged();
                                Log.e("completed", " --- " + completedSessionArrayList);
                                if (completedSessionArrayList.size() <= 0) {
                                    listviewdefaulter.setVisibility(View.GONE);
                                    searchView.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText("You do not have any completed sessions");
                                } else {
                                    listviewdefaulter.setVisibility(View.VISIBLE);
                                    searchView.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                    tv_nodata.setText("");
                                }
                                // completedSessionAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            listviewdefaulter.setVisibility(View.GONE);
                            searchView.setVisibility(View.GONE);
                            tv_nodata.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), defaulterResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                                UIUtil.stopProgressDialog(getActivity());
                                Toast.makeText(getActivity(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();
                                listviewdefaulter.setVisibility(View.GONE);
                                searchView.setVisibility(View.GONE);
                                tv_nodata.setVisibility(View.VISIBLE);
                                logout();
                            } else {
                                listviewdefaulter.setVisibility(View.GONE);
                                searchView.setVisibility(View.GONE);
                                tv_nodata.setVisibility(View.VISIBLE);
                                UIUtil.stopProgressDialog(getActivity());
                                Toast.makeText(getActivity(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. logging out..");
                RetrofitAPI.getInstance(getActivity()).getApi().logout(new Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());
                            Log.e("API", "logout-" + object.toString());
                            Toast.makeText(getActivity(), "Logged Out successfully", Toast.LENGTH_SHORT).show();
                            PreferencesManger.clearPreferences(getActivity());

                            startActivity(new Intent(getActivity(), MainLandingActivity.class));
                            //overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                            //finishAffinity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        PreferencesManger.clearPreferences(getActivity());
                        UIUtil.stopProgressDialog(getActivity());
                        Toast.makeText(getActivity(), "Logout successfully..", Toast.LENGTH_SHORT).show();
                        // finish();
                        startActivity(new Intent(getActivity(), MainLandingActivity.class));
                        //overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
