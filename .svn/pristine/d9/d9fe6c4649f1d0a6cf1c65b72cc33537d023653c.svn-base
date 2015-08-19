package com.example.testing_load;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Karinnah May on 3/13/2015.
 */
public class DBManager {
    Context context;
    private SQLiteDatabase db;

    public DBManager(Context context){
        this.context = context;
        CustomHelper helper = new CustomHelper(context);
        db = helper.getWritableDatabase();
    }

    /*public ArrayList<ArrayList<Object>> displaySearch(String selectionArgs){
        ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
        Cursor cursor;
        String[] args = {selectionArgs};
        cursor = db.query("entry", new String[]{"id","name","definition","category"}, "name=?", args, null, null, null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            do{
                ArrayList<Object> row = new ArrayList<Object>();
                row.add(cursor.getInt(0));
                row.add(cursor.getString(1));
                row.add(cursor.getString(2));
                row.add(cursor.getString(3));
                rows.add(row);
            }while(cursor.moveToNext());
        }
        return rows;
    }*/

    /*public ArrayList<ArrayList<Object>> displayAll(char alphabet){
        ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
        Cursor cursor;
        cursor = db.query("entry", new String[]{"id","name"}, null, null, null, null, null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            do{
                ArrayList<Object> row = new ArrayList<Object>();
                if(cursor.getString(1).toString().charAt(0) == alphabet)
                {
                    row.add(cursor.getInt(0));
                    row.add(cursor.getString(1));
                    rows.add(row);
                }
            }while(cursor.moveToNext());
        }
        return rows;
    }*/

    public void addRow(String a, String b, String c, String d, String e){
        ContentValues val = new ContentValues();
        val.put("pin", a);
        val.put("eqpt_id", b);
        val.put("nildefect", c);
        val.put("dcl_id", d);
        val.put("dfn_id", e);
        db.insert("DefectEntry", null, val);
    }

    private class CustomHelper extends SQLiteOpenHelper {
        public CustomHelper(Context context){
            super(context,"DefectDB",null,1);
        }

        public void onCreate(SQLiteDatabase db){
            String sql = "create table entry(id integer primary key autoincrement not null, dt datetime default current_timestamp, pin text, eqpt_id text, nildefect text, dcl_id text, dfn_id text);";
            //id integer primary key autoincrement not null
            db.execSQL(sql);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
            db.execSQL("drop table if exists entry;");
            onCreate(db);
        }
    }

}

