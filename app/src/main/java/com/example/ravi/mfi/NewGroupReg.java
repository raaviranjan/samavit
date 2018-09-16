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
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Somnath Yadav on 30-06-2018.
 */

public class NewGroupReg extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    private static final String URL_FOR_REGISTRATION = "http://samavit.in/fe_app_lighter_version/group_reg_lv.php";
    String gname,gid,g_nomember="";
    LocationManager locationManager;
    Context context;
    ProgressDialog progressDialog;
    Button b_grp_reg_submit;
    EditText et_group_name,et_grp_ID,et_total_members;
    String gName="",gId="";
    SharedPreferences preferences;
    int[] viewedNewGrp = new int[60];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLang();
        setContentView(R.layout.group_registration);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        context = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        b_grp_reg_submit = findViewById(R.id.b_grp_reg_submit);
        et_group_name = findViewById(R.id.et_group_name);
        et_grp_ID = findViewById(R.id.et_centre_group_id);
        et_total_members=findViewById(R.id.et_total_members);



        b_grp_reg_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gName = et_group_name.getText().toString();
                gId = et_grp_ID.getText().toString();
                g_nomember=et_total_members.getText().toString();

                if(gName.equals("") || gId.equals("") || g_nomember.equals("")){
                    Toast.makeText(NewGroupReg.this, getResources().getString(R.string.fill_all), Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("clicked","Group");
                    editor.putString("group_name",gName);
                    editor.putString("group_id",gId);

                    editor.putInt("count_" + gName + gId, viewedNewGrp.length);
                    for (int j = 0; j < viewedNewGrp.length; j++) {
                        editor.putInt("IntValue_" + gName + gId + j, viewedNewGrp[j]);
                    }

                    editor.apply();
                    registerGroup(gName,gId,g_nomember);
                }

            }
        });

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

    private void registerGroup(final String g_name, final String g_id, final String g_no_member) {
        // Tag used to cancel the request
        String cancel_req_tag = "register_group";

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
                        Intent intent = new Intent(NewGroupReg.this, ChoicesMFI.class);
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
                // Posting params to register  url
                Map<String, String> params = new HashMap<String, String>();
                params.put("g_name",g_name);
                params.put("g_id",g_id);
                params.put("g_no_member",g_no_member);
                params.put("locality","locality");
                params.put("location","location");
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
