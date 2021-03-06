package com.home.rhounsell.example.fbloginexample.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.home.rhounsell.example.fbloginexample.database.SQLiteHelper;
import com.home.rhounsell.example.fbloginexample.database.DatabaseContract.UserEntry;
import com.home.rhounsell.example.fbloginexample.model.User;

/**
 * Created by raphael on 03/04/17.
 */

public class UserDataSource {
    private SQLiteHelper helper;
    private SQLiteDatabase database;
    private String allColumns[]={UserEntry._ID,
            UserEntry.COLUMN_NAME_NAME,
            UserEntry.COLUMN_NAME_EMAIL,
            UserEntry.COLUMN_NAME_FB_ID};
    public UserDataSource(Context context){
        helper = SQLiteHelper.getInstance(context);
    }
    public void open(){
        database= helper.getWritableDatabase();
    }
    public void close(){
        database.close();
    }
    public User createUser(String name, String email, String fbId){
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_NAME_NAME, name);
        values.put(UserEntry.COLUMN_NAME_EMAIL, email);
        values.put(UserEntry.COLUMN_NAME_FB_ID, fbId);

        long insertId=database.insert(UserEntry.TABLE_NAME, null, values);
        Cursor cursor = database.query(UserEntry.TABLE_NAME,
                allColumns,
                UserEntry._ID+"="+insertId,
                null,null,null,null);
        User newUser= new User();
        if(cursor.moveToFirst()){
            newUser = cursorToUser(cursor);
        }
        cursor.close();
        return newUser;
    }
    public User getUser(long userId){
        Cursor cursor = database.query(UserEntry.TABLE_NAME,
                allColumns,
                UserEntry._ID+"="+userId,
                null,null,null,null);
        User user = new User();
        if(cursor.moveToFirst()){
            user = cursorToUser(cursor);
        }
        cursor.close();
        return user;
    }
    public int countUsers(){
        String rawQuery = "select count(*) from "+UserEntry.TABLE_NAME;
        Cursor cursor = database.rawQuery(rawQuery, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
    public User cursorToUser(Cursor cursor){
        User user = new User();
        user.setUserId(cursor.getLong(0));
        user.setName(cursor.getString(1));
        user.setEmail(cursor.getString(2));
        user.setFbId(cursor.getString(3));
        return user;
    }
}
