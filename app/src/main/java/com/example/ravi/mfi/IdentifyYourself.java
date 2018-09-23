package com.example.ravi.mfi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Locale;
import java.util.Map;

/**
 * Created by Ravi on 29-Jun-18.
 */

public class IdentifyYourself extends AppCompatActivity {

    Button bMfiStaff,bMfiClient;
    Boolean login_Mfi_staff_reg,login_Mfi_client_reg;
    private static final String TAG = "RegisterMFIClient";
    private static final String URL_FOR_REGISTRATION = "http://samavit.in/fe_app_lighter_version/mfi_client_reg_lv.php";
    ProgressDialog progressDialog;
    SharedPreferences preferences;

    Spinner spinnerMFI,spinnerCLient;
    Button client_doneBtn;
    EditText client_et_specify_mfi,client_id;
    LinearLayout client_llMFIName,staff_linear_layout,client_linear_layout;

    String  specify_mfi = "",client_Id="";
    private static final String TAG2 = "RegisterMFIStaff";
    private static final String URL_FOR_REGISTRATION2 = "http://samavit.in/fe_app_lighter_version/mfi_staff_reg_lv.php";
    Button bMfiStaffRegSubmit;
    EditText et_mfi_name_other,et_staff_id;
    Context context;
    LinearLayout llMFINameOther;
    String  mfi = "", staff_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLang();
        setContentView(R.layout.identify_yourself);


        bMfiStaff = findViewById(R.id.bMFIStaff);
        bMfiClient = findViewById(R.id.bMFIClient);
        staff_linear_layout=findViewById(R.id.staff_linear);
        client_linear_layout=findViewById(R.id.client_linear);

        SharedPreferences preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        login_Mfi_staff_reg = preferences.getBoolean("login_Mfi_staff_reg", false);
        login_Mfi_client_reg = preferences.getBoolean("login_Mfi_client_reg", false);

        bMfiStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!login_Mfi_staff_reg) {
                    client_linear_layout.setVisibility(View.GONE);
                    staff_linear_layout.setVisibility(View.VISIBLE);

                } else {
                    Intent intent = new Intent(IdentifyYourself.this, EnterAs.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        bMfiClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!login_Mfi_client_reg) {
                    client_linear_layout.setVisibility(View.VISIBLE);
                    staff_linear_layout.setVisibility(View.GONE);

                } else {
                    Intent intent = new Intent(IdentifyYourself.this, ChoicesMFI.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        context = this;
        preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        final SharedPreferences.Editor editor1 = preferences.edit();

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        client_et_specify_mfi = findViewById(R.id.client_et_specify_mfi);
        client_llMFIName = findViewById(R.id.client_llMFIName);
        client_id=findViewById(R.id.et_client_id);





        //dropdown for name of mfi
        spinnerCLient= findViewById(R.id.client_spinnerMFI);
        ArrayAdapter adapter0 = ArrayAdapter.createFromResource(this, R.array.mfi_name, android.R.layout.simple_spinner_item);
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCLient.setAdapter(adapter0);

        spinnerCLient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                EditText client_number=findViewById(R.id.et_client_number);
                String client_no=client_number.getText().toString();

                if (specify_mfi.equals("") || client_Id.equals(""))
                    Toast.makeText(IdentifyYourself.this, getResources().getString(R.string.fill_all), Toast.LENGTH_SHORT).show();
                else {
                    SharedPreferences preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("login_Mfi_client_reg", true);
                    editor.putString("clicked", "User");
                    editor.apply();

                    registerMFIClient(specify_mfi,client_Id,client_no);
                }
            }
        });

        //StaffReg

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
                EditText staff_no=findViewById(R.id.et_staff_number);
                String staff_number=staff_no.getText().toString();

                if(mfi.contains("Other"))
                    mfi = et_mfi_name_other.getText().toString();


                if (mfi.equals("") || staff_id.equals("")|| mfi.contains("Choose") )
                    Toast.makeText(IdentifyYourself.this, getResources().getString(R.string.fill_all), Toast.LENGTH_SHORT).show();
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
                    registerMFIStaff(mfi, staff_id,staff_number);
                }

            }
        });


    }

    private void registerMFIClient(final String specify_mfi, final String client_Id, final String clientNo) {
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
                        Intent intent = new Intent(IdentifyYourself.this, ChoicesMFI.class);
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
                params.put("client_phone_no", clientNo);
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




    //staffreg

    private void registerMFIStaff(final String mfi,final String staffID,final String no) {
        // Tag used to cancel the request
        String cancel_req_tag = "register_mfi_staff";

        progressDialog.setMessage(getResources().getString(R.string.wait));
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, URL_FOR_REGISTRATION2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        // Launch module activity
                        Intent intent = new Intent(IdentifyYourself.this, EnterAs.class);
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
                params.put("staff_no", no);
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
