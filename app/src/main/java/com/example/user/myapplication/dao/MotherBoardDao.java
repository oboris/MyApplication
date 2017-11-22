package com.example.user.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.myapplication.database.MultiDbHelper;
import com.example.user.myapplication.model.MotherBoard;

import java.util.ArrayList;
import java.util.List;

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

    public synchronized List<MotherBoard> selectAllMotherBoardFromDB() {
        final SQLiteDatabase db = MultiDbHelper.getInstance(context).getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_MB, null,
                null, null, null, null, null);

        List<MotherBoard> motherBoards = convertFromCursorToListMBs(cursor);
        cursor.close();

        for (MotherBoard motherBoard: motherBoards) {
            cursor = db.query(TABLE_NAME_MB_PICT, null,
                    COLUMN_ID_MB + "= ?", new String[]{String.valueOf(motherBoard.getIdMotherBoard())},
                    null, null, null);
            List<String> picUrls = convertFromCursorToListPicUrls(cursor);
            motherBoard.setPhotos(picUrls);
        }
        cursor.close();

        db.close();
        return motherBoards;
    }

    private List<MotherBoard> convertFromCursorToListMBs(Cursor cursor) {
        List<MotherBoard> motherBoards = new ArrayList<>(cursor.getCount());
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                motherBoards.add(fromCursorToMB(cursor));
            } while (cursor.moveToNext());
        }
        return motherBoards;
    }

   private MotherBoard fromCursorToMB(Cursor cursor) {
        int idMB = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_MB));
        String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        String chipSet = cursor.getString(cursor.getColumnIndex(COLUMN_CHIP_SET));
        return new MotherBoard(idMB, name, chipSet);
    }

    private List<String> convertFromCursorToListPicUrls(Cursor cursor) {
        List<String> pictUrls = new ArrayList<>(cursor.getCount());
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                pictUrls.add(fromCursorToPict(cursor));
            } while (cursor.moveToNext());
        }
        return pictUrls;
    }

    private String fromCursorToPict(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(COLUMN_PIC_URL));
    }

    private ContentValues getMBContent(MotherBoard motherBoard) {
        final ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, motherBoard.getName());
        values.put(COLUMN_CHIP_SET, motherBoard.getChipSet());
        return values;
    }
}
