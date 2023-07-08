package com.kaelesty.chatx;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersViewModel extends AndroidViewModel {

    public final static String TAG = "UsersViewModel";

    private FirebaseAuth auth;
    private FirebaseDatabase db;

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<List<User>> users = new MutableLiveData<>();

    public UsersViewModel(@NonNull Application application) {
        super(application);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user.postValue(firebaseAuth.getCurrentUser());
            }
        });

        db.getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> usersList = new ArrayList<>();
                for (DataSnapshot dataSnapshotChildren : snapshot.getChildren()) {
                    User user = dataSnapshotChildren.getValue(User.class);
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (user == null || firebaseUser == null) {
                        continue;
                    }
                    if (Objects.equals(user.getId(), firebaseUser.getUid())) {
                        continue;
                    }
                    // Log.d(TAG, user.toString());
                    usersList.add(user);
                }
                users.postValue(usersList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void signOut() {
        auth.signOut();
    }
}
