package com.example.user.myapplication.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.model.CPU;
import com.example.user.myapplication.model.MultiModel;

import java.util.Locale;

public class CPUHolder extends RecyclerView.ViewHolder implements ViewHolderBind {

    private TextView tvName;
    private TextView tvFrequency;

    public CPUHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.cpu_name);
        tvFrequency = itemView.findViewById(R.id.cpu_frequency);
    }

    @Override
    public void bind(MultiModel model) {
        tvName.setText(((CPU)model).getName());
        tvFrequency.setText(String.format(Locale.getDefault(),"%d", ((CPU) model).getFrequency()));
    }
}
