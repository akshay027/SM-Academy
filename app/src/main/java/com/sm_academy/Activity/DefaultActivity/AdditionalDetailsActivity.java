package com.sm_academy.Activity.DefaultActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.API.RetrofitAPIInterface;
import com.sm_academy.Activity.BaseActivity;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.AdditionalDetails.AdditionDetailsResponse;
import com.sm_academy.ModelClass.SignupResponse;
import com.sm_academy.ModelClass.StatusResponse;
import com.sm_academy.ModelClass.UploadObject;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdditionalDetailsActivity extends BaseActivity {
    private EditText etHighestQualification, etAddress;
    private Button btnNext;
    private TextView etDOB;
    private LinearLayout dobView;
    private long fromDate;
    ProgressBar progressBar;
    String id, money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_details);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        etDOB = findViewById(R.id.etDOB);
        etHighestQualification = findViewById(R.id.etHighestQualification);
        etAddress = findViewById(R.id.etAddress);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        money = intent.getStringExtra("money");

        btnNext = findViewById(R.id.btnNext);
        dobView = findViewById(R.id.dobView);
        dobView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.46:3000/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPIInterface service = retrofit.create(RetrofitAPIInterface.class);
        service.profilePicture("learner"+"/"+);*/

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additionalDetails();
                // newadditionalDetails();
            }
        });
    }

    private boolean validateCredentials() {

        if (etDOB.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter DOB", Toast.LENGTH_SHORT).show();
            // etUserName.setError("Please enter username");
            return false;
        }
        if (etHighestQualification.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter Highest Qualification", Toast.LENGTH_SHORT).show();
            //etPassword.setError("Please enter Password");
            return false;
        }
        if (etAddress.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter Address", Toast.LENGTH_SHORT).show();
            // etUserName.setError("Please enter username");
            return false;
        }
        return true;
    }

    private void openDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                monthOfYear = monthOfYear + 1;

                String month, day;
                //Calendar myCalendar = Calendar.getInstance();
                if (monthOfYear < 10) {
                    month = "0" + String.valueOf(monthOfYear);
                } else {
                    month = String.valueOf(monthOfYear);
                }

                if (dayOfMonth < 10) {
                    day = "0" + String.valueOf(dayOfMonth);
                } else {
                    day = String.valueOf(dayOfMonth);
                }

                try {
                    String str_date = day + "-" + month + "-" + String.valueOf(year);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = (Date) formatter.parse(str_date);
                    fromDate = date.getTime();
                    System.out.println("Today is " + date.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                etDOB.setText(day + "/" + month + "/" + String.valueOf(year));


            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void additionalDetails() {
        try {
            if (validateCredentials()) {
                if (UIUtil.isInternetAvailable(this)) {
                    UIUtil.startProgressDialog(this, "Please Wait.. ");
                    JsonObject jsonObject = new JsonObject();
                    JsonObject jsonObject1 = new JsonObject();
                    jsonObject.addProperty("highest_qualification", etHighestQualification.getText().toString());
                    jsonObject.addProperty("date_of_birth", etDOB.getText().toString());
                    jsonObject.addProperty(":address", etAddress.getText().toString());
                    jsonObject1.add("learner", jsonObject);
                    jsonObject1.addProperty("id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_ID));
                    jsonObject1.addProperty("batch_id", id);

                    RetrofitAPI.getInstance(this).getApi().getadditionalDetails(jsonObject1, new Callback<StatusResponse>() {
                        @Override
                        public void success(StatusResponse object, Response response) {
                            try {
                                if (response.getStatus() == Constants.SUCCESS) {
                                    // Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    //  Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_DATE_OF_BIRTH, etDOB.getText().toString());
                                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("money", money);
                                    startActivity(intent);
                                    //Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
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
