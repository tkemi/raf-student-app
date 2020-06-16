package com.example.project2.holders;

import android.view.View;
import android.widget.TextView;

import com.example.project2.R;
import com.example.project2.model.Message;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ResponseHolder extends  AbstractMessageHolder {

    @BindView(R.id.dateResponseInChat)
    TextView date;
    @BindView(R.id.messageResponseInChat)
    TextView message;

    public ResponseHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.date = itemView.findViewById(R.id.dateResponseInChat);
        this.message = itemView.findViewById(R.id.messageResponseInChat);

    }

    @Override
    public void bind(Message message) {
        this.date.setText(message.getDate());
        this.message.setText(message.getText());
    }
}
