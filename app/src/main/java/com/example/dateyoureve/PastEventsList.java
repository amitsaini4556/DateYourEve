package com.example.dateyoureve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class PastEventsList extends AppCompatActivity {
    PastEventDataAdapter myAdapter;
    private List<PastEventData> pastEventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_events_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       pastEventData = new ArrayList<PastEventData>();
       pastEventData.add(new PastEventData("mid terms","10/01/2020",10));
        pastEventData.add(new PastEventData("Projects","20/65/1523",2));
        pastEventData.add(new PastEventData("Office Works","32/12/2021",40));
        myAdapter = new PastEventDataAdapter(pastEventData,PastEventsList.this);
        recyclerView.setAdapter(myAdapter);
    }
}