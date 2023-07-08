package com.kaelesty.chatx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.checkAuth();

        viewModel.getIsAuth().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean auth) {
                if (auth) {
                    startActivity(UsersActivity.newIntent(MainActivity.this));
                }
                else {
                    startActivity(LoginActivity.newIntent(MainActivity.this));
                }
                finish();
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}