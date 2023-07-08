package com.kaelesty.chatx;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class MainViewModel extends AndroidViewModel {

    private FirebaseAuth auth;

    private MutableLiveData<Boolean> isAuth = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public void checkAuth() {
        if (auth.getCurrentUser() == null) {
            isAuth.postValue(false);
        }
        else {
            isAuth.postValue(true);
        }
    }

    public LiveData<Boolean> getIsAuth() { return isAuth; }
}
