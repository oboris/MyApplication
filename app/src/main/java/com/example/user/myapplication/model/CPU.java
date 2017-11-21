package com.example.user.myapplication.model;

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

    public CPU(int idCPU, String name, int frequency) {
        this.idCPU = idCPU;
        this.name = name;
        this.frequency = frequency;
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
}
