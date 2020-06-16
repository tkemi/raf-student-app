package com.example.project2.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project2.R;
import com.example.project2.activity.ChatActivity;
import com.example.project2.adapter.ChatAdapter;
import com.example.project2.model.User;
import com.example.project2.viewmodel.ChatViewModel;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatFragment extends Fragment {

    private static final  String USERNAME_KEY ="userNameKey";
    private static final String INDEX_ID_KEY ="indexIdKey";

    @BindView(R.id.chatRecycleView)
    RecyclerView chatRecycleView;

    private ChatAdapter chatAdapter;
    private ChatViewModel chatViewModel;

    public static ChatFragment newInstance(){
        ChatFragment chatFragment = new ChatFragment();
        return chatFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        ButterKnife.bind(this,view);
        init(view);

        observers();

        return view;
    };

    private void init(View view){

        this.chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

        chatAdapter = new ChatAdapter();

        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        this.chatRecycleView.setLayoutManager(layout);
        this.chatRecycleView.setAdapter(chatAdapter);



    }

    private void observers(){

        this.chatViewModel.getUsersLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                chatAdapter.setData(users);
            }
        });

        this.chatAdapter.setOnClickOnuser(new ChatAdapter.onClickOnuser() {
            @Override
            public void onUserClick(String index) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);

                String username = sharedPreferences.getString(USERNAME_KEY,null);
                String messageIndex = sharedPreferences.getString(INDEX_ID_KEY,null);

                String id;

                int compare = messageIndex.compareTo(index);

                if(compare > 0){
                    id = messageIndex + index;
                }
                else{
                    id = index + messageIndex;
                }

                intent.putExtra("idOfChat",id);
                startActivity(intent);

            }
        });

    }
}
