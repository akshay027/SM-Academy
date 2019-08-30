package com.sm_academy.Activity.DefaultActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sm_academy.API.RetrofitAPI;

import com.sm_academy.Activity.LearnerActivity.CourseSummariesActivity;
import com.sm_academy.Activity.LearnerActivity.MySubscriptionActivity;
import com.sm_academy.Activity.LearnerActivity.PaymentHistoriesActivity;
import com.sm_academy.Activity.LearnerActivity.ProfileViewActivity;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit.RetrofitError;

public class AccountSettingActivity extends Fragment {
    private static final String TAG = AccountSettingActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://192.168.1.12:3000/api/v1";
    private Uri uri;
    private TextView etDOB;
    private long fromDate;
    private LinearLayout logoutView, profileView, mySubscriptionsView, paymentHistoryView, CourseSummariesView;
    private CircleImageView imageView;
    private TextView tvName;
    private ProgressBar progressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static AccountSettingActivity newInstance() {
        AccountSettingActivity fragment = new AccountSettingActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account_setting, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.commoncolor)));
        progressBar = view.findViewById(R.id.progress);
        mySubscriptionsView = view.findViewById(R.id.mySubscriptionsView);
        tvName = view.findViewById(R.id.tvName);
        logoutView = view.findViewById(R.id.logoutView);
        paymentHistoryView = view.findViewById(R.id.paymentHistoryView);
        CourseSummariesView = view.findViewById(R.id.CourseSummariesView);
        imageView = view.findViewById(R.id.imageView);
        profileView = view.findViewById(R.id.profileView);
        //  tvName.setText(PreferencesManger.getStringFields(getActivity(), Constants.Pref.COMMON_NAME));
        /*if (TextUtils.isEmpty(PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_PROFILE_IMAGE))) {
            Picasso.with(getActivity()).load(R.drawable.photo).transform(new CircleTransform()).fit().centerInside().into(imageView);
        } else {
            Picasso.with(getActivity()).load(PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_PROFILE_IMAGE)).transform(new CircleTransform()).into(imageView);
        }*/
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileViewActivity.class));
            }
        });

        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmation();
            }
        });

        paymentHistoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PaymentHistoriesActivity.class));
            }
        });

        mySubscriptionsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MySubscriptionActivity.class));
            }
        });
        CourseSummariesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CourseSummariesActivity.class));
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (TextUtils.isEmpty(PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_PROFILE_IMAGE))) {
                    //Picasso.with(getActivity()).load(R.drawable.photo).fit().centerInside().into(imageView);
                    Glide
                            .with(getActivity())
                            .load(R.drawable.photo)
                            .fitCenter()
                            .into(imageView);
                } else {
            /*Glide
                    .with(getActivity())
                    .load(PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_PROFILE_IMAGE))
                    .fitCenter()
                    .into(imageView);*/
                    Glide.with(getActivity())
                            .load(PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_PROFILE_IMAGE))
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(imageView);

                    //Picasso.with(getActivity()).load(PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_PROFILE_IMAGE)).fit().into(imageView);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AccountSettingActivity.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        tvName.setText(PreferencesManger.getStringFields(getActivity(), Constants.Pref.COMMON_NAME));
        if (TextUtils.isEmpty(PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_PROFILE_IMAGE))) {
            //Picasso.with(getActivity()).load(R.drawable.photo).fit().centerInside().into(imageView);
            Glide
                    .with(getActivity())
                    .load(R.drawable.photo)
                    .fitCenter()
                    .into(imageView);
        } else {
            /*Glide
                    .with(getActivity())
                    .load(PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_PROFILE_IMAGE))
                    .fitCenter()
                    .into(imageView);*/
            Glide.with(this)
                    .load(PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_PROFILE_IMAGE))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);

            //Picasso.with(getActivity()).load(PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_PROFILE_IMAGE)).fit().into(imageView);
        }
    }
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, AccountSettingActivity.this);
                File file = new File(filePath);
                Log.d(TAG, "Filename " + file.getName());
                //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SERVER_PATH)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitAPIInterface uploadImage = retrofit.create(RetrofitAPIInterface.class);
                Call<UploadObject> fileUpload = uploadImage.uploadFile(fileToUpload, filename);
                fileUpload.enqueue(new Callback<UploadObject>() {
                    @Override
                    public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                        Toast.makeText(AccountSettingActivity.this, "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                        Toast.makeText(AccountSettingActivity.this, "Success " + response.body().getSuccess(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<UploadObject> call, Throwable t) {
                        Log.d(TAG, "Error " + t.getMessage());
                    }
                });
            } else {
                // EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (uri != null) {
            String filePath = getRealPathFromURIPath(uri, AccountSettingActivity.this);
            File file = new File(filePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitAPIInterface uploadImage = retrofit.create(RetrofitAPIInterface.class);
            Call<UploadObject> fileUpload = uploadImage.uploadFile(fileToUpload, filename);
            fileUpload.enqueue(new Callback<UploadObject>() {
                @Override
                public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                    Toast.makeText(AccountSettingActivity.this, "Success " + response.message(), Toast.LENGTH_LONG).show();
                    Toast.makeText(AccountSettingActivity.this, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<UploadObject> call, Throwable t) {
                    Log.d(TAG, "Error " + t.getMessage());
                }
            });
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
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
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
*/

    private void showLogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. logging out..");
                //Map<String, String> params = new HashMap<String, String>();
                // params.put("branch_id", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(getActivity()).getApi().logout(new retrofit.Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject object, retrofit.client.Response response) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());
                            Log.e("API", "logout-" + object.toString());
                            Toast.makeText(getActivity(), "Logged Out successfully", Toast.LENGTH_SHORT).show();
                            //  PreferencesManger.addStringFields(getActivity(), Constants.Pref.LOGIN_FLAG, "0");
                            PreferencesManger.clearPreferences(getActivity());
                            //SugarContext.terminate();
                            //SchemaGenerator schemaGenerator = new SchemaGenerator(getActivity());
                            //schemaGenerator.deleteTables(new SugarDb(getActivity()).getDB());
                            //SugarContext.init(getActivity());
                            // schemaGenerator.createDatabase(new SugarDb(getActivity()).getDB());
                            //finish();
                            //startActivity(new Intent(TeacherLandingActivity.this, LoginActivity.class));
                            //overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                            startActivity(new Intent(getActivity(), MainLandingActivity.class));
                            //overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                            // finishAffinity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        PreferencesManger.clearPreferences(getActivity());
                        UIUtil.stopProgressDialog(getActivity());
                        Toast.makeText(getActivity(), "Logout successfully..", Toast.LENGTH_SHORT).show();
                        // PreferencesManger.addStringFields(getActivity(), Constants.Pref.LOGIN_FLAG, "0");
                        //finish();
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