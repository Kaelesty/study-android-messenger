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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterViewlModel extends AndroidViewModel {

    private FirebaseAuth auth;
    private FirebaseDatabase db;

    private MutableLiveData<Boolean> isAuthSuccessful = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public RegisterViewlModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
    }

    public LiveData<String> getError() { return error; }

    public void register(RegisterInput input) {
        if (!input.validate()) {
            error.postValue("Invalid input");
            return;
        }
        auth.createUserWithEmailAndPassword(input.getEmail(), input.getPassword())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        DatabaseReference dbRef = db.getReference("Users");

                        User user = input.toUser();
                        FirebaseUser firebaseUser = authResult.getUser();
                        if (firebaseUser == null) {
                            return;
                        }
                        user.setId(firebaseUser.getUid());

                        dbRef.child(firebaseUser.getUid()).setValue(user);

                        isAuthSuccessful.postValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        error.postValue(e.toString());
                    }
                });
    }

    public LiveData<Boolean> getIsAuthSuccessful() {
        return isAuthSuccessful;
    }
}
