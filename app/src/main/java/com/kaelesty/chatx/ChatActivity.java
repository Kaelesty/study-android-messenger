package com.kaelesty.chatx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel viewModel;

    private TextView textViewTitle;
    private View viewIsOnline;
    private RecyclerView recyclerViewChat;
    private EditText editTextMessage;
    private ImageView imageViewSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        initViews();
    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        viewIsOnline = findViewById(R.id.viewIsOnline);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageViewSend = findViewById(R.id.imageViewSend);
    }
}