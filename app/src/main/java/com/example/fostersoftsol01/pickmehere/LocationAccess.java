package com.example.fostersoftsol01.pickmehere;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Pawneshwer on 11-Apr-16.
 */
public class LocationAccess extends Service implements LocationListener {
  //  SharedPreferences    SavePrefs;
    SharedPreferences sharedpreferences;



    //
    // = getSharedPreferences("Hello", Context.MODE_PRIVATE);
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 5;
    private final Context mContext;
    public boolean isGPSEnabled = false;
    protected LocationManager locationManager;
    boolean isNetworkEnabled = false;
    public boolean canGetLocation = false;
    Location location;
    double latitude;
    double longitude;


    public LocationAccess(Context context) throws IOException
    {
        this.mContext = context;
        //getLocation();
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        /*geocoder=new Geocoder(this, Locale.getDefault());
        addresses=geocoder.getFromLocation(latitude,longitude,1);
        address=addresses.get(0).getAddressLine(0);
        city=addresses.get(0).getLocality();
        state=addresses.get(0).getAdminArea();
        country=addresses.get(0).getCountryName();
        postalcode=addresses.get(0).getPostalCode();
        knownname=addresses.get(0).getFeatureName();
*/
      //  CheckConnection();

        getLocation();
    }

    public void CheckConnection()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("GPS settings");
        builder.setMessage("GPS is not enable");
        builder.setPositiveButton("Setting on", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(i);
            }
        });

        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();
            }
        });

        builder.show();


    }


    private void getLocation() {
        //To setup location manager
        try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.v("isGPSEnabled", "=" + isGPSEnabled);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.v("isNetworkEnabled", "=" + isNetworkEnabled);
            if (!isGPSEnabled && !isNetworkEnabled)
            {

            }
            else
                {
                this.canGetLocation = true;
                //To request location updates
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("lant", String.valueOf(latitude));
                            editor.putString("long", String.valueOf(longitude));
                            editor.apply();
//                            SavePrefs.writePrefs(mContext, Constants.KEY_LATITUDE, String.valueOf(latitude));
//                            SavePrefs.writePrefs(mContext, Constants.KEY_LONGITUDE, String.valueOf(longitude));



                }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("lant", String.valueOf(latitude));
                                editor.putString("long", String.valueOf(longitude));


                                editor.commit();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
//            Log.e(Constants.TAG, e.getMessage());
        }
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(LocationAccess.this);
        }
    }


    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() throws Exception {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).setTitle("GPS is settings")
                .setMessage("GPS is not enabled. Do you want to go to settings menu?")
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                mContext.startActivity(intent);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                .show();
        TextView msgText = (TextView) alertDialog.findViewById(android.R.id.message);
        msgText.setTextSize(25);
        Button yesBtn = (Button) alertDialog.findViewById(android.R.id.button1);
        yesBtn.setTextSize(25);
        Button noBtn = (Button) alertDialog.findViewById(android.R.id.button2);
        noBtn.setTextSize(25);

    }
    public  double getLatitude()
    {
        return location.getLatitude();
    }

    public  double getLongitude()
    {
        return location.getLongitude();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("lant", String.valueOf(latitude));
        editor.putString("long", String.valueOf(longitude));
        editor.commit();
//        Log.d(Constants.TAG, "Lat : " + latitude + " and lon : " + longitude);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}