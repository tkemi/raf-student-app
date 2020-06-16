package com.example.project2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project2.R;
import com.example.project2.model.ScheduleItem;
import com.example.project2.repository.db.entity.ScheduleItemEntity;
import com.example.project2.util.ScheduleDiffCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DiffUtil.Callback;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class  ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {

    private List<ScheduleItemEntity> mScheduleDataSet;
    private onClickProfesorName onClickProfesorName;

    public ScheduleAdapter() {
        mScheduleDataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.schedule_list_item_view, parent, false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        ScheduleItemEntity scheduleItem = mScheduleDataSet.get(position);
        holder.tvItemName.setText(String.format("%s - %s",scheduleItem.getClassName(),scheduleItem.getClassType()));
        holder.tvItemProfessor.setText(scheduleItem.getProfessor());
        holder.tvItemClassroom.setText(scheduleItem.getClassroom());
        holder.tvItemGroups.setText(scheduleItem.getGroups());
        holder.tvItemDay.setText(scheduleItem.getDay());
        holder.tvItemTime.setText(scheduleItem.getTime());
    }

    @Override
    public int getItemCount() {
        return mScheduleDataSet.size();
    }

    public void setData(List<ScheduleItemEntity> scheduleItemsEntity) {
        ScheduleDiffCallback callback = new ScheduleDiffCallback(mScheduleDataSet,scheduleItemsEntity);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        mScheduleDataSet.clear();
        mScheduleDataSet.addAll(scheduleItemsEntity);
        result.dispatchUpdatesTo(this);
    }

    public class ScheduleHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.tv_list_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_list_item_professor)
        TextView tvItemProfessor;
        @BindView(R.id.tv_list_item_classroom)
        TextView tvItemClassroom;
        @BindView(R.id.tv_list_item_groups)
        TextView tvItemGroups;
        @BindView(R.id.tv_list_item_day)
        TextView tvItemDay;
        @BindView(R.id.tv_list_item_time)
        TextView tvItemTime;

        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            this.tvItemProfessor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){

                        ScheduleItemEntity l =  mScheduleDataSet.get(position);
                        onClickProfesorName.onProfesorName(l.getProfessor());
                    }
                }
            });
        }
    }

    public void setOnClickProfesorName(ScheduleAdapter.onClickProfesorName onClickProfesorName) {
        this.onClickProfesorName = onClickProfesorName;
    }

    public interface onClickProfesorName {
        void onProfesorName(String professor);
    }

}
