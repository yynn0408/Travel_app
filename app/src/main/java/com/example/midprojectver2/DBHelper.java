package com.example.midprojectver2;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static DBHelper mInstance;
    private static SQLiteDatabase mdb;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context, name,factory,version);
        this.context=context;
    }
    private DBHelper(final Context context){
        super(context, "mapDB",null,1);
    }
    public DBHelper getmInstance(Context hcontext){
        if(mInstance==null) {
            mInstance=new DBHelper(hcontext);
        }
        Log.e("succ","succ");
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE mapDB ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" TITLE TEXT, ");
        sb.append(" CONTENT TEXT, ");
        sb.append(" LAT REAL, ");
        sb.append(" LONGI REAL,  "); // SQLite Database로 쿼리 실행
        sb.append(" URI TEXT) ");
        db.execSQL(sb.toString());
        mdb=db;
        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show();

    }
    public void testDB() {
        mdb = getReadableDatabase();
    }
    public void addItem(ITEM item){
        mdb = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO mapDB ( ");
        sb.append(" TITLE, CONTENT, LAT, LONGI, URI ) ");
        sb.append(" VALUES ( ?, ?, ?, ?, ?) ");
        mdb.execSQL(sb.toString(),new Object[]{
                item.get_title(),item.get_snip(),item.get_lat(),item.get_longi(),item.get_suri()
        });

    }
    public ArrayList<ITEM> getAllITEM(){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM mapDB");
        mdb=getReadableDatabase();
        Cursor cursor=mdb.rawQuery(sb.toString(),null);
        ArrayList<ITEM> record=new ArrayList<>();
        ITEM item=null;
        while(cursor.moveToNext()){
            item=new ITEM();
            item.set_id(cursor.getInt(0));
            item.set_title(cursor.getString(1));
            item.set_snip(cursor.getString(2));
            item.set_lat(cursor.getDouble(3));
            item.set_longi(cursor.getDouble(4));
            item.set_uri(cursor.getString(5));
            record.add(item);
        }
        return record;
    }
    public ITEM getSELITEM(int position){
        Log.e("sel","imhere");
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM mapDB where _ID=");
        sb.append(Integer.toString(position));
        mdb=getReadableDatabase();
        Cursor cursor=mdb.rawQuery(sb.toString(),null);
        ITEM item=null;
        while(cursor.moveToNext()){
            item=new ITEM();
            item.set_id(cursor.getInt(0));
            item.set_title(cursor.getString(1));
            item.set_snip(cursor.getString(2));
            item.set_lat(cursor.getDouble(3));
            item.set_longi(cursor.getDouble(4));
            item.set_uri(cursor.getString(5));
        }
        return item;
    }
    public void deleteITEM(int position){
        Log.e("del","imhere");
        StringBuffer sb = new StringBuffer();
        mdb = getWritableDatabase();
        sb.append("delete FROM mapDB where _ID=");
        sb.append(Integer.toString(position));
        mdb=getWritableDatabase();
        mdb.execSQL(sb.toString() );
        Log.e("imhere","imhere");
        Cursor cursor=mdb.rawQuery(sb.toString(),null);
    }
    public ITEM MarkerITEM(Marker marker){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM mapDB where LAT=");
        sb.append(marker.getPosition().latitude);
        sb.append(" AND LONGI=");
        sb.append(marker.getPosition().longitude);
        mdb=getReadableDatabase();
        Cursor cursor=mdb.rawQuery(sb.toString(),null);
        ITEM item=null;
        while(cursor.moveToNext()){
            item=new ITEM();
            item.set_id(cursor.getInt(0));
            item.set_title(cursor.getString(1));
            item.set_snip(cursor.getString(2));
            item.set_lat(cursor.getDouble(3));
            item.set_longi(cursor.getDouble(4));
            item.set_uri(cursor.getString(5));
        }
        return item;
    }
}