package com.example.user.myapplication.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.myapplication.database.MultiDbHelper;
import com.example.user.myapplication.model.CPU;

import java.util.ArrayList;
import java.util.List;

public class CPUDao {
    private Context context;

    public CPUDao(Context context) {
        this.context = context;
    }

    public synchronized List<CPU> selectAllCPUFromDB() {
        final SQLiteDatabase db = MultiDbHelper.getInstance(context).getReadableDatabase();

        Cursor cursor = db.query(CPU.TABLE_NAME_CPU, null,
                null, null, null, null, null);

        List<CPU> cpuList = convertFromCursorToListCPUs(cursor);
        cursor.close();
        db.close();
        return cpuList;
    }


    public synchronized void deleteAllRecordsFromDB() {
        final SQLiteDatabase db = MultiDbHelper.getInstance(context).getWritableDatabase();
        db.delete(CPU.TABLE_NAME_CPU, null, null);
        db.close();
    }

    public synchronized void insertRecordToDB(CPU cpu) {
        final SQLiteDatabase db = MultiDbHelper.getInstance(context).getWritableDatabase();
        db.insertWithOnConflict(CPU.TABLE_NAME_CPU, null, cpu.getContent(), SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public synchronized void insertListRecordsToDB(List<CPU> cpus) {
        final SQLiteDatabase db = MultiDbHelper.getInstance(context).getWritableDatabase();
        db.beginTransaction();
        try {
            for (CPU cpu : cpus)
                db.insertWithOnConflict(CPU.TABLE_NAME_CPU, null, cpu.getContent(), SQLiteDatabase.CONFLICT_IGNORE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    private static List<CPU> convertFromCursorToListCPUs(Cursor cursor) {
        List<CPU> cpuList = new ArrayList<>(cursor.getCount());
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                cpuList.add(new CPU(cursor));
            } while (cursor.moveToNext());
        }
        return cpuList;
    }
}
