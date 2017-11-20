package com.example.user.myapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.myapplication.model.CPU;

import java.util.ArrayList;
import java.util.List;

public class MultiDbHelper extends SQLiteOpenHelper {
    private static MultiDbHelper instance;

    public static final String DB_NAME = "multi.db3";
    private static final int DBVersion = 4;

    public static synchronized MultiDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MultiDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private MultiDbHelper(Context context) {
        super(context, DB_NAME, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CPU.CREATE_TABLE_CPU);
        db.execSQL("INSERT INTO CPUs (id_cpu,name,frequency) " +
                "VALUES (1,'i5_1',2000),(2,'i3_1',1500),(3,'i7_1',3000);");
       // db.execSQL(MotherBoard.CREATE_TABLE_MB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", CPU.CREATE_TABLE_CPU));
        //db.execSQL(String.format("DROP TABLE IF EXISTS %s", MotherBoard.CREATE_TABLE_MB));
        onCreate(db);
    }
}
