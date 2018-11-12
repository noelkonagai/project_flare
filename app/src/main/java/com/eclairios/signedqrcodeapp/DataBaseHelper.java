package com.eclairios.signedqrcodeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

   private static final String DB_NAME = "DataBaseQr";

   private static final String TB_NAME = "qrcodetable";
   private static final String TB_NAME1="userdemo";
   private static final String TB_NAME2="qr_code";
   private static final String TB_NAME3="ticket";
   private static final String TB_NAME4="signature";
   private static final String TB_NAME5="turno";

   private static final String QRCODE_ID = "id";
   private static final String QRCODE_NAME = "qr_name";
   private static final String QRCODE_BITMAP = "qr_bitmap";

    private static final String TURNO_ID = "id";
    private static final String TURNO_PVA = "pva";
    private static final String TURNO_VENDEDOR = "vendedor";
    private static final String TURNO_DIA = "dia";
    private static final String TURNO_TURNO = "turno";

    //////////////////////qrcode/////////////////
private static final String QR_ID = "qrid";
private static final String QR_VALUE = "qr_value";

private static final String QR_STATUS="qr_status";
private static final String QR_USER_ID="qr_userid";

private static final String TICKET_ID = "tId";
private static final String TICKET_DATE = "t_date";
private static final String TICKET_TIME = "tTime";
private static final String QR_LOCATION_LAT = "qr_location_lat";
private static final String QR_LOCATION_LNG = "qr_location_lng";

private static final String TICKET_TICKET_BITMAP="tbitmap";
private static final String Ticket_status="status";
private static final String TICKET_USER_ID="t_userid";

