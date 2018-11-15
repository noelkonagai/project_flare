package com.eclairios.signedqrcodeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arduinosensors.R;

public class VerificationScreen extends AppCompatActivity {

    EditText ed_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialization();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ed_email.getText().toString();

                if (email.trim().isEmpty()){
                    Toast.makeText(VerificationScreen.this, "This is an empty email", Toast.LENGTH_SHORT).show();
                    return;
                }

                Snackbar.make(view, "Email sent", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                Intent intent = new Intent(VerificationScreen.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initialization() {
        ed_email = (EditText) findViewById(R.id.ed_email_template);
    }

}
