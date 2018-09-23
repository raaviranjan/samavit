package com.example.ravi.mfi;

import android.Manifest;
import android.app.AlertDialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class MfiClientReg extends AppCompatActivity {

    private static final String TAG = "RegisterMFIClient";
    private static final String URL_FOR_REGISTRATION = "http://samavit.in/fe_app_lighter_version/mfi_client_reg_lv.php";
    ProgressDialog progressDialog;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    SharedPreferences preferences;
    Context context;

    Button client_doneBtn;
    EditText  client_et_specify_mfi,client_id;
    Spinner  spinnerMFI;
    LinearLayout client_llMFIName;

    String  specify_mfi = "",client_Id="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLang();
        setContentView(R.layout.mfi_client_reg);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        context = this;
        preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        client_et_specify_mfi = findViewById(R.id.client_et_specify_mfi);
        client_llMFIName = findViewById(R.id.client_llMFIName);
        client_id=findViewById(R.id.et_client_id);

        //dropdown for name of mfi
        spinnerMFI = findViewById(R.id.client_spinnerMFI);
        ArrayAdapter adapter0 = ArrayAdapter.createFromResource(this, R.array.mfi_name, android.R.layout.simple_spinner_item);
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMFI.setAdapter(adapter0);

        spinnerMFI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //checking if "other" option is clicked in the mfi name list
                if (i == 6) {
                    client_llMFIName.setVisibility(View.VISIBLE);//displaying the editText to enter the name of mfi
                } else {
                    client_llMFIName.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        client_doneBtn = findViewById(R.id.client_doneBtn);
        client_doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting spinner value
                specify_mfi = spinnerMFI.getSelectedItem().toString();

                if(specify_mfi.contains("Other"))
                    specify_mfi = client_et_specify_mfi.getText().toString();

                //getting edittext
                client_Id=client_id.getText().toString();

                if (specify_mfi.equals("") || client_Id.equals(""))
                    Toast.makeText(MfiClientReg.this, getResources().getString(R.string.fill_all), Toast.LENGTH_SHORT).show();
                else {
                    SharedPreferences preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("login_Mfi_client_reg", true);
                    editor.putString("clicked", "User");
                    editor.apply();

                    registerMFIClient(specify_mfi,client_Id);
                }
            }
        });
    }


/*
    public void buildAlertMsgNoGPS() {
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
*/
    private void registerMFIClient(final String specify_mfi,final String client_Id) {
        // Tag used to cancel the request
        String cancel_req_tag = "register_mfi_client";

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
                        Intent intent = new Intent(MfiClientReg.this, ChoicesMFI.class);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(context, "Here", Toast.LENGTH_SHORT).show();
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
                params.put("name", "name");
                params.put("gender", "gender");
                params.put("age", "age");
                params.put("education", "education");
                params.put("occupation", "occupation");
                params.put("locality", "locality");
                params.put("specify_mfi", specify_mfi);
                params.put("how_know_the_app", "how_know_the_app");
                params.put("id_client", client_Id);
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
