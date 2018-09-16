package com.example.ravi.mfi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

/**
 * Created by Ravi on 29-Jun-18.
 */

public class IdentifyYourself extends AppCompatActivity {

    Button bMfiStaff,bMfiClient;
    Boolean login_Mfi_staff_reg,login_Mfi_client_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLang();
        setContentView(R.layout.identify_yourself);

        bMfiStaff = findViewById(R.id.bMFIStaff);
        bMfiClient = findViewById(R.id.bMFIClient);

        SharedPreferences preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        login_Mfi_staff_reg = preferences.getBoolean("login_Mfi_staff_reg", false);
        login_Mfi_client_reg = preferences.getBoolean("login_Mfi_client_reg", false);

        bMfiStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!login_Mfi_staff_reg) {
                    Intent intent = new Intent(IdentifyYourself.this, MFIStaffReg.class);
                    startActivity(intent);
                    finish();
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
                    Intent intent = new Intent(IdentifyYourself.this, MfiClientReg.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(IdentifyYourself.this, ChoicesMFI.class);
                    startActivity(intent);
                    finish();
                }
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
