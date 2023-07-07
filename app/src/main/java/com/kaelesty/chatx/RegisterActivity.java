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

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextLastname;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextAge;
    private Button buttonRegister;

    private RegisterViewlModel viewlModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        viewlModel = new ViewModelProvider(this).get(RegisterViewlModel.class);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        observeViewModel();
    }

    private void initViews() {
        editTextName = findViewById(R.id.editTextName);
        editTextLastname = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextAge = findViewById(R.id.editTextAge);
        buttonRegister = findViewById(R.id.buttonRegister);
    }

    private void observeViewModel() {
        viewlModel.getIsAuthSuccessful().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccessful) {
                if (isSuccessful != null) {
                    if (isSuccessful) {
                        startActivity(MainActivity.newIntent(getApplication()));
                    }
                }
            }
        });

        viewlModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_LONG);
            }
        });
    }

    private RegisterInput getRegisterInput() {
        return new RegisterInput(
                editTextName.getText().toString(),
                editTextLastname.getText().toString(),
                editTextPassword.getText().toString(),
                editTextEmail.getText().toString(),
                Integer.parseInt(editTextAge.getText().toString())
        );
    }

    private void register() {
        viewlModel.register(getRegisterInput());
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }
}