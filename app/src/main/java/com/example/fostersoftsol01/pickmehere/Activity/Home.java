package com.example.fostersoftsol01.pickmehere.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fostersoftsol01.pickmehere.Constants;
import com.example.fostersoftsol01.pickmehere.GeocodeAddressIntentService;
import com.example.fostersoftsol01.pickmehere.LocationAccess;
import com.example.fostersoftsol01.pickmehere.PlaceArrayAdapter;
import com.example.fostersoftsol01.pickmehere.R;
import com.example.fostersoftsol01.pickmehere.Timer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity implements GoogleMap.OnMapLongClickListener,GoogleMap.OnMarkerDragListener,GoogleMap.OnMapClickListener,OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
    //extends FragmentActivity implements OnMapReadyCallback
    ImageView draw_icon;
    DrawerLayout drawerLayout;
    Button btn_address;
    private TextView mNameTextView,mAddressTextView,mIdTextView,mPhoneTextView,mWebTextView
            ,mAttTextView,tvTnC,tvCreateAccount,newAccount,device_id;
    private GoogleApiClient mGoogleApiClient;
    private static final String LOG_TAG = "MainActivity";

    GoogleMap googleMap;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView,dAutocompleteTextView;
    private PlaceArrayAdapter mPlaceArrayAdapter;
//    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
//            new LatLng(32.6393, -117.004304), new LatLng(44.901184, -67.32254));
private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
        new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));


    AddressResultReceiver mResultReceiver;
    String lat,log;
    double f=0,g=0,f1=-34,g1=151;

    boolean fetchAddress;
    int fetchType = Constants.USE_ADDRESS_NAME;

    private static final String TAG = "MAIN_ACTIVITY";
    LocationManager locationManager;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mResultReceiver = new AddressResultReceiver(null);

//        mGoogleApiClient = new GoogleApiClient
//                .Builder(this)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .enableAutoManage(this, this)
//                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(Home.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        id_Intilisation();
        click_Listener();
       btn_address.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(Home.this, "welcome", Toast.LENGTH_SHORT).show();
               Toast.makeText(Home.this, mAutocompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(Home.this, GeocodeAddressIntentService.class);
               intent.putExtra(Constants.RECEIVER, mResultReceiver);
               intent.putExtra(Constants.FETCH_TYPE_EXTRA, fetchType);

//               if(mAutocompleteTextView.getText().length() == 0) {
//                       Toast.makeText(Home.this, "Please enter an address name", Toast.LENGTH_LONG).show();
//                       return;
//                   }
                   intent.putExtra(Constants.LOCATION_NAME_DATA_EXTRA, mAutocompleteTextView.getText().toString());


               startService(intent);

           }
       });





    }


    private void id_Intilisation() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawyer_layout);
        draw_icon = (ImageView) findViewById(R.id.image_icon);
        btn_address=(Button)findViewById(R.id.btn_address);
        dAutocompleteTextView=(AutoCompleteTextView)findViewById(R.id.destination_location);
        dAutocompleteTextView.setThreshold(3);

        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .pick_location);
        mAutocompleteTextView.setThreshold(3);
        mNameTextView = (TextView) findViewById(R.id.name);
        mAddressTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        mIdTextView = (TextView) findViewById(R.id.place_id);
        mPhoneTextView = (TextView) findViewById(R.id.phone);
        mWebTextView = (TextView) findViewById(R.id.web);
        mAttTextView = (TextView) findViewById(R.id.att);
        //mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
        dAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        InputMethodManager mgr = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(mAutocompleteTextView.getWindowToken(), 0);

        InputMethodManager mgr1 = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(dAutocompleteTextView.getWindowToken(), 0);


    }


    private void click_Listener() {
        draw_icon.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == draw_icon) {

            drawerLayout.openDrawer(GravityCompat.START);
        }


    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LatLng dragPosition = marker.getPosition();
        double dragLat = dragPosition.latitude;
        double dragLong = dragPosition.longitude;
        Log.i("info", "on drag end :" + dragLat + " dragLong :" + dragLong);
        Toast.makeText(getApplicationContext(), "Marker Dragged..!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(f, g);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            if (resultCode == Constants.SUCCESS_RESULT) {
                final Address address = resultData.getParcelable(Constants.RESULT_ADDRESS);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // progressBar.setVisibility(View.GONE);
//                        infoText.setVisibility(View.VISIBLE);
//                        infoText.setText("Latitude: " + address.getLatitude() + "\n" +
//                                "Longitude: " + address.getLongitude() + "\n" +
                              //  "Address: " + resultData.getString(Constants.RESULT_DATA_KEY));
                       //double  str=address.getLatitude();
                        f=address.getLatitude();
                        g=address.getLongitude();
                        lat=String.valueOf(address.getLatitude());
                        log=String.valueOf(address.getLongitude());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("lant", String.valueOf(f));
                        editor.putString("long", String.valueOf(g));
                        editor.apply();
//

//                        f1=f;
//                        g1=g;
//                        CameraPosition INIT =
//                                new CameraPosition.Builder()
//                                        .target(new LatLng(f,g))
//                                        .zoom(17.5F)
//                                        .bearing(300F) // orientation
//                                        .tilt( 50F) // viewing angle
//                                        .build();
//
//                        // use map to move camera into position
//                        googleMap.moveCamera( CameraUpdateFactory.newCameraPosition(INIT) );
//
//                        //create initial marker
//                        googleMap.addMarker( new MarkerOptions()
//                                .position( new LatLng(f, g) )
//                                .title("Location")
//                                .snippet("First Marker")).showInfoWindow();

                        Toast.makeText(Home.this,String.valueOf(address.getLatitude()), Toast.LENGTH_SHORT).show();
                    }

                });
            }
            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Home.this, "else part", Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.GONE);
//                        infoText.setVisibility(View.VISIBLE);
//                        infoText.setText(resultData.getString(Constants.RESULT_DATA_KEY));
                    }
                });
            }
        }
    }


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//
//        // Add a marker in Sydney and move the camera
////        LatLng sydney = new LatLng(f1, g1);
////        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
////        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//       // LatLng sydney = new LatLng(f,g);
////
////
////
////        googleMap.addMarker(new MarkerOptions().position(sydney).title(country+address+city+state+postalcode+knownname));
////        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
////        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));
//    }



    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            mNameTextView.setText(Html.fromHtml(place.getName() + ""));
            mAddressTextView.setText(Html.fromHtml(place.getAddress() + ""));
            mIdTextView.setText(Html.fromHtml(place.getId() + ""));
            mPhoneTextView.setText(Html.fromHtml(place.getPhoneNumber() + ""));
            mWebTextView.setText(place.getWebsiteUri() + "");
            if (attributions != null) {
                mAttTextView.setText(Html.fromHtml(attributions.toString()));
            }
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }






}

