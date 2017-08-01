package com.example.leet.myminlist;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by leet on 17-7-28.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    private static final String DB_NAME="list.db";
    private static final String TABLE_NAME="note";
    public MyDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    public MyDBHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table if not exists "+TABLE_NAME+"(_id integer primary key autoincrement , note text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql="DROP TABLE IF EXISTS"+TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
    }

}
