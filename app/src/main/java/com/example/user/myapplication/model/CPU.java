package com.example.user.myapplication.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.user.myapplication.dao.CPUDao;
import com.google.gson.annotations.SerializedName;

//@Entity
public class CPU implements MultiModel {

  //  @PrimaryKey(autoGenerate = true)
    private int idCPU;

    @SerializedName("FIRSTNAME")
    private String name;

    @SerializedName("RECORD_TYPE")
    private int frequency;



    public CPU(String name, int frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public CPU(Cursor cursor) {
        idCPU = cursor.getInt(cursor.getColumnIndex(CPUDao.COLUMN_ID_CPU));
        name = cursor.getString(cursor.getColumnIndex(CPUDao.COLUMN_NAME));
        frequency = cursor.getInt(cursor.getColumnIndex(CPUDao.COLUMN_FREQUENCY));
    }

    public int getIdCPU() {
        return idCPU;
    }

    public String getName() {
        return name;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public int getType() {
        return CPU_TYPE;
    }

    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        //values.put(COLUMN_ID_CPU, getIdCPU());
        values.put(CPUDao.COLUMN_NAME, getName());
        values.put(CPUDao.COLUMN_FREQUENCY, getFrequency());
        return values;
    }
}
