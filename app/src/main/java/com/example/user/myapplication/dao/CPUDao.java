package com.example.user.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.myapplication.database.MultiDbHelper;
import com.example.user.myapplication.model.CPU;

import java.util.ArrayList;
import java.util.List;

public class CPUDao {
    private Context context;

    public static final String TABLE_NAME_CPU = "CPUs";

    public static final String COLUMN_ID_CPU = "id_cpu";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FREQUENCY = "frequency";

    public static final String CREATE_TABLE_CPU = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_CPU +
            " (\n" +
            COLUMN_ID_CPU + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            COLUMN_NAME + " TEXT,\n" +
            COLUMN_FREQUENCY + " INTEGER\n" +
            ");";

    public CPUDao(Context context) {
        this.context = context;
    }

    public synchronized List<CPU> selectAllCPUFromDB() {
        final SQLiteDatabase db = MultiDbHelper.getInstance(context).getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_CPU, null,
                null, null, null, null, null);

        List<CPU> cpuList = convertFromCursorToListCPUs(cursor);
        cursor.close();
        db.close();
        return cpuList;
    }


    public synchronized void deleteAllRecordsFromDB() {
        final SQLiteDatabase db = MultiDbHelper.getInstance(context).getWritableDatabase();
        db.delete(TABLE_NAME_CPU, null, null);
        db.close();
    }

    public synchronized void insertRecordToDB(CPU cpu) {
        final SQLiteDatabase db = MultiDbHelper.getInstance(context).getWritableDatabase();
        db.insertWithOnConflict(TABLE_NAME_CPU, null, getCPUContent(cpu), SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public synchronized void insertListRecordsToDB(List<CPU> cpus) {
        final SQLiteDatabase db = MultiDbHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();
        try {
            for (CPU cpu : cpus)
                db.insertWithOnConflict(TABLE_NAME_CPU, null, getCPUContent(cpu), SQLiteDatabase.CONFLICT_IGNORE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    private List<CPU> convertFromCursorToListCPUs(Cursor cursor) {
        List<CPU> cpuList = new ArrayList<>(cursor.getCount());
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                cpuList.add(fromCursorToCPU(cursor));
            } while (cursor.moveToNext());
        }
        return cpuList;
    }

    private CPU fromCursorToCPU(Cursor cursor) {
        int idCPU = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_CPU));
        String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        int frequency = cursor.getInt(cursor.getColumnIndex(COLUMN_FREQUENCY));
        return new CPU(idCPU, name, frequency);
    }

    private ContentValues getCPUContent(CPU cpu) {
        final ContentValues values = new ContentValues();
        //values.put(COLUMN_ID_CPU, getIdCPU());
        values.put(COLUMN_NAME, cpu.getName());
        values.put(COLUMN_FREQUENCY, cpu.getFrequency());
        return values;
    }
}
