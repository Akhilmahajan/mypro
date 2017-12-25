package com.example.fostersoftsol01.pickmehere.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fostersoftsol01.pickmehere.Constant;
import com.example.fostersoftsol01.pickmehere.R;
import com.example.fostersoftsol01.pickmehere.Register;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText email_login,password_login;
    TextView tv_forgot_password,tv_new_account;
    Button btn_login;
    EditText et_forget_Email,et_OTP,et_new_password,et_confirm_password;
    Button btn_forget_submit,btn_OTP_submit,btn_change_password_submit;
    Dialog dialog_Email,dialog_OTP,dialog_change_password;
    LinearLayout linear_layout;
    SharedPreferences sharedPreferences;
    String get_login_email,get_login_password,get_login_mobile_id;
    String get_forget_email,get_edt_otp;
    String strUserId;
    String get_user_email;
    String  str_cnfrm_password,str_new_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(Constant.KEY_PREF, Context.MODE_PRIVATE);
        strUserId = sharedPreferences.getString(Constant.Key_user_id,"" );
        get_user_email = sharedPreferences.getString(Constant.Key_email,"" );

        id_Intilisation();
        click_Listener();
    }

    private void id_Intilisation() {
        linear_layout=(LinearLayout)findViewById(R.id.linear_layout1);
        email_login=(EditText)findViewById(R.id.et_email);
        password_login=(EditText)findViewById(R.id.et_password);
        tv_forgot_password=(TextView)findViewById(R.id.tv_forgot);
        tv_new_account=(TextView)findViewById(R.id.tv_newAccount);
        btn_login=(Button)findViewById(R.id.btn_login);
    }
    private void click_Listener()
    {
        tv_new_account.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_forgot_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==tv_new_account)
        {
            startActivity(new Intent(Login.this, Register.class));
        }
        if(v==btn_login)
        {
            get_login_email=email_login.getText().toString().trim();
            get_login_password=password_login.getText().toString().trim();
            get_login_mobile_id= Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);

            if(email_login.getText().toString().length()==0)
            {
                Snackbar snackbar = Snackbar
                        .make(linear_layout, "Enter Emailid", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();

            }
           else if(password_login.getText().toString().length()==0)
            {
                Snackbar snackbar = Snackbar
                        .make(linear_layout, "Enter Password", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();

            }
            else
            {
            new Login_Api().execute(get_login_email, get_login_password,get_login_mobile_id);
//
            }
           // startActivity(new Intent(Login.this,Home.class));
        }
        if(v==tv_forgot_password)
        {
            showDialog();

        }
    }


        private void showDialog()
        {
            AlertDialog.Builder builder ;
            builder = new AlertDialog.Builder(Login.this);
            LayoutInflater inflater = ((Activity) Login.this).getLayoutInflater();
            View v = inflater.inflate(R.layout.password_forgot, null);
            builder.setView(v);

            et_forget_Email = (EditText) v.findViewById(R.id.et_forgot_Email);
             btn_forget_submit = (Button) v.findViewById(R.id.btn_forgot_submit);

            btn_forget_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    get_forget_email=et_forget_Email.getText().toString();

 //                   new Get_forgot_password().execute(get_forget_email);
                    showOTPDialog();
//                    get_edt_email=edtemail.getText().toString();
//
//                    new Get_forgot_password().execute(get_edt_email);


//                if (edtPhoneNo.getText().toString().trim().length() < 10) {
//                    edtPhoneNo.setError("Enter vailed Number");
//                }
//                else
//                {
//                    forgotPasswordMobile();
//
//
//
//                }

                }
            });
            dialog_Email= builder.create();
            dialog_Email.show();
        }

    private void showOTPDialog() {
        dialog_Email.dismiss();

        AlertDialog.Builder builder ;
        builder = new AlertDialog.Builder(Login.this);
        LayoutInflater inflater = ((Activity) Login.this).getLayoutInflater();
        View v = inflater.inflate(R.layout.otp_page, null);
        builder.setView(v);

        et_OTP = (EditText) v.findViewById(R.id.et_OTP);
        btn_OTP_submit = (Button) v.findViewById(R.id.btn_OTP_Submit);

        btn_OTP_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_edt_otp=et_OTP.getText().toString().trim();
                showPasswordChangeDialog();

//                if (et_OTP.getText().toString().trim().length() < 4) {
//                    et_OTP.setError("Enter vailed OTP");
//                }
//                else
//                {
//                     new Get_otp().execute(strUserId,get_user_email,get_edt_otp);
//                   // showPasswordChangeDialog();
//                }

            }
        });
        dialog_OTP= builder.create();
        dialog_OTP.show();
    }

    private void showPasswordChangeDialog() {
        dialog_OTP.dismiss();

        AlertDialog.Builder builder ;
        builder = new AlertDialog.Builder(Login.this);
        LayoutInflater inflater = ((Activity) Login.this).getLayoutInflater();
        View v = inflater.inflate(R.layout.change_password, null);
        builder.setView(v);

        et_new_password = (EditText) v.findViewById(R.id.et_NewPass);
        et_confirm_password = (EditText) v.findViewById(R.id.et_ConfPass);
       btn_change_password_submit = (Button) v.findViewById(R.id.btn_Change_Password_Submit);

        btn_change_password_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_new_password = et_new_password.getText().toString().trim();
                str_cnfrm_password= et_confirm_password.getText().toString().trim();
                startActivity(new Intent(Login.this,Login.class));

//                if (et_new_password.getText().toString().trim().length() < 6) {
//                    et_new_password.setError("Enter Password between 6 to 8 digit");
//                } else if (et_confirm_password.getText().toString().trim().length() < 6) {
//                    et_confirm_password.setError("Enter Password between 6 to 8 digit");
//                } else if(!(et_new_password.getText().toString().equals(et_confirm_password.getText().toString()))) {
//                    et_confirm_password.setError("Enter Same Password");
//                }
//                else
//                {
//                    changePassowrd();
//                    startActivity(new Intent(Login.this,Login.class));
//                    dialog_change_password.dismiss();
//
//                }
//
            }
        });
        dialog_change_password= builder.create();
        dialog_change_password.show();
    }
    public class Login_Api extends AsyncTask<Object, Object, Object> {





        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Login.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Registering ...");
            //pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected Object doInBackground(Object[] params) {

            //String type = (String) params[0];
            //String user_id= (String) params[0];
            String user_name = (String) params[0];
            String pass_word = (String) params[1];
            String device_id= (String) params[2];
            String login_url = Constant.URL_LOGIN;

            try {
                URL url = new URL(login_url);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode(Constant.Key_email, "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" + URLEncoder.encode(Constant.Key_password, "UTF-8") + "=" + URLEncoder.encode(pass_word, "UTF-8") + "&" + URLEncoder.encode(Constant.Key_device_id, "UTF-8") + "=" + URLEncoder.encode(device_id, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            pDialog.dismiss();

            Toast.makeText(getBaseContext(), "" + o, Toast.LENGTH_LONG).show();



            try {

                //Toast.makeText(SignIn.this, "welcome", Toast.LENGTH_SHORT).show();
                JSONObject j = new JSONObject(String.valueOf(o));
                String success = j.getString("status");
                String guserid = j.getString("user_id");
                String gemail = j.getString("email");
                String gdate = j.getString("date_of_birth");
                String glocation = j.getString("location");
                String gfullname = j.getString("full_name");

                if (success.equals("1")) {
                    String message = j.getString("message");

                    // Toast.makeText(SignIn.this, gemail, Toast.LENGTH_SHORT).show();

                    if (message.equals("null")) {
                        Toast.makeText(Login.this, "Invalid Username/Password.", Toast.LENGTH_SHORT).show();
                    } else {
                        // JSONObject jsonObject = j.getJSONObject("message");
//                        String guserid = j.getString("user_id");
//                        String gfullname = j.getString("full_name");
//                        String gdate = j.getString("date_of_birth");
//                        String gemail = j.getString("email");
//                        String glocation = j.getString("location");
//                        String gdevice_id=j.getString("device_id");

                        if (j.getString(Constant.Key_user_type).equals("user"))
                        {
                            startActivity(new Intent(Login.this,Home.class));
                        }
//
//


//                        Toast.makeText(loginpage.this, " " + guserid + " " + gfirstname + "" + glastname, Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(loginpage.this, navigation_view.class);
//                        startActivity(i);
                        sharedPreferences = getSharedPreferences(Constant.KEY_PREF, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(Constant.Key_user_id, guserid);
                        editor.putString(Constant.KEY_FIRSTNAME, gfullname);
                        editor.putString(Constant.KEY_LASTNAME, gdate);
                        editor.putString(Constant.Key_email, gemail);
                        editor.putString(Constant.KEY_MOBILE, glocation);
         //               editor.putString(Constant.Key_device_id, gdevice_id);



                        editor.commit();
                    }

                } else {
                    Toast.makeText(Login.this, "Failed to Connect Server", Toast.LENGTH_SHORT).show();

                }


                //Toast.makeText(context," "+success+" "+name+" "+fname+" "+gname+" "+qname+" "+ename+" ", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {

                // Toast.makeText(SignIn.this, "Error", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar
                        .make(linear_layout, "Enter Valid Email or Password", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();
                e.printStackTrace();

            }




        }
    }

//    public class Get_forgot_password extends AsyncTask<Object, Object, Object> {
//
//
//
//
//
//        ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            pDialog = new ProgressDialog(Login.this);
//            pDialog.setTitle("Contacting Servers");
//            pDialog.setMessage("Registering ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
//
//
//        @Override
//        protected Object doInBackground(Object[] params) {
//
//            //String type = (String) params[0];
//            //String user_id= (String) params[0];
//            // String user_name = (String) params[0];
//            String user_get_email = (String) params[0];
//
//            String login_url = Constant.URL_FORGET_PASSWORD;
//
//            try {
//                URL url = new URL(login_url);
//                try {
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setRequestMethod("POST");
//                    httpURLConnection.setDoOutput(true);
//                    httpURLConnection.setDoInput(true);
//                    OutputStream outputStream = httpURLConnection.getOutputStream();
//                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                    String post_data = URLEncoder.encode(Constant.Key_email, "UTF-8") + "=" + URLEncoder.encode(user_get_email, "UTF-8");
//                    bufferedWriter.write(post_data);
//                    bufferedWriter.flush();
//                    bufferedWriter.close();
//                    InputStream inputStream = httpURLConnection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                    String result = "";
//                    String line = "";
//                    while ((line = bufferedReader.readLine()) != null) {
//                        result += line;
//                    }
//                    bufferedReader.close();
//                    inputStream.close();
//                    httpURLConnection.disconnect();
//                    return result;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//            pDialog.dismiss();
//
//            Toast.makeText(getBaseContext(), "" + o, Toast.LENGTH_LONG).show();
//
//
//
//            try {
//
//                // Toast.makeText(SignIn.this, "welcome", Toast.LENGTH_SHORT).show();
//                JSONObject j = new JSONObject(String.valueOf(o));
//                String success = j.getString("status");
//                String get_user_id=j.getString(Constant.Key_user_id);
//
//                //Toast.makeText(SignIn.this, get_user_id, Toast.LENGTH_SHORT).show();
//                if (success.equals("1")) {
//                    String message = j.getString("message");
//                    if (message.equals("null")) {
//                        Toast.makeText(Login.this, "Invalid Username/Password.", Toast.LENGTH_SHORT).show();
//                    } else {
//                        // JSONObject jsonObject = j.getJSONObject("message");
////                        String guserid = j.getString("user_id");
////                        String gfullname = j.getString("full_name");
////                        String gdate = j.getString("date_of_birth");
////                        String gemail = j.getString("email");
////                        String glocation = j.getString("location");
////                        String gdevice_id=j.getString("device_id");
//
////                        if (j.getString("user_type").equals("user"))
////                        {
////                            startActivity(new Intent(SignIn.this,SelectProduct.class));
////                        }
////
//                        showOTPDialog();
//
//
////                        Toast.makeText(loginpage.this, " " + guserid + " " + gfirstname + "" + glastname, Toast.LENGTH_SHORT).show();
////                        Intent i = new Intent(loginpage.this, navigation_view.class);
////                        startActivity(i);
////                        sharedprefrence = getSharedPreferences("akh", Context.MODE_PRIVATE);
////                        SharedPreferences.Editor editor = sharedprefrence.edit();
//
//                        //                       editor.putString("user_id", get_user_id);
////                        editor.putString("full_name", gfullname);
////                        editor.putString("date_of_birth", gdate);
////                        editor.putString("email", gemail);
////                        editor.putString("location", glocation);
////                        editor.putString("device_id", gdevice_id);
//
//
//
//                        //                  editor.commit();
//                    }
//
//                } else {
//                    Toast.makeText(Login.this, "Failed to Connect Server", Toast.LENGTH_SHORT).show();
//
//                }
//
//
//                //Toast.makeText(context," "+success+" "+name+" "+fname+" "+gname+" "+qname+" "+ename+" ", Toast.LENGTH_SHORT).show();
//            } catch (JSONException e) {
//
//                Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//
//            }
//
//
//
//
//        }
//    }













//    public class Get_otp extends AsyncTask<Object, Object, Object> {
//
//
//
//
//
//        ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            pDialog = new ProgressDialog(Login.this);
//            pDialog.setTitle("Contacting Servers");
//            pDialog.setMessage("Registering ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
//
//
//        @Override
//        protected Object doInBackground(Object[] params) {
//
//            //String type = (String) params[0];
//            String suser_id= (String) params[0];
//            String suser_email = (String) params[1];
//            String suser_token = (String) params[2];
//
//            String login_url = Constant.URL_OTP;
//
//            try {
//                URL url = new URL(login_url);
//                try {
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setRequestMethod("POST");
//                    httpURLConnection.setDoOutput(true);
//                    httpURLConnection.setDoInput(true);
//                    OutputStream outputStream = httpURLConnection.getOutputStream();
//                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                    String post_data = URLEncoder.encode(Constant.Key_user_id, "UTF-8") + "=" + URLEncoder.encode(suser_id, "UTF-8") + "&" + URLEncoder.encode(Constant.Key_email, "UTF-8") + "=" + URLEncoder.encode(suser_email, "UTF-8") + "&" + URLEncoder.encode(Constant.KEY_OTP_TOKEN, "UTF-8") + "=" + URLEncoder.encode(suser_token, "UTF-8");
//                    bufferedWriter.write(post_data);
//                    bufferedWriter.flush();
//                    bufferedWriter.close();
//                    InputStream inputStream = httpURLConnection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                    String result = "";
//                    String line = "";
//                    while ((line = bufferedReader.readLine()) != null) {
//                        result += line;
//                    }
//                    bufferedReader.close();
//                    inputStream.close();
//                    httpURLConnection.disconnect();
//                    return result;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//            pDialog.dismiss();
//
//            Toast.makeText(getBaseContext(), "" + o, Toast.LENGTH_LONG).show();
//
//
//
//            try {
//
//                //Toast.makeText(SignIn.this, "welcome", Toast.LENGTH_SHORT).show();
//                JSONObject j = new JSONObject(String.valueOf(o));
//                String success = j.getString("status");
//                String get_user_id=j.getString(Constant.Key_user_id);
//                //Toast.makeText(SignIn.this, get_user_id, Toast.LENGTH_SHORT).show();
//                if (success.equals("1")) {
//                    String message = j.getString("message");
//                    if (message.equals("null")) {
//                        Toast.makeText(Login.this, "Invalid Username/Password.", Toast.LENGTH_SHORT).show();
//                    } else {
//                        // JSONObject jsonObject = j.getJSONObject("message");
//
//                        showPasswordChangeDialog();
//
//
//
//                    }
//
//                } else {
//                    Toast.makeText(Login.this, "Failed to Connect Server", Toast.LENGTH_SHORT).show();
//
//                }
//
//
//                //Toast.makeText(context," "+success+" "+name+" "+fname+" "+gname+" "+qname+" "+ename+" ", Toast.LENGTH_SHORT).show();
//            } catch (JSONException e) {
//
//                //Toast.makeText(SignIn.this,"Code not matched", Toast.LENGTH_SHORT).show();
//                Snackbar snackbar = Snackbar
//                        .make(linear_layout, "Enter Valied OTP", Snackbar.LENGTH_LONG)
//                        .setAction("", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                            }
//                        });
//
//                snackbar.show();
//
//                e.printStackTrace();
//
//            }
//
//
//
//
//        }
//    }














//    public void changePassowrd()
//    {
//
//
//        Log.e("password",str_new_password);
//        Log.e("password",str_cnfrm_password);
//
//        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_CHANGE_PASSWORD,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i("res>", response);
//
//
//                        try {
//                            JSONObject jsonObjectMobile = new JSONObject(response);
//                            String streemail="";
//
//                            streemail = jsonObjectMobile.getString(Constant.Key_email);
//
//                           sharedPreferences = getApplicationContext().getSharedPreferences("akh", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString(Constant.Key_password, streemail);
//
//
//                            editor.commit();
//
//
//                            Toast.makeText(Login.this,response , Toast.LENGTH_LONG).show();
//
//                            Log.e("Phone",streemail);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        //  Toast.makeText(Login.this,response , Toast.LENGTH_LONG).show();
//                    }
//
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG ).show();
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> map = new HashMap<String,String>();
//                map.put(Constant.Key_user_id, strUserId);
//                // map.put(Constant.Key_password, strnewpassword);
//                map.put(Constant.Key_email,get_forget_email);
//                map.put(Constant.Key_password,str_cnfrm_password);
//                return map;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }

    }

