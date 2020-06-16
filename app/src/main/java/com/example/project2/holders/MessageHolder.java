package com.example.project2.holders;

import android.view.View;
import android.widget.TextView;

import com.example.project2.R;
import com.example.project2.model.Message;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageHolder extends AbstractMessageHolder {
    @BindView(R.id.dateInChat)
    TextView date;
    @BindView(R.id.messageInChat)
    TextView message;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.date = itemView.findViewById(R.id.dateInChat);
        this.message = itemView.findViewById(R.id.messageInChat);


    }

    @Override
    public void bind(Message message) {
        this.date.setText(message.getDate());
        this.message.setText(message.getText());
    }
}
