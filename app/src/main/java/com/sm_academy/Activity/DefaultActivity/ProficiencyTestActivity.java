package com.sm_academy.Activity.DefaultActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sm_academy.API.RetrofitAPI;


import com.sm_academy.Activity.BaseActivity;


import com.sm_academy.Adapters.ProfieciencyTestListAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.PTDetails.PTAnswers;
import com.sm_academy.ModelClass.PTDetails.PTDetails;

import com.sm_academy.ModelClass.StatusResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProficiencyTestActivity extends BaseActivity {

    private ListView recyclerTestView;
    private ProfieciencyTestListAdapter expandableListAdapter;
    private ArrayList<PTAnswers> ptAnswersArrayList;
    private List arrayListsize;
    private Button submitBtn;
    private TextView timerView;
    String id = "", s;
    // String answer_id = "";
    private JsonArray answer_id;
    public HashMap<Integer, String> map = new HashMap();
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proficiency_test);

        recyclerTestView = findViewById(R.id.recyclerTestView);
        submitBtn = findViewById(R.id.submitBtn);
        timerView = findViewById(R.id.timerView);
        answer_id = new JsonArray();
        ptAnswersArrayList = new ArrayList<>();
        //  questionsArrayList = new ArrayList<>();

        //recyclerTestView.setHasFixedSize(true);
        //recyclerTestView.setLayoutManager(new LinearLayoutManager(this));

        expandableListAdapter = new ProfieciencyTestListAdapter(this, 0, ptAnswersArrayList);
        expandableListAdapter.SetOnItemClickListener(new ProfieciencyTestListAdapter.OnItemClickListener() {
            @Override
            public void onAnswerZeroClick(View view, int position) {
                Log.e("data", "" + position);
                // answer_id = answer_id + ptAnswersArrayList.get(position).getOptionId0();
                map.put(ptAnswersArrayList.get(position).getId(), ptAnswersArrayList.get(position).getOptionId0().toString());
                //answer_id.add(new JsonPrimitive(ptAnswersArrayList.get(position).getOptionId0().toString()));
                Log.e("answerid 0", "===" + map);


            }

            @Override
            public void onAnswerOneClick(View view, int position) {

                map.put(ptAnswersArrayList.get(position).getId(), ptAnswersArrayList.get(position).getOptionId1().toString());
                // answer_id.add(new JsonPrimitive(ptAnswersArrayList.get(position).getOptionId1().toString()));
                Log.e("data", "" + position);
                Log.e("answerid 1", "===" + map);

            }

            @Override
            public void onAnswerTwoClick(View view, int position) {
                map.put(ptAnswersArrayList.get(position).getId(), ptAnswersArrayList.get(position).getOptionId2().toString());
                //answer_id.add(new JsonPrimitive(ptAnswersArrayList.get(position).getOptionId2().toString()));
                Log.e("data", "" + position);
                Log.e("answerid 2", "===" + map);
            }

            @Override
            public void onAnswerThreeClick(View view, int position) {
                map.put(ptAnswersArrayList.get(position).getId(), ptAnswersArrayList.get(position).getOptionId3().toString());
                //answer_id.add(new JsonPrimitive(ptAnswersArrayList.get(position).getOptionId3().toString()));
                Log.e("data", "" + position);
                Log.e("answerid 3", "===" + map);
            }
        });
        ptTestDetails();


        // expandableListAdapter.notifyDataSetChanged();
        /*expandableListAdapter.SetOnItemClickListener(new ProfieciencyTestListAdapter.OnItemClickListener() {
            @Override
            public void onAnswerZeroClick(String view, int position) {

                answer_id = answer_id + ptAnswersArrayList.get(position).getOptionId0();
                Log.e("answerid 0", "===" + answer_id);
            }

            @Override
            public void onAnswerOneClick(String view, int position) {
                answer_id = answer_id + ptAnswersArrayList.get(position).getOptionId1();
                Log.e("answerid 1", "===" + answer_id);
            }

            @Override
            public void onAnswerTwoClick(String view, int position) {
                answer_id = answer_id + ptAnswersArrayList.get(position).getOptionId2();
                Log.e("answerid 2", "===" + answer_id);
            }

            @Override
            public void onAnswerThreeClick(String view, int position) {
                answer_id = answer_id + ptAnswersArrayList.get(position).getOptionId3();
                Log.e("answerid 3", "===" + answer_id);
            }

            @Override
            public void onItemClick(String view, int position) {

            }
        });
*/


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("answerid 2", "===" + map.size());
                Log.e("answerid 1", "===" + id.length());
                if (ptAnswersArrayList.size() == map.size()) {
                    submitBtn.setVisibility(View.VISIBLE);
                    s = map.toString();
                    s = s.replace("=", "=>");

                    Log.e("String", "===" + s);

                   /* Set<Map.Entry<Integer, String>> hashSet = map.entrySet();
                    for (Map.Entry entry : hashSet) {

                        System.out.println("Key=" + entry.getKey() + ", Value=" + entry.getValue());

                        s.add("" + entry.getKey() + ":" + entry.getValue());
                        //System.out.println(s);

                    }*/
                    submitBtn();

                } else {
                    Toast.makeText(getApplicationContext(), "Answer all the questions", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    private void ptTestDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                //Map<String, String> params = new HashMap<String, String>();
                // params.put("course_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_ID));
                //params.put("auth_token", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));

                //params.put("course_id", String.valueOf(getdepartCategory(depart)));
                RetrofitAPI.getInstance(this).getApi().getptedetails(new Callback<PTDetails>() {
                    @Override
                    public void success(PTDetails object, Response response) {
                        try {
                            if (response.getStatus() == 200) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                ptAnswersArrayList.addAll(object.getPtQuestions());
                                recyclerTestView.setAdapter(expandableListAdapter);
                                expandableListAdapter.notifyDataSetChanged();
                              /*  arrayListsize = new ArrayList();
                                for (int i = 0; i < ptAnswersArrayList.size(); i++) {

                                    arrayListsize.add(ptAnswersArrayList.get(i).getQuestion());
                                    id = id + ptAnswersArrayList.get(i).getId().toString();
                                }*/
                                timerView.setText("Total Questions : " + ptAnswersArrayList.size());
                                Log.e("size1", "" + arrayListsize.size());
                                Log.e("size2", "" + ptAnswersArrayList.size());
                                Log.e("q_id", "" + id);
                                Log.e("q_id", "" + (id.length() - 1));



                               /* if (batchArraylist.size() <= 0) {
                                    batchView.setVisibility(View.GONE);
                                    //etSearchdefaulters.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                } else {
                                    batchView.setVisibility(View.VISIBLE);
                                    //etSearchdefaulters.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                }*/

                                Log.e("data", "---" + ptAnswersArrayList.toString());

                              /*  arrayList.clear();
                                arrayList.addAll(object.getAssignedBatchesIds());
                                if (arrayList.size() > 0) {
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ENROLL_STATUS, "true");
                                    //commonflag = "true";
                                }*/
                                // Log.e("arr", "===" + arrayList);

                                //Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();

                                logout();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void submitBtn() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");

                JsonObject jsonObject = new JsonObject();
                Map<String, String> params = new HashMap<String, String>();
                params.put("pt_answers", s);


                RetrofitAPI.getInstance(this).getApi().submitPTETestDetails(params, new Callback<StatusResponse>() {
                    @Override
                    public void success(StatusResponse object, Response response) {
                        try {
                            if (response.getStatus() == 200) {
                                Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                                // startActivity(new Intent(getApplicationContext(), PteTestResultActivity.class));
                                Intent intent = new Intent(getApplicationContext(), PteTestResultActivity.class);

                                intent.putExtra("answer", object.getCorrectAnswers().toString());
                                intent.putExtra("question", object.getTotalQuestions().toString());
                                intent.putExtra("percentage", object.getPercentage().toString());
                                startActivity(intent);

                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                            logout();
                        }
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();

                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void logout() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. logging out..");
                RetrofitAPI.getInstance(this).getApi().logout(new Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("API", "logout-" + object.toString());
                            Toast.makeText(getApplicationContext(), "Logged Out successfully", Toast.LENGTH_SHORT).show();
                            PreferencesManger.clearPreferences(getApplicationContext());

                            startActivity(new Intent(getApplicationContext(), MainLandingActivity.class));
                            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                            finishAffinity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        PreferencesManger.clearPreferences(getApplicationContext());
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "Logout successfully..", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainLandingActivity.class));
                        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

