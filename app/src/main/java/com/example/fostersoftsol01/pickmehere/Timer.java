package com.example.fostersoftsol01.pickmehere;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.util.List;
import java.util.Locale;

/**
 * Created by MAHAJAN on 4/21/2017.
 */

public class Timer extends Service implements OnMapReadyCallback {
    CountDownTimer mCountDownTimer;
    LocationManager locationManager;
    Boolean isgpsenable, isnetworkenable;
    // LocationAccess locationAccess;
    private GoogleMap mMap;



    SharedPreferences sharedPreferences;
    String get_lat,
            get_lang;

    Double f,g;
    LocationAccess locationAccess;
    Geocoder geocoder;
    List<Address> addresses;
    String address,city,state,country,postalcode,knownname,getthrough;

    Location location;
    double latitude;
    double longitude;

    int i=0;

    @Override
    public void onDestroy() {

        mCountDownTimer.cancel();

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            locationAccess = new LocationAccess(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
       /* isgpsenable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isnetworkenable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
*/
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        getprefrencevalue();

        geocoder=new Geocoder(this, Locale.getDefault());
        try {
            addresses=geocoder.getFromLocation(f,g,1);
            for(int i=0;i<addresses.size();i++){
                address=addresses.get(i).getAddressLine(i);
                city=addresses.get(i).getLocality();
                state=addresses.get(i).getAdminArea();
                country=addresses.get(i).getSubAdminArea();
                postalcode=addresses.get(i).getPostalCode();
                knownname=addresses.get(i).getFeatureName();

                //  Toast.makeText(this, country+address+city+state+postalcode+knownname, LENGTH_SHORT).show();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }





        mCountDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUnitFinished) {


            }

            @Override
            public void onFinish() {
                // getlatlong();
                getprefrencevalue();
                sharedPreferences = getSharedPreferences("hh", Context.MODE_PRIVATE);
                String suserid = sharedPreferences.getString("id", "");
              //  String gete_pass = sharedpreferences.getString("eemail", "");



                String slattitude=String.valueOf(f);
                String slongitude=String.valueOf(g);

                Toast.makeText(Timer.this, String.valueOf(f), Toast.LENGTH_SHORT).show();
                Toast.makeText(Timer.this, String.valueOf(g), Toast.LENGTH_SHORT).show();


               /* i=i+1;
                Toast.makeText(TimmerService.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
*/
                mCountDownTimer.start();
            }
        };
        mCountDownTimer.start();

        return START_STICKY;
    }












    private void getprefrencevalue() {
       sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Timer.this);
        getSharedPreferences("Hello", Context.MODE_PRIVATE);
        get_lat=sharedPreferences.getString("lant","0");
        get_lang=sharedPreferences.getString("long","0");

        f= Double.parseDouble(get_lat);
        g=Double.parseDouble(get_lang);

      /*  Toast.makeText(this,get_lat , LENGTH_SHORT).show();
        Toast.makeText(this,get_lang , LENGTH_SHORT).show();
*/
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        LatLng sydney = new LatLng(f,g);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.addMarker(new MarkerOptions().position(sydney).title(country+address+city+state+postalcode+knownname));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));

    }
}
