package com.example.dateyoureve;

import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import static com.example.dateyoureve.R.color.textcolor;

public class CreateEvent extends AppCompatActivity {
    TextView textView;
    Button button;
    private int date,month,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        textView=(TextView)findViewById(R.id.date_text);
        button=(Button)findViewById(R.id.pick_date);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        }
        else
        {
            frameLayout.removeAllViews();

        }
    }
}