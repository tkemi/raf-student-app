package com.example.project2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;

import com.example.project2.R;
import com.example.project2.adapter.ScheduleAdapter;
import com.example.project2.model.ScheduleItemFilter;
import com.example.project2.repository.db.entity.ScheduleItemEntity;
import com.example.project2.viewmodel.ScheduleViewModel;

import java.util.List;

public class FilterActivity extends AppCompatActivity {


    @BindView(R.id.scheduleRecyclerViewActivity)
    RecyclerView recyclerView;

    private ScheduleViewModel scheduleViewModel;
    private ScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        everythingElse();
    }

    private void everythingElse(){

        this.adapter = new ScheduleAdapter();

        LinearLayoutManager layout = new LinearLayoutManager(FilterActivity.this);
        this.recyclerView.setLayoutManager(layout);
        this.recyclerView.setAdapter(adapter);

        this.scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);

        String professor = getIntent().getExtras().getString("professor");

        ScheduleItemFilter sc = new ScheduleItemFilter();
        sc.setClassNameOrProfessor(professor);
        sc.setDay("");
        sc.setGroup("");

        this.scheduleViewModel.setFilter(sc);

        this.scheduleViewModel.getFilteredScheduleItems().observe(this, new Observer<List<ScheduleItemEntity>>() {
            @Override
            public void onChanged(List<ScheduleItemEntity> scheduleEntities) {
                adapter.setData(scheduleEntities);
            }
        });


    }
}
