package com.example.sqlite;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    //database
    public static final String DATABASE_NAME="data";
    public static final String TABLE_USER="user";
    public static final String COLUMN_USER_ID="id";
    public static final String COLUMN_USER_NAME="name";
    public static final String COLUMN_USER_AVATAR="avatar";
    public static final String TABLE_DEPARTMENT="depart";

    public static final String COLUMN_DEPA_ID="departid";
    public static final String COLUMN_DEPA_NAME="departname";
    public static final String COLUMN_USER_DEPARTID="usdepartid";
    //funtion(chức năng)
    public static Bitmap converToBitmapFromAssets(Context context,String nameImage) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("images/" + nameImage);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Utils", "Error loading image from assets", e);
        }
        return null;
    }
}
