package com.example.ravi.mfi;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

/**
 * Created by Somnath Yadav on 02-08-2018.
 */

public class ChoicesMFI extends AppCompatActivity{

    private Button compulsory,desirable,supple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLang();
        setContentView(R.layout.choicesmfi);

        compulsory=findViewById(R.id.compulsory);
        desirable=findViewById(R.id.desirable);
        supple=findViewById(R.id.supplementary);

        compulsory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoicesMFI.this,ModuleCompulsory.class));


            }
        });

        desirable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoicesMFI.this,ModuleDesirable.class));
            }
        });


        supple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoicesMFI.this,ModuleSupplementary.class));
            }
        });


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
