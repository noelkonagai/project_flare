package com.eclairios.signedqrcodeapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class BackgroundTask extends AsyncTask<String, Void, JSONObject> {

    private Context ctx;

    //Calling Constructor
  BackgroundTask(Context ctx) {

      /////getting context from calling Activity
        this.ctx = ctx;

    }

    @Override
    protected JSONObject doInBackground(String... params) {//params will get the data from the Mainactivity bbackround tack.execute


        String qr_value = params[0];//getting first parameter (value: qr_value)
        String location_lat = params[1];//getting second parameter (value: latitude)
        String location_lng=params[2];//getting third parameter (value: longitude)
        String date = params[3];//getting fourth parameter (value: date)
        String time = params[4];//getting fifth parameter (value: time)
        String ticket_image = params[5];//getting sixth parameter (value: ticket image Base64)
        String signature_image = params[6];//getting seven parameter (value: signature image Base64)
        String user_id=params[7];//getting eight parameter (value: user id )

        BufferedReader reader = null;//by default buffer reader have no value
        JSONObject response = null;  //by default JSon Object have no value

        try {//the code below gives exception

            ///Getting url from class Constants
            URL url = new URL(Constants.urlInsert);
            //opening url connection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //enable sending data
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            //Buffer Writer for sending data to server
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
           //Encoding data to urlEncoder
            String data = URLEncoder.encode("qr_value", "UTF-8") + "=" + URLEncoder.encode(qr_value, "UTF-8") + "&" +
                    URLEncoder.encode("location_lat", "UTF-8") + "=" + URLEncoder.encode(location_lat, "UTF-8") + "&" +
                    URLEncoder.encode("location_lng", "UTF-8") + "=" + URLEncoder.encode(location_lng, "UTF-8") + "&" +
                    URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                    URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&" +
                    URLEncoder.encode("ticket_image", "UTF-8") + "=" + URLEncoder.encode(ticket_image, "UTF-8") + "&" +
                    URLEncoder.encode("signature_image", "UTF-8") + "=" + URLEncoder.encode(signature_image, "UTF-8")+"&"+
                    URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");

            ///putting data to bufferWriter
            bufferedWriter.write(data);
            //flushes the characters from a write buffer to the character or byte stream as an intended destination
            bufferedWriter.flush();
            //close buffer writer
            bufferedWriter.close();
            //enable getting data from server
            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuilder sb = new StringBuilder();
            //if there is no data to retrieve from server
            if (inputStream == null) {
                return null;
            }
            //data from server to buffered reader
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            //while there are more lines
            while ((line = reader.readLine()) != null) {
                //add line by line to String Builder
                sb.append(line).append("\n");
            }
            //converting string builder to JSON Object
            response = new JSONObject(sb.toString());


        } catch (IOException e) {//if IO Exception print it in logcat
            e.printStackTrace();
        } catch (JSONException e) {//if Json Exception print it in logcat
            e.printStackTrace();
        }

        //returning JsonObject: response
       return response;
    }

    @Override
    protected void onPostExecute(JSONObject result) {

        try {
            //when data Successfully Inserted to server
            if (result.getString("status").equals("success")) {

                //Creating DataBase class Object
                    DataBaseHelper helper=new DataBaseHelper(ctx);
                // delete QrCode Value locally
                    helper.DeleteAllQr();
                // delete ticket data locally
                    helper.DeleteAllTicket();
                // delete signature data locally
                    helper.DeleteAllSignature();

          }
        } catch (JSONException e) {//if it gives Json Exception
            e.printStackTrace();
        }catch (NullPointerException e){//if it gives Null pointer Exception
            e.printStackTrace();
        }

    }

}
