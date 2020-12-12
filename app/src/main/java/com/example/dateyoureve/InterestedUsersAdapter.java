package com.example.dateyoureve;

import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.List;

public class InterestedUsersAdapter extends RecyclerView.Adapter<InterestedUsersAdapter.ViewHolder> {
    List<User> interestedUsersData;
    InterestedUsersList context;

    public InterestedUsersAdapter(List<User> interestedUsersData, InterestedUsersList interestedUsersList) {
        this.interestedUsersData = interestedUsersData;
        this.context=interestedUsersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.interested_users_card_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final User interestedUsersList = interestedUsersData.get(position);
        holder.name.setText(interestedUsersList.getUserName());
        holder.email.setText(interestedUsersList.getEmail());
        holder.phone.setText(interestedUsersList.getPhone());

    }

    @Override
    public int getItemCount() {
        return interestedUsersData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email,phone;


        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email= itemView.findViewById(R.id.email);
            phone=itemView.findViewById(R.id.phone);
        }
    }
}
