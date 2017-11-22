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
    private static final int DB_VERSION = 6;

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
        db.execSQL("INSERT INTO " + MotherBoardDao.TABLE_NAME_MB + " ("+
                MotherBoardDao.COLUMN_ID_MB+","+MotherBoardDao.COLUMN_NAME+","+MotherBoardDao.COLUMN_CHIP_SET+") " +
                "VALUES (1,'MSI M5','Z170'),(2,'ASUS XX','H70+'),(3,'Abit Q35','z300');");

        db.execSQL(MotherBoardDao.CREATE_TABLE_MB_PICT);
        db.execSQL("INSERT INTO " + MotherBoardDao.TABLE_NAME_MB_PICT +"("+
                MotherBoardDao.COLUMN_ID_MB_PICT+","+MotherBoardDao.COLUMN_PIC_URL+","+MotherBoardDao.COLUMN_ID_MB+") " +
                "VALUES (1,'https://i1.rozetka.ua/goods/1359597/asus_h110m_k_images_1359597921.jpg',1)," +
                "(2,'https://i2.rozetka.ua/goods/1359598/asus_h110m_k_images_1359598383.jpg',2)," +
                "(3,'https://i1.rozetka.ua/goods/1359597/asus_h110m_k_images_1359597921.jpg',3)," +
                "(4,'https://i1.rozetka.ua/goods/1359597/asus_h110m_k_images_1359597921.jpg',1)," +
                "(5,'https://i1.rozetka.ua/goods/1359597/asus_h110m_k_images_1359597921.jpg',1)," +
                "(6,'https://i2.rozetka.ua/goods/1359598/asus_h110m_k_images_1359598383.jpg',3)," +
                "(7,'https://i1.rozetka.ua/goods/1359597/asus_h110m_k_images_1359597921.jpg',3)," +
                "(8,'https://i1.rozetka.ua/goods/1359597/asus_h110m_k_images_1359597921.jpg',2)," +
                "(9,'https://i1.rozetka.ua/goods/1359597/asus_h110m_k_images_1359597921.jpg',3)," +
                "(10,'https://i2.rozetka.ua/goods/1359598/asus_h110m_k_images_1359598383.jpg',1)," +
                "(11,'https://i1.rozetka.ua/goods/1359597/asus_h110m_k_images_1359597921.jpg',2)," +
                "(12,'https://i2.rozetka.ua/goods/1359598/asus_h110m_k_images_1359598383.jpg',3);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", CPUDao.TABLE_NAME_CPU));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", MotherBoardDao.TABLE_NAME_MB));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", MotherBoardDao.TABLE_NAME_MB_PICT));
        onCreate(db);
    }
}
