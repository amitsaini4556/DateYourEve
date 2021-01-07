package com.example.dateyoureve;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dateyoureve.ui.FavouriteFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavouriteDataAdapter extends RecyclerView.Adapter<FavouriteDataAdapter.ViewHolder> {
    List<MyHomeData> myHomeData;
    List<MyHomeData> myHomeDataFull;
    FavouriteFragment context;
    AlertDialog.Builder builder;
    public FavouriteDataAdapter(List<MyHomeData> myHomeData, FavouriteFragment favouriteFragment) {
        this.myHomeData = myHomeData;
        this.myHomeDataFull = new ArrayList<>(myHomeData);
        this.context = favouriteFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.favourite_ui_design, parent, false);
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
        holder.itemView.findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(context.getActivity(),R.style.DialogTheme);
                builder
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
                                String path = "users/" + mAuth.getUid() + "/FavEvents";
                                DatabaseReference userData = FirebaseDatabase.getInstance().getReference(path);
                                userData.child(myHomeDataList.getEventId()).removeValue();
                                Toast.makeText(context.getActivity(),"Delete",Toast.LENGTH_LONG).show();
                                try{
                                    myHomeData.remove(position);
                                    notifyItemRemoved(position);
                                }catch (Exception e)
                                {

                                }

                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Delete \nAre you sure ?");
                alertDialog.show();
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
            eventImage = itemView.findViewById(R.id.eventFav);
            titleView = itemView.findViewById(R.id.titleText);
            descriptionView = itemView.findViewById(R.id.desText);
            dateView = itemView.findViewById(R.id.dateText);
            venueView = itemView.findViewById(R.id.venueText);
        }
    }
}
