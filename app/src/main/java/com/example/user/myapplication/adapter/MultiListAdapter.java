package com.example.user.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.user.myapplication.R;
import com.example.user.myapplication.holder.CPUHolder;
import com.example.user.myapplication.holder.MotherBoardHolder;
import com.example.user.myapplication.holder.ViewHolderBind;
import com.example.user.myapplication.model.MultiModel;

import java.util.List;

public class MultiListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MultiModel> multiModelList;

    public MultiListAdapter(List<MultiModel> multiModelList) {
        this.multiModelList = multiModelList;
    }

    @Override
    public int getItemViewType(int position) {
        return multiModelList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MultiModel.CPU_TYPE:
                return new CPUHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cpu_item, parent,false));
            case MultiModel.MOTHER_BOARD_TYPE:
                return new MotherBoardHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mother_board_item, parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderBind)holder).bind(multiModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return multiModelList.size();
    }
}
