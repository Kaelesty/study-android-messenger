package com.kaelesty.chatx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UsersActivity extends AppCompatActivity {

    public static final String TAG = "UsersActivity";

    public static final String CURRENT_USER_EXTRA = "current";

    private UsersViewModel viewModel;

    private RecyclerView recyclerViewUsers;
    private UsersAdapter adapter;

    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        adapter = new UsersAdapter();

        initViews();
        observeViewModel();
        unpackExtras();

        adapter.setOnUserClickListener(new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User otherUser) {
                Intent intent = ChatActivity.newIntent(
                        UsersActivity.this,
                        currentUserID,
                        otherUser.getId());
                startActivity(intent);
            }
        });

        recyclerViewUsers.setAdapter(adapter);
    }

    private void unpackExtras() {
        Intent intent = getIntent();
        currentUserID = intent.getStringExtra(CURRENT_USER_EXTRA);
    }

    private void initViews() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
    }

    private void observeViewModel() {
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    startActivity(MainActivity.newIntent(UsersActivity.this));
                    finish();
                }
            }
        });
        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for (User user : users) {
                    Log.d(TAG, user.toString());
                }
                adapter.setUsers(users);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.users_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemSignout) {
            viewModel.signOut();
            viewModel.setIsOnline(false);
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context, String currentUserID) {
        Intent intent = new Intent(context, UsersActivity.class);
        intent.putExtra(CURRENT_USER_EXTRA, currentUserID);
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