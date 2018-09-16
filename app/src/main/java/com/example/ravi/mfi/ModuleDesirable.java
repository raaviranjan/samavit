package com.example.ravi.mfi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Somnath Yadav on 02-08-2018.
 */

public class ModuleDesirable extends AppCompatActivity {

    RelativeLayout des_rlModule1, des_rlModule3, des_rlModule4;
    TextView desi_tvViewsModule1, desi_tvViewsModule3, desi_tvViewsModule4;
    TextView desi_tvPer1, desi_tvPer3, desi_tvPer4;
    String gName,gId,clicked,groupNameAndID,lang;
    SharedPreferences preferences;
    LinearLayout llGrpNameAndId;
    TextView tv_gName_on_Module,tv_gId_on_Module;
    int viewed[][];
    int i, cnt1 = 0, pert1 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLang();
        setContentView(R.layout.module_desirable);

        viewed = new int[7][60];

        preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        lang = preferences.getString("language", "en");

        llGrpNameAndId = findViewById(R.id.desi_llGrpNameAndId);
        tv_gName_on_Module = findViewById(R.id.desi_tv_gName_on_Module);
        tv_gId_on_Module = findViewById(R.id.desi_tv_gId_on_Module);

        clicked = preferences.getString("clicked",null);

        if(clicked.equals("Group")){
            gName = preferences.getString("group_name",null);
            gId = preferences.getString("group_id",null);
            groupNameAndID = gName + gId;
            llGrpNameAndId.setVisibility(View.VISIBLE);
            tv_gName_on_Module.setText(getResources().getString(R.string.group_name_module) + gName);
            tv_gId_on_Module.setText(getResources().getString(R.string.group_id_module) + gId);
        }

        if(clicked.equals("User"))
            viewed = getFromPref("views");
        else if(clicked.equals("Group"))
            viewed = getFromPref(groupNameAndID);

        //to add back button on toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        desi_tvViewsModule1 = findViewById(R.id.desi_tvViewsModule1);
        desi_tvViewsModule3 = findViewById(R.id.desi_tvViewsModule3);
        desi_tvViewsModule4 = findViewById(R.id.desi_tvViewsModule4);

        desi_tvPer1 = findViewById(R.id.desi_tvPer1);
        desi_tvPer3 = findViewById(R.id.desi_tvPer3);
        desi_tvPer4 = findViewById(R.id.desi_tvPer4);

        des_rlModule1 = findViewById(R.id.des_rlModule1);
        des_rlModule3 = findViewById(R.id.des_rlModule3);
        des_rlModule4 = findViewById(R.id.des_rlModule4);

        //percentage calculation
        percentageCalculation(viewed);

        des_rlModule1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openingSubmodule("Financial Planning and Budgeting"); }
        });
        des_rlModule3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openingSubmodule("Consumer Beware");

            }
        });
        des_rlModule4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openingSubmodule("Currency Literacy");

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //on back pressed
        if (id == android.R.id.home)
            finish();
        if (id == R.id.about) {
            Intent intent = new Intent(ModuleDesirable.this, About.class);
            startActivity(intent);
        }
        if (id == R.id.attendance) {
            Intent intent = new Intent(ModuleDesirable.this, Attendance.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (clicked.equals("User")) {
            menu.findItem(R.id.attendance).setEnabled(false).setVisible(false);
        }
        return true;
    }

    public int[][] getFromPref(String name) {
        int ret[][];
        int count = preferences.getInt("count_" + name, 60);
        ret = new int[7][count];
        for(int kk = 0;kk<7;kk++){
            for (int k = 0; k < count; k++) {
                ret[kk][k] = preferences.getInt("IntValue_" + name +kk+ k, 10);
            }
        }

        return ret;
    }

    public void percentageCalculation(int[][] viewed){
        switch (lang) {
            //percentage calculation for english
            case "en":
                module1Percentage(14, 17, 0, 3);
                //module2Percentage(17, 18, 0, 1);
                module3Percentage(18, 20, 0, 2);
                module4Percentage(20, 22, 0, 2);
                break;
            //percentage calculation for hindi language
            case "hi":
                module1Percentage(15, 17, 1, 2);
                //module2Percentage(17, 18, 1, 1);
                module3Percentage(18, 20, 1, 2);
                module4Percentage(20, 22, 1, 2);
                break;
            //percentage calculation for gujarati language
            case "gu":
                module1Percentage(16, 19, 2, 3);
                //module2Percentage(19, 20, 2, 1);
                module3Percentage(20, 22, 2, 2);
                module4Percentage(22, 24, 2, 2);
                break;
            //percentage calculation for marathi language
            case "mr":
                module1Percentage(15, 18, 3, 3);
                //module2Percentage(18, 19, 3, 1);
                module3Percentage(19, 21, 3, 2);
                module4Percentage(21, 23, 3, 2);
                break;
            //percentage calculation for tamil language
            case "ta":
                module1Percentage(15, 18, 4, 3);
                //module2Percentage(18, 19, 4, 1);
                module3Percentage(19, 21, 4, 2);
                module4Percentage(21, 23, 4, 2);
                break;
            //percentage calculation for kannada language
            case "kn":
                module1Percentage(15, 18, 5, 3);
                //module2Percentage(18, 19, 5, 1);
                module3Percentage(19, 21, 5, 2);
                module4Percentage(21, 23, 5, 2);
                break;
            //percentage calculation for oriya language
            case "or":
                module1Percentage(15, 18, 6, 3);
                //module2Percentage(18, 19, 6, 1);
                module3Percentage(19, 21, 6, 2);
                module4Percentage(21, 23, 6, 2);
                break;
        }

    }

    public void module1Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        desi_tvViewsModule1.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        desi_tvPer1.setText(pert1 + "%");
        if (pert1 >= 50)
            desi_tvPer1.setTextColor(getResources().getColor(R.color.green));
        else
            desi_tvPer1.setTextColor(getResources().getColor(R.color.red));
    }
    public void module3Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        desi_tvViewsModule3.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        desi_tvPer3.setText(pert1 + "%");
        if (pert1 >= 50)
            desi_tvPer3.setTextColor(getResources().getColor(R.color.green));
        else
            desi_tvPer3.setTextColor(getResources().getColor(R.color.red));
    }
    public void module4Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        desi_tvViewsModule4.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        desi_tvPer4.setText(pert1 + "%");
        if (pert1 >= 50)
            desi_tvPer4.setTextColor(getResources().getColor(R.color.green));
        else
            desi_tvPer4.setTextColor(getResources().getColor(R.color.red));
    }

    public void openingSubmodule(String moduleName){

        Intent intent1 = new Intent(ModuleDesirable.this, SubModule2.class);
        intent1.putExtra("module_name", moduleName);
        intent1.putExtra("choice","desirable");
        startActivity(intent1);
        finish();
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
