package com.kaelesty.chatx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    public static final String TAG = "ChatActivity";

    public static final String CURRENT_USER_EXTRA = "current";
    public static final String OTHER_USER_EXTRA = "other";

    private ChatViewModel viewModel;

    private TextView textViewTitle;
    private View viewIsOnline;
    private RecyclerView recyclerViewChat;
    private EditText editTextMessage;
    private ImageView imageViewSend;

    private MessagesAdapter adapter;

    private String currentUserID;
    private String otherUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        unpackExtras();
        initViews();

        viewModel = new ViewModelProvider(
                this,
                new ChatViewModelFactory(currentUserID, otherUserID))
                .get(ChatViewModel.class);

        adapter = new MessagesAdapter(currentUserID);

        observeViewModel();
        recyclerViewChat.setAdapter(adapter);
    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        viewIsOnline = findViewById(R.id.viewIsOnline);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageViewSend = findViewById(R.id.imageViewSend);
        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.sendMessage(editTextMessage.getText().toString());
            }
        });
    }

    private void unpackExtras() {
        Intent intent = getIntent();
        currentUserID = intent.getStringExtra(CURRENT_USER_EXTRA);
        otherUserID = intent.getStringExtra(OTHER_USER_EXTRA);
    }

    private void observeViewModel() {
        viewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User otherUser) {
                textViewTitle.setText(otherUser.toTitle());
                int backgroundID;
                if (otherUser.isOnline()) {
                    backgroundID = R.drawable.circle_green;
                }
                else {
                    backgroundID = R.drawable.circle_red;
                }
                viewIsOnline.setBackground(ContextCompat.getDrawable(ChatActivity.this, backgroundID));
            }
        });
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ChatActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
        viewModel.getMessageSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSent) {
                if (isSent) {
                    editTextMessage.setText("");
                }
            }
        });
        viewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                adapter.setMessages(messages);
            }
        });
    }

    public static Intent newIntent(Context context, String currentUserID, String otherUserID) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(CURRENT_USER_EXTRA, currentUserID);
        intent.putExtra(OTHER_USER_EXTRA, otherUserID);
        return intent;
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setIsOnline(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setIsOnline(true);
    }
}