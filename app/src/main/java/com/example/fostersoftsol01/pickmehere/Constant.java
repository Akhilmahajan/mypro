package com.example.fostersoftsol01.pickmehere;

import android.provider.Settings;

/**
 * Created by fostersoftsol01 on 16/6/17.
 */

public class Constant {
    public static final String BASE_URL = " http://fostersoftsolutions.com/shoparound/";



    /////......COMMON KEY VALUES......./////
    public static String KEY_PREF = "pref";

    public static String Key_email = "email";
    public static String Key_user_id="user_id";
    public static String Key_password="password";
    public static final String KEY_MOBILE = "mobile";
    public static String Key_device_id = "device_id";
    public static String Key_user_type = "user_type";



    ///////........LOGIN URL + LOGIN KEY VALUES......../////////

    public static String URL_LOGIN = BASE_URL + "login.php";




    ///////........REGISTER URL + REGISTER KEY VALUES......../////////

    public static final String KEY_FIRSTNAME = "first_name";
    public static final String KEY_LASTNAME = "last_name";


    public static String URL_REGISTER = BASE_URL + "register.php";
    public static final String KEY_REGISTER_FIRSTNAME = "&first_name=";
    public static final String KEY_REGISTER_LASTNAME = "&last_name=";
    public static final String KEY_REGISTER_EMAIL = "&email=";
    public static final String KEY_REGISTER_MOBILE = "&mobile=";
    public static final String KEY_REGISTER_PASSWORD = "&password=";
    public static final String KEY_REGISTER_DEVICE_ID = "&device_id=";
    public static String Key_Register_user_type = "&user_type=";




    //////........FORGET_PASSWORD URL + FPOGET_PASSWORD KEY VALUES......../////////

    public static String URL_FORGET_PASSWORD = BASE_URL + "change_password.php";

    public static final String KEY_FORGOT_PASSWORD_MOBILE = "mobile";






    /////........OTP URL + OTP KEY VALUES......../////////

    public static String URL_OTP = BASE_URL + "change_password.php";
    public static final String KEY_OTP_TOKEN = "token";




    //////........CHANGR_PASSWORD URL + CHANGE_PASSWORD KEY VALUES......../////////

    public static String URL_CHANGE_PASSWORD = BASE_URL + "change_password.php";
    public static final String KEY_MOBILE_CHANGE_PASSWORD = "mobile";
    public static final String KEY_PASSWORD_CHANGE_PASSWORD = "password";

}
