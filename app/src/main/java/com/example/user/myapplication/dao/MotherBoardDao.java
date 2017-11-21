package com.example.user.myapplication.dao;

import android.content.Context;

public class MotherBoardDao {
    private Context context;

    public static final String TABLE_NAME_MB = "MotherBoards";

    public static final String COLUMN_ID_MB = "id_mb";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CHIP_SET = "chip_set";

    public static final String TABLE_NAME_MB_PICT = "MotherBoardPict";

    public static final String COLUMN_ID_MB_PICT = "id_mb_pict";
    public static final String COLUMN_PIC_URL = "pic_url";


    public static final String CREATE_TABLE_MB = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_MB +
            "(\n" +
            COLUMN_ID_MB + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            COLUMN_NAME + " TEXT,\n" +
            COLUMN_CHIP_SET + " TEXT\n" +
            ");";

    public static final String CREATE_TABLE_MB_PICT = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_MB_PICT +
            "(\n" +
            COLUMN_ID_MB_PICT + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            COLUMN_PIC_URL + " TEXT,\n" +
            COLUMN_ID_MB + " INTEGER\n" +
            ");";

    public MotherBoardDao(Context context) {
        this.context = context;
    }
}
