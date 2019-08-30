package com.sm_academy.Activity.DefaultActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.BaseActivity;
import com.sm_academy.Activity.LearnerActivity.LearnerDashBoardActivity;
import com.sm_academy.Adapters.CalenderEventAdapter;
import com.sm_academy.Adapters.CustomAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.BatchDetails;
import com.sm_academy.ModelClass.BatchTiming;
import com.sm_academy.ModelClass.BatchTimingResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;


import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.widget.Toast.LENGTH_SHORT;

public class BatchTimingActivity extends BaseActivity {
    private ArrayList<BatchTiming> dates;
    long milliseconds, milliseconds1, milliseconds2, milliseconds3;
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
    private String month, year, trip_id;
    private TextView monthtv, tv_nodata;
    private Date d;
    private Button btnPay;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> listenarrayList = new ArrayList<>();
    private ArrayList<String> writearrayList = new ArrayList<>();
    private ArrayList<String> readarrayList = new ArrayList<>();
    private ArrayList<String> speakarrayList = new ArrayList<>();
    String id, money, seats, flag, name;
    String curDate;

    private RecyclerView calenderView;
    private Button cancelBtn;
    private ArrayList<String> calenderResponseArraylist;
    private ArrayList<BatchTiming> calenderArraylist;
    private CalenderEventAdapter calenderEventAdapter;
    private Context context = this;
    private ArrayList simplelist;
    private TextView statusMessage, cantMessage, seatMessage;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_timing);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();
        statusMessage = findViewById(R.id.statusMessage);
        //cantMessage = findViewById(R.id.cantMessage);
        //seatMessage = findViewById(R.id.seatMessage);
        monthtv = findViewById(R.id.monthtv);
        compactCalendarView = findViewById(R.id.compactCalendarView);

        btnPay = findViewById(R.id.btnPay);
        dates = new ArrayList<>();
        calenderArraylist = new ArrayList();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");
        money = intent.getStringExtra("money");
        flag = intent.getStringExtra("flag");
        seats = intent.getStringExtra("seats");
        Log.e("money", "---" + money);
        Log.e("already enroll flag", "" + flag);
        Log.e("seats", "" + seats);
        Log.e("can enroll", "" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.ENROLL_STATUS));
        if (TextUtils.isEmpty(name)) {
            actionBar.setTitle("Batch Timings ");
        } else {
            actionBar.setTitle(name + " " + "Timings");
        }
        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.ENROLL_STATUS).equalsIgnoreCase("false")) {
            if (seats.equals("0")) {
                btnPay.setVisibility(View.GONE);
                statusMessage.setVisibility(View.VISIBLE);
                Log.e("11111111", "" + "abc");
                statusMessage.setText("No Seats");
            } else {
                if (flag.equals("true")) {
                    btnPay.setVisibility(View.GONE);
                    statusMessage.setVisibility(View.VISIBLE);
                    statusMessage.setText("Already Enrolled");
                    Log.e("2222222222", "" + "abc");
                } else {
                    btnPay.setVisibility(View.GONE);
                    statusMessage.setVisibility(View.VISIBLE);
                    Log.e("3333333333", "" + "abc");
                    statusMessage.setText("Already Enrolled to another batch");
                }
            }
        } else {
            Log.e("444444444", "" + "abc");
            if (seats.equals("0")) {
                btnPay.setVisibility(View.GONE);
                statusMessage.setVisibility(View.VISIBLE);
                statusMessage.setText("No Seats");
            } else {
                btnPay.setVisibility(View.VISIBLE);
                statusMessage.setVisibility(View.GONE);
            }
        }

    /*        btnPay.setVisibility(View.GONE);
            statusMessage.setVisibility(View.VISIBLE);
            //seatMessage.setVisibility(View.GONE);
            statusMessage.setText("You can not enroll");
        } else {
            btnPay.setVisibility(View.VISIBLE);
            statusMessage.setVisibility(View.GONE);
        }*/
      /*  if (seats.equals("0")) {
            btnPay.setVisibility(View.GONE);
            statusMessage.setVisibility(View.GONE);
            seatMessage.setText("No Seats Left");
        }

        if (flag.equals("true")) {
            btnPay.setVisibility(View.GONE);
            seatMessage.setVisibility(View.GONE);
            statusMessage.setText("Already enroll");
        }*/
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM", Locale.getDefault());
        month = simpleDateFormat1.format(cal1.getTime());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy", Locale.getDefault());
        year = simpleDateFormat2.format(cal1.getTime());
        getcalenderdate();
        year = simpleDateFormat2.format(cal1.getTime());

        btnPay.setText("Enroll");

           /*     if (contactListFiltered.get(position).getName()) {
            btnPay.setVisibility(View.VISIBLE);
        } else {
            btnPay.setVisibility(View.VISIBLE);
        }*/

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        monthtv.setText(simpleDateFormat.format(cal.getTime()));
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        // compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        //getcalenderdate();
        compactCalendarView.showCalendarWithAnimation();
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                //Calendar date = Calendar.getInstance();
// for your date format use
                calenderArraylist.clear();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
