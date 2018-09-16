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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
 * Created by Somnath Yadav on 29-06-2018.
 */

public class MFIStaffReg extends AppCompatActivity {

    private static final String TAG = "RegisterMFIStaff";
    private static final String URL_FOR_REGISTRATION = "http://samavit.in/fe_app_lighter_version/mfi_staff_reg_lv.php";
    ProgressDialog progressDialog;
    private static final int REQUEST_LOCATION = 1;
    Button bMfiStaffRegSubmit;
    EditText et_mfi_name_other,et_staff_id;
    LocationManager locationManager;
    Context context;
    Spinner spinnerMFI;
    LinearLayout llMFINameOther;
    String  mfi = "", staff_id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLang();
        setContentView(R.layout.mfi_staff_registration);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        context = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        bMfiStaffRegSubmit = findViewById(R.id.b_mfi_staff_submit);
        et_mfi_name_other = findViewById(R.id.et_mfi_name_other);
        et_staff_id = findViewById(R.id.et_staff_id);
        llMFINameOther = findViewById(R.id.llMFINameOther);

        //dropdown for name of mfi
        spinnerMFI = findViewById(R.id.spinnerMFI);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.mfi_name, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMFI.setAdapter(adapter1);

        //dropdown for designation of the staff


        spinnerMFI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //checking if "other" option is clicked in the mfi name list
                if (i == 6) {
                    llMFINameOther.setVisibility(View.VISIBLE);//displaying the editText to enter the name of mfi
                } else {
                    llMFINameOther.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        bMfiStaffRegSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting spinner value
                mfi = spinnerMFI.getSelectedItem().toString();
                //getting edittext
                staff_id = et_staff_id.getText().toString();

                if(mfi.contains("Other"))
                    mfi = et_mfi_name_other.getText().toString();


                if (mfi.equals("") || staff_id.equals("")|| mfi.contains("Choose") )
                    Toast.makeText(MFIStaffReg.this, getResources().getString(R.string.fill_all), Toast.LENGTH_SHORT).show();
                else
                {
                    SharedPreferences preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("login_Mfi_staff_reg", true);
                    /*editor.putInt("count_views", 60);
                    for (int j = 0; j < 34; j++) {
                        editor.putInt("IntValue_views" + j, 0);
                    }*/
                    editor.apply();
                    registerMFIStaff(mfi, staff_id);
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

    private void registerMFIStaff(final String mfi,final String staffID) {
        // Tag used to cancel the request
        String cancel_req_tag = "register_mfi_staff";

        progressDialog.setMessage(getResources().getString(R.string.wait));
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, URL_FOR_REGISTRATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        // Launch module activity
                        Intent intent = new Intent(MFIStaffReg.this, EnterAs.class);
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
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("mfi", mfi);
                params.put("branch_name", "branchName");
                params.put("branch_id", "branchID");
                params.put("mfi_name", "name");
                params.put("desig", "designation");
                params.put("staff_id", staffID);
                params.put("location", "location");
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
