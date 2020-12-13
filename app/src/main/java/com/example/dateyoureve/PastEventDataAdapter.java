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

public class PastEventDataAdapter extends RecyclerView.Adapter<PastEventDataAdapter.ViewHolder> {
    List<PastEventData> pastEventData;
    PastEventsList context;

    public PastEventDataAdapter(List<PastEventData> pastEventData, PastEventsList pastEventsList) {
        this.pastEventData = pastEventData;
        this.context=pastEventsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.past_events_card_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final PastEventData pastEventDataList = pastEventData.get(position);
        holder.titleView.setText(pastEventDataList.getTitle());
        holder.date.setText(pastEventDataList.getDate());
        holder.interested.setText(String.valueOf(pastEventDataList.getInterested_count()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,InterestedUsersList.class);
                context.startActivity(intent);
            }
    });
    }

    @Override
    public int getItemCount() {
        return pastEventData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView,date,interested;


        public ViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.titleView);
            date= itemView.findViewById(R.id.date);
            interested=itemView.findViewById(R.id.intrested);
        }
    }
}
