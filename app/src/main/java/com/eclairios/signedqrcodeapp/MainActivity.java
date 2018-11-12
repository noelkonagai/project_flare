package com.eclairios.signedqrcodeapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ankit.gpslibrary.MyTracker;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import fr.quentinklein.slt.LocationTracker;
public class MainActivity extends AppCompatActivity {
    TextView tv_title;
    EditText ed_username, ed_password;
    Button btn_login;
    TextView tv_imei_code;
    private TelephonyManager mTelephonyManager;
    String imei;
    ProgressDialog progressDialog;
    String deviceid;
    int Camera_request_code=201;
    int external_storage_request_code=202;
    int Coarse_request_code=203;
    int fine_request_code=204;
    int read_request_code=205;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        InsertDataToDataBase();//Inserting Local Data to Server
        initialization();//here we are contecting xml Views object with the java class
        RunTimePermissions();//Taking Runtime Permission


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//login button click listener

              //getting data from user_name Textview
                String username = ed_username.getText().toString();
                //getting data from password Textview
                String password = ed_password.getText().toString();

                if (username.trim().isEmpty()) {//when user_name textView don't have any text
                    Toast.makeText(MainActivity.this, "Please Enter The Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {//when password textView don't have any text
                    Toast.makeText(MainActivity.this, "Please Enter the Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //when internet is connected
                if (Utill.verifyConection(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this, AddContact.class);
                    //when there is no internet connection
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
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
        tv_title = (TextView) findViewById(R.id.tv_title);
        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_password = (EditText) findViewById(R.id.ed_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        AssetManager am = getApplicationContext().getAssets();
        progressDialog = new ProgressDialog(MainActivity.this);
        Typeface custom_font = Typeface.createFromAsset(am, "fonts/PlayfairDisplay-BlackItalic.otf");
        tv_title.setTypeface(custom_font);
    }

    private void InsertDataToDataBase() {
        ArrayList<String> sign=new ArrayList<String>();
        ArrayList<String> qrvlaue=new ArrayList<String>();
        ArrayList<String> loc_lat=new ArrayList<String>();
        ArrayList<String> loc_lng=new ArrayList<String>();
        ArrayList<String> date_string=new ArrayList<String>();
        ArrayList<String> time_string=new ArrayList<String>();
        ArrayList<String> tic_img=new ArrayList<String>();
        ArrayList<String> hash=new ArrayList<String>();


        ArrayList<Integer> qr_ids=new ArrayList<>();
        ArrayList<Integer> tic_ids=new ArrayList<>();
        ArrayList<Integer> sign_ids=new ArrayList<>();
        DataBaseHelper helper=new DataBaseHelper(this);

      //  getting user id from shared preference
        String user_id_pred=Utill.getDataSP("user_idd",MainActivity.this);
      //getting All ids from QRCODE Table
        qr_ids=helper.showAllQr();
        //getting All ids from TICKET Table
        tic_ids=helper.showAllTicket();
        //getting All ids from SIGNATURE Table
        sign_ids=helper.showAllSignature();


        //adding data to Arraylist
        for (int i=0;i<qr_ids.size();i++){
            ArrayList<String>
                    qr=helper.showsingleQr(qr_ids.get(i)+"");
            qrvlaue.add(qr.get(1));
            loc_lat.add(qr.get(2));
            loc_lng.add(qr.get(3));
        }

        //adding data to Arraylist
        for (int i=0;i<tic_ids.size();i++){
            ArrayList<String>
                   tic_col=helper.showsingleTicket(tic_ids.get(i)+"");
            date_string.add(tic_col.get(1));
            time_string.add(tic_col.get(2));
            tic_img.add(tic_col.get(3));
        }
        //adding data to Arraylist
        for (int i=0;i<sign_ids.size();i++){
            ArrayList<String>
            sign_col=helper.showsingleSignature(sign_ids.get(i)+"");
            sign.add(sign_col.get(1));
            hash.add("");
        }

        for (int i=0;i<qrvlaue.size();i++) {
            //if internet connection is avalible
            if (Utill.verifyConection(MainActivity.this)) {
                //sending data to server
                new BackgroundTask(MainActivity.this).execute(qrvlaue.get(i),loc_lat.get(i),loc_lng.get(i),date_string.get(i),time_string.get(i),tic_img.get(i),sign.get(i),user_id_pred);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //in resume it check all  runtime permissions
        //when API level is greater then 23
        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {

                requestPermissions(new String[]{Manifest.permission.CAMERA}, Camera_request_code);

            }
            else if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    ){

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, external_storage_request_code);

            }else if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ){

                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Coarse_request_code);


            }else if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, fine_request_code);

            }else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, read_request_code);

            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //at start it check all runtime permissions
        //when API level is greater then 23

        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA}, Camera_request_code);

            } else if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    ) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, external_storage_request_code);

            } else if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Coarse_request_code);


            } else if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, fine_request_code);

            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, read_request_code);

            }
        }
    }

    public class Authenticate extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading Data....");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String url = params[0];
            Log.e("URL", url + "");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            JSONObject response = null;

            try {
                URL url1 = new URL(url);
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url1.openConnection();
                urlConnection.setRequestMethod("POST");
               // urlConnection.connect();
                urlConnection.setDoOutput(true);
                OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());

                writer.flush();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                Log.d("myResponse", buffer.toString());
                response = new JSONObject(buffer.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

//        @Override
//        protected void onPostExecute(JSONObject json) {
//            super.onPostExecute(json);
//            Log.i("Log", json + "");
//            progressDialog.dismiss();
//            try {
//                //successful response from server
//                //user is login successful
//                if (json.getString("status").equals("success")) {
//
//                    String lng_idle_time=json.getString("idle_time");
//                    Utill.addDataSP("idletime",""+lng_idle_time,MainActivity.this);
//                    String user_id=json.getString("user_id");
//                    Utill.addDataSP("user_idd",""+user_id,MainActivity.this);
//
//                    //start intent to QrCode Scan Activity
//                    Intent intent = new Intent(MainActivity.this, InicioTurno.class);
//                   //start Activity
//                    startActivity(intent);
//                }
//                //successful response from server
//                //user doesnot exist in dataBase
//                else {
//                    Toast.makeText(MainActivity.this, "Sorry Wrong Username Or Password", Toast.LENGTH_SHORT).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }catch (NullPointerException e){
//                Log.d("qaz","Nulllllll");
//            }
//
//        }

    }





}
