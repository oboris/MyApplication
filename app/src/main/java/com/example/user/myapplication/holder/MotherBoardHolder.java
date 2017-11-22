package com.example.user.myapplication.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.adapter.MotherBoardPictAdapter;
import com.example.user.myapplication.model.MotherBoard;
import com.example.user.myapplication.model.MultiModel;

import java.util.List;

public class MotherBoardHolder extends RecyclerView.ViewHolder implements ViewHolderBind {
    private TextView tvName;
    private TextView tvChipSet;
    private RecyclerView rvPict;

    public MotherBoardHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.mb_name);
        tvChipSet = itemView.findViewById(R.id.mb_chip_set);
        rvPict = itemView.findViewById(R.id.mb_pict);
    }

    @Override
    public void bind(MultiModel model) {
        tvName.setText(((MotherBoard)model).getName());
        tvChipSet.setText(((MotherBoard)model).getChipSet());
        initList(((MotherBoard)model).getPhotos());
    }

    private void initList(List<String> stringList){
        rvPict.setHasFixedSize(true);
        MotherBoardPictAdapter mbPictAdapter = new MotherBoardPictAdapter(stringList, rvPict.getContext());
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rvPict.getContext(), LinearLayoutManager.HORIZONTAL, false);
//        rvPict.setLayoutManager(layoutManager);
        rvPict.setAdapter(mbPictAdapter);
    }
}
