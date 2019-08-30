package com.sm_academy.Activity.LearnerActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Adapters.CourseListViewAdapter;
import com.sm_academy.Adapters.CourseSubFieldViewAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.DashBoard.DashBoardResponse;
import com.sm_academy.ModelClass.DashBoard.DashBoardSectionResponse;
import com.sm_academy.ModelClass.DashBoard.DashBoardSections;
import com.sm_academy.ModelClass.DashBoard.DashCourse;
import com.sm_academy.ModelClass.DashBoard.LiveSession;
import com.sm_academy.ModelClass.DashBoard.Toefl;
import com.sm_academy.R;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.nashapp.androidsummernote.Summernote;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeNavigationTabActivity extends Fragment {
    private CircularProgressBar circularProgressBar, writingCircularProgressbar, speakingCircularProgressbar, readingCircularProgressbar;
    private int a = 87;
    private int counter = 0;
    private LinearLayout mockView, mockTestView, newUserView, existingUserView, ielstLvView, ptetLvView, courseCountView;
    private TextView dateView, timeView, noUpcomingSessionView, testTypeView, textView, teacherView, newDateView, newTimeView, newTestTypeView, newTeacherView, attendancePercentView;
    private ProgressBar progressView;
    private LinearLayout nextClassView, readingView, fielsView;

    private RecyclerView courseFieldListView;
    private RecyclerView courseView;
    private CourseListViewAdapter courseListViewAdapter;
    private CourseSubFieldViewAdapter courseSubFieldViewAdapter;
    // private ImageView imageView;
    private ArrayList<DashCourse> dashCourseArrayList;
    private ArrayList<DashBoardSections> dashBoardSectionsArrayList;
    private ArrayList<DashCourse> greBlockArrayList;
    private ArrayList<Toefl> toeflBlockArrayList;
    private ArrayList<LiveSession> liveSessionArrayList;
    private TextView tv_nodata, tv_nofield, fielsName, upcomingtextView;
    private int pos;
    // WebView summernote;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static HomeNavigationTabActivity newInstance() {
        HomeNavigationTabActivity fragment = new HomeNavigationTabActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_navigation_tab, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.commoncolor)));

        textView = view.findViewById(R.id.textView);

        noUpcomingSessionView = view.findViewById(R.id.noUpcomingSessionView);
        courseView = view.findViewById(R.id.courseView);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        tv_nofield = view.findViewById(R.id.tv_nofield);

        upcomingtextView = view.findViewById(R.id.upcomingtextView);

        mockTestView = view.findViewById(R.id.mockTestView);
        mockView = view.findViewById(R.id.mockView);

        fielsName = view.findViewById(R.id.fielsName);
        fielsView = view.findViewById(R.id.fieldView);
        fielsView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_scale_animation));
//        /summernote = view.findViewById(R.id.summernote);
        //summernote.setRequestCodeforFilepicker(5);

        liveSessionArrayList = new ArrayList<>();
        ptetLvView = view.findViewById(R.id.ptetLvView);
        ielstLvView = view.findViewById(R.id.ielstLvView);
        ielstLvView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_scale_animation));
        courseCountView = view.findViewById(R.id.courseCountView);

        nextClassView = view.findViewById(R.id.nextClassView);
        nextClassView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_scale_animation));
        existingUserView = view.findViewById(R.id.existingUserView);
        newUserView = view.findViewById(R.id.newUserView);

        readingView = view.findViewById(R.id.readingView);
        readingView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_scale_animation));

        courseFieldListView = view.findViewById(R.id.courseFieldListView);
        dashCourseArrayList = new ArrayList<>();
        dashBoardSectionsArrayList = new ArrayList<>();

        courseView.setHasFixedSize(true);
        courseView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        courseListViewAdapter = new CourseListViewAdapter(getActivity(), dashCourseArrayList);
        courseView.setAdapter(courseListViewAdapter);
        //courseListViewAdapter.notifyDataSetChanged();
        courseFieldListView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        courseFieldListView.setLayoutManager(llm);
        courseSubFieldViewAdapter = new CourseSubFieldViewAdapter(getActivity(), dashBoardSectionsArrayList);

        attendancePercentView = view.findViewById(R.id.attendancePercentView);

        progressView = view.findViewById(R.id.progressView);

        newDateView = view.findViewById(R.id.newDateView);
        newTimeView = view.findViewById(R.id.newTimeView);
        newTestTypeView = view.findViewById(R.id.newTestTypeView);
        newTeacherView = view.findViewById(R.id.newTeacherView);

        dateView = view.findViewById(R.id.dateView);
        timeView = view.findViewById(R.id.timeView);
        testTypeView = view.findViewById(R.id.testTypeView);
        teacherView = view.findViewById(R.id.teacherView);
      /*  String myHtml = "this is a test of <b>ImageGetter</b> it contains " +
                "two images: <br/>" +
                "<img src='http://developer.android.com/assets/images/dac_logo.png'>";*/


        //String myHtml = "<p>This is text and this is an image <img src='http://www.example.com/image.jpg' />.</p>";
        //textView.setText(Html.fromHtml(myHtml, new ImageGetter(), null));
        // Spanned spannedFromHtml = Html.fromHtml(myHtml, new DrawableImageGetter(), null);
        //summernote.loadData(myHtml, "text/html", "UTF-8");
