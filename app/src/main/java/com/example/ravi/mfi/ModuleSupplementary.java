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

public class ModuleSupplementary  extends AppCompatActivity{

    RelativeLayout supp_rlModule1, supp_rlModule2, supp_rlModule3, supp_rlModule4, supp_rlModule5, supp_rlModule6, supp_rlModule7;
    TextView supp_tvViewsModule1, supp_tvViewsModule2, supp_tvViewsModule3, supp_tvViewsModule4, supp_tvViewsModule5, supp_tvViewsModule6, supp_tvViewsModule7;
    TextView supp_tvPer1, supp_tvPer2, supp_tvPer3, supp_tvPer4, supp_tvPer5, supp_tvPer6, supp_tvPer7;
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
        setContentView(R.layout.module_supp);

        viewed = new int[7][60];

        preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        lang = preferences.getString("language", "en");

        llGrpNameAndId = findViewById(R.id.supp_llGrpNameAndId);
        tv_gName_on_Module = findViewById(R.id.supp_tv_gName_on_Module);
        tv_gId_on_Module = findViewById(R.id.supp_tv_gId_on_Module);

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

        supp_tvViewsModule1 = findViewById(R.id.supp_tvViewsModule1);
        supp_tvViewsModule2 = findViewById(R.id.supp_tvViewsModule2);
        supp_tvViewsModule3 = findViewById(R.id.supp_tvViewsModule3);
        supp_tvViewsModule4 = findViewById(R.id.supp_tvViewsModule4);
        supp_tvViewsModule5 = findViewById(R.id.supp_tvViewsModule5);
        supp_tvViewsModule6 = findViewById(R.id.supp_tvViewsModule6);
        supp_tvViewsModule7 = findViewById(R.id.supp_tvViewsModule7);

        supp_tvPer1 = findViewById(R.id.supp_tvPer1);
        supp_tvPer2 = findViewById(R.id.supp_tvPer2);
        supp_tvPer3 = findViewById(R.id.supp_tvPer3);
        supp_tvPer4 = findViewById(R.id.supp_tvPer4);
        supp_tvPer5 = findViewById(R.id.supp_tvPer5);
        supp_tvPer6 = findViewById(R.id.supp_tvPer6);
        supp_tvPer7 = findViewById(R.id.supp_tvPer7);

        supp_rlModule1 = findViewById(R.id.supp_rlModule1);
        supp_rlModule2 = findViewById(R.id.supp_rlModule2);
        supp_rlModule3 = findViewById(R.id.supp_rlModule3);
        supp_rlModule4 = findViewById(R.id.supp_rlModule4);
        supp_rlModule5 = findViewById(R.id.supp_rlModule5);
        supp_rlModule6 = findViewById(R.id.supp_rlModule6);
        supp_rlModule7 = findViewById(R.id.supp_rlModule7);

        //percentage calculation
        percentageCalculation(viewed);

