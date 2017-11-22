package com.example.user.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MotherBoardPictAdapter extends RecyclerView.Adapter<MotherBoardPictAdapter.MbViewHolder>{

    private List<String> stringList;
    private Context context;

    public MotherBoardPictAdapter(List<String> stringList, Context context) {
        this.stringList = stringList;
        this.context = context;
    }

    @Override
    public MbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MbViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mb_pict_item, parent,false));
    }

    @Override
    public void onBindViewHolder(MbViewHolder holder, int position) {
        holder.tvStr.setText(stringList.get(position));
        Picasso.with(context).load(stringList.get(position)).resize(300,300).into(holder.ivPic);
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
