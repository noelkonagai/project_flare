package com.eclairios.signedqrcodeapp;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {

    EditText ed_name, ed_phone;
    Button btn_ok, btn_add;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        initialization();

        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString();
                String phone = ed_phone.getText().toString();

                if (name.trim().isEmpty()){
                    Toast.makeText(AddContact.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.trim().isEmpty()){
                    Toast.makeText(AddContact.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(AddContact.this, AddContact2.class);
                startActivity(intent);
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString();
                String phone = ed_phone.getText().toString();

                if (name.trim().isEmpty()){
                    Toast.makeText(AddContact.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.trim().isEmpty()){
                    Toast.makeText(AddContact.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(AddContact.this, VerificationScreen.class);
                startActivity(intent);
            }
        });
    }

    private void initialization() {
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_add = (Button) findViewById(R.id.btn_add);
        ed_name = (EditText) findViewById(R.id.name);
        ed_phone = (EditText) findViewById(R.id.phone);
    }
}
