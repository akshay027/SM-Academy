package com.sm_academy.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.BatchTimingActivity;

import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Adapters.CompletedSessionAdapter;
import com.sm_academy.Adapters.OngoingBatchAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.BatchTabDetails.BatchResponse;
import com.sm_academy.ModelClass.BatchTabDetails.Ongoingbatches;
import com.sm_academy.ModelClass.LiveSession.CompletedSession;
import com.sm_academy.ModelClass.LiveSession.LiveSessionResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OngoingBatchFragment extends Fragment {

    private OngoingBatchAdapter ongoingBatchAdapter;
    private ListView lvongoingBatch;
    private EditText etSearchdefaulters;
    private Handler handler;
    private String pos;
    public static final int TIME_OUT = 1000;
    private ArrayList<Ongoingbatches> ongoingbatchesArrayList, searchArrayList;
    private TextView tv_nodata;
    private LinearLayout searchView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public OngoingBatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongoing_batch, container, false);
        lvongoingBatch = (ListView) view.findViewById(R.id.lvongoingBatch);

        ongoingbatchesArrayList = new ArrayList<>();
        searchArrayList = new ArrayList<>();
        searchView = view.findViewById(R.id.searchView);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        etSearchdefaulters = (EditText) view.findViewById(R.id.etSearchdefaulters);
        ongoingBatchAdapter = new OngoingBatchAdapter(getActivity(), ongoingbatchesArrayList);
        // LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        //etSearchdefaulters = view.findViewById(R.id.etSearchdefaulters);

        handler = new Handler();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        lvongoingBatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), BatchTimingActivity.class);
                intent.putExtra("name", ongoingbatchesArrayList.get(position).getName());
                intent.putExtra("id", ongoingbatchesArrayList.get(position).getId().toString());
                intent.putExtra("money", ongoingbatchesArrayList.get(position).getPrice());
                intent.putExtra("flag", ongoingbatchesArrayList.get(position).getAlreadyEnrolled().toString());
                intent.putExtra("seats", ongoingbatchesArrayList.get(position).getSeatsLeft().toString());
                startActivity(intent);
            }
        });
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
                        ongoingbatchesArrayList.clear();
                        ongoingbatchesArrayList.addAll(searchArrayList);
                        ongoingBatchAdapter.notifyDataSetChanged();
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
            ongoingbatchesArrayList.clear();
            for (int i = 0; i < searchArrayList.size(); i++) {
                Ongoingbatches defaulterDatamodel = searchArrayList.get(i);
                if (defaulterDatamodel.getName().toLowerCase().contains(search.toLowerCase()) ||
                        defaulterDatamodel.getPrice().toLowerCase().contains(search.toLowerCase()) ||
                        defaulterDatamodel.getStartDateEndDateInRangeInReadableFormat().toLowerCase().contains(search.toLowerCase()) ||
                        defaulterDatamodel.getLiveSessionHoursDisplay().toLowerCase().contains(search.toLowerCase()) ||
                        defaulterDatamodel.getSeatsLeft().toString().contains(search.toLowerCase())) {
                    ongoingbatchesArrayList.add(defaulterDatamodel);
                }
            }
            if (ongoingbatchesArrayList.size() <= 0) {
                etSearchdefaulters.setError("No Record found");
            }
            ongoingBatchAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSessionData() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("course_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.COURSE_ID));
                params.put("type", PreferencesManger.getStringFields(getActivity(), Constants.Pref.COURSE_TYPE));
                //Map<String, String> params = new HashMap<String, String>();
                //params.put("auth_token", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_TOKEN));

                RetrofitAPI.getInstance(getActivity()).getApi().getBatchTabDetails(params, new Callback<BatchResponse>() {
                    @Override
                    public void success(BatchResponse defaulterResponseModel, Response response) {
                        if (defaulterResponseModel.getStatus().equals("success")) {
                            try {
                                UIUtil.stopProgressDialog(getActivity());
                                Log.e("jsonObject", "jsonObject --- " + defaulterResponseModel.toString());
                                searchArrayList.clear();
                                ongoingbatchesArrayList.clear();
                                searchArrayList.addAll(defaulterResponseModel.getOngoingBatches());
                                ongoingbatchesArrayList.addAll(defaulterResponseModel.getOngoingBatches());
                                lvongoingBatch.setAdapter(ongoingBatchAdapter);
                                ongoingBatchAdapter.notifyDataSetChanged();
                                Log.e("completed", " --- " + ongoingbatchesArrayList);
                                if (ongoingbatchesArrayList.size() <= 0) {
                                    lvongoingBatch.setVisibility(View.GONE);
                                    searchView.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText("You do not have any ongoing batch");
                                } else {
                                    lvongoingBatch.setVisibility(View.VISIBLE);
                                    searchView.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                    tv_nodata.setText("");
                                }
                                // completedSessionAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            lvongoingBatch.setVisibility(View.GONE);
                            searchView.setVisibility(View.GONE);
                            tv_nodata.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), defaulterResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {

                            if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                                Toast.makeText(getActivity(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();
                                UIUtil.stopProgressDialog(getActivity());
                                lvongoingBatch.setVisibility(View.GONE);
                                searchView.setVisibility(View.GONE);
                                tv_nodata.setVisibility(View.VISIBLE);
                                logout();
                            } else {
                                Toast.makeText(getActivity(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                                lvongoingBatch.setVisibility(View.GONE);
                                searchView.setVisibility(View.GONE);
                                tv_nodata.setVisibility(View.VISIBLE);
                                UIUtil.stopProgressDialog(getActivity());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (
                Exception e) {
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
