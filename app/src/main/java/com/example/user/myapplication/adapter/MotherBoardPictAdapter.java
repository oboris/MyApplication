package com.example.user.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.myapplication.R;

import java.util.List;

public class MotherBoardPictAdapter extends RecyclerView.Adapter<MotherBoardPictAdapter.MbViewHolder>{

    private List<String> stringList;

    public MotherBoardPictAdapter(List<String> stringList) {
        this.stringList = stringList;
    }

    @Override
    public MbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MbViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mb_pict_item, parent,false));
    }

    @Override
    public void onBindViewHolder(MbViewHolder holder, int position) {
        holder.tvStr.setText(stringList.get(position));
        //holder.ivPic.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    static class MbViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvStr;

        MbViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pict);
            tvStr = itemView.findViewById(R.id.mb_str);
        }
    }
}
