package com.sm_academy.Utility;

import android.content.Context;


public class Constants {

    //public static final String Host = "http://sm-academy-beta.wepedhd2vx.ap-south-1.elasticbeanstalk.com/api/v1";
    //public static final String Host = "http://sm-academy-testing.fbs3xmwp9d.ap-south-1.elasticbeanstalk.com/api/v1";
    public static final String Host = "http://192.168.1.18:3000/api/v1";
    public static final String CITY = "city";
    public static final String SECTION = "sec";
    public static final String STANDARD = "std";
    public static final String NAME = "name";
    public static final String PARENT = "adress";

    public static final String TRIP_STUDENT = "trip_student";
    public static final String STATUSTRUE = "true";
    public static final String PROFILE = "profile";
    public static final String STATUSFALSE = "false";
    public static final String ONWARD = "Onward";
    public static final String LEAVEID = "leaveid";
    public static final String REMARK = "Remark";
    public static final String STATUS = "true";
    public static final String REASON = "Reason";
    public static final String FROMDATE = "27-06-2000";
    public static final String ENDDATE = "25-06-2000";
    public static final String HALFDAY = "No";
    public static final String LEAVETYPE = "Casual Leave";

    public static final int ADMISSIONNO = 1;

    public static final String YEARFRAGMENT = "topics";
    public static final String MONTHFRAGMENT = "sub_topics";
    public static final String DAILYFRAGMENT = "daily_topics";
    public static final String ADMINATTENDANCECHARACTEREMP = "Employee";
    public static final String ADMINATTENDANCECHARACTERSTU = "Student";

    public static final int ON_TRIP = 2;
    public static final int ERROR = 0;

    public static final String FLAGTRUE = "true";
    public static final String FLAGFALSE = "false";

    public static final boolean TRUE = true;
    public static final boolean FALSE = false;

    public static final double NOT_SELECTED = 0;
    public static final double ON_PROGRESS = 1;
    public static final double COMPLETTED = 2;

    public static final int NULL_REMARK = 0;
    public static final int UPDATE_REMARK = 1;
    public static final int DATA_REMARK = 2;
    public static final int NORMAL_REMARK = 3;

    public static final int STATUSCOMPLETED = 1;
    public static final int STATUSNOTCOMPLETED = 0;
    public static final int PRESENT = 1;
    public static final int ABSENT = 2;
    public static final int HALFDAYATTEN = 3;
    public static final int LEAVEDAY = 4;
    public static final int NONWORKINGDAY = 5;
    public static final int SCHOOLHOLIDAY = 6;
    public static final int FUTUREDATE = 7;
    public static final int SUCCESS = 200;
    public static final int SWR = 303;
    public static final int NDF = 404;
    public static final int CMF = 206;
    public static final int AE = 202;
    public static final int MF = 400;
    public static final int ISE = 500;
    public static final int WORKINGDAYNOTASSIGN = 8;
    public static final int STUDENTTIMETABLENOTASSIGN = 8;

    public static Context context;
    public static final String TEACHING = "Teaching";
    public static final String CLASS_TEACHER = "class teacher";

    public static final int SENT = 1;

    public static class Pref {
        public static final String KEY_USERNAME = "user_name";
        public static final String KEY_EMAIL = "email";

        public static final String KEY_MEDIA_PATH = "media_path";
        public static final String KEY_USER_ID = "0";
        public static final String KEY_IMAGE = "image";
        public static final String KEY_TOKEN = "token";
        public static final String KEY_USER_TYPE = "user_type";
        public static final String KEY_BRANCH_ID = "";
        public static final String LOGIN_FLAG = "1";
        public static final String COURSE_NAME = "coursename";
        public static final String COURSE_ID = "3";
        public static final String COURSE_TYPE = "academy";

        public static final String ACCOUNT_FLAG = "2";
        public static final String KEY_ACTIVE_COURSE = "false";
        public static final String KEY_DATE_OF_BIRTH = "1 apr 2018";
        public static final String COMMON_NAME = "abc";
        public static final String ENROLL_STATUS = "poi";
        public static final String PT_TAKEN = "pteTaken";
        public static final String KEY_PROFILE_IMAGE = "profileImage";
        public static final String KEY_PROFILE_IMAGE_NAME = "poiuytrewq";
        public static final String KEY_SECTION = "HEADER";
        public static final String LOGIN_CONDITION_MESSAGE = "LOGIN_CONDITION_MESSAGE";

        public static final String KEY_PROFILE_IMAGE_ID = "pic_id";

        public static final String FIRST_NAME = "akshay";
        public static final String LAST_NAME = "Mahurkar";
        public static final String MOBILE = "9770446338";
        public static final String GENDER = "OTHER";

        public static final String KEY_VIDEO_CHECK = "VideoCHECK";
        public static final String KEY_AUDIO_CHECK = "AudioCHECK";
        public static final String KEY_DOC_CHECK = "DOCCHECK";
        public static final String KEY_IMAGE_CHECK = "IMAGECHECK";
        public static final String KEY_ALL_CHECK = "ALlCHECK";

        public static final String DAY_ORDER = "";
        public static final String KEY_SUPER_BRANCH_ID = "super_branch_id";
        public static final String KEY_BRANCH_NAME = "branch_name";
        public static final String KEY_USER_SUB_TYPE = "user_sub_type";
        public static final String KEY_TIME_TABLE_DOWNLOAD_OWN = "is_time_table_own";
        public static final String KEY_TIME_TABLE_DOWNLOAD_CLASS = "is_time_table_class";
        public static final String KEY_CURRENT_TIME_TABLE_STRUCTURE_ID = "current_structure_id";
        public static final String KEY_CURRENT_TIMESTAMP = "current_timestamp";
        public static final String KEY_FCM_REG_ID = "fcm_reg_id";
        public static final String KEY_CHAT_OPEN = "chat_open";
        public static final String KEY_Id = "42";

        public static final String KEY_NAME = "akshay";
        public static final String KEY_TEACHER_ROLE = "class Teacher";
        public static final String paymentfor = "";
        public static final String paymentstatus = "";

    }

    public static class Fragment {
        public static final String DEALER = "Dealer";
        public static final String FAVORITES = "FAVORITES";
        public static final String HISTORY = "HISTORY";
        public static final String HOME = "HOME";
        public static final String MOLARITY_CALCULATOR = "MOLARITY_CALCULATOR";
    }

    public static class Units {
        public static final double VALUE_OF_G_L = 1.0;
        public static final double VALUE_OF_MG_ML = 0.001;
        public static final double VALUE_OF_MICRO = 0.000001;
    }
}