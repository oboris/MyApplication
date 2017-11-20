package com.example.user.myapplication.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

//@Entity
public class CPU implements MultiModel {

  //  @PrimaryKey(autoGenerate = true)
    private int idCPU;

    @SerializedName("FIRSTNAME")
    private String name;

    @SerializedName("RECORD_TYPE")
    private int frequency;

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

    public CPU(String name, int frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public CPU(Cursor cursor) {
        idCPU = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_CPU));
        name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        frequency = cursor.getInt(cursor.getColumnIndex(COLUMN_FREQUENCY));
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
        values.put(COLUMN_NAME, getName());
        values.put(COLUMN_FREQUENCY, getFrequency());
        return values;
    }
}
