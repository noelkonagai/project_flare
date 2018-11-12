package com.eclairios.signedqrcodeapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {
    Button contacts, calibration, test, log_out;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initialization();//here we are contecting xml Views object with the java class
        RunTimePermissions();//Taking Runtime Permission

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//login button click listener


                //when internet is connected
                if (Utill.verifyConection(MainMenuActivity.this)) {
                    Intent intent = new Intent(MainMenuActivity.this, EditContacts.class);
                    //when there is no internet connection
                    startActivity(intent);
                } else {
                    Toast.makeText(MainMenuActivity.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        calibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//login button click listener


                //when internet is connected
                if (Utill.verifyConection(MainMenuActivity.this)) {
                    Intent intent = new Intent(MainMenuActivity.this, Calibration.class);
                    //when there is no internet connection
                    startActivity(intent);
                } else {
                    Toast.makeText(MainMenuActivity.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//login button click listener


                //when internet is connected
                if (Utill.verifyConection(MainMenuActivity.this)) {
                    Intent intent = new Intent(MainMenuActivity.this, AddContact.class);
                    //when there is no internet connection
                    startActivity(intent);
                } else {
                    Toast.makeText(MainMenuActivity.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//login button click listener
                Intent intent = new Intent(MainMenuActivity.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                //when internet is connected
                //if (Utill.verifyConection(MainMenuActivity.this)) {
                //    Intent intent = new Intent(MainMenuActivity.this, InicioTurno.class);
                    //when there is no internet connection
                //    startActivity(intent);
                //} else {
                //    Toast.makeText(MainMenuActivity.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                //}
            }
        });
    }

    private void RunTimePermissions() {
        //if device API level is greater then 23
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this,  Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE
                    },

                    100);
        }
    }

    private void initialization() {
        contacts = (Button) findViewById(R.id.contacts);
        calibration = (Button) findViewById(R.id.calibration);
        test = (Button) findViewById(R.id.test);
        log_out = (Button) findViewById(R.id.log_out);
        AssetManager am = getApplicationContext().getAssets();
        Typeface custom_font = Typeface.createFromAsset(am, "fonts/PlayfairDisplay-BlackItalic.otf");

    }}

