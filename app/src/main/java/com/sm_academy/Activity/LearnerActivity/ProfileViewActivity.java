package com.sm_academy.Activity.LearnerActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.JsonObject;
import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.API.RetrofitAPIInterface;

import com.sm_academy.Activity.DefaultActivity.AccountSettingActivity;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.DashBoard.Learner;
import com.sm_academy.ModelClass.Photo;
import com.sm_academy.ModelClass.Profile.Profile;
import com.sm_academy.ModelClass.Profile.ProfileResponse;
import com.sm_academy.ModelClass.SigninUser;
import com.sm_academy.ModelClass.StatusResponse;
import com.sm_academy.ModelClass.UploadObject;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit.RetrofitError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class ProfileViewActivity extends AppCompatActivity {
    private EditText etName, etEmail, etMobile, etLastName, etGender;
    private CircleImageView poster;
    ImageView editimageView;
    private TextView etDOB;
    Button upload, pick_img;
    TextView update;
    String mediaPath, mediaPath1;
    ImageView imgView;
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    ProgressDialog progressDialog;
    TextView str1, str2;
    public static final int MY_PERMISSION_REQEST = 100;

    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE};
    String aboutPhoto, attachmentPhoto;
    Context mContext;

    View parentView;
    ImageView imageView;
    TextView textView;

    String imagePath;

    PermissionsChecker checker;

    private Toolbar toolbar;
    private String gen = "";
    private RadioButton radioButton1, radioButton2, radioButton3;
    private RadioGroup rbg;
    private String id;
    private Long fromDate;
    // private GoogleApiClient client;

    File file;
    private Bitmap bitmap;
    private File destination;
    private InputStream inputStreamImg;
    private ProgressBar progressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        progressBar = findViewById(R.id.progress);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editimageView = findViewById(R.id.editimageView);
        // deleteimageView = findViewById(R.id.deleteimageView);

        etName = findViewById(R.id.tvName);
        etEmail = findViewById(R.id.tvEmail);
        etMobile = findViewById(R.id.tvMobile);

        etDOB = findViewById(R.id.tvDOB);
        etLastName = findViewById(R.id.etLastName);
        poster = findViewById(R.id.poster);

        Log.e("image", "====" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE));
        rbg = (RadioGroup) findViewById(R.id.radioGroup1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton3 = findViewById(R.id.radioButton3);

        upload = findViewById(R.id.upload);
        update = findViewById(R.id.update);
        pick_img = findViewById(R.id.pick_img);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getProfileDetails();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProfileDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        id = PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_ID);
       /* etName.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.FIRST_NAME));
        etLastName.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.LAST_NAME));
        etEmail.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_EMAIL));
        etMobile.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.MOBILE));
        etDOB.setText(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_DATE_OF_BIRTH));*/

        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        editimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editConfirmation();
            }
        });
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("path ofery..*.........", mediaPath + "");
                //editConfirmation();
                if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE))) {
                    Toast.makeText(getApplicationContext(), "No Image ", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ProfileImageviewActivity.class);
                    intent.putExtra("name", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE_NAME));
                    intent.putExtra("image", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE));
                    startActivity(intent);
                }
            }
        });
        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.GENDER).equalsIgnoreCase("male")) {
            radioButton1.setChecked(true);
            gen = "male";
        } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.GENDER).equalsIgnoreCase("female")) {
            radioButton2.setChecked(true);
            gen = "female";
        } else {
            radioButton3.setChecked(true);
            gen = "other";
        }


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        if (ContextCompat.checkSelfPermission(ProfileViewActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ProfileViewActivity.this, new String[]
                    {android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQEST);
        }

        progressDialog.setMessage("Uploading...");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //file = new File(mediaPath);
                uploadFile();
                //getProfilePicture();

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileDetails();
            }
        });


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

    private void deleteConfirmation() {
        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);

