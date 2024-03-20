package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DepartmentDataQuery {
    //add new user
    public static long insert(Context context, Department department)
    {
        UserDBHelper userDBHelper=new UserDBHelper(context);

        SQLiteDatabase sqLiteDatabase=userDBHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Utils.COLUMN_DEPA_NAME,department.name);
        long rs= sqLiteDatabase.insert(Utils.TABLE_DEPARTMENT,null,values);

        return (rs);
    }
    //lay danh sach
    public static ArrayList<Department> getAll(Context context)
    {
        ArrayList<Department>lstDepartment=new ArrayList<>();
        UserDBHelper userDBHelper=new UserDBHelper(context);
        SQLiteDatabase db=userDBHelper.getReadableDatabase();

        Cursor cs=db.rawQuery("Select * from "+ Utils.TABLE_DEPARTMENT,null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            lstDepartment.add(new Department(id, name));
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstDepartment;

    }

    //delete item
    public static boolean delete(Context context,int id)
    {
        UserDBHelper userDBHelper=new UserDBHelper(context);
        SQLiteDatabase sqLiteDatabase=userDBHelper.getWritableDatabase();
        int rs=sqLiteDatabase.delete(Utils.TABLE_DEPARTMENT,Utils.COLUMN_DEPA_ID+"=?", new String[]{String.valueOf(id)});
        return (rs > 0);
    }

    //update
    public static int update(Context context, Department de)
    {
        UserDBHelper userDBHelper=new UserDBHelper(context);
        SQLiteDatabase sqLiteDatabase=userDBHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Utils.COLUMN_DEPA_NAME,de.getName());
        int rs=sqLiteDatabase.update(Utils.TABLE_DEPARTMENT,values,Utils.COLUMN_DEPA_ID+" = ?", new String[]{String.valueOf(de.getId())});
        return rs;
    }
}
