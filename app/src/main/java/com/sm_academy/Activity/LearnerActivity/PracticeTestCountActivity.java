package com.sm_academy.Activity.LearnerActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Activity.DefaultActivity.ProficiencyTestActivity;
import com.sm_academy.Adapters.PracticeTestCountAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.PracticeTest.AnswerKeyResponse;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestResult;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestTopicWise;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestTopicWiseResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PracticeTestCountActivity extends AppCompatActivity {
    private PracticeTestCountAdapter practiceTestCountAdapter;
    private RecyclerView practiceTestCountView;

    private ArrayList<PracticeTestTopicWise> practiceTestCountViewArrayList;
    String course_id, sectionName, practice_test_id;

    private Context context = this;
    private List<Integer> arrayList;
    private TextView tv_nodata;
    private String commonflag;
    private ActionBar actionBar;

    private WebView totalQuestionView, tvAnswerView, correctAnswerView, percentageView, timeTakenView;
    Button cancelBtn;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_test_count);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        course_id = intent.getStringExtra("id");
        sectionName = intent.getStringExtra("section_name");
        practice_test_id = intent.getStringExtra("practice_test_id");

        practiceTestCountView = findViewById(R.id.practiceTestCountView);
        tv_nodata = findViewById(R.id.tv_nodata);
        practiceTestCountViewArrayList = new ArrayList();

        practiceTestListCountViewDetails();

        practiceTestCountView.setHasFixedSize(true);
        practiceTestCountView.setLayoutManager(new LinearLayoutManager(this));
        practiceTestCountAdapter = new PracticeTestCountAdapter(this, practiceTestCountViewArrayList, context);
        practiceTestCountView.setAdapter(practiceTestCountAdapter);
        practiceTestCountAdapter.notifyDataSetChanged();

        Log.e("Final course", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                practiceTestListCountViewDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        practiceTestCountAdapter.SetOnItemClickListener(new PracticeTestCountAdapter.OnItemClickListener() {

            @Override
            public void onstartClick(View view, PracticeTestTopicWise position) {
                Intent intent = new Intent(getApplicationContext(), PracticeTestStartActivity.class);
                intent.putExtra("id", course_id);
                intent.putExtra("section_name", sectionName);
                intent.putExtra("readingcomponent", "Study Materials");
                intent.putExtra("practice_test_id", position.getId().toString());
                startActivity(intent);
            }

            @Override
            public void onView(View view, PracticeTestTopicWise position) {
                // dilogbox();

            }

            @Override
            public void onAnswerClick(View view, PracticeTestTopicWise position) {
                testAnswerScore(position.getId().toString());
                dilogbox();
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menusearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                practiceTestCountAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                practiceTestCountAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
            return true;
        }
/*        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {
            // alertBox();
        }
        if (id == R.id.dashboard) {
            // alertBox();
            startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
        }*/
        switch (item.getItemId()) {


            //noinspection SimplifiableIfStatement


            case R.id.logout:
                showLogoutConfirmation();
                return true;
            case R.id.profile:
                // showLogoutConfirmation();
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.dashboard:
                if (id == R.id.dashboard) {
                    // alertBox();
                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN).equalsIgnoreCase("true")) {
                        startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), ProficiencyTestActivity.class));
                    }

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showLogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(this, R.style.AppTheme_AppBarOverlay);

        builder.setTitle("Confirmation");
        String message = "Do you want to logout?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();

            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void logout() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. logging out..");
                //Map<String, String> params = new HashMap<String, String>();
                //params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().logout(new Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("API", "logout-" + object.toString());
                            Toast.makeText(getApplicationContext(), "Logged Out successfully", Toast.LENGTH_SHORT).show();
                            //  PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "0");
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
                        // PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "0");
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

    private void practiceTestListCountViewDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();
                if (practice_test_id == null) {
                    params.put("section_id", course_id);
                    params.put("topic_id", " ");
                } else {
                    params.put("section_id", course_id);
                    params.put("topic_id", practice_test_id);
                }
                //params.put("auth_token", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));

                RetrofitAPI.getInstance(this).getApi().getPracticeTestTopicWiseResponse(params, new Callback<PracticeTestTopicWiseResponse>() {
                    @Override
                    public void success(PracticeTestTopicWiseResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                practiceTestCountViewArrayList.clear();
                                practiceTestCountViewArrayList.addAll(object.getPracticeTests());
                                if (practiceTestCountViewArrayList.size() <= 0) {
                                    practiceTestCountView.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText("No Data Available");
                                } else {
                                    practiceTestCountView.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                    tv_nodata.setText("");
                                }
                                practiceTestCountAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + practiceTestCountViewArrayList.toString());

                              /*  arrayList.clear();
                                arrayList.addAll(object.getAssignedBatchesIds());
                                if (arrayList.size() > 0) {
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ENROLL_STATUS, "true");
                                    //commonflag = "true";
                                }*/
                                Log.e("arr", "===" + arrayList);

                                // Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                tv_nodata.setVisibility(View.VISIBLE);
                                practiceTestCountView.setVisibility(View.GONE);
                                tv_nodata.setText(object.getMessage());
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

    public void dilogbox() {
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.score_card_for_answer, null);

        tvAnswerView = confirmDialog.findViewById(R.id.tvAnswerView);
        // cancelBtn = confirmDialog.findViewById(R.id.cancelBtn);


        //Creating an alertdialog builder
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setCancelable(false);
        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog

        alert.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final android.app.AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
    }

    private void testAnswerScore(String id) {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);


                RetrofitAPI.getInstance(this).getApi().gettestAnswerScore(params, new Callback<AnswerKeyResponse>() {
                    @Override
                    public void success(AnswerKeyResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());

                                tvAnswerView.loadData("Time Taken : " + object.getAnswers_key(), "text/html", "UTF-8");

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
}
