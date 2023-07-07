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
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {

    public static final String EXTRA_EMAIL = "email";

    private EditText editTextEmail;
    private Button buttonReset;

    ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initViews();

        String email = getIntent().getStringExtra(EXTRA_EMAIL);
        if (email != null) {
            editTextEmail.setText(email);
        }

        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        viewModel.getIsResetSuccessful().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isResetSuccessful) {
                if (isResetSuccessful) {
                    Toast.makeText(ResetPasswordActivity.this, "Check your email", Toast.LENGTH_LONG).show();
                    startActivity(LoginActivity.newIntent(getApplication()));
                }
                else {
                    Toast.makeText(ResetPasswordActivity.this, "Reset error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void resetPassword() {
        EmailInput user = getEmailInput();
        if (!user.validate()) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_LONG).show();
            return;
        }
        viewModel.resetPassword(user);
    }

    private EmailInput getEmailInput() {
        return new EmailInput(editTextEmail.getText().toString());
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonReset = findViewById(R.id.buttonResetPassword);
    }

    public static Intent newIntent(Context context, LoginInput user) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra(EXTRA_EMAIL, user.getEmail());
        return intent;
    }
}