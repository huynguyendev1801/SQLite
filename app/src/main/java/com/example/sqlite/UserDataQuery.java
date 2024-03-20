package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sqlite.UserDBHelper;
import com.example.sqlite.Utils;

import java.util.ArrayList;

public class UserDataQuery {
    //add new user
    public static long insert(Context context, User us)
    {
        UserDBHelper userDBHelper=new UserDBHelper(context);

        SQLiteDatabase sqLiteDatabase=userDBHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Utils.COLUMN_USER_NAME,us.name);
        values.put(Utils.COLUMN_USER_AVATAR,us.avatar);
        values.put(Utils.COLUMN_USER_DEPARTID,us.de_id);
        long rs= sqLiteDatabase.insert(Utils.TABLE_USER,null,values);
        return (rs);
    }
    //lay danh sach
    public static ArrayList<User>getAll(Context context)
    {
        ArrayList<User>lstUser=new ArrayList<>();
        UserDBHelper userDBHelper=new UserDBHelper(context);
        SQLiteDatabase db=userDBHelper.getReadableDatabase();
//        Cursor cs = db.rawQuery("SELECT " + Utils.COLUMN_USER_ID + ", "+ Utils.COLUMN_USER_NAME + "," + Utils.COLUMN_USER_AVATAR + "," + Utils.COLUMN_DEPA_ID + "," + Utils.COLUMN_DEPA_NAME+ " FROM " + Utils.TABLE_USER +
//                " LEFT JOIN " + Utils.TABLE_DEPARTMENT +
//                " ON " + Utils.TABLE_USER + "." + Utils.COLUMN_USER_DEPARTID +
//                " = " + Utils.TABLE_DEPARTMENT + "." + Utils.COLUMN_DEPA_ID, null);
        Cursor cs = db.rawQuery("SELECT * FROM " + Utils.TABLE_USER, null);

        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            String avatar = cs.getString(2);
            int de_id = cs.getInt(3);
            String de_name = "";

            // Truy vấn để lấy thông tin phòng ban tương ứng với de_id
            Cursor csDepartment = db.rawQuery("SELECT " + Utils.COLUMN_DEPA_NAME + " FROM " + Utils.TABLE_DEPARTMENT + " WHERE " + Utils.COLUMN_DEPA_ID + " = ?", new String[]{String.valueOf(de_id)});
            if (csDepartment.moveToFirst()) {
                de_name = csDepartment.getString(0);
            }
            csDepartment.close(); // Đóng con trỏ khi đã sử dụng xong

            lstUser.add(new User(id, name, avatar, de_id, de_name));
            cs.moveToNext();
        }

        cs.close();
        db.close();
        return lstUser;

    }

    //delete item
    public static boolean delete(Context context,int id)
    {
        UserDBHelper userDBHelper=new UserDBHelper(context);
        SQLiteDatabase sqLiteDatabase=userDBHelper.getWritableDatabase();
        int rs=sqLiteDatabase.delete(Utils.TABLE_USER,Utils.COLUMN_USER_ID+"=?", new String[]{String.valueOf(id)});
        return (rs > 0);
    }

    //update
    public static int update(Context context, User us)
    {
        UserDBHelper userDBHelper=new UserDBHelper(context);
        SQLiteDatabase sqLiteDatabase=userDBHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Utils.COLUMN_USER_NAME,us.getName());
        values.put(Utils.COLUMN_USER_AVATAR,us.getAvatar());
        int rs=sqLiteDatabase.update(Utils.TABLE_USER,values,Utils.COLUMN_USER_ID+" = ?", new String[]{String.valueOf(us.getId())});
        return rs;
    }

}

