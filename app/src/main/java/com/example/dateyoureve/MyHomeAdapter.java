package com.example.dateyoureve;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dateyoureve.ui.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MyHomeAdapter extends RecyclerView.Adapter<MyHomeAdapter.ViewHolder> implements Filterable {

    List<MyHomeData> myHomeData;
    List<MyHomeData> myHomeDataFull;
    HomeFragment context;
    public MyHomeAdapter(List<MyHomeData> myHomeData, HomeFragment homeFragment) {
        this.myHomeData = myHomeData;
        myHomeDataFull = new ArrayList<>(myHomeData);
        this.context = homeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.my_home_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyHomeData myHomeDataList = myHomeData.get(position);
        holder.titleView.setText(myHomeDataList.getTitle());
        holder.descriptionView.setText(myHomeDataList.getDescription());
        holder.dateView.setText(myHomeDataList.getDate());
        holder.venueView.setText(myHomeDataList.getVenue());
        holder.eventImage.setImageResource(myHomeDataList.getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getContext(), EventDetails.class);
                intent.putExtra("title", myHomeDataList.getTitle());
                intent.putExtra("decs", myHomeDataList.getDescription());
                intent.putExtra("date", myHomeDataList.getDate());
                intent.putExtra("venue", myHomeDataList.getVenue());
                intent.putExtra("image", myHomeDataList.getImage());
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
            eventImage = itemView.findViewById(R.id.eventView);
            titleView = itemView.findViewById(R.id.titleView);
            descriptionView = itemView.findViewById(R.id.descView);
            dateView = itemView.findViewById(R.id.dateView);
            venueView = itemView.findViewById(R.id.venueView);
        }
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase().trim();
                List<MyHomeData> contactListFiltered = new ArrayList<MyHomeData>();
                if (charSequence == null || charSequence.length() == 0) {
                    contactListFiltered.addAll(myHomeDataFull);
                } else {
                    for (MyHomeData row : myHomeDataFull) {
                        if ((row.getTitle().toLowerCase()).contains(charString)) {
                            contactListFiltered.add(new MyHomeData(row.getTitle(),row.getDescription(),row.getDate(),row.getVenue(),row.getImage()));
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                myHomeData.clear();
                myHomeData.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
