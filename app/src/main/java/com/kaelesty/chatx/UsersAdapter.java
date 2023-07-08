package com.kaelesty.chatx;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private List<User> users = new ArrayList<>();

    private OnUserClickListener onUserClickListener;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.user_item,
                parent,
                false
        );
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = users.get(position);
        String info = user.getName() + " " + user.getLastname() + ", " + user.getAge();
        int status;
        if (user.isOnline()) {
            status = R.drawable.circle_green;
        }
        else {
            status = R.drawable.circle_red;
        }
        holder.viewIsOnline.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), status));
        holder.textViewUserInfo.setText(info);
        if (onUserClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUserClickListener.onUserClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnUserClickListener {
        void onUserClick();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUserInfo;
        private View viewIsOnline;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            viewIsOnline = itemView.findViewById(R.id.viewIsOnline);
            textViewUserInfo = itemView.findViewById(R.id.textViewUserInfo);
        }
    }
}
