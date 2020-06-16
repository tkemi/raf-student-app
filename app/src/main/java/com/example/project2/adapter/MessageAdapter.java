package com.example.project2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project2.R;
import com.example.project2.util.InChatDiffCallback;
import com.example.project2.holders.AbstractMessageHolder;
import com.example.project2.holders.MessageHolder;
import com.example.project2.holders.ResponseHolder;
import com.example.project2.model.Message;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<AbstractMessageHolder> {

    private List<Message> data;

    public MessageAdapter(){
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public AbstractMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){

            case Message.TYPE_RESPONSE: {
                View view = inflater.inflate(R.layout.list_item_response_message,parent,false);
                return new ResponseHolder(view);
            }
            case Message.TYPE_MESSAGE: {
                View view = inflater.inflate(R.layout.list_item_message,parent,false);
                return new MessageHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractMessageHolder holder, int position) {
        Message message = data.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int getItemViewType(int position){
        Message message = data.get(position);
        return message.getType();
    }

    public void setData(List<Message> newList){
        InChatDiffCallback callback = new InChatDiffCallback(data,newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        data.clear();
        data.addAll(newList);
        result.dispatchUpdatesTo(this);

    }

}
