package com.example.dateyoureve;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import ru.semkin.yandexplacepicker.PlaceParcelable;
import ru.semkin.yandexplacepicker.YandexPlacePicker;

public class CreateEvent extends AppCompatActivity {
    TextView textView;
    Button button;
    private int cdate,cmonth,cyear,mHour, mMinute;
    int PLACE_PICKER_REQUEST = 1;
    Button selectvenue;
    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    Button btnSelectImage,cancel,create,btnTimePicker;
    TextInputLayout titleEvent;
    TextInputLayout decriptionEvent;
    TextInputLayout timeInputEvent;
    TextInputLayout notes;
    String venue;
    RadioGroup radioGroupOnOffMode;
    FirebaseDatabase mDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String title,description,note,mode,isFree,time,dateEvent,location;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        textView=(TextView)findViewById(R.id.date_text);
        button=findViewById(R.id.pick_date);
        selectvenue = findViewById(R.id.selectVenue);
        btnSelectImage = findViewById(R.id.chooseImage);
        btnTimePicker=findViewById(R.id.picktime);
        imageView = findViewById(R.id.image);
        titleEvent = findViewById(R.id.titleEvent);
        decriptionEvent = findViewById(R.id.desEvent);
        timeInputEvent = findViewById(R.id.timeInputEvent);
        notes = findViewById(R.id.notes);
        cancel = findViewById(R.id.cancelEvent);
        create = findViewById(R.id.createEvent);
        radioGroupOnOffMode = findViewById(R.id.radioGroupEventOnOff);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = mDatabase.getReference("Events");
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        progressDialog = new ProgressDialog(CreateEvent.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        radioGroupOnOffMode.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.offline:
                    mode = "Offline";
                    break;
                case R.id.online:
                    mode = "Online";
                    break;
            }
        });
        cancel.setOnClickListener(view -> {
            Intent intent = new Intent(CreateEvent.this,Home.class);
            startActivity(intent);
            finish();
        });
        create.setOnClickListener(view -> {
            title = titleEvent.getEditText().getText().toString();
            description = decriptionEvent.getEditText().getText().toString();
            note = notes.getEditText().getText().toString();
            time = timeInputEvent.getEditText().getText().toString();
            dateEvent = textView.getText().toString();
            venue = selectvenue.getText().toString();
            if(!title.isEmpty() && !description.isEmpty() && !note.isEmpty() && !time.isEmpty() && !dateEvent.isEmpty() && !venue.isEmpty())
            {
                progressDialog.show();
                uploadImage();
            }
            else
            {
                progressDialog.dismiss();
                Toast.makeText(CreateEvent.this, "Error! Add all fields", Toast.LENGTH_SHORT).show();
            }
        });

        btnTimePicker.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            if(minute<10)
                            Objects.requireNonNull(timeInputEvent.getEditText()).setText(hourOfDay + ":0" + minute);
                            else
                                Objects.requireNonNull(timeInputEvent.getEditText()).setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        });
        button.setOnClickListener(view -> {
            final Calendar calendar=Calendar.getInstance();
            cdate=calendar.get(Calendar.DATE);
            cmonth=calendar.get(Calendar.MONTH);
            cyear=calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog =new DatePickerDialog(CreateEvent.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                   if((date>cdate && month == cmonth && year==cyear) || (date == cdate && month > cmonth && year==cyear) ||(date == cdate && month == cmonth && year > cyear)||((date > cdate && month > cmonth && year > cyear)))
                    textView.setText(date+"/"+(month+1)+"/"+year);
                    else
                        Toast.makeText(CreateEvent.this, "Please enter valid date", Toast.LENGTH_SHORT).show();
                }
            },cyear,cmonth,cdate);
            datePickerDialog.show();
        });

        selectvenue.setOnClickListener(view -> {
            YandexPlacePicker.IntentBuilder builder = new YandexPlacePicker.IntentBuilder();
            builder.setYandexMapsKey("245bda3d-eba8-4fec-a792-f11f3e79fc02");
            Intent placeIntent = builder.build(CreateEvent.this);
            startActivityForResult(placeIntent, PLACE_PICKER_REQUEST);
        });

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if ((requestCode == PLACE_PICKER_REQUEST)) {
            if(data != null) {
                PlaceParcelable place = YandexPlacePicker.getPlace(data);
                location =place.getName()+ place.getAddress();
                selectvenue.setText(place.getName()+place.getAddress());
                Toast.makeText(this, "You selected: " + place.getClass(), Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        if(filePath != null)
        {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(filePath));
            fileReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uploadId = databaseReference.push().getKey();
                                    EventDetailsSetter eventDetailsSetter = new EventDetailsSetter(title,description,note,mode,isFree,uri.toString(),dateEvent,time,location,user.getUid(),uploadId);
                                    Log.i("test",fileReference.getDownloadUrl().toString());
                                    databaseReference.child(uploadId).setValue(eventDetailsSetter);
                                    DatabaseReference userData = FirebaseDatabase.getInstance().getReference("users");
                                    EventIdObj eventIdObj = new EventIdObj(uploadId);
                                    userData.child(user.getUid()).child("createdEvents").child(title).setValue(eventIdObj);
                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(CreateEvent.this, "Event successful created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateEvent.this,Home.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateEvent.this, "Event creation Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else
        {
            progressDialog.dismiss();
            Toast.makeText(CreateEvent.this, "Event creation Failed", Toast.LENGTH_SHORT).show();
        }
    }
}