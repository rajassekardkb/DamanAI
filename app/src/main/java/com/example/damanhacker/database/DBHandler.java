package com.example.damanhacker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.damanhacker.model.DataModelMainData;
import com.example.damanhacker.utlities.Mapping;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "coursedb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME_DAMAN_SERVER = "DAMAN_SERVER";
    private static final String DM_SNO = "DM_SNO";
    private static final String DM_PERIOD = "DM_PERIOD";
    private static final String DM_NUMBER = "DM_NUMBER";
    private static final String DM_VALUE = "DM_VALUE";
    private static final String DM_COLOR = "DM_COLOR";
    private static final String DM_DATE = "DM_DATE";
    private static final String DM_CURRENT_DATE_TIME = "DM_CURRENT_DATE_TIME";
    private static final String DM_FLAG = "DM_FLAG";
    private static final String CAMA = ",";
    private static final String SELECT = "SELECT ";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String FROM = " FROM ";


    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME_DAMAN_SERVER + " (" + DM_SNO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DM_PERIOD + " INTEGER(6)," + DM_NUMBER + " INTEGER(1) NOT NULL DEFAULT 0," + DM_VALUE + " VARCHAR(5) NOT NULL DEFAULT ''," + DM_COLOR + " VARCHAR(1) NOT NULL DEFAULT ''," + DM_DATE + " VARCHAR(12) NOT NULL DEFAULT ''," + DM_CURRENT_DATE_TIME + " VARCHAR(59) NOT NULL DEFAULT ''," + DM_FLAG + " INTEGER(1) NOT NULL DEFAULT 0)";
        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewCourse(ArrayList<DataModelMainData> list) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        // on below line we are creating a
        // variable for content values.
        for (int i = 0; i < list.size(); i++) {
            ContentValues values = new ContentValues();
            DataModelMainData data = list.get(i);
            // on below line we are passing all values
            // along with its key and value pair.
            values.put(DM_PERIOD, data.getPeriod());
            values.put(DM_NUMBER, data.getNumber());
            values.put(DM_VALUE, data.getValue());
            values.put(DM_COLOR, data.getColor());
            values.put(DM_DATE, data.getDate());
            // after adding all values we are passing
            // content values to our table.
            db.insert(TABLE_NAME_DAMAN_SERVER, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public int getCount(String date) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(DM_SNO) FROM " + TABLE_NAME_DAMAN_SERVER + WHERE + DM_DATE + "='" + date + "'", null);
        if (c.moveToFirst()) {
            do {
                // Passing values
                count = Integer.parseInt(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return count;
    }

    public ArrayList<String> getDateList() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT DISTINCT " + DM_DATE + " FROM " + TABLE_NAME_DAMAN_SERVER, null);
        if (c.moveToFirst()) {
            do {
                list.add(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }


    public int getCheck(String date, String period) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(DM_SNO) FROM " + TABLE_NAME_DAMAN_SERVER + WHERE + DM_DATE + "='" + date + "' " + AND + DM_PERIOD + "='" + period + "'", null);
        if (c.moveToFirst()) {
            do {
                // Passing values
                count = Integer.parseInt(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return count;
    }

    public ArrayList<DataModelMainData> getData(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DataModelMainData> listValue = new ArrayList<>();
        Cursor c = db.rawQuery(SELECT + DM_SNO + CAMA + DM_PERIOD + CAMA + DM_NUMBER + CAMA + DM_VALUE + CAMA + DM_COLOR + CAMA + DM_DATE + CAMA + DM_CURRENT_DATE_TIME + CAMA + DM_FLAG + FROM + TABLE_NAME_DAMAN_SERVER + WHERE + DM_DATE + "='" + date + "' ORDER BY " + DM_PERIOD + " DESC", null);
        if (c.moveToFirst()) {
            do {
                listValue.add(new DataModelMainData(c.getInt(0), c.getInt(1), c.getInt(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return listValue;
    }

    public ArrayList<DataModelMainData> getDataProcess(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DataModelMainData> listValue = new ArrayList<>();
        Cursor c = db.rawQuery(SELECT + DM_SNO + CAMA + DM_PERIOD + CAMA + DM_NUMBER + CAMA + DM_VALUE + CAMA + DM_COLOR + CAMA + DM_DATE + CAMA + DM_CURRENT_DATE_TIME + CAMA + DM_FLAG + FROM + TABLE_NAME_DAMAN_SERVER + WHERE + DM_DATE + "='" + date + "' ORDER BY " + DM_PERIOD + " ASC", null);
        if (c.moveToFirst()) {
            do {
                listValue.add(new DataModelMainData(c.getInt(0), c.getInt(1), c.getInt(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return listValue;
    }
    public ArrayList<com.example.damanhacker.utlities.CheckSerialNumberRelatedOptphp.DataModelMainData> getDataProcess_(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<com.example.damanhacker.utlities.CheckSerialNumberRelatedOptphp.DataModelMainData> listValue = new ArrayList<>();
        Cursor c = db.rawQuery(SELECT + DM_SNO + CAMA + DM_PERIOD + CAMA + DM_NUMBER + CAMA + DM_VALUE + CAMA + DM_COLOR + CAMA + DM_DATE + CAMA + DM_CURRENT_DATE_TIME + CAMA + DM_FLAG + FROM + TABLE_NAME_DAMAN_SERVER + WHERE + DM_DATE + "='" + date + "' ORDER BY " + DM_PERIOD + " ASC", null);
        if (c.moveToFirst()) {
            do {
                listValue.add(new com.example.damanhacker.utlities.CheckSerialNumberRelatedOptphp.DataModelMainData(c.getInt(0), c.getInt(1), c.getInt(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return listValue;
    }

    public ArrayList<DataModelMainData> getDataRaw(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DataModelMainData> listValue = new ArrayList<>();
        Cursor c = db.rawQuery(SELECT + DM_SNO + CAMA + DM_PERIOD + CAMA + DM_NUMBER + CAMA + DM_VALUE + CAMA + DM_COLOR + CAMA + DM_DATE + CAMA + DM_CURRENT_DATE_TIME + CAMA + DM_FLAG + FROM + TABLE_NAME_DAMAN_SERVER + WHERE + DM_DATE + "='" + date + "'", null);
        if (c.moveToFirst()) {
            do {
                listValue.add(new DataModelMainData(c.getInt(0), c.getInt(1), c.getInt(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return listValue;
    }

    public ArrayList<DataModelMainData> getDataForUpdate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DataModelMainData> listValue = new ArrayList<>();
        Cursor c = db.rawQuery(SELECT + DM_SNO + CAMA + DM_PERIOD + CAMA + DM_NUMBER + CAMA + DM_VALUE + CAMA + DM_COLOR + CAMA + DM_DATE + CAMA + DM_CURRENT_DATE_TIME + CAMA + DM_FLAG + FROM + TABLE_NAME_DAMAN_SERVER + WHERE + DM_DATE + "='" + date + "'", null);
        if (c.moveToFirst()) {
            do {
                listValue.add(new DataModelMainData(c.getInt(0), c.getInt(1), c.getInt(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return listValue;
    }

    public void InsertDataMaster(DataModelMainData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DM_NUMBER, data.getNumber());
        values.put(DM_VALUE, data.getValue());
        values.put(DM_COLOR, data.getColor());
        values.put(DM_FLAG, "1");
        values.put(DM_DATE, data.getDate());
        values.put(DM_PERIOD, data.getPeriod());
        db.insert(TABLE_NAME_DAMAN_SERVER, null, values);
        db.close();
    }

    public void updateCourseSingle(DataModelMainData data, int number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String value = new Mapping().getValue(number);
        String color = new Mapping().getColor(number);
        values.put(DM_NUMBER, number);
        values.put(DM_VALUE, value);
        values.put(DM_COLOR, color);
        values.put(DM_FLAG, "1");
        db.update(TABLE_NAME_DAMAN_SERVER, values, DM_SNO + "= ?", new String[]{data.getSno() + ""});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DAMAN_SERVER);
        onCreate(db);
    }

    public void insertDataIfNotExists(ArrayList<DataModelMainData> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
           // System.out.println("DataDownloading-> Request size->"+dataList.size()+":"+i);

            DataModelMainData data = dataList.get(i);
            if (!dataExists(data.getDate(), String.valueOf(data.getPeriod()))) {
                //System.out.println("DataDownloading-> Request ins->"+data.getPeriod()+":"+data.getDate());
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DM_NUMBER, data.getNumber());
                values.put(DM_VALUE, data.getValue());
                values.put(DM_COLOR, data.getColor());
                values.put(DM_FLAG, "1");
                values.put(DM_DATE, data.getDate());
                values.put(DM_PERIOD, data.getPeriod());
                db.insert(TABLE_NAME_DAMAN_SERVER, null, values);
                db.close();
            }
        }
    }

    public boolean dataExists(String date, String period) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(DM_SNO) FROM " + TABLE_NAME_DAMAN_SERVER + " WHERE " + DM_DATE + "='" + date + "' AND " + DM_PERIOD + "='" + period + "'", null);
        boolean exists = false;

        if (c.moveToFirst()) {
            exists = Integer.parseInt(c.getString(0)) > 0;
        }

        c.close();
        db.close();
        return exists;
    }

}