// set a string to format your current date
                curDate = sdf.format(dateClicked.getTime());
// print the date in your log cat
                Log.d("CUR_DATE", curDate);
                for (int i = 0; i < arrayList.size(); i++) {

                    String date = arrayList.get(i);
                    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                    d = new Date();
                    try {
                        d = f.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    milliseconds = d.getTime();
                    Log.e("data", "======" + d + "   ajhvshdgvhjsdav :  " + dateClicked);
                    if (dateClicked.equals(d)) {

                        calenderArraylist.clear();
                        for (int j = 0; j < dates.size(); j++) {
                            if (dates.get(j).getSessionDate().equalsIgnoreCase(curDate)) {
                                calenderArraylist.add(dates.get(j));
                            }
                        }


                        /*Intent intent = new Intent(getApplicationContext(), CalenderEventActivity.class);
                        intent.putExtra("list", calenderArraylist);
                        startActivity(intent);*/
                    }

                    //List<Event> events = compactCalendarView.getEvents(dateClicked);
                    //Log.d("TAG", "Day was clicked: " + dateClicked + " with events " + events);

                    Log.e("data", "======" + milliseconds);
                }
                dilogbox();


                //Intent calIntent = new Intent(Intent.ACTION_INSERT);
                // calIntent.setData(CalendarContract.Events.CONTENT_URI);
                //startActivity(calIntent);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthtv.setText(simpleDateFormat.format(firstDayOfNewMonth));
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM", Locale.getDefault());
                month = simpleDateFormat1.format(firstDayOfNewMonth);
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy", Locale.getDefault());
                year = simpleDateFormat2.format(firstDayOfNewMonth);
                // getcalenderdate();
                Log.d("TAG", "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_DATE_OF_BIRTH))) {
                    Intent intent = new Intent(getApplicationContext(), AdditionalDetailsActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("money", money);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("money", money);
                    startActivity(intent);
                }
            }
        });
    }

    private void getcalenderdate() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("batch_id", id);
                // params.put("auth_token", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));
                RetrofitAPI.getInstance(this).getApi().getCalenderDetails(params, new Callback<BatchTimingResponse>() {
                    @Override
                    public void success(BatchTimingResponse dashRes, Response response) {
                        try {
                            if (response.getStatus() == Constants.SUCCESS) {
                                // UIUtil.stopProgressDialog(getApplicationContext());
                                dates.clear();
                                dates.addAll((dashRes.getLiveSessions()));
                                Log.e("dates", "-----" + dates);

                                //dilogbox();
                               /* listenarrayList.clear();
                                for (int j = 0; j < dates.size(); j++) {
                                    if (dates.get(j).getSectionName().equalsIgnoreCase("Listening")) {
                                        listenarrayList.add(dates.get(j).getSessionDate());
                                    }

                                }
                                for (int i = 0; i < listenarrayList.size(); i++) {

                                    String date = listenarrayList.get(i);
                                    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                                    d = new Date();
                                    try {
                                        d = f.parse(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    milliseconds = d.getTime();
                                    Event ev1 = new Event(R.drawable.circle3, milliseconds);
                                    compactCalendarView.addEvent(ev1, true);
                                    Log.e("datamilliseconds", "======" + milliseconds);
                                }*/


                                /*speakarrayList.clear();
                                for (int j = 0; j < dates.size(); j++) {
                                    if (dates.get(j).getSectionName().equalsIgnoreCase("Speaking")) {
                                        speakarrayList.add(dates.get(j).getSessionDate());
                                    }

                                }
                                for (int i = 0; i < speakarrayList.size(); i++) {

                                    String date = speakarrayList.get(i);
                                    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                                    d = new Date();
                                    try {
                                        d = f.parse(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    milliseconds = d.getTime();
                                    Event ev2 = new Event(R.drawable.circle3, milliseconds);
                                    compactCalendarView.addEvent(ev2, true);
                                    Log.e("datamilliseconds1", "======" + milliseconds);
                                }


                                readarrayList.clear();
                                for (int j = 0; j < dates.size(); j++) {
                                    if (dates.get(j).getSectionName().equalsIgnoreCase("Reading")) {
                                        readarrayList.add(dates.get(j).getSessionDate());
                                    }

                                }
                                for (int i = 0; i < readarrayList.size(); i++) {

                                    String date = readarrayList.get(i);
                                    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                                    d = new Date();
                                    try {
                                        d = f.parse(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    milliseconds = d.getTime();
                                    Event ev3 = new Event(R.drawable.circle3, milliseconds);
                                    compactCalendarView.addEvent(ev3, true);
                                    Log.e("datamilliseconds2", "======" + milliseconds);
                                }

                                writearrayList.clear();
                                for (int j = 0; j < dates.size(); j++) {
                                    if (dates.get(j).getSectionName().equalsIgnoreCase("Writing")) {
                                        writearrayList.add(dates.get(j).getSessionDate());
                                    }

                                }
                                for (int i = 0; i < writearrayList.size(); i++) {

                                    String date = readarrayList.get(i);
                                    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                                    d = new Date();
                                    try {
                                        d = f.parse(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    milliseconds = d.getTime();
                                    Event ev4 = new Event(R.drawable.circle4, milliseconds);
                                    compactCalendarView.addEvent(ev4, true);
                                    Log.e("datamilliseconds2", "======" + milliseconds);
                                }
*/

                                for (int i = 0; i < dates.size(); i++) {

                                    arrayList.add(dates.get(i).getSessionDate());
                                }

                                for (int i = 0; i < arrayList.size(); i++) {

                                    String date = arrayList.get(i);
                                    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                                    d = new Date();
                                    try {
                                        d = f.parse(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    milliseconds = d.getTime();
                                    Event ev1 = new Event(R.drawable.circle, milliseconds);
                                    compactCalendarView.addEvent(ev1, true);
                                    Log.e("data", "======" + milliseconds);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "" + dashRes.getMessage(), LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try{UIUtil.stopProgressDialog(getApplicationContext());
                        if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(getApplicationContext(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();

                            logout();
                        }else {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                        }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
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
            return true;
        }
        switch (item.getItemId()) {

            case R.id.logout:
                showLogoutConfirmation();
                return true;
     /*       case R.id.profile:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                // showLogoutConfirmation();
                return true;*/
            case R.id.dashboard:
                if (id == R.id.dashboard) {
                    // alertBox();
                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN).equalsIgnoreCase("true")) {
                        startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), PteTestStartActivity.class));
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

    /*  private void dilogbox() {
          AlertDialog.Builder builder = new AlertDialog.Builder(BatchTimingActivity.this);
          View view = new View(BatchTimingActivity.this);
          view = (View) getLayoutInflater().inflate(R.layout.activity_calender_event, null);
          //builder.setCustomTitle(titleView);
          builder.setView(view);
          calenderView =  view.findViewById(R.id.calenderView);
          ArrayAdapter<String> containerAdapter = new ArrayAdapter(BatchTimingActivity.this, android.R.layout.simple_list_item_1, calenderArraylist);
          calenderView.setAdapter(containerAdapter);
          final AlertDialog alert = builder.create();
          alert.show();

          builder.setNegativeButton("OK", null);
      }*/
    private void dilogbox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(BatchTimingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.activity_calender_event, null);
        //builder.setView(convertView);
        // builder.setTitle("List");
        builder.setView(convertView);
        calenderView = convertView.findViewById(R.id.calenderView);
        tv_nodata = convertView.findViewById(R.id.tv_nodata);
        //  cancelBtn = convertView.findViewById(R.id.cancelBtn);
        calenderView.setHasFixedSize(true);
        calenderView.setLayoutManager(new LinearLayoutManager(this));
        CalenderEventAdapter calenderEventAdapter = new CalenderEventAdapter(this, calenderArraylist);
        calenderView.setAdapter(calenderEventAdapter);

        if (calenderArraylist.size() <= 0) {
            calenderView.setVisibility(View.GONE);
            //etSearchdefaulters.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
        } else {
            calenderView.setVisibility(View.VISIBLE);
            //etSearchdefaulters.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
        }
        calenderEventAdapter.notifyDataSetChanged();
        calenderEventAdapter.SetOnItemClickListener(new CalenderEventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        // builder.show();


        //* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
/*
      LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.activity_calender_event, null);
        calenderView = promptView.findViewById(R.id.calenderView);
        calenderArraylist.clear();
        for (int j = 0; j < dates.size(); j++) {
            if (dates.get(j).getSessionDate().equalsIgnoreCase(curDate)) {
                calenderArraylist.add(dates.get(j));
            }
        }*/

     /*   calenderEventAdapter = new CalenderEventAdapter(this, calenderArraylist);
        calenderView.setAdapter(calenderEventAdapter);*/
        //  calenderEventAdapter.notifyAll();

        builder.setView(convertView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // dialog.cancel();
                        Log.e("cancel", "--cancel-" + "cancel kilik");
                        dialog.dismiss();

                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
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
                            Toast.makeText(getApplicationContext(), "Logged Out successfully", LENGTH_SHORT).show();
                            //  PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "0");
                            PreferencesManger.clearPreferences(getApplicationContext());
                            //SugarContext.terminate();
                            //SchemaGenerator schemaGenerator = new SchemaGenerator(getApplicationContext());
                            //schemaGenerator.deleteTables(new SugarDb(getApplicationContext()).getDB());
                            //SugarContext.init(getApplicationContext());
                            // schemaGenerator.createDatabase(new SugarDb(getApplicationContext()).getDB());
                            //finish();
                            //startActivity(new Intent(TeacherLandingActivity.this, LoginActivity.class));
                            //overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
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
                        Toast.makeText(getApplicationContext(), "Logout successfully..", LENGTH_SHORT).show();
                        // PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "0");
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainLandingActivity.class));
                        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



