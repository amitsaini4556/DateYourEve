package com.example.dateyoureve;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dateyoureve.ui.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class MyHomeAdapter extends RecyclerView.Adapter<MyHomeAdapter.ViewHolder> implements Filterable {
    List<MyHomeData> myHomeData;
    List<MyHomeData> myHomeDataFull;
    HomeFragment context;
    FirebaseStorage storage  = FirebaseStorage.getInstance();
    public MyHomeAdapter(List<MyHomeData> myHomeData, HomeFragment homeFragment) {
        this.myHomeData = myHomeData;
        this.myHomeDataFull = new ArrayList<>(myHomeData);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
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
        holder.itemView.findViewById(R.id.shareButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,myHomeDataList.getTitle());
                String stringMessage = myHomeDataList.getTitle()  + "\n\n" + myHomeDataList.getDescription() +"\n\n Date :" + myHomeDataList.getDate() +"\n\n Venue :" +myHomeDataList.getVenue() +"\n\n";
                stringMessage = stringMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                shareIntent.putExtra(Intent.EXTRA_TEXT, stringMessage);
                context.startActivity(Intent.createChooser(shareIntent,"Share via"));
            }
        });
        holder.itemView.findViewById(R.id.favButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
                String path = "users/" + mAuth.getUid() + "/FavEvents";
                DatabaseReference userData = FirebaseDatabase.getInstance().getReference(path);
                EventIdObj eventIdObj = new EventIdObj(myHomeDataList.getEventId());
                userData.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(myHomeDataList.getEventId()))
                        {
                            Toast.makeText(context.getContext(),"Already in Favourite section!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            userData.child(myHomeDataList.getEventId()).setValue(eventIdObj);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
