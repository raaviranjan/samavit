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

//space added
public class ModuleCompulsory extends AppCompatActivity {


    RelativeLayout comp_rlModule1, comp_rlModule2, comp_rlModule3, comp_rlModule4;
    TextView comp_tvViewsModule1, comp_tvViewsModule2, comp_tvViewsModule3, comp_tvViewsModule4;
    TextView comp_tvPer1, comp_tvPer2, comp_tvPer3, comp_tvPer4;
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
        setContentView(R.layout.module_compulsory);

        viewed = new int[7][60];

        preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        lang = preferences.getString("language", "en");

        llGrpNameAndId = findViewById(R.id.comp_llGrpNameAndId);
        tv_gName_on_Module = findViewById(R.id.comp_tv_gName_on_Module);
        tv_gId_on_Module = findViewById(R.id.comp_tv_gId_on_Module);

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

        comp_tvViewsModule1 = findViewById(R.id.comp_tvViewsModule1);
        comp_tvViewsModule2 = findViewById(R.id.comp_tvViewsModule2);
        comp_tvViewsModule3 = findViewById(R.id.comp_tvViewsModule3);
        comp_tvViewsModule4 = findViewById(R.id.comp_tvViewsModule4);

        comp_tvPer1 = findViewById(R.id.comp_tvPer1);
        comp_tvPer2 = findViewById(R.id.comp_tvPer2);
        comp_tvPer3 = findViewById(R.id.comp_tvPer3);
        comp_tvPer4 = findViewById(R.id.comp_tvPer4);

        comp_rlModule1 = findViewById(R.id.comp_rlModule1);
        comp_rlModule2 = findViewById(R.id.comp_rlModule2);
        comp_rlModule3 = findViewById(R.id.comp_rlModule3);
        comp_rlModule4 = findViewById(R.id.comp_rlModule4);

        //percentage calculation
        percentageCalculation(viewed);

        comp_rlModule1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openingSubmodule("Responsible Borrowing");

            }
        });
        comp_rlModule2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openingSubmodule("Savings");

            }
        });
        comp_rlModule3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openingSubmodule("Insurance");

            }
        });
        comp_rlModule4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openingSubmodule("Digital Transaction");

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
            Intent intent = new Intent(ModuleCompulsory.this, About.class);
            startActivity(intent);
        }
        if (id == R.id.attendance) {
            Intent intent = new Intent(ModuleCompulsory.this, Attendance.class);
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
                module1Percentage(0, 3, 0, 3);
                module2Percentage(3, 6, 0, 3);
                module3Percentage(6, 9, 0, 3);
                module4Percentage(9, 14, 0, 5);
                break;
            //percentage calculation for hindi language
            case "hi":
                module1Percentage(0, 3, 1, 3);
                module2Percentage(3, 5, 1, 2);
                module3Percentage(5, 8, 1, 3);
                module4Percentage(8, 15, 1, 7);
                break;
            //percentage calculation for gujarati language
            case "gu":
                module1Percentage(0, 3, 2, 3);
                module2Percentage(3, 5, 2, 2);
                module3Percentage(5, 8, 2, 3);
                module4Percentage(8, 16, 2, 8);
                break;
            //percentage calculation for marathi language
            case "mr":
                module1Percentage(0, 3, 3, 3);
                module2Percentage(3, 5, 3, 2);
                module3Percentage(5, 8, 3, 3);
                module4Percentage(8, 15, 3, 7);
                break;
            //percentage calculation for tamil language
            case "ta":
                module1Percentage(0, 3, 4, 3);
                module2Percentage(3, 5, 4, 2);
                module3Percentage(5, 8, 4, 3);
                module4Percentage(8, 15, 4, 7);
                break;
            //percentage calculation for kannada language
            case "kn":
                module1Percentage(0, 3, 5, 3);
                module2Percentage(3, 5, 5, 2);
                module3Percentage(5, 8, 5, 3);
                module4Percentage(8, 15, 5, 7);
                break;
            //percentage calculation for oriya language
            case "or":
                module1Percentage(0, 3, 6, 3);
                module2Percentage(3, 5, 6, 2);
                module3Percentage(5, 8, 6, 3);
                module4Percentage(8, 15, 6, 7);
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
        comp_tvViewsModule1.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        comp_tvPer1.setText(pert1 + "%");
        if (pert1 >= 50)
            comp_tvPer1.setTextColor(getResources().getColor(R.color.green));
        else
            comp_tvPer1.setTextColor(getResources().getColor(R.color.red));
    }
    public void module2Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        comp_tvViewsModule2.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        comp_tvPer2.setText(pert1 + "%");
        if (pert1 >= 50)
            comp_tvPer2.setTextColor(getResources().getColor(R.color.green));
        else
            comp_tvPer2.setTextColor(getResources().getColor(R.color.red));
    }
    public void module3Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        comp_tvViewsModule3.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        comp_tvPer3.setText(pert1 + "%");
        if (pert1 >= 50)
            comp_tvPer3.setTextColor(getResources().getColor(R.color.green));
        else
            comp_tvPer3.setTextColor(getResources().getColor(R.color.red));
    }
    public void module4Percentage(int loopStart, int loopEnd,int whichLang,int noOfVideos){
        cnt1 = 0;
        for (i = loopStart; i < loopEnd; i++) {
            if (viewed[whichLang][i] == 1)
                cnt1++;
        }
        pert1 = (cnt1 * 100) / noOfVideos;
        comp_tvViewsModule4.setText(getResources().getString(R.string.viewed) + "  " + cnt1
                + "    " + getResources().getString(R.string.total) + "  " + String.valueOf(noOfVideos));
        comp_tvPer4.setText(pert1 + "%");
        if (pert1 >= 50)
            comp_tvPer4.setTextColor(getResources().getColor(R.color.green));
        else
            comp_tvPer4.setTextColor(getResources().getColor(R.color.red));
    }

    public void openingSubmodule(String moduleName){

        Intent intent1 = new Intent(ModuleCompulsory.this, SubModule2.class);
        intent1.putExtra("module_name", moduleName);
        intent1.putExtra("choice","compulsory");
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
