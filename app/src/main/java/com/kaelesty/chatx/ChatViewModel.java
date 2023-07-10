package com.kaelesty.chatx;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {

    private MutableLiveData<List<Message>> messages = new MutableLiveData<>();
    private MutableLiveData<User> otherUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> messageSent = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    private String currentUserID;
    private String otherUserID;

    FirebaseDatabase db;

    public ChatViewModel(String currentUserID, String otherUserID) {
        this.currentUserID = currentUserID;
        this.otherUserID = otherUserID;

        db = FirebaseDatabase.getInstance();
        DatabaseReference dbRefUsers = db.getReference("Users");
        dbRefUsers.child(otherUserID).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                User otherUserFromDB = dataSnapshot.getValue(User.class);
                otherUser.postValue(otherUserFromDB);
            }
        });
        DatabaseReference dbRefMessages = db.getReference("Messages");
        dbRefMessages.child(currentUserID).child(otherUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Message> messageList = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Message message = child.getValue(Message.class);
                    messageList.add(message);
                }
                messages.postValue(messageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError dbError) {
                error.postValue(dbError.getDetails());
            }
        });

        dbRefUsers.child(otherUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User otherUserFromDB = snapshot.getValue(User.class);
                otherUser.postValue(otherUserFromDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //
            }
        });
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public LiveData<User> getOtherUser() {
        return otherUser;
    }

    public LiveData<Boolean> getMessageSent() {
        return messageSent;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void setIsOnline(boolean isOnline) {
        db.getReference("Users")
                .child(currentUserID)
                .child("online")
                .setValue(isOnline);
    }

    public void sendMessage(String text) {
        Message message = new Message(
                text,
                currentUserID,
                otherUserID
        );
        DatabaseReference dbRef = db.getReference("Messages");
        dbRef.child(currentUserID).child(otherUserID).push().setValue(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dbRef.child(otherUserID).child(currentUserID).push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                messageSent.postValue(true);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                error.postValue(e.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        error.postValue(e.toString());
                    }
                });
    }


}
