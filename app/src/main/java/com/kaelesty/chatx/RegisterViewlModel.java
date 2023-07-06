package com.kaelesty.chatx;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterViewlModel extends AndroidViewModel {

    private FirebaseAuth auth;

    private MutableLiveData<Boolean> isAuthSuccessful = new MutableLiveData<>();

    public RegisterViewlModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void register(RegisterInput user) {
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        isAuthSuccessful.postValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        isAuthSuccessful.postValue(false);
                    }
                });
    }

    public LiveData<Boolean> getIsAuthSuccessful() {
        return isAuthSuccessful;
    }
}
