package com.example.dateyoureve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class Slider extends AppCompatActivity {
    ViewPager viewPager;
    Button btnNext;
    int[] layouts;
    Adapter adapter;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_slider);
        viewPager=findViewById(R.id.pager);
        btnNext=findViewById(R.id.button);
        LinearLayout sliderDotspanel;



        layouts=new int[]{
                R.layout.slider1,
                R.layout.slider2,
                R.layout.slider3
        };
        adapter=new Adapter(this,layouts);
        sliderDotspanel =(LinearLayout)findViewById(R.id.SliderDots);
        viewPager.setAdapter(adapter);

    dotscount=adapter.getCount();
    dots= new ImageView[dotscount];
    for(int i=0;i<dotscount;i++)
    {
        dots[i]=new ImageView(this);
        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(40,0,40,0);
        sliderDotspanel.addView(dots[i],params);
    }
    dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem()+1 < layouts.length)
                {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
                else {
                    //go to sign up or sign in screen
                    startActivity(new Intent(getApplicationContext(),SignupActivity.class));
                    finish();
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
            for (int i=0;i<dotscount;i++)
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
            }
            dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
