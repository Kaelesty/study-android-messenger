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

        TextView tw = findViewById(R.id.textView);

        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.signOut();
            }
        });

        viewModel.getIsUserAuth().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isAuth) {
                if (isAuth) {
                    tw.setText("User " + viewModel.getUserEmail());
                }
                else {
                    startActivity(LoginActivity.newIntent(getApplication()));
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}