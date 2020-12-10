package com.example.dateyoureve;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
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

import ru.semkin.yandexplacepicker.PlaceParcelable;
import ru.semkin.yandexplacepicker.YandexPlacePicker;

import static com.example.dateyoureve.R.color.textcolor;

public class CreateEvent extends AppCompatActivity {
    TextView textView;
    Button button;
    private int date,month,year;
    int PLACE_PICKER_REQUEST = 1;
    Button selectvenue;
    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    Button btnSelectImage,cancel,create;
    TextInputLayout titleEvent,decriptionEvent,timeInputEvent,notes;
    RadioGroup radioGroupOnOffMode,radioGroupAmPm;
    CheckBox paidCheckBox;
    FirebaseDatabase mDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String title,description,note,mode,isFree,fees,time,dateEvent;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        textView=(TextView)findViewById(R.id.date_text);
        button=(Button)findViewById(R.id.pick_date);
        selectvenue = findViewById(R.id.selectVenue);
        btnSelectImage = findViewById(R.id.chooseImage);
        imageView = findViewById(R.id.image);
        titleEvent = findViewById(R.id.titleEvent);
        decriptionEvent = findViewById(R.id.desEvent);
        timeInputEvent = findViewById(R.id.timeInputEvent);
        notes = findViewById(R.id.notes);
        cancel = findViewById(R.id.cancelEvent);
        create = findViewById(R.id.createEvent);
        radioGroupOnOffMode = findViewById(R.id.radioGroupEventOnOff);
        radioGroupAmPm = findViewById(R.id.radioGroupAmPm);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        paidCheckBox = findViewById(R.id.cb);
        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = mDatabase.getReference("Events");
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        progressDialog = new ProgressDialog(CreateEvent.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        if(paidCheckBox.isChecked())
        {
            isFree = "Paid Event" + fees;
        }
        else
        {
            isFree = "Free Event";
        }
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
            if(!title.isEmpty() && !description.isEmpty() && !note.isEmpty())
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
        button.setOnClickListener(view -> {
            final Calendar calendar=Calendar.getInstance();
            date=calendar.get(Calendar.DATE);
            month=calendar.get(Calendar.MONTH);
            year=calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog =new DatePickerDialog(CreateEvent.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                    textView.setText(date+"/"+month+"/"+year);
                }
            },year,month,date);
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

    public void show_input_box(View view) {
        CheckBox checkBox=findViewById(R.id.cb);
        FrameLayout frameLayout;
        TextInputLayout textInputLayout;
        TextInputEditText textInputEditText;


        // Check which radio button was clicked
        frameLayout= (FrameLayout)findViewById(R.id.container);
        textInputLayout = new TextInputLayout(this, null, R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox);
        textInputLayout.setHint("Fees");;
        textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        textInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.hintcolor)));
        textInputLayout.setPadding(20,0,0,0);
        textInputLayout.setBoxCornerRadii(5, 5, 5, 5);
        textInputEditText = new TextInputEditText(textInputLayout.getContext());
        textInputLayout.addView(textInputEditText);
        textInputEditText.setTextColor(ColorStateList.valueOf(getResources().getColor(textcolor)));

        if (checkBox.isChecked()) {

            frameLayout.addView(textInputLayout);
            fees = textInputEditText.getText().toString();
        }
        else
        {
            frameLayout.removeAllViews();
            fees = " ";
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
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            EventDetailsSetter eventDetailsSetter = new EventDetailsSetter(title,description,note,mode,isFree,taskSnapshot.getUploadSessionUri().toString(),dateEvent,time,user.getUid());
                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(title).setValue(eventDetailsSetter);
                            DatabaseReference userData = FirebaseDatabase.getInstance().getReference("users");
                            EventIdObj eventIdObj = new EventIdObj(uploadId);
                            userData.child(user.getUid()).child("createdEvents").child(title).setValue(eventIdObj);
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