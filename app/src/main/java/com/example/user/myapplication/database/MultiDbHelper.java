package com.example.user.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.myapplication.dao.CPUDao;
import com.example.user.myapplication.dao.MotherBoardDao;
import com.example.user.myapplication.model.CPU;

public class MultiDbHelper extends SQLiteOpenHelper {
    private static MultiDbHelper instance;

    public static final String DB_NAME = "multi.db3";
    private static final int DB_VERSION = 5;

    public static synchronized MultiDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MultiDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private MultiDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CPUDao.CREATE_TABLE_CPU);
        db.execSQL("INSERT INTO CPUs (id_cpu,name,frequency) " +
                "VALUES (1,'i5_1',2000),(2,'i3_1',1500),(3,'i7_1',3000);");

        db.execSQL(MotherBoardDao.CREATE_TABLE_MB);
        db.execSQL("INSERT INTO CPUs (id_cpu,name,frequency) " +
                "VALUES (1,'i5_1',2000),(2,'i3_1',1500),(3,'i7_1',3000);");

        db.execSQL(MotherBoardDao.CREATE_TABLE_MB_PICT);
        db.execSQL("INSERT INTO CPUs (id_cpu,name,frequency) " +
                "VALUES (1,'i5_1',2000),(2,'i3_1',1500),(3,'i7_1',3000);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", CPUDao.TABLE_NAME_CPU));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", MotherBoardDao.TABLE_NAME_MB));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", MotherBoardDao.TABLE_NAME_MB_PICT));
        onCreate(db);
    }
}
