package com.example.project2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project2.R;
import com.example.project2.activity.FilterActivity;
import com.example.project2.adapter.ScheduleAdapter;
import com.example.project2.model.ScheduleItemFilter;
import com.example.project2.repository.db.entity.ScheduleItemEntity;
import com.example.project2.repository.web.model.Resource;
import com.example.project2.viewmodel.ScheduleViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleFragment extends Fragment {

    private ScheduleViewModel scheduleViewModel;
    private ScheduleAdapter scheduleAdapter;

    @BindView(R.id.spinner_schedule_fragment_group)
    Spinner groupSpinner;
    @BindView(R.id.spinner_schedule_fragment_day)
    Spinner daySpinner;
    @BindView(R.id.rv_schedule_fragment)
    RecyclerView recyclerView;
    @BindView(R.id.et_schedule_fragment_filter)
    TextInputEditText etFilter;
    @BindView(R.id.btn_search_schedule_fragment)
    MaterialButton btnSearch;

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, view);
        initViewModel();
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        initSpinners(view);
        initRecyclerView(view);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classNameOrProfessor = etFilter.getText().toString().toLowerCase();
                ScheduleItemFilter filter = new ScheduleItemFilter();
                filter.setClassNameOrProfessor(classNameOrProfessor);
                if (groupSpinner.getSelectedItem().equals("GROUPS")) {
                    filter.setGroup("");
                } else {
                    filter.setGroup(groupSpinner.getSelectedItem().toString());
                }
                if (daySpinner.getSelectedItem().equals("DAYS")) {
                    filter.setDay("");
                } else {
                    filter.setDay(daySpinner.getSelectedItem().toString());
                }

                scheduleViewModel.setFilter(filter);
            }
        });
    }

    private void initRecyclerView(View view) {
        scheduleAdapter = new ScheduleAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(scheduleAdapter);

        scheduleAdapter.setOnClickProfesorName(new ScheduleAdapter.onClickProfesorName() {
            @Override
            public void onProfesorName(String professor) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                intent.putExtra("professor", professor);
                startActivity(intent);
            }
        });
    }

    private void initSpinners(View view) {
        ArrayAdapter<CharSequence> groupSpinnerAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.groupsArray, android.R.layout.simple_spinner_dropdown_item);
        groupSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(groupSpinnerAdapter);

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String classNameOrProfessor = etFilter.getText().toString().toLowerCase();
                ScheduleItemFilter filter = new ScheduleItemFilter();
                filter.setClassNameOrProfessor(classNameOrProfessor);
                Log.e("SOADmsaod", "disajndsakjdksad");
                if (groupSpinner.getSelectedItem().equals("GROUPS")) {
                    filter.setGroup("");
                } else {
                    filter.setGroup(groupSpinner.getSelectedItem().toString());
                }
                if (daySpinner.getSelectedItem().equals("DAYS")) {
                    filter.setDay("");
                } else {
                    filter.setDay(daySpinner.getSelectedItem().toString());
                }

                scheduleViewModel.setFilter(filter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> daySpinnerAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.daysArray, android.R.layout.simple_spinner_dropdown_item);
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(daySpinnerAdapter);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String classNameOrProfessor = etFilter.getText().toString().toLowerCase();
                ScheduleItemFilter filter = new ScheduleItemFilter();
                filter.setClassNameOrProfessor(classNameOrProfessor);
                if (groupSpinner.getSelectedItem().equals("GROUPS")) {
                    filter.setGroup("");
                } else {
                    filter.setGroup(groupSpinner.getSelectedItem().toString());
                }
                if (daySpinner.getSelectedItem().equals("DAYS")) {
                    filter.setDay("");
                } else {
                    filter.setDay(daySpinner.getSelectedItem().toString());
                }
                scheduleViewModel.setFilter(filter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initViewModel() {
        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        scheduleViewModel.getSheduleItems().observe(this, new Observer<Resource<Void>>() {
            @Override
            public void onChanged(Resource<Void> resource) {
                showToast(resource.isSuccessful());
            }
        });
        scheduleViewModel.getFilteredScheduleItems().observe(this, new Observer<List<ScheduleItemEntity>>() {
            @Override
            public void onChanged(List<ScheduleItemEntity> scheduleItemEntities) {
                scheduleAdapter.setData(scheduleItemEntities);
            }
        });
        scheduleViewModel.getAllScheduleEntries().observe(this, new Observer<List<ScheduleItemEntity>>() {
            @Override
            public void onChanged(List<ScheduleItemEntity> scheduleItemEntities) {
                scheduleAdapter.setData(scheduleItemEntities);
            }
        });
    }

    private void showToast(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(this.getContext(), "Data successfully fetched", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getContext(), "Failed fetching data", Toast.LENGTH_LONG).show();
        }

    }

}
