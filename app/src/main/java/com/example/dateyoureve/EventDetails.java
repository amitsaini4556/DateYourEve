package com.example.dateyoureve;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.util.List;

public class EventDetails extends AppCompatActivity {
    Button callbutton,register,direction;
    TextView title,decs,dateVenue,notes;
    ImageView imageView;
    FusedLocationProviderClient mFusedLocationClient;
    Double latitude, longit;
    String country,locality,addr;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    boolean preOrnot;
    ImageView qrImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        try {
            new UserLocation(this);
        }catch (Exception e){
            Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            finish();
            recreate();
        }
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getIntent().getStringExtra("title"));
        toolBarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));
        toolBarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimary));
        decs = findViewById(R.id.decsView);
        imageView = findViewById(R.id.eventImage);
        qrImage = findViewById(R.id.qrImage);
        dateVenue = findViewById(R.id.dateVenue);
        notes = findViewById(R.id.notesView);
        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(imageView);
        decs.setText(getIntent().getStringExtra("decs"));
        dateVenue.setText(getIntent().getStringExtra("date") + "  ||  " + getIntent().getStringExtra("venue"));
        notes.setText(getIntent().getStringExtra("note"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        register = findViewById(R.id.register);
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        String path = "users/" + auth.getUid() + "/InterestedEvents";
        DatabaseReference userData = FirebaseDatabase.getInstance().getReference(path);
        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(getIntent().getStringExtra("eventId")))
                {
                    register.setEnabled(false);
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix matrix = multiFormatWriter.encode(auth.getUid(), BarcodeFormat.QR_CODE,500,500);
                        BarcodeEncoder encoder = new BarcodeEncoder();
                        Bitmap bitmap = encoder.createBitmap(matrix);
                        qrImage.setImageBitmap(bitmap);
                        InputMethodManager manager = (InputMethodManager) getSystemService(
                                Context.INPUT_METHOD_SERVICE
                        );
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    register.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                udateEvent();
                DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");
                EventIdObj eventIdObj = new EventIdObj(getIntent().getStringExtra("eventId"));
                user.child(auth.getUid()).child("InterestedEvents").child(getIntent().getStringExtra("eventId")).setValue(eventIdObj);
                register.setEnabled(false);
            }
        });
        direction = findViewById(R.id.direction);
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sSource = addr;
                String sDestination = getIntent().getStringExtra("venue");
                try {
                    Geocoder coder = new Geocoder(getApplicationContext());
                    List<Address> address;
                    Address location = null;

                    try {
                        address = coder.getFromLocationName(sDestination,5);
                        location=address.get(0);
                        location.getLatitude();
                        location.getLongitude();
                        Uri uri = Uri.parse("google.navigation:q=" + location.getLatitude() + "," + location.getLongitude() + "&mode=l");
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        intent.setPackage("com.google.android.apps.maps");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }catch (ActivityNotFoundException e)
                    {

                    }
                }catch (ActivityNotFoundException | IOException e){
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String stringMessage = getIntent().getStringExtra("title")  + "\n\n" + getIntent().getStringExtra("decs") +"\n\n Date :" + getIntent().getStringExtra("date") + "\n\n Additional Info :" +getIntent().getStringExtra("note")  + "\n\n Venue :" + getIntent().getStringExtra("date") +"\n\n";
                stringMessage = stringMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                String shareSub = getIntent().getStringExtra("title");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, stringMessage);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        callbutton = findViewById(R.id.call);
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+ getIntent().getStringExtra("phone")));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(callIntent);
            }
        });
    }

    public synchronized void udateEvent() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        User userId = new User(user.getUid());
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("Events");
        data.child(getIntent().getStringExtra("eventId")).child("InterestedPeoples").child(user.getUid()).setValue(userId);
        Log.i("test",getIntent().getStringExtra("eventId"));
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}