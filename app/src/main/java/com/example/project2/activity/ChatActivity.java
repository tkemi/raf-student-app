package com.example.project2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.project2.R;
import com.example.project2.adapter.MessageAdapter;
import com.example.project2.model.Message;
import com.example.project2.repository.MessageRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final  String USERNAME_KEY ="userNameKey";
    private static final String INDEX_ID_KEY ="indexIdKey";

    @BindView(R.id.inChatRecycleView)
    RecyclerView messageRecyclerView;
    @BindView(R.id.messageTextInChat)
    TextInputEditText messageEditText;
    @BindView(R.id.sendInChat)
    MaterialButton sendBtn;

    private String id;
    private MessageAdapter messageAdapter;
    private MessageRepository messageRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        init();

        observers();
    }

    private void init(){

        this.messageAdapter = new MessageAdapter();

        id = getIntent().getExtras().getString("idOfChat");


        this.messageRepository = new MessageRepository(getApplication(),id);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplication());
        this.messageRecyclerView.setLayoutManager(layoutManager);

        this.messageRecyclerView.setAdapter(this.messageAdapter);


        messageRecyclerView.scrollToPosition(messageAdapter.getItemCount()-1);

    }

    private void observers(){

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(getApplication().getPackageName(), Context.MODE_PRIVATE);

        String username = sharedPreferences.getString(USERNAME_KEY,null);
        String index = sharedPreferences.getString(INDEX_ID_KEY,null);



        sendBtn.setOnClickListener( e -> {

            Message message = new Message();
            message.setText(messageEditText.getText().toString());

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            message.setDate(dateFormat.format(date));

            message.setSenderIndex(index);



            messageRepository.addMessage(id,message);

            messageEditText.getText().clear();

            messageRecyclerView.scrollToPosition(messageAdapter.getItemCount()-1);

        });

        this.messageRepository.getMessageLiveData().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messageAdapter.setData(messages);
            }
        });

    }

}
