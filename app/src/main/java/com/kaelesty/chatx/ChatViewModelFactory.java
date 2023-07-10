package com.kaelesty.chatx;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ChatViewModelFactory implements ViewModelProvider.Factory {

    private String currentUserID;
    private String otherUserID;

    public ChatViewModelFactory(@NonNull String currentUserID, @NonNull String otherUserID) {
        this.currentUserID = currentUserID;
        this.otherUserID = otherUserID;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChatViewModel(currentUserID, otherUserID);
    }
}
