package com.example.dateyoureve;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dateyoureve.ui.RecentFragment;

import java.util.ArrayList;
import java.util.List;

public class RegisteredEventsAdapter extends RecyclerView.Adapter<RegisteredEventsAdapter.ViewHolder> {
    List<MyHomeData> myHomeData;
    List<MyHomeData> myHomeDataFull;
    RecentFragment context;
    AlertDialog.Builder builder;
    public RegisteredEventsAdapter(List<MyHomeData> myHomeData, RecentFragment recentFragment) {
        this.myHomeData = myHomeData;
        this.myHomeDataFull = new ArrayList<>(myHomeData);
        this.context = recentFragment;
    }

    @NonNull
    @Override
    public RegisteredEventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.register_ui_design, parent, false);
        RegisteredEventsAdapter.ViewHolder viewHolder = new RegisteredEventsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RegisteredEventsAdapter.ViewHolder holder, int position) {
        final MyHomeData myHomeDataList = myHomeData.get(position);
        holder.titleView.setText(myHomeDataList.getTitle());
        holder.descriptionView.setText(myHomeDataList.getDescription());
        holder.dateView.setText(myHomeDataList.getDate());
        holder.venueView.setText(myHomeDataList.getVenue());
        Glide.with(context)
                .load(myHomeDataList.getImage())
                .into(holder.eventImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getContext(), EventDetails.class);
                intent.putExtra("title", myHomeDataList.getTitle());
                intent.putExtra("decs", myHomeDataList.getDescription());
                intent.putExtra("date", myHomeDataList.getDate() + " || " + myHomeDataList.getTime());
                intent.putExtra("venue", myHomeDataList.getVenue());
                intent.putExtra("image", myHomeDataList.getImage());
                intent.putExtra("note", myHomeDataList.getNotes());
                intent.putExtra("mode", myHomeDataList.getMode());
                intent.putExtra("eventId", myHomeDataList.getEventId());
                intent.putExtra("phone", myHomeDataList.getPhone());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myHomeData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImage;
        TextView titleView, descriptionView, dateView, venueView;
        public ViewHolder(View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.eventFavRegister);
            titleView = itemView.findViewById(R.id.titleTextRegister);
            descriptionView = itemView.findViewById(R.id.desTextRegister);
            dateView = itemView.findViewById(R.id.dateTextRegister);
            venueView = itemView.findViewById(R.id.venueTextRegister);
        }
    }
}
