package com.people.utils;


public class AppConstants {

    public static int VOTER=1;
    public static int LEADER=2;

    public static final int PICKFILE_RESULT_CODE = 1;


    public static abstract class Screens {
        public static final int HOME = 0, REPORT = 1, PIECHART =2, PRIORITIES =3,
                PUSHUPDATES = 4, REQUEST = 5, VOTERQUERYRAISING = 6, HISTORY = 7,
                VOTERVIEWFEEDBACK = 8, VOTERSEARCH = 9, TODO = 10, FEEDBACK = 11;
    }

    public static abstract class WebApi{
        //live
        private final static String LOCAL_URL = "http://192.168.0.191/peopleapp/index.php/Api/";
        private final static String BASE_URL=LOCAL_URL;

        //methods
        public static final String GENERATE_OTP =  BASE_URL + "generate_otp";
        public static final String LOGIN =  BASE_URL + "login";
        public static final String UPDATE_PROFILE =  BASE_URL + "updateProfile";
        public static final String GET_PROFILE =  BASE_URL + "getProfile";
        public static final String SUBMIT_QUERY =  BASE_URL + "submitQuery";
        public static final String FOLLOW_LEADER =  BASE_URL + "followLeader";
        public static final String GET_HISTORY =  BASE_URL + "getHistory";
        public static final String PUSH_UPDATE = BASE_URL + "pushUpdate";
        public static final String GET_QUERY_COUNT = BASE_URL + "getQueryCount";
        public static final String GET_REPORT = BASE_URL + "getReport";

    }

    public static abstract class Preference {
        //Preferences
        public static final String PREF_ROLE = "key_role";
        public static final String PREF_USER_ID = "key_id";
        public static final String PREF_F_NAME = "key_fname";
        public static final String PREF_L_NAME = "key_lname";
        public static final String PREF_MOBILE = "key_mobile";
        public static final String PREF_POST_POS = "key_post_pos";
        public static final String PREF_STATE_POS = "key_state_pos";
        public static final String PREF_DISTRICT_POS = "key_district_pod";
        public static final String PREF_TALUKA = "key_taluka";
        public static final String PREF_CITY = "key_city";
        public static final String PREF_POSTAL_CODE = "key_postal_code";
        public static final String PREF_LNG = "key_lang";
    }
}//app contants end
