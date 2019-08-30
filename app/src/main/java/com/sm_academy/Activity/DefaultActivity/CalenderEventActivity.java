package com.sm_academy.Activity.DefaultActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.BaseActivity;

import com.sm_academy.Adapters.CalenderEventAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.BatchDetails;
import com.sm_academy.ModelClass.BatchTiming;
import com.sm_academy.R;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class CalenderEventActivity extends BaseActivity {
    private ArrayList calenderArraylist;
    private CalenderEventAdapter calenderEventAdapter;
    private Context context = this;
    private ListView calenderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_event);
       // calenderView = findViewById(R.id.calenderView);
        calenderArraylist = new ArrayList();
        //ArrayList<String> myList = getIntent().getParcelableExtra("list");

        ArrayList myList = (ArrayList<Integer>) getIntent().getSerializableExtra("list");
        Log.e("data", "======"  + "   ajhvshdgvhjsdav :  " + myList);
        calenderView = (ListView)findViewById(R.id.calenderView);
        ArrayAdapter<String> containerAdapter = new ArrayAdapter(CalenderEventActivity.this, android.R.layout.simple_list_item_1, myList);
        calenderView.setAdapter(containerAdapter);


   /*     calenderView.setHasFixedSize(true);
        calenderView.setLayoutManager(new LinearLayoutManager(this));
        //calenderEventAdapter = new CalenderEventAdapter(this, filelist);
        calenderView.setAdapter(calenderEventAdapter);
        calenderEventAdapter.notifyDataSetChanged();

        calenderEventAdapter.SetOnItemClickListener(new CalenderEventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {

            case R.id.logout:
                showLogoutConfirmation();
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
   /* private void dilogbox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(BatchTimingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.activity_calender_event, null);
        //builder.setView(convertView);
        // builder.setTitle("List");
        builder.setView(convertView);
        calenderView = convertView.findViewById(R.id.calenderView);
        //  cancelBtn = convertView.findViewById(R.id.cancelBtn);
        calenderView.setHasFixedSize(true);
        calenderView.setLayoutManager(new LinearLayoutManager(this));
        ArrayAdapter calenderEventAdapter = new ArrayAdapter(this, calenderArraylist);
        calenderView.setAdapter(calenderEventAdapter);
        calenderEventAdapter.notifyDataSetChanged();
        calenderEventAdapter.SetOnItemClickListener(new CalenderEventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        // builder.show();


        //* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);

    *//*    LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.activity_calender_event, null);
        calenderView = promptView.findViewById(R.id.calenderView);
        calenderArraylist.clear();
        for (int j = 0; j < dates.size(); j++) {
            if (dates.get(j).getSessionDate().equalsIgnoreCase(curDate)) {
                calenderArraylist.add(dates.get(j));
            }
        }

        calenderEventAdapter = new CalenderEventAdapter(this, calenderArraylist);
        calenderView.setAdapter(calenderEventAdapter);*//*
        //  calenderEventAdapter.notifyAll();
        final AlertDialog alert = builder.create();
        alert.show();
        builder.setView(convertView)
                .setNegativeButton("OK", null);
       *//* builder.setView(convertView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // dialog.cancel();
                        Log.e("cancel", "--cancel-" + "cancel kilik");
                        dialog.dismiss();

                    }
                });
*//*


    }*/

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
