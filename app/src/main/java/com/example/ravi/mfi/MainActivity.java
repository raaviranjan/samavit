package com.example.ravi.mfi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button tvHin, tvEng, tvTamil, tvKannada, tvOriya, tvGujarati, tvMarathi;
    Boolean login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        login = preferences.getBoolean("login", false);

        //hiding the action bar
        this.getSupportActionBar().hide();

        tvEng = findViewById(R.id.tvEng);
        tvHin = findViewById(R.id.tvHin);
        tvTamil = findViewById(R.id.tvTamil);
        tvKannada = findViewById(R.id.tvKannada);
        tvOriya = findViewById(R.id.tvOriya);
        tvGujarati = findViewById(R.id.tvGujarati);
        tvMarathi = findViewById(R.id.tvMarathi);

        tvEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLanguage("en");
                Intent intent = new Intent(MainActivity.this, IdentifyYourself.class);
                startActivity(intent);

            }
        });
        tvHin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLanguage("hi");
                Intent intent = new Intent(MainActivity.this, IdentifyYourself.class);
                startActivity(intent);
            }
        });
        tvGujarati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLanguage("gu");
                Intent intent = new Intent(MainActivity.this, IdentifyYourself.class);
                startActivity(intent);
            }
        });
        tvMarathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLanguage("mr");
                Intent intent = new Intent(MainActivity.this, IdentifyYourself.class);
                startActivity(intent);
            }
        });
        tvTamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLanguage("ta");
                Intent intent = new Intent(MainActivity.this, IdentifyYourself.class);
                startActivity(intent);
            }
        });
        tvKannada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLanguage("kn");
                Intent intent = new Intent(MainActivity.this, IdentifyYourself.class);
                startActivity(intent);
            }
        });
        tvOriya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLanguage("or");
                Intent intent = new Intent(MainActivity.this, IdentifyYourself.class);
                startActivity(intent);
            }
        });
    }

    public void saveLanguage(String lang) {
        SharedPreferences preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", lang);
        editor.apply();

        recreate();
    }

    public void loadLanguage() {
        Locale locale = new Locale(getLangCode());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    public String getLangCode() {
        SharedPreferences preferences = getSharedPreferences("SHAR_PREF_NAME", MODE_PRIVATE);
        String langCode = preferences.getString("language", "en");
        // save english 'en' as the default language
        return langCode;
    }
}
