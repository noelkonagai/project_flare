package com.eclairios.signedqrcodeapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arduinosensors.R;

public class AddContact2 extends AppCompatActivity {
    TextView tv_subtitle_2;
    EditText ed_name_2, ed_phonenumber;
    Button btn_ok;
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact2);

        initialization();//here we are contecting xml Views object with the java class

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//ok button click listener

                //getting data from user_name Textview
                String username = ed_name_2.getText().toString();
                //getting data from password Textview
                String password = ed_phonenumber.getText().toString();

                if (username.trim().isEmpty()) {//when user_name textView don't have any text
                    Toast.makeText(AddContact2.this, "Please Enter The Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {//when password textView don't have any text
                    Toast.makeText(AddContact2.this, "Please Enter the Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //when internet is connected
                if (Utill.verifyConection(AddContact2.this)) {
                    Intent intent = new Intent(AddContact2.this, VerificationScreen.class);
                    //when there is no internet connection
                    startActivity(intent);
                } else {
                    Toast.makeText(AddContact2.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialization() {
        tv_subtitle_2 = (TextView) findViewById(R.id.tv_subtitle_2);
        ed_name_2 = (EditText) findViewById(R.id.ed_name_2);
        ed_phonenumber = (EditText) findViewById(R.id.ed_phonenumber);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        AssetManager am = getApplicationContext().getAssets();
//        Typeface custom_font = Typeface.createFromAsset(am, "fonts/PlayfairDisplay-BlackItalic.otf");
//        tv_subtitle_2.setTypeface(custom_font);
    }
    }