//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(this, R.style.AppTheme_AppBarOverlay);

        builder.setTitle("Confirmation");
        String message = "Do you want to delete this image ?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletedImage();

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

    private void editConfirmation() {
        final CharSequence[] options = {"Camera", "Choose from Gallery", "Remove Image", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileViewActivity.this);
        builder.setTitle("Profile Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Remove Image")) {
                    deleteConfirmation();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == 1 && null != data) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPath = "";
                mediaPath = destination.getAbsolutePath();
                // poster.setImageBitmap(bitmap);
                Log.e("mediapath", "====" + mediaPath);
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_MEDIA_PATH, mediaPath);
                uploadFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK && null != data) {

            // Get the Image from data
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = "";
            mediaPath = cursor.getString(columnIndex);
            PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_MEDIA_PATH, mediaPath);
            Log.e("pre pathaaaa-----", "===" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_MEDIA_PATH));
            Log.e("media path-----", "===" + mediaPath);

            //str1.setText(mediaPath);
            // Set the Image in ImageView for Previewing the Media
            // Picasso.with(getApplicationContext()).load(mediaPath).transform(new CircleTransform()).into(poster);
            uploadFile();
            // poster.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            cursor.close();

        }

        /*else if (requestCode == 2 && data != null) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            mediaPath = "";
            mediaPath = c.getString(columnIndex);
            PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_MEDIA_PATH, mediaPath);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(mediaPath));
            Log.w("path ofery..*.........", mediaPath + "");
            //poster.setImageBitmap(thumbnail);
            uploadFile();
        }*/
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = "";
                mediaPath = cursor.getString(columnIndex);
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_MEDIA_PATH, mediaPath);
                Log.e("pre pathaaaa-----", "===" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_MEDIA_PATH));
                Log.e("media path-----", "===" + mediaPath);

                //str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                // Picasso.with(getApplicationContext()).load(mediaPath).transform(new CircleTransform()).into(poster);
                uploadFile();
                // poster.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } // When an Video is picked
            else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

                // Get the Video from data
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                mediaPath1 = cursor.getString(columnIndex);
                //str2.setText(mediaPath1);
                // Set the Video Thumb in ImageView Previewing the Media
                uploadFile();
                //Picasso.with(getApplicationContext()).load(selectedVideo).transform(new CircleTransform()).into(poster);
                // poster.setImageBitmap(getThumbnailPathForLocalFile(ProfileViewActivity.this, selectedVideo));
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }*/

    // Providing Thumbnail For Selected Image
    public Bitmap getThumbnailPathForLocalFile(Activity context, Uri fileUri) {
        long fileId = getFileId(context, fileUri);
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
    }

    // Getting Selected File ID
    public long getFileId(Activity context, Uri fileUri) {
        Cursor cursor = context.managedQuery(fileUri, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            return cursor.getInt(columnIndex);
        }
        return 0;
    }

    private void uploadFile() {
        String token = PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Token token=" + token.replace("\"", ""));

        file = new File(mediaPath);


        progressDialog = new ProgressDialog(ProfileViewActivity.this);
        progressDialog.setMessage("Uploading image..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part attachment =
                MultipartBody.Part.createFormData("attachment", file.getName(), requestFile);
        MultipartBody.Part description =
                MultipartBody.Part.createFormData("description", "photo");
        MultipartBody.Part attached_by_id =
                MultipartBody.Part.createFormData("attached_by_id", id);
        MultipartBody.Part destroy =
                MultipartBody.Part.createFormData("_destroy", "false");
        MultipartBody.Part id =
                MultipartBody.Part.createFormData("id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE_ID));


        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), file.getName());


        RetrofitAPIInterface getResponse = RetrofitAPI.getRetrofit().create(RetrofitAPIInterface.class);
        Call<ProfileResponse> resultCall = getResponse.uploadImage(headers, attachment, description, attached_by_id, destroy, id);

        Log.e("body", "=====" + file.getName());

        resultCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                //progressDialog.dismiss();
                Log.e("response1", "==" + response.body().getLearner());
                Log.e("call1", "==" + call);
                // Response Success or Fail
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("success")) {
                        //progressDialog.dismiss();
                        bindData(response.body().getLearner());
                        Log.e("response", "==" + response.toString());
                        Log.e("call", "==" + call);
                        //Toast.makeText(getApplicationContext(), "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "try some time latter", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "try some time latter", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                if (t.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                    logout();
                }
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                Log.e("error", "-----" + t.getCause());
            }
        });
    }

    private void deletedImage() {
        String token = PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Token token=" + token.replace("\"", ""));
        Log.e("media path-----", "===" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_MEDIA_PATH));
        file = new File(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_MEDIA_PATH));

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ProfileViewActivity.this);
        progressDialog.setMessage("Deleting image..");
        progressDialog.setCancelable(false);
        progressDialog.show();

 /*       RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part attachment =
                MultipartBody.Part.createFormData("attachment", file.getName(), requestFile);
        MultipartBody.Part description =
                MultipartBody.Part.createFormData("description", "photo");*/
        MultipartBody.Part attached_by_id =
                MultipartBody.Part.createFormData("attached_by_id", id);
        MultipartBody.Part destroy =
                MultipartBody.Part.createFormData("_destroy", "true");
        MultipartBody.Part id =
                MultipartBody.Part.createFormData("id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE_ID));


        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), file.getName());


        RetrofitAPIInterface getResponse = RetrofitAPI.getRetrofit().create(RetrofitAPIInterface.class);
        Call<ProfileResponse> resultCall = getResponse.deleteImage(headers, attached_by_id, destroy, id);

        Log.e("body", "=====" + file.getName());

        resultCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {


                Log.e("response1", "==" + response.body().getLearner());
                Log.e("call1", "==" + call);
                // Response Success or Fail
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("success")) {
                        progressDialog.dismiss();
                        bindData(response.body().getLearner());
                        Log.e("response", "==" + response.toString());
                        Log.e("call", "==" + call);
                        //Toast.makeText(getApplicationContext(), "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "try some time latter", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "try some time latter", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                if (t.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                    logout();
                }
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                Log.e("error", "-----" + t.getCause());
            }
        });
    }


    private void bindData(SigninUser signinUser) {
        bindPhoto(signinUser.getPhoto());

    }

    private void bindPhoto(final Photo photo) {
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE, photo.getAttachment());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE_ID, photo.getId());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE_NAME, photo.getFileName());
        if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE))) {
            //Picasso.with(getApplicationContext()).load(R.drawable.photo).fit().centerInside().into(poster);
            Glide
                    .with(getApplicationContext())
                    .load(R.drawable.photo)
                    .fitCenter()
                    .into(poster);
        } else {
            Glide.with(this)
                    .load(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(poster);


        /*    Glide
                    .with(getApplicationContext())
                    .load(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE))
                    .fitCenter()
                    .into(poster);*/
            // Picasso.with(getApplicationContext()).load(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE)).into(poster);
        }
        progressDialog.dismiss();
        aboutPhoto = photo.getFileName();
        attachmentPhoto = photo.getAttachment();

    }

    public void showImagePopup(View view) {
        if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        } else {
            // File System.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_PICK);

            // Chooser of file system options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image to Puload");
            startActivityForResult(chooserIntent, 1010);
        }
    }

    private void getProfileDetails() {
        try {

            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Getting Profile Information...");

                RetrofitAPI.getInstance(this).getApi().getProfile(new retrofit.Callback<ProfileResponse>() {
                    @Override
                    public void success(ProfileResponse profileResponse, retrofit.client.Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());

                            if (profileResponse.getStatus().equals("success")) {
                                bindDataForProfile(profileResponse.getLearner());
                            } else {
                                Toast.makeText(getApplicationContext(), "" + profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(getApplicationContext(), "Server is not responding, Try after some time.", Toast.LENGTH_SHORT).show();
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

    private void bindDataForProfile(SigninUser signinUser) {
        id = signinUser.getId().toString();
        etName.setText(signinUser.getFirstName());
        etLastName.setText(signinUser.getLastName());
        etEmail.setText(signinUser.getEmail());
        etMobile.setText(signinUser.getMobileNumber());
        etDOB.setText(signinUser.getDateOfBirth());
        if (signinUser.getGender().equalsIgnoreCase("male")) {
            radioButton1.setChecked(true);
            gen = "male";
        } else if (signinUser.getGender().equalsIgnoreCase("female")) {
            radioButton2.setChecked(true);
            gen = "female";
        } else {
            radioButton3.setChecked(true);
            gen = "other";
        }
        bindDataforPhoto(signinUser.getPhoto());

    }

    public void bindDataforPhoto(Photo photo) {
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE, photo.getAttachment());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE_NAME, photo.getFileName());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE_ID, photo.getId());
        if (TextUtils.isEmpty(PreferencesManger.getStringFields(this, Constants.Pref.KEY_PROFILE_IMAGE))) {
            Picasso.with(this).load(R.drawable.photo).fit().centerInside().into(poster);
            Glide
                    .with(getApplicationContext())
                    .load(R.drawable.photo)
                    .fitCenter()
                    .into(poster);
        } else {
            Glide.with(this)
                    .load(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(poster);

          /*  Glide
                    .with(getApplicationContext())
                    .load(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE))
                    .fitCenter()
                    .into(poster);*/
            //Picasso.with(this).load(PreferencesManger.getStringFields(this, Constants.Pref.KEY_PROFILE_IMAGE)).fit().into(poster);
        }
    }

    private void updateProfileDetails() {
        try {
            if (validateCredentials()) {
                if (UIUtil.isInternetAvailable(this)) {
                    UIUtil.startProgressDialog(this, "Getting Profile Information...");

                    JsonObject jsonObject = new JsonObject();
                    JsonObject jsonObject1 = new JsonObject();
                    jsonObject.addProperty("first_name", etName.getText().toString());
                    jsonObject.addProperty("last_name", etLastName.getText().toString());
                    jsonObject.addProperty("gender", gen);
                    jsonObject.addProperty("email", etEmail.getText().toString());
                    jsonObject.addProperty("mobile_number", etMobile.getText().toString());
                    jsonObject.addProperty("date_of_birth", etDOB.getText().toString());
                    jsonObject1.addProperty("id", id);
                    jsonObject1.add("learner", jsonObject);


                    RetrofitAPI.getInstance(this).getApi().getUpdateProfile(jsonObject1, new retrofit.Callback<StatusResponse>() {
                        @Override
                        public void success(StatusResponse profileResponse, retrofit.client.Response response) {
                            try {
                                UIUtil.stopProgressDialog(getApplicationContext());

                                if (profileResponse.getStatus().equals("success")) {

                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.FIRST_NAME, etName.getText().toString());
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LAST_NAME, etLastName.getText().toString());
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.MOBILE, etMobile.getText().toString());
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.GENDER, gen);
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_EMAIL, etEmail.getText().toString());
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_DATE_OF_BIRTH, etDOB.getText().toString());
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COMMON_NAME, etName.getText().toString() + " " + etLastName.getText().toString());
                                    Toast.makeText(getApplicationContext(), "" + profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                    overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                                } else {
                                    Toast.makeText(getApplicationContext(), "" + profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(getApplicationContext(), "Server is not responding, Try after some time.", Toast.LENGTH_SHORT).show();

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


    private void startPermissionsActivity(String[] permission) {
        PermissionsActivity.startActivityForResult(this, 0, permission);
    }

    private boolean validateCredentials() {

        if (etName.getText().toString().isEmpty()) {
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

        String s = etMobile.getText().toString();
        if (s.length() > 15) {
            Toast.makeText(getApplicationContext(), "Mobile Number can not be more than 15 digits ", Toast.LENGTH_SHORT).show();
            //etPassword.setError("Please enter Password");
            return false;
        } else if (s.length() < 10) {
            Toast.makeText(getApplicationContext(), " Mobile Number must be atleast 10 digits", Toast.LENGTH_SHORT).show();
            //etPassword.setError("Please enter Password");
            return false;
        }
        if (etDOB.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Select Date Of Birth", Toast.LENGTH_SHORT).show();
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
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();

            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    private void logout() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. logging out..");
                RetrofitAPI.getInstance(this).getApi().logout(new retrofit.Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject object, retrofit.client.Response response) {
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
