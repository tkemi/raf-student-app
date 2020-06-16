package com.example.project2.holders;

import android.view.View;

import com.example.project2.model.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractMessageHolder extends RecyclerView.ViewHolder {

    public AbstractMessageHolder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract void bind(Message message);
}
