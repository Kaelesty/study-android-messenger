package com.kaelesty.chatx;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordViewModel extends AndroidViewModel {

    private FirebaseAuth auth;

    private MutableLiveData<Boolean> isResetSuccessful = new MutableLiveData<>();

    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void resetPassword(EmailInput user) {
        auth.sendPasswordResetEmail(user.getEmail())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        isResetSuccessful.postValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        isResetSuccessful.postValue(false);
                    }
                })
        ;
    }

    public LiveData<Boolean> getIsResetSuccessful() {
        return isResetSuccessful;
    }
}
