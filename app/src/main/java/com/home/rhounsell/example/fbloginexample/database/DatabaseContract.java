package com.home.rhounsell.example.fbloginexample.database;

import android.provider.BaseColumns;

/**
 * Created by raphael on 03/04/17.
 */

public class DatabaseContract {
    private DatabaseContract(){}
    public static class UserEntry implements BaseColumns{
        public static final String TABLE_NAME="user";
        public static final String COLUMN_NAME_NAME="name";
        public static final String COLUMN_NAME_FB_ID="fb_id";
        public static final String COLUMN_NAME_EMAIL="email";
    }
}