private static final String SIGNATURE_ID="sign_id";
private static final String SIGNATURE_IMAGE="sign_image";
private static final String SIGNATURE_HASHVALUE="sign_hashvalue";
private static final String SIGNATURE_STATUS="sign_status";
private static final String SIGNATURE_USER_ID="sign_userid";

    Context context;
    String qrcode_bitmap;


    final static String QRY = " CREATE TABLE " + TB_NAME + " ( " + QRCODE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QRCODE_NAME + " TEXT, " + QRCODE_BITMAP + " TEXT) ";

    final static String QRY_QRCODE = " CREATE TABLE " + TB_NAME2 + " ( " + QR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QR_VALUE + " TEXT, "+ QR_LOCATION_LAT + " TEXT, "+ QR_LOCATION_LNG + " TEXT, " +QR_STATUS + " INTEGER, " +QR_USER_ID + " TEXT ) ";
    final static String QRY_TICKET = " CREATE TABLE " + TB_NAME3 + " ( " + TICKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+ TICKET_DATE + " TEXT, " + TICKET_TIME + " TEXT, " + TICKET_TICKET_BITMAP + " TEXT, "+ Ticket_status+ " INTEGER, " +TICKET_USER_ID + " TEXT) ";
    final static String QRY_SIGN = " CREATE TABLE " + TB_NAME4 + " ( " + SIGNATURE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+ SIGNATURE_IMAGE + " TEXT, " + SIGNATURE_HASHVALUE+ " TEXT, "+ SIGNATURE_STATUS+ " INTEGER, " +SIGNATURE_USER_ID + " TEXT) ";
    final static String QRY_TURNO = " CREATE TABLE " + TB_NAME5 + " ( " + TURNO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+ TURNO_VENDEDOR + " TEXT, " + TURNO_DIA+ " TEXT, "+ TURNO_PVA+ " TEXT, " +TURNO_TURNO + " INTEGER) ";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(QRY);
        sqLiteDatabase.execSQL(QRY_QRCODE);
        sqLiteDatabase.execSQL(QRY_TICKET);
        sqLiteDatabase.execSQL(QRY_SIGN);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //////////////////////////////////This is for signature database table///////////////////////
    public long InsertData(Qrcode_Model obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(QRCODE_NAME, obj.getQr_name());
        cv.put(QRCODE_BITMAP, obj.getQr_bitmap());

        long k = db.insert(TB_NAME, null, cv);

        if (k > 0) {
              Toast.makeText(context, "Data Successfully Inserted", Toast.LENGTH_SHORT).show();
        } else {
             Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

        return k;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////For signature data/////////////////////////////////////////////
    public long InsertData_Signature(Qrcode_Model obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SIGNATURE_IMAGE, obj.getSignature_image());
        cv.put(SIGNATURE_HASHVALUE, obj.getSignature_hashvalue());
        cv.put(SIGNATURE_STATUS,obj.getSignature_status());
        cv.put(SIGNATURE_USER_ID,obj.getUser_id());
        long k = db.insert(TB_NAME4, null, cv);

        if (k > 0) {
            Toast.makeText(context, "Signature Successfully Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

        return k;
    }
    /////////////////////////////////////////for qrcdoe value/////////////////////////////
    public long InsertData_Qrcode(Qrcode_Model obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(QR_VALUE, obj.getQr_value());
        cv.put(QR_LOCATION_LAT, obj.getLocation_lat());
        cv.put(QR_LOCATION_LNG, obj.getLocation_lng());
        cv.put(QR_STATUS, obj.getQr_status());
        cv.put(QR_USER_ID,obj.getUser_id());

        long k = db.insert(TB_NAME2, null, cv);

        if (k > 0) {
            Toast.makeText(context, "Qr_Value Successfully Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

        return k;
    }
    /////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////for turno value/////////////////////////////
    public long InsertData_Turno(Turno_Model obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(QR_VALUE, obj.getPva());
        cv.put(QR_LOCATION_LAT, obj.getTurno());
        cv.put(QR_LOCATION_LNG, obj.getVendedor());
        cv.put(QR_STATUS, obj.getDia());

        long k = db.insert(TB_NAME2, null, cv);

        if (k > 0) {
            Toast.makeText(context, "Qr_Value Successfully Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

        return k;
    }
    /////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////for insert ticket data////////////////////////
    public long InsertData_Ticket(Qrcode_Model obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TICKET_DATE, obj.getTicket_date());
        cv.put(TICKET_TIME, obj.getTicket_time());
        cv.put(TICKET_TICKET_BITMAP, obj.getTicket_image());
        cv.put(Ticket_status, obj.getTicket_status());
        cv.put(TICKET_USER_ID,obj.getUser_id());
        long k = db.insert(TB_NAME3, null, cv);

        if (k > 0) {
            Toast.makeText(context, "Ticket Successfully Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

        return k;
    }
    ////////////////////////////////////////////////////////////////////////////////////



    public List<Qrcode_Model> ShowAll() {
        List<Qrcode_Model> qr_code_model=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_NAME, null);
        while (c.moveToNext()) {
            Qrcode_Model qrcode_model=new Qrcode_Model();
            int id = c.getInt(0);
            qrcode_model.setQr_name(c.getString(1));
            qrcode_model.setQr_bitmap(c.getString(2));
            qr_code_model.add(qrcode_model);
        }
        return qr_code_model;
    }
    public String showSingle(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TB_NAME + " WHERE " + QRCODE_ID + " = '" + id+"'", null);
        while (c.moveToNext()) {
             qrcode_bitmap= c.getString(2);
        }
        return qrcode_bitmap;
    }


    //////////////Sending Data to Server//////////////////


    public ArrayList showAllQr() {
        ArrayList<Integer> id=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TB_NAME2, null);
        while (c.moveToNext()) {
            id.add(Integer.parseInt(c.getString(0)));
        }
        return id;
    }

    public ArrayList showAllTicket() {
        ArrayList<Integer> id=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TB_NAME3, null);
        while (c.moveToNext()) {
            id.add(Integer.parseInt(c.getString(0)));
        }
        return id;
    }

    public ArrayList showAllSignature() {
        ArrayList<Integer> id=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TB_NAME4, null);
        while (c.moveToNext()) {
            id.add(Integer.parseInt(c.getString(0)));
        }
        return id;
    }
    public ArrayList showsingleQr(String idx) {
        ArrayList<String> id=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TB_NAME2 + " WHERE " + QR_ID + " = "+idx, null);
        while (c.moveToNext()) {
            id.add(c.getString(0));
            id.add(c.getString(1));
            id.add(c.getString(2));
            id.add(c.getString(3));

            id.add(c.getString(4));
        }
        return id;
    }

    public ArrayList showsingleTicket(String idx) {
        ArrayList<String> id=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TB_NAME3 + " WHERE " + TICKET_ID + " = "+idx, null);
        while (c.moveToNext()) {
            id.add(c.getString(0));
            id.add(c.getString(1));
            id.add(c.getString(2));
            id.add(c.getString(3));
            id.add(c.getString(4));
        }
        return id;
    }

    public ArrayList showsingleSignature(String idx) {
        ArrayList<String> id=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TB_NAME4 + " WHERE " + SIGNATURE_ID + " = "+idx, null);
        while (c.moveToNext()) {
            id.add(c.getString(0));
            id.add(c.getString(1));
            id.add(c.getString(2));
            id.add(c.getString(3));
        }
        return id;
    }



    ///////////////Deleting Local Data//////////////////



    public void DeleteAllQr()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TB_NAME2, QR_STATUS+ " = 0", null);

        if (id > 0){}
        //  Toast.makeText(context, "Data Successfully \n Deleted", Toast.LENGTH_SHORT).show();
        else{}
        //  Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();


    }

    public void DeleteAllTicket()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TB_NAME3, Ticket_status+ " = 0", null);

        if (id > 0){}
        //  Toast.makeText(context, "Data Successfully \n Deleted", Toast.LENGTH_SHORT).show();
        else{}
        //  Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();


    }

    public void DeleteAllSignature()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TB_NAME4, SIGNATURE_STATUS+ " = 0" , null);

        if (id > 0){}
        //  Toast.makeText(context, "Data Successfully \n Deleted", Toast.LENGTH_SHORT).show();
        else{}
        //  Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();


    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // String query="select * from tablename where id = "+nn;
        long id = db.delete(TB_NAME, null, null);

        if (id > 0){}
        //  Toast.makeText(context, "Data Successfully \n Deleted", Toast.LENGTH_SHORT).show();
        else{}
        //  Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();


    }


}



