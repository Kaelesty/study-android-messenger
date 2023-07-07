package com.kaelesty.chatx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView buttonResetPassword;
    private TextView buttonRegister;

    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        observeViewModel();
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
    }

    private void observeViewModel() {
        viewModel.getAuthentification().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean authentification) {
                if (authentification != null) {
                    if (authentification) {
                        startActivity(MainActivity.newIntent(LoginActivity.this));
                    }
                }
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private LoginInput getUserInput() {
        return new LoginInput(editTextEmail.getText().toString(), editTextPassword.getText().toString());
    }

    private void login() {
        viewModel.login(getUserInput());
    }

    private void resetPassword() {
        startActivity(ResetPasswordActivity.newIntent(this, getUserInput()));
    }

    private void register() {
        startActivity(RegisterActivity.newIntent(this));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}