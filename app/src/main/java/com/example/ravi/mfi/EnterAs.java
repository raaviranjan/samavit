package com.example.ravi.mfi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by Ravi on 30-Jun-18.
 */

public class EnterAs extends AppCompatActivity {

    private static final String URL_FOR_REGISTRATION = "http://samavit.in/fe_app_lighter_version/get_group_lv.php";

    Button bEnterAsUser,bManageGrps,bExistingGrps,bNewGrp;
    TextView textView;
    LinearLayout llExistingAndNewGrp;
    String[] GroupName;
    String[] GroupId;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLang();
        setContentView(R.layout.enter_as);

        preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);

        getGroupData();

        bEnterAsUser = findViewById(R.id.bEnterAsUser);
        bManageGrps = findViewById(R.id.bManageGrp);
        bExistingGrps = findViewById(R.id.bExistingGrp);
        bNewGrp = findViewById(R.id.bNewGrp);
        textView=findViewById(R.id.or);

        llExistingAndNewGrp = findViewById(R.id.llExistingAndNewGrp);

        final SharedPreferences.Editor editor = preferences.edit();

        bEnterAsUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("clicked","User");
                editor.putBoolean("flag",false);//this is for after quiz
                editor.apply();
                Intent intent = new Intent(EnterAs.this, ChoicesMFI.class);
                startActivity(intent);
                finish();
            }
        });
        bManageGrps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llExistingAndNewGrp.setVisibility(View.VISIBLE);
                bEnterAsUser.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                bManageGrps.setVisibility(View.GONE);
            }
        });
        bExistingGrps.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                getGroupData();
                final AlertDialog.Builder builder = new AlertDialog.Builder(EnterAs.this);
                builder.setTitle(R.string.select_grp);
                builder.setItems(GroupName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection

                        editor.putString("clicked","Group");
                        editor.putString("group_name",GroupName[item]);
                        editor.putString("group_id",GroupId[item]);
                        editor.apply();
                        Intent intent = new Intent(EnterAs.this,ChoicesMFI.class);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        bNewGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnterAs.this, NewGroupReg.class);
                startActivity(intent);
                finish();
            }
        });

    }


    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void getGroupData() {

        JsonArrayRequest req = new JsonArrayRequest(URL_FOR_REGISTRATION,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parsing json array response
                            // loop through each json object
                            GroupName=new String[response.length()];
                            GroupId=new String[response.length()];
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response.get(i);

                                String g_name = person.getString("g_name");
                                String g_id = person.getString("g_id");

                                GroupName[i]=g_name;
                                GroupId[i]=g_id;


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"errorresponse", Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(req,"TAG");
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
