package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite.Utils;

public class UserDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME= Utils.DATABASE_NAME;
    private static final int DATABASE_VERSION=1;

    public UserDBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE="CREATE TABLE "+ Utils.TABLE_USER+" ("
                +Utils.COLUMN_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Utils.COLUMN_USER_NAME+" TEXT, "
                +Utils.COLUMN_USER_AVATAR+" TEXT, "
                +Utils.COLUMN_USER_DEPARTID+" INTEGER"+")";


        String CREATE_DEPA_TABLE="CREATE TABLE " + Utils.TABLE_DEPARTMENT+"("+
                Utils.COLUMN_DEPA_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Utils.COLUMN_DEPA_NAME+" TEXT"+")";

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_DEPA_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Utils.TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Utils.TABLE_DEPARTMENT);
        onCreate(sqLiteDatabase);
    }
}