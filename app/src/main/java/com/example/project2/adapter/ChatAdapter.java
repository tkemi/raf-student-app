package com.example.project2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project2.R;
import com.example.project2.util.ChatDiffCallback;
import com.example.project2.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    private List<User> data;
    private onClickOnuser onClickOnuser;

    public ChatAdapter(){
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_chat,parent,false);
        return new ChatHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {

        User user = data.get(position);
        holder.name.setText(user.getName());

        Picasso.get().load("https://picsum.photos/1080/1920/?random").into(holder.image);



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<User> users){
        ChatDiffCallback callback = new ChatDiffCallback(data,users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        data.clear();
        data.addAll(users);
        result.dispatchUpdatesTo(this);
    }


    public class ChatHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.chatImageView)
    ImageView image;
    @BindView(R.id.chatName)
    TextView name;

    public ChatHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

        this.itemView.setOnClickListener( e -> {
            int position = getAdapterPosition();

            if(position != RecyclerView.NO_POSITION){
                User user = data.get(position);
                onClickOnuser.onUserClick(user.getIndexId());

            }

        });

    }
}

    public void setOnClickOnuser(ChatAdapter.onClickOnuser onClickOnuser) {
        this.onClickOnuser = onClickOnuser;
    }

    public interface onClickOnuser{
    void onUserClick(String index);
}

}
