package com.example.dateyoureve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Slider extends AppCompatActivity {
    ViewPager viewPager;
    Button btnNext;
    int[] layouts;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_slider);
        viewPager=findViewById(R.id.pager);
        btnNext=findViewById(R.id.button);

        layouts=new int[]{
                R.layout.slider1,
                R.layout.slider2,
                R.layout.slider3
        };
        adapter=new Adapter(this,layouts);
        viewPager.setAdapter(adapter);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem()+1 < layouts.length)
                {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
                else {
                    //go to sign up or sign in screen
                    startActivity(new Intent(getApplicationContext(),SignupActivity.class)
                            );
                }
            }
        });
        viewPager.addOnPageChangeListener(viewPagerChangeListener);
    }
    ViewPager.OnPageChangeListener viewPagerChangeListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position==layouts.length-1)
            {
                btnNext.setText("CONTINUE");
            }
            else
            {
                btnNext.setText("NEXT");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
