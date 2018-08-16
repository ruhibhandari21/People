package com.people.utils;


public class AppConstants {

    public static int VOTER=1;
    public static int LEADER=2;

    public static final int PICKFILE_RESULT_CODE = 1;

    //live
    public static String LOCAL_URL = "http://192.168.0.191/peopleapp/index.php/Api/";
    public static String BASE_URL=LOCAL_URL;

    //methods
    public static final String GENERATE_OTP =  "generate_otp";
    public static final String LOGIN =  "login";
    public static final String UPDATE_PROFILE =  "updateProfile";

    //Preferences
    public static final String PREF_ROLE = "key_role";


    public static abstract class Screens {
        public static final int HOME = 0, REPORT = 1, PIECHART =2, PRIORITIES =3, PUSHUPDATES = 4, REQUEST = 5,
                VOTERQUERYRAISING = 6, HISTORY = 7, VOTERVIEWFEEDBACK = 8, VOTERSEARCH = 9,
                TODO = 10, FEEDBACK = 11;
    }

}//app contants end
