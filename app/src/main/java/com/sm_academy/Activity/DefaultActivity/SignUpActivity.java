package com.sm_academy.Activity.DefaultActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.gson.JsonObject;
import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.SignupResponse;
import com.sm_academy.ModelClass.User;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity {
    private TextView etFirstName, etLastName, etEmail, etPhoneno, btnNext;
    private RadioButton otherView, maleView, femaleView;
    private TextView resendBtn, receivetextOtp, changenoBtn, textView;
    private PinEntryEditText pinEntry;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private ArrayList<User> signupResponseArrayList;
    private String firstName, lastName, email, phone;
    private CharSequence otp;
    private char otp_1, otp_2, otp_3, otp_4;
    public int counter;
    private long timeleft = 120000;
    private AlertDialog alertDialog;
    private String gen = "";
    private RadioButton radioButton1, radioButton2, radioButton3;
    private RadioGroup rbg;
    private RadioButton gender;
    private ImageView cancelBtn;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }
        rbg = (RadioGroup) findViewById(R.id.radioGroup1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton3 = findViewById(R.id.radioButton3);
        //int selected = rbg.getCheckedRadioButtonId();
        // gender = (RadioButton) findViewById(selected);

        //gen = gender.getText();


        // find the radiobutton by the previously returned id

        //gen = gender.getText().toString();

        //Log.e("radioButton1", "---" + gen);
    /*    if (radioButton1.isChecked()) {
            gen = "Male";
            Log.e("radioButton1", "---" + gen);

        } else if (radioButton2.isChecked()) {
            gen = "Female";
            Log.e("radioButton2", "---" + gen);
        }*/
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        //etDob = findViewById(R.id.etDob);
        etPhoneno = findViewById(R.id.etPhoneno);

        btnNext = findViewById(R.id.btnNext);


        signupResponseArrayList = new ArrayList<>();
        //maleView = findViewById(R.id.maleView);
        //femaleView = findViewById(R.id.femaleView);
        //otherView = findViewById(R.id.otherView);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), ReadingComponentActivity.class));
                //startActivity(new Intent(getApplicationContext(), OTPActivity.class));
                registration();


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);

        }
        return true;
    }

    private boolean validateCredentials() {

        if (etFirstName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter First Name", Toast.LENGTH_SHORT).show();
            // etUserName.setError("Please enter username");
            return false;
        }

        if (etLastName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter Last Name", Toast.LENGTH_SHORT).show();
            //etPassword.setError("Please enter Password");
            return false;
        }
        if (etEmail.getText().toString().isEmpty() || !etEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            Toast.makeText(getApplicationContext(), "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
            //etPassword.setError("Please enter Password");
            return false;
        }

        String s = etPhoneno.getText().toString();
        if (s.length() > 15) {
            Toast.makeText(getApplicationContext(), "Mobile Number can not be more than 15 digits ", Toast.LENGTH_SHORT).show();
            //etPassword.setError("Please enter Password");
            return false;
        } else if (s.length() < 10) {
            Toast.makeText(getApplicationContext(), " Mobile Number must be atleast 10 digits", Toast.LENGTH_SHORT).show();
            //etPassword.setError("Please enter Password");
            return false;
        }

        if (rbg.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            Toast.makeText(getApplicationContext(), "Please Select Your Gender ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButton1:
                if (checked)
                    gen = "";
                gen = "Male";
                Log.e("radioButton1", "---" + gen);
                break;
            case R.id.radioButton2:
                if (checked)
                    gen = "";
                gen = "Female";
                Log.e("radioButton2", "---" + gen);
                break;
            case R.id.radioButton3:
                if (checked)
                    gen = "";
                gen = "Other";
                Log.e("radioButton3", "---" + gen);
                break;
        }
    }

    public void dilogbox() {
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.activity_otp, null);
        textView = confirmDialog.findViewById(R.id.textView);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        receivetextOtp = confirmDialog.findViewById(R.id.receivetextOtp);
        resendBtn = confirmDialog.findViewById(R.id.resendBtn);
        changenoBtn = confirmDialog.findViewById(R.id.changenoBtn);
        cancelBtn = confirmDialog.findViewById(R.id.cancelBtn);
        resendBtn.setVisibility(View.GONE);
        receivetextOtp.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        pinEntry = confirmDialog.findViewById(R.id.txt_pin_entry);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                alertDialog.dismiss();
            }
        });
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                registration();
            }
        });
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    otp = str;
                    CharSequence test = otp;
                    otp_1 = test.charAt(0);
                    otp_2 = test.charAt(1);
                    otp_3 = test.charAt(2);
                    otp_4 = test.charAt(3);
                    otpVarification();
                   /* if (str.toString().equals("1234")) {
                        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG).equalsIgnoreCase("1")) {

                            Intent intent = new Intent(getApplicationContext(), MainLandingActivity.class);
                            // PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "1");
                            //intent.putExtra("Check", false);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), BatchActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                        Toast.makeText(SignUpActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                        pinEntry.setText(null);
                    }*/
                }
            });
        }


        changenoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BatchTimingActivity.class));
            }
        });
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                TextView tv = findViewById(R.id.txtview);
                tv.setText(message);
            }
        }
    };

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    CountDownTimer cTimer = null;

    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    private void registration() {
        try {
            if (validateCredentials()) {
                if (UIUtil.isInternetAvailable(this)) {
                    UIUtil.startProgressDialog(this, "Please Wait.. ");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", etEmail.getText().toString());
                    params.put("mobile_number", etPhoneno.getText().toString());
                    params.put("first_name", etFirstName.getText().toString());
                    params.put("last_name", etLastName.getText().toString());
                    params.put("gender", gen);

                    RetrofitAPI.getInstance(this).getApi().getRegistration(params, new Callback<SignupResponse>() {
                        @Override
                        public void success(SignupResponse object, Response response) {
                            try {
                                if (object.getStatus().equals("success")) {
                                    // Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    //gen = "";
                                    //rbg.clearCheck();
                                    dilogbox();
                                    counter = 120;
                                    textView.setVisibility(View.VISIBLE);
                                    countDownTimer = new CountDownTimer(120000, 1000) {
                                        public void onTick(long millisUntilFinished) {
                                            textView.setText(String.valueOf(counter) + " " + "Sec");
                                            counter--;
                                        }

                                        public void onFinish() {
                                            resendBtn.setVisibility(View.VISIBLE);
                                            receivetextOtp.setVisibility(View.VISIBLE);
                                            textView.setVisibility(View.GONE);
                                            //textView.setText("FINISH!!");
                                        }
                                    }.start();

                                    signupResponseArrayList.add(object.getUser());
                                    for (int i = 0; i < signupResponseArrayList.size(); i++) {
                                        firstName = signupResponseArrayList.get(i).getFirstName();
                                        lastName = signupResponseArrayList.get(i).getLastName();
                                        email = signupResponseArrayList.get(i).getEmail();
                                        phone = signupResponseArrayList.get(i).getMobileNumber();

                                    }
                                    Log.e("firstName", "---" + firstName);
                                    Log.e("lastName", "---" + lastName);
                                    Log.e("email", "---" + email);
                                    Log.e("phone", "---" + phone);

                                    Log.e("data", "---" + signupResponseArrayList.toString());
                                    //Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();

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

    private void otpVarification() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("email", email);
                jsonObject.addProperty("mobile_number", phone);
                jsonObject.addProperty("first_name", firstName);
                jsonObject.addProperty("last_name", lastName);
                jsonObject.addProperty("gender", gen);
                jsonObject.addProperty("otp_1", otp_1);
                jsonObject.addProperty("otp_2", otp_2);
                jsonObject.addProperty("otp_3", otp_3);
                jsonObject.addProperty("otp_4", otp_4);
                jsonObject.addProperty("auth_token", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));

                RetrofitAPI.getInstance(this).getApi().getOtpVarification(jsonObject, new Callback<SignupResponse>() {
                    @Override
                    public void success(SignupResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                pinEntry.setText(null);
                                Toast.makeText(SignUpActivity.this, object.getMessage(), Toast.LENGTH_SHORT).show();
                                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG).equalsIgnoreCase("1")) {

                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    // PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "1");
                                    //intent.putExtra("Check", false);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), BatchDetailsTabActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(SignUpActivity.this, object.getMessage(), Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                pinEntry.setText(null);
                                pinEntry.setText("");
                                dilogbox();
                                counter = 120;
                                textView.setVisibility(View.VISIBLE);
                                new CountDownTimer(120000, 1000) {
                                    public void onTick(long millisUntilFinished) {
                                        textView.setText(String.valueOf(counter) + " " + "Sec");
                                        counter--;
                                    }

                                    public void onFinish() {
                                        resendBtn.setVisibility(View.VISIBLE);
                                        receivetextOtp.setVisibility(View.VISIBLE);
                                        textView.setVisibility(View.GONE);
                                        //textView.setText("FINISH!!");
                                    }
                                }.start();


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
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
}
