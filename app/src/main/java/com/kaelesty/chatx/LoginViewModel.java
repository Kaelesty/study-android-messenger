package com.kaelesty.chatx;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginViewModel extends AndroidViewModel {

    private final FirebaseAuth auth;
    private FirebaseDatabase db;

    private final MutableLiveData<FirebaseUser> authentification = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        authentification.postValue(null);
        db = FirebaseDatabase.getInstance();
    }

    public LiveData<FirebaseUser> getAuthentification() {
        return authentification;
    }

    public LiveData<String> getError() { return error; }

    public void login(LoginInput user) {
        if (!user.validate()) {
            error.postValue("Invalid input");
            return;
        }
        auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authentification.postValue(authResult.getUser());

                        DatabaseReference dbRef = db.getReference();

                        FirebaseUser firebaseUser = authResult.getUser();
                        if (firebaseUser == null) {
                            return;
                        }

                        dbRef.child("Users").child(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                if (user == null) {
                                    return;
                                }
                                user.setIsOnline(true);
                                dbRef.child("Users").child(firebaseUser.getUid()).setValue(user);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        authentification.postValue(null);
                        error.postValue(e.toString());
                    }
                });
    }
}