/*
        circularProgressBar = view.findViewById(R.id.yourCircularProgressbar);
        writingCircularProgressbar = view.findViewById(R.id.writingCircularProgressbar);
        speakingCircularProgressbar = view.findViewById(R.id.speakingCircularProgressbar);
        readingCircularProgressbar = view.findViewById(R.id.readingCircularProgressbar);

        circularProgressBar.setColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_gray));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBar_dimen));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.default_stroke_width));
        int animationDuration = 2500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(a, animationDuration);

        writingCircularProgressbar.setColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        writingCircularProgressbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_gray));
        writingCircularProgressbar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBar_dimen));
        writingCircularProgressbar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.default_stroke_width));
        int animationDuration1 = 2500; // 2500ms = 2,5s
        writingCircularProgressbar.setProgressWithAnimation(a, animationDuration1);

        readingCircularProgressbar.setColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        readingCircularProgressbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_gray));
        readingCircularProgressbar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBar_dimen));
        readingCircularProgressbar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.default_stroke_width));
        int animationDuration2 = 2500; // 2500ms = 2,5s
        readingCircularProgressbar.setProgressWithAnimation(a, animationDuration2);

        speakingCircularProgressbar.setColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        speakingCircularProgressbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.light_gray));
        speakingCircularProgressbar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBar_dimen));
        speakingCircularProgressbar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.default_stroke_width));
        int animationDuration3 = 2500; // 2500ms = 2,5s
        speakingCircularProgressbar.setProgressWithAnimation(a, animationDuration3);*/

        nextClassView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LiveSessionsActivity.class));
            }
        });
        readingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CourseWithSyllabusViewActivity.class));
            }
        });
        courseCountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        mockView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MockTestListActivity.class));
                String time = "00:02:30 hrs";
                time = time.replace(" hrs", "");//mm:ss
                String[] units = time.split(":"); //will break the string up into an array
                int hour = Integer.parseInt(units[0]);
                int minutes = Integer.parseInt(units[1]); //first element
                int seconds = Integer.parseInt(units[2]); //second element
                int duration = (hour * 3600) + (60 * minutes) + seconds;
                Log.e("time", "====" + duration);
            }
        });
        courseSubFieldViewAdapter.SetOnItemClickListener(new CourseSubFieldViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ReadingComponentActivity.class);
                intent.putExtra("id", dashBoardSectionsArrayList.get(position).getId().toString());
                intent.putExtra("section_name", dashBoardSectionsArrayList.get(position).getTitleizedName().toString());
                startActivity(intent);
            }
        });


        courseListViewAdapter.SetOnItemClickListener(new CourseListViewAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(View view, int position) {

                pos = position;
                // view.clearAnimation();
                //view.getFocusables(position);
                //view.setSelected(true);
                courseListViewAdapter.setSelectedIndex(position);
                courseListViewAdapter.notifyDataSetChanged();
                //view.setBackgroundResource(R.drawable.bookexam);
                // holder.imageView.setImageResource(R.drawable.ear);
                //Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
                dashBoardSectionDetails();
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });
        fielsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("cor", "" + dashCourseArrayList.get(pos).getId());
                Intent intent = new Intent(getActivity(), CourseWithSyllabusViewActivity.class);
                intent.putExtra("course_id", dashCourseArrayList.get(pos).getId().toString());
                intent.putExtra("header", dashCourseArrayList.get(pos).getName());

                startActivity(intent);
            }
        });
        dashBoardDetails();

        return view;

    }
   /* private class DrawableImageGetter implements Html.ImageGetter {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public Drawable getDrawable(String source) {
            Resources res = getResources();
            int drawableId = res.getIdentifier(source, "drawable", getContext().getPackageName());
            Drawable drawable = res.getDrawable(drawableId, getContext().getTheme());

            //int size = (int) getTextSize();
            //int width = size;
            //int height = size;

//            int width = drawable.getIntrinsicWidth();
//            int height = drawable.getIntrinsicHeight();

            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            return drawable;
        }
    }*/
   /* private class ImageGetter implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int id;

            id = getResources().getIdentifier(source, "drawable", getActivity().getPackageName());

            if (id == 0) {
                // the drawable resource wasn't found in our package, maybe it is a stock android drawable?
                id = getResources().getIdentifier(source, "drawable", "android");
            }

            if (id == 0) {
                // prevent a crash if the resource still can't be found
                return null;
            } else {
                Drawable d = getResources().getDrawable(id);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        }

    }*/

    private void dashBoardDetails() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. ");

                RetrofitAPI.getInstance(getActivity()).getApi().getdashBoardDetails(new Callback<DashBoardResponse>() {
                    @Override
                    public void success(DashBoardResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getActivity());
                                dashCourseArrayList.clear();
                                dashCourseArrayList.addAll(object.getCourses());
                                if (object.getUpcomingSession() == null) {
                                    noUpcomingSessionView.setVisibility(View.VISIBLE);
                                    upcomingtextView.setVisibility(View.GONE);
                                    teacherView.setVisibility(View.INVISIBLE);
                                    testTypeView.setVisibility(View.INVISIBLE);
                                    dateView.setVisibility(View.INVISIBLE);
                                    timeView.setVisibility(View.INVISIBLE);
                                    noUpcomingSessionView.setText("No Upcoming Session");
                                    // dateView.setText("null");
                                    //testTypeView.setText("null");
                                    //teacherView.setText("null");
                                } else {
                                    teacherView.setVisibility(View.VISIBLE);
                                    dateView.setVisibility(View.VISIBLE);
                                    testTypeView.setVisibility(View.VISIBLE);
                                    noUpcomingSessionView.setVisibility(View.GONE);
                                    upcomingtextView.setVisibility(View.VISIBLE);
                                    binddata(object.getUpcomingSession());
                                }

                                courseView.setAdapter(courseListViewAdapter);

                                int s = dashCourseArrayList.size();
                                Log.e("data", "----" + dashCourseArrayList.size());
                                attendancePercentView.setText(String.valueOf(dashCourseArrayList.size()));

                                courseListViewAdapter.notifyDataSetChanged();

                                if (dashCourseArrayList.size() <= 0) {
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    courseView.setVisibility(View.GONE);
                                    attendancePercentView.setText("0");
                                } else {
                                    tv_nodata.setVisibility(View.GONE);
                                    courseView.setVisibility(View.VISIBLE);
                                    attendancePercentView.setText(String.valueOf(dashCourseArrayList.size()));
                                    // attendancePercentView.setText(dashCourseArrayList.size());
                                }

                                Log.e("ups", "===" + object.getUpcomingSession());

                                // JSONArray categories;
                                //categories = json.getJSONArray("section");


                                // Log.e("categories222", "" + contacts);

                            /*   // liveSessionArrayList.addAll(object.getLiveSessions());
                                // for (int i = 0; i < liveSessionArrayList.size(); i++) {
                                testTypeView.setText(liveSessionArrayList.get(0).getSectionName());
                                teacherView.setText(liveSessionArrayList.get(0).getTrainerName());
                                dateView.setText(liveSessionArrayList.get(0).getSessionDateReadableFormat() + " " +
                                        liveSessionArrayList.get(0).getStartTime() + " - " + liveSessionArrayList.get(0).getEndTime());
                                Log.e("name", "" + liveSessionArrayList.get(0).getSectionName());
                                Log.e("getTrainerName", "" + liveSessionArrayList.get(0).getTrainerName());
                                Log.e("getTrainerName", "" + liveSessionArrayList.get(0).getSessionDateReadableFormat() + " " +
                                        liveSessionArrayList.get(0).getStartTime() + " - " + liveSessionArrayList.get(0).getEndTime());
                                Log.e("name", "" + liveSessionArrayList.get(0).getSectionName());*/

                                // }

                                //batchArraylist.addAll(object.getBatches());
                            /*    if (batchArraylist.size() <= 0) {
                                    batchView.setVisibility(View.GONE);
                                    //etSearchdefaulters.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                } else {
                                    batchView.setVisibility(View.VISIBLE);
                                    //etSearchdefaulters.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                }*/
                                //batchAdapter.notifyDataSetChanged();
                                //Log.e("data", "---" + batchArraylist.toString());

                              /*  arrayList.clear();
                                arrayList.addAll(object.getAssignedBatchesIds());
                                if (arrayList.size() > 0) {
                                    PreferencesManger.addStringFields(getActivity(), Constants.Pref.ENROLL_STATUS, "true");
                                    //commonflag = "true";
                                }*/
                                // Log.e("arr", "===" + arrayList);

                                //Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                UIUtil.stopProgressDialog(getActivity());
                                Toast.makeText(getActivity(), "No Data Available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());

                            if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {

                                Toast.makeText(getActivity(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();
                                logout();
                            }
                            Log.e("ups", "===" + error.getMessage());
                            UIUtil.stopProgressDialog(getActivity());
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


    private void dashBoardSectionDetails() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. ");

                Map<String, String> params = new HashMap<String, String>();
                params.put("course_id", dashCourseArrayList.get(pos).getId().toString());
                RetrofitAPI.getInstance(getActivity()).getApi().getdashBoardSectionsDetails(params, new Callback<DashBoardSectionResponse>() {
                    @Override
                    public void success(DashBoardSectionResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getActivity());
                                // Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
                                dashBoardSectionsArrayList.clear();
                                dashBoardSectionsArrayList.addAll(object.getSections());
                                fielsView.setVisibility(View.VISIBLE);
                                mockView.setVisibility(View.VISIBLE);
                                fielsName.setText("About " + dashCourseArrayList.get(pos).getName());
                                courseFieldListView.setAdapter(courseSubFieldViewAdapter);
                                Log.e("data", "----" + dashBoardSectionsArrayList);

                                courseSubFieldViewAdapter.notifyDataSetChanged();

                                if (dashBoardSectionsArrayList.size() <= 0) {
                                    courseFieldListView.setVisibility(View.GONE);
                                    tv_nofield.setVisibility(View.VISIBLE);

                                } else {
                                    tv_nofield.setVisibility(View.GONE);
                                    courseFieldListView.setVisibility(View.VISIBLE);

                                }
                                // Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                UIUtil.stopProgressDialog(getActivity());
                                Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());
                            if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                                UIUtil.stopProgressDialog(getActivity());
                                Toast.makeText(getActivity(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();

                                logout();
                            } else {
                                Log.e("ups", "===" + error.getMessage());
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

    public void binddata(LiveSession liveSession) {

        dateView.setText(liveSession.getSessionDateReadableFormat());
        timeView.setText(liveSession.getStartTime() + " - " + liveSession.getEndTime());
        testTypeView.setText(liveSession.getSectionName());
        teacherView.setText(liveSession.getTrainerName());
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
                            // overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
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
                        // finishA();
                        startActivity(new Intent(getActivity(), MainLandingActivity.class));
                        // overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
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

