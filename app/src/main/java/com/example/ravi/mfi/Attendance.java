package com.example.ravi.mfi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Ravi on 30-Jun-18.
 */

public class Attendance extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    private static final String URL_FOR_REGISTRATION = "http://samavit.in/fe_app_lighter_version/attendance_lv.php";

    LocationManager locationManager;
    Context context;
    String date,currentTime,gName,gId,clicked,groupNameAndID;
    SharedPreferences preferences;
    TextView tvDateAttendance,tvTimeAttendance,tv_gName_attend,tv_gId_attend;
    EditText tvLocAttendance;
    Button bSubmitAttendance;
    ProgressDialog progressDialog;
    EditText no_member;
    ImageView ibLocAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLang();
        setContentView(R.layout.attendance);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);

        context = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        //getting current date
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
        Date d1 = new Date();
        date = sdf1.format(d1);

        //getting current time
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        currentTime = timeFormat.format(Calendar.getInstance().getTime());

        tvDateAttendance = findViewById(R.id.tvDateAttendance);
        tvTimeAttendance = findViewById(R.id.tvTimeAttendance);
        tv_gName_attend = findViewById(R.id.tv_gName_on_attend);
        tv_gId_attend = findViewById(R.id.tv_gId_on_attend);
        tvLocAttendance = findViewById(R.id.tvLocAttendance);
        no_member=findViewById(R.id.et_NoOfMemberPresent);
        bSubmitAttendance = findViewById(R.id.bSubmitAttendance);
        ibLocAttendance = findViewById(R.id.ibLocAttendance);

        tvDateAttendance.setText(date);
        tvTimeAttendance.setText(currentTime);

        clicked = preferences.getString("clicked",null);

        if(clicked.equals("Group")){
            gName = preferences.getString("group_name",null);
            gId = preferences.getString("group_id",null);
            groupNameAndID = gName + gId;
            tv_gName_attend.setText(getResources().getString(R.string.group_name_module) + gName);
            tv_gId_attend.setText(getResources().getString(R.string.group_id_module) + gId);
        }

        bSubmitAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no_of_member = no_member.getText().toString();
                String time=currentTime;

                String location =tvLocAttendance.getText().toString();

                if(no_of_member.equals("") || location.equals("")){
                    Toast.makeText(Attendance.this, R.string.fill_all, Toast.LENGTH_SHORT).show();
                } else {
                    takeattendance(no_of_member,time,location,gName,gId);
                }
            }
        });
        ibLocAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    buildAlertMsgNoGPS();
                } else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    getLocation();
                }
            }
        });

    }
    public void getLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.
                checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location!=null){
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                tvLocAttendance.setText(getCompleteAddressString(lat,lng));
            } else
                Toast.makeText(this, "Unable to track your location", Toast.LENGTH_SHORT).show();
        }
    }

    public void buildAlertMsgNoGPS(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Turn on GPS")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current location ", strReturnedAddress.toString());
            } else {
                Log.w("My Current location", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current location", "Cannot get Address!");
        }
        return strAdd;
    }

    private void takeattendance(final String no_member, final String time, final String location, final String group_Name, final String group_id) {
        // Tag used to cancel the request
        String cancel_req_tag = "attendance";

        progressDialog.setMessage(getResources().getString(R.string.wait));
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST, URL_FOR_REGISTRATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        // Launch module activity
                        Intent intent = new Intent(Attendance.this, ModuleCompulsory.class);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"errorresponse", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("no_member",no_member);
                params.put("time",time);
                params.put("location",location);
                params.put("group_name",group_Name);
                params.put("group_id",group_id);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void loadLang() {
        SharedPreferences preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        String langCode = preferences.getString("language", "en");

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

}
