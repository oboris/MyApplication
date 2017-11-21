package com.example.user.myapplication.model;

import java.util.List;

public class MotherBoard implements MultiModel {

    private int idMotherBoard;
    private String name;
    private String chipSet;
    private List<String> photos;

    public MotherBoard(String name, String chipSet, List<String> photos) {
        this.name = name;
        this.chipSet = chipSet;
        this.photos = photos;
    }

    public MotherBoard(int idMotherBoard, String name, String chipSet, List<String> photos) {
        this.idMotherBoard = idMotherBoard;
        this.name = name;
        this.chipSet = chipSet;
        this.photos = photos;
    }

    public int getIdMotherBoard() {
        return idMotherBoard;
    }

    public String getName() {
        return name;
    }

    public String getChipSet() {
        return chipSet;
    }

    public List<String> getPhotos() {
        return photos;
    }

    @Override
    public int getType() {
        return MOTHER_BOARD_TYPE;
    }
}
