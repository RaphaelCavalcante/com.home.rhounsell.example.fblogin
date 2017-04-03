package com.home.rhounsell.example.fbloginexample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.home.rhounsell.example.fbloginexample.database.DatabaseContract.UserEntry;
/**
 * Created by raphael on 03/04/17.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="user.db";
    private static final int DATABASE_VERSION=1;
    private static final String createString="create table "+
            UserEntry.TABLE_NAME+"("+
            UserEntry._ID+" integer not null primary key, "+
            UserEntry.COLUMN_NAME_NAME+" text not null, "+
            UserEntry.COLUMN_NAME_EMAIL+" text, "+
            UserEntry.COLUMN_NAME_FB_ID+" text);";
    private static SQLiteHelper helper;
    public static SQLiteHelper getInstance(Context context){
        if(helper == null){
            helper = new SQLiteHelper(context.getApplicationContext());
        }
        return helper;
    }
    public SQLiteHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createString);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
