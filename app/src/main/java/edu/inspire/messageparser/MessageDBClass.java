package edu.inspire.messageparser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;


public class MessageDBClass extends SQLiteOpenHelper {

    private static final String DBNAME = "EmoMessageParse";
    public static final String EMER_MSG = "emerMsg";
    public static final String ADDRESS = "address";
    public static final String SMSBODY = "smsbody";
    public static final String STATUS = "status";
    public static final String HPY_MSG = "hpyMsg";
    public static final String EMO_MSG = "emoMsg";
    public static final String ID = "_id";
    private static int DBVER = 1;
    SQLiteDatabase db1;

    public MessageDBClass(Context context) {
        super(context, DBNAME, null, DBVER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db1 = db;
        createTableEmergency();
        createTableHappy();
        createTableEmotional();


    }


    public void createTableEmergency() {

        //SQLiteDatabase db = getWritableDatabase();
        String createTabl = "CREATE TABLE " + EMER_MSG + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ADDRESS + " TEXT, " + SMSBODY + " TEXT, " + STATUS + " TEXT)";
        db1.execSQL(createTabl);


    }

    public void createTableHappy() {
        //SQLiteDatabase db = getWritableDatabase();
        String createTabl = "CREATE TABLE " + HPY_MSG + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ADDRESS + " TEXT, " + SMSBODY + " TEXT, " + STATUS + " TEXT)";
        db1.execSQL(createTabl);
    }

    public void createTableEmotional() {
        //SQLiteDatabase db = getWritableDatabase();
        String createTabl = "CREATE TABLE " + EMO_MSG + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ADDRESS + " TEXT, " + SMSBODY + " TEXT, " + STATUS + " TEXT)";
        db1.execSQL(createTabl);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        DBVER = newVersion;

    }


    public void insertIntoEmerTab(String address, String smsBody, String status) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ADDRESS, address);
        values.put(SMSBODY, smsBody);
        values.put(STATUS, status);
        db.insert(EMER_MSG, null, values);
        db.close();


    }

    public void insertIntoHpyTab(String address, String smsBody, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ADDRESS, address);
        values.put(SMSBODY, smsBody);
        values.put(STATUS, status);
        db.insert(HPY_MSG, null, values);
        db.close();


    }

    public void insertIntoEmoTab(String address, String smsBody, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ADDRESS, address);
        values.put(SMSBODY, smsBody);
        values.put(STATUS, status);
        db.insert(EMO_MSG, null, values);
        db.close();


    }

    public Cursor getDataFromEmerTab() {

        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + EMER_MSG;
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    public Cursor getDataFromHpyTab() {

        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + HPY_MSG;
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    public Cursor getDataFromEmoTab() {

        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + EMO_MSG;
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    public Cursor getLastRowEmer() {

        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + EMER_MSG + " WHERE " + ID + " = (SELECT MAX(_id)  FROM " + EMER_MSG + " )";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String address = cursor.getString(cursor.getColumnIndex(MessageDBClass.ADDRESS));
        String body = cursor.getString(cursor.getColumnIndex(MessageDBClass.SMSBODY));
        int id = cursor.getInt(cursor.getColumnIndex(MessageDBClass.ID));
        String status = cursor.getString(cursor.getColumnIndex(MessageDBClass.STATUS));

        System.out.println(address);
        System.out.println(body);
        System.out.println(String.valueOf(id));
        System.out.println(status);


        return cursor;

    }


    public void updateUnreadtoRead(String address, String type){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS, "Read");
        if(type.equals("emotional")){
            db.update(EMO_MSG, values, "address = ?", new String[] {address});
        } else if(type.equals("happy")){
            db.update(HPY_MSG, values, "address = ?", new String[] {address});
        } else  if(type.equals("emergency")){
            db.update(EMER_MSG, values, "address = ?", new String[] {address});
        }
        //db.update(EMER_MSG, values, "address = ?", new String[] {address});
        db.close();



    }





}
