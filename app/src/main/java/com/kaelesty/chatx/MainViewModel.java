package com.kaelesty.chatx;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class MainViewModel extends AndroidViewModel {

    private FirebaseAuth auth;

    private MutableLiveData<Boolean> isUserAuth = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                isUserAuth.postValue(firebaseAuth.getCurrentUser() != null);
            }
        });
    }

    public LiveData<Boolean> getIsUserAuth() {
        return isUserAuth;
    }

    public void signOut() {
        auth.signOut();
    }

    public String getUserEmail() {
        return auth.getCurrentUser().getEmail();
    }
}
