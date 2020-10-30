package com.example.dateyoureve;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dateyoureve.ui.HomeFragment;

public class MyHomeAdapter extends RecyclerView.Adapter<MyHomeAdapter.ViewHolder>{

    MyHomeData[] myHomeData;
    HomeFragment context;
    public MyHomeAdapter(MyHomeData[] myHomeData, HomeFragment homeFragment) {
        this.myHomeData = myHomeData;
        this.context = homeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.my_home_ui,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  MyHomeData myHomeDataList = myHomeData[position];
        holder.titleView.setText(myHomeDataList.getTitle());
        holder.descriptionView.setText(myHomeDataList.getDescription());
        holder.dateView.setText(myHomeDataList.getDate());
        holder.venueView.setText(myHomeDataList.getVenue());
        holder.eventImage.setImageResource(myHomeDataList.getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getContext(),EventDetails.class);
                intent.putExtra("title",myHomeDataList.getTitle());
                intent.putExtra("decs",myHomeDataList.getDescription());
                intent.putExtra("date",myHomeDataList.getDate());
                intent.putExtra("venue",myHomeDataList.getVenue());
                intent.putExtra("image",myHomeDataList.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myHomeData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView eventImage;
        TextView titleView,descriptionView,dateView,venueView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.eventView);
            titleView = itemView.findViewById(R.id.titleView);
            descriptionView = itemView.findViewById(R.id.descView);
            dateView = itemView.findViewById(R.id.dateView);
            venueView = itemView.findViewById(R.id.venueView);
        }
    }
}
