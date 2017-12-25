package com.example.fostersoftsol01.pickmehere;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fostersoftsol01.pickmehere.Activity.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Register extends AppCompatActivity implements View.OnClickListener{
    EditText et_first_name,et_last_name,et_email,et_phone_number,et_password,et_confirm_password;
    Button btn_signUp;
    TextView tv_have_an_account;
    String get_et_first_name,get_et_last_name,get_et_email,get_et_phone_number,get_et_password,get_et_confirm_password;
    String get_mobile_id,Register_user_type;
    String get_user_id;
    SharedPreferences sharedPreferences;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        id_Intilisation();
        click_Listener();
    }

    private void id_Intilisation() {
        linearLayout=(LinearLayout)findViewById(R.id.linear_layout2);
        et_first_name=(EditText)findViewById(R.id.et_firstName);
        et_last_name=(EditText)findViewById(R.id.et_lastName);
        et_email=(EditText)findViewById(R.id.et_register_Email);
        et_phone_number=(EditText)findViewById(R.id.et_phoneNumber);
        et_password=(EditText)findViewById(R.id.et_register_password);
        et_confirm_password=(EditText)findViewById(R.id.et_register_conforimPassword);

        btn_signUp=(Button)findViewById(R.id.btn_signup);
        tv_have_an_account=(TextView)findViewById(R.id.tv_haveAnAccount);
    }
    private void click_Listener()
    {
        tv_have_an_account.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==tv_have_an_account)
        {
            startActivity(new Intent(Register.this, Login.class));
        }
        if(v==btn_signUp)
        {
//            get_et_first_name=et_first_name.getText().toString().trim();
//            get_et_last_name=et_last_name.getText().toString().trim();
//            get_et_email=et_email.getText().toString().trim();
//            get_et_phone_number=et_phone_number.getText().toString().trim();
//            get_et_password=et_password.getText().toString().trim();
//            get_et_confirm_password=et_confirm_password.getText().toString().trim();
//            Register_user_type="user";
//            get_mobile_id= Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);



            if (et_first_name.getText().toString().trim().length() ==0) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Enter First Name", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();
            } else if (et_last_name.getText().toString().trim().length() ==0) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Enter Last Name", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();
            }else if (et_email.getText().toString().trim().length() ==0) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Enter Your Email", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();
            }
            else if (et_phone_number.getText().toString().trim().length() ==0) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Enter Your Mobile Number", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();
            }
            else if (et_password.getText().toString().trim().length() ==0 ) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Enter Your Password", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();
            }
            else if (et_password.getText().toString().trim().length() < 6 ) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Enter Password within 6 to 8", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();
            }
            else if (et_confirm_password.getText().toString().trim().length() ==0 ) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Enter Your Password", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();
            }
            else if (et_confirm_password.getText().toString().trim().length() <6 ) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Enter Confirm Password WithIn 6 to 8", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();
            }
            else if (et_password.getText().toString().trim() != et_confirm_password.getText().toString().trim()) {
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Enter Same Password", Snackbar.LENGTH_LONG)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                snackbar.show();
            }
            else
            {
               // new Register_Api().execute();
                startActivity(new Intent(Register.this,Login.class));
            }




//            startActivity(new Intent(Register.this,Login.class));
        }
    }

//    public class Register_Api extends AsyncTask<Void, Void, Void> {
//        String success;
//        String message;
//        ProgressDialog pDialog;
//
//        @Override
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(Register.this);
//            pDialog.setTitle("Contacting Servers");
//            pDialog.setMessage("Registering ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//
//            String url, urlprm = null;
//            url = Constant.URL_REGISTER;
//            try {
//                urlprm = Constant.KEY_REGISTER_FIRSTNAME + URLEncoder.encode(get_et_first_name, "UTF-8") + Constant.KEY_REGISTER_LASTNAME + URLEncoder.encode(get_et_last_name, "UTF-8") + Constant.KEY_REGISTER_EMAIL + URLEncoder.encode(get_et_email, "UTF-8") + Constant.KEY_REGISTER_MOBILE + URLEncoder.encode(get_et_phone_number, "UTF-8") + Constant.KEY_REGISTER_PASSWORD + URLEncoder.encode(get_et_password, "UTF-8") + Constant.KEY_REGISTER_DEVICE_ID + URLEncoder.encode(get_mobile_id, "UTF-8") + Constant.Key_Register_user_type + URLEncoder.encode(Register_user_type, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//            JSONParserClient jason = new JSONParserClient();
//            try {
//                JSONObject obj1 = jason.makaHTTPRequest(url, urlprm);
//                success = obj1.getString("status");
//                message=obj1.getString("message");
//                Register_user_type =  obj1.getString(Constant.Key_user_type);
//
//                get_user_id = obj1.getString(Constant.Key_user_id);
//                String gfirst_name = obj1.getString(Constant.KEY_FIRSTNAME);
//                String glast_name = obj1.getString(Constant.KEY_LASTNAME);
//                String gemail = obj1.getString(Constant.Key_email);
//                String gmobile = obj1.getString(Constant.KEY_MOBILE);
//                String gdevice_id=obj1.getString(Constant.Key_device_id);
//
//                //Toast.makeText(Register.this, gfullname, Toast.LENGTH_SHORT).show();
//
//                sharedPreferences = getSharedPreferences("hh", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                editor.putString(Constant.Key_user_id, get_user_id);
//                editor.putString(Constant.KEY_FIRSTNAME, gfirst_name);
//                editor.putString(Constant.KEY_LASTNAME, glast_name);
//                editor.putString(Constant.Key_email, gemail);
//                editor.putString(Constant.KEY_MOBILE, gmobile);
//                editor.putString(Constant.Key_device_id, gdevice_id);
//
//
//
//                editor.commit();
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//
//
//            super.onPostExecute(aVoid);
//
//            //Toast.makeText(Register.this, "Success"+message, Toast.LENGTH_SHORT).show();
//            if(success.equals("1")){
//                //Toast.makeText(Register.this, "Success"+success, Toast.LENGTH_SHORT).show();
//                Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
//                Intent i=new Intent(Register.this,Login.class);
//
//                startActivity(i);
//
//            }
//            else{
//                Toast.makeText(Register.this, "Connect with internet", Toast.LENGTH_SHORT).show();
//            }
//            pDialog.dismiss();
//
//        }
//    }
}