        supp_rlModule1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openingSubmodule("Loan");

            }
        });
        supp_rlModule2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openingSubmodule("Savings");

            }
        });
        supp_rlModule3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openingSubmodule("Insurance");
            }
        });
        supp_rlModule4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openingSubmodule("Digital Transactions");

            }
        });
        supp_rlModule5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openingSubmodule("Financial Planning and Budgeting"); }
        });
        supp_rlModule6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openingSubmodule("Pension");

            }
        });
        supp_rlModule7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openingSubmodule("Others");

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
            Intent intent = new Intent(ModuleSupplementary.this, About.class);
            startActivity(intent);
        }
        if (id == R.id.attendance) {
            Intent intent = new Intent(ModuleSupplementary.this, Attendance.class);
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
                module1Percentage(22, 24, 0, 2);
                module2Percentage(24, 28, 0, 4);
                module3Percentage(28, 30, 0, 2);
                module4Percentage(30, 33, 0, 3);
                module5Percentage(33, 35, 0, 2);
                module6Percentage(35, 37, 0, 2);
                module7Percentage(37, 41, 0, 4);
                break;
            //percentage calculation for hindi language
            case "hi":
                module1Percentage(22, 26, 1, 4);
                module2Percentage(26, 29, 1, 3);
                module3Percentage(29, 34, 1, 5);
                module4Percentage(34, 40, 1, 6);
                module5Percentage(40, 42, 1, 2);
                module6Percentage(42, 46, 1, 4);
                module7Percentage(46, 56, 1, 10);
                break;
            //percentage calculation for gujarati language
            case "gu":
                module1Percentage(24, 26, 2, 2);
                module2Percentage(26, 27, 2, 1);
                module4Percentage(27, 31, 2, 4);
                module6Percentage(31, 33, 2, 2);
                module7Percentage(33, 36, 2, 3);
                break;
            //percentage calculation for marathi language
            case "mr":
                module1Percentage(23, 25, 3, 2);
                module2Percentage(25, 28, 3, 3);
                module3Percentage(28, 29, 3, 1);
                module4Percentage(29, 37, 3, 8);
                module6Percentage(37, 38, 3, 1);
                module7Percentage(38, 40, 3, 2);
                break;
            //percentage calculation for tamil language
            case "ta":
                module1Percentage(23, 27, 4, 4);
                module2Percentage(27, 30, 4, 3);
                module4Percentage(30, 36, 4, 6);
                module5Percentage(36, 37, 4, 1);
                module6Percentage(37, 39, 4, 2);
                module7Percentage(39, 42, 4, 3);
                break;
            //percentage calculation for kannada language
            case "kn":
                module1Percentage(23, 25, 5, 2);
                module2Percentage(25, 27, 5, 2);
                module4Percentage(27, 35, 5, 8);
                module7Percentage(35, 39, 5, 4);
                break;
            //percentage calculation for oriya language
            case "or":
                module1Percentage(23, 24, 6, 1);
                module4Percentage(24, 31, 6, 7);
                module6Percentage(31, 32, 6, 1);
                module7Percentage(32, 33, 6, 1);
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
        supp_tvViewsModule1.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        supp_tvPer1.setText(pert1 + "%");
        if (pert1 >= 50)
            supp_tvPer1.setTextColor(getResources().getColor(R.color.green));
        else
            supp_tvPer1.setTextColor(getResources().getColor(R.color.red));
    }
    public void module2Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        supp_tvViewsModule2.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        supp_tvPer2.setText(pert1 + "%");
        if (pert1 >= 50)
            supp_tvPer2.setTextColor(getResources().getColor(R.color.green));
        else
            supp_tvPer2.setTextColor(getResources().getColor(R.color.red));
    }
    public void module3Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        supp_tvViewsModule3.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        supp_tvPer3.setText(pert1 + "%");
        if (pert1 >= 50)
            supp_tvPer3.setTextColor(getResources().getColor(R.color.green));
        else
            supp_tvPer3.setTextColor(getResources().getColor(R.color.red));
    }
    public void module4Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        supp_tvViewsModule4.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        supp_tvPer4.setText(pert1 + "%");
        if (pert1 >= 50)
            supp_tvPer4.setTextColor(getResources().getColor(R.color.green));
        else
            supp_tvPer4.setTextColor(getResources().getColor(R.color.red));
    }
    public void module5Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        supp_tvViewsModule5.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        supp_tvPer5.setText(pert1 + "%");
        if (pert1 >= 50)
            supp_tvPer5.setTextColor(getResources().getColor(R.color.green));
        else
            supp_tvPer5.setTextColor(getResources().getColor(R.color.red));
    }
    public void module6Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        supp_tvViewsModule6.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        supp_tvPer6.setText(pert1 + "%");
        if (pert1 >= 50)
            supp_tvPer6.setTextColor(getResources().getColor(R.color.green));
        else
            supp_tvPer6.setTextColor(getResources().getColor(R.color.red));
    }
    public void module7Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        supp_tvViewsModule7.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        supp_tvPer7.setText(pert1 + "%");
        if (pert1 >= 50)
            supp_tvPer7.setTextColor(getResources().getColor(R.color.green));
        else
            supp_tvPer7.setTextColor(getResources().getColor(R.color.red));
    }

    public void openingSubmodule(String moduleName){

        Intent intent1 = new Intent(ModuleSupplementary.this, SubModule2.class);
        intent1.putExtra("module_name", moduleName);
        intent1.putExtra("choice","suppl");
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
