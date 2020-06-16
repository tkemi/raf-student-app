package com.example.project2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project2.model.WallMessage;
import com.example.project2.R;
import com.example.project2.util.WallDiffCallback;
import com.example.project2.model.WallMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WallAdapter extends RecyclerView.Adapter<WallAdapter.WallHolder> {

    private List<WallMessage> data;

    public WallAdapter(){
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public WallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_wall,parent,false);
        return new WallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallHolder holder, int position) {

        WallMessage wallMessage = data.get(position);

        holder.name.setText(wallMessage.getName());

        holder.date.setText(wallMessage.getDate());

        if(wallMessage.getMeesage() == null) {
           holder.text.setVisibility(View.GONE);
            Picasso.get().load(wallMessage.getUrl()).into(holder.image);
        }else{
            holder.text.setText(wallMessage.getMeesage());
            holder.image.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<WallMessage> newList){
        WallDiffCallback callback = new WallDiffCallback(data,newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        data.clear();
        data.addAll(newList);
        result.dispatchUpdatesTo(this);
    }

    public class WallHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.nameWall)
    TextView name;
    @BindView(R.id.dateWall)
    TextView date;
    @BindView(R.id.textWall)
    TextView text;
    @BindView(R.id.imageWall)
    ImageView image;

    public WallHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}

}
