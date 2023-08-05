package com.example.careconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    int[] layouts;
    Button btnNext, btnSkip;
    ViewSliderAdapter mAdapter;
    boolean isLastPageSwiped;
    int counterPageScroll;
    //To display viewer page
    private void init()
    {
        layouts = new int[]{
                R.layout.slide_screen_1,
                R.layout.slide_screen_2,
                R.layout.slide_screen_3
        };
        viewPager2=(ViewPager2) findViewById(R.id.view_pager);

        mAdapter = new ViewSliderAdapter(layouts);
        viewPager2.setAdapter(mAdapter);

        btnNext=(Button) findViewById(R.id.btn_next);
        btnSkip=(Button) findViewById(R.id.btn_skip);
        viewPager2.registerOnPageChangeCallback(pageChangeCallback);


        btnSkip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                launchLoginScreen();
            }
        });



        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager2.setCurrentItem(current);
                    if(current== layouts.length-1)
                    {
                        btnNext.setText(R.string.cont);
                    }
                } else {
                    launchLoginScreen();
                }
            }
        });



    }
    ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);



            if (position == layouts.length - 1) {
                // last page. make button text to CONTINUE
                btnNext.setText(getString(R.string.cont));
                btnSkip.setVisibility(View.GONE);

            }


            else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }


        }



        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            isLastPageSwiped = false ? true : false;
            if (position == layouts.length-1  && !isLastPageSwiped)
            {
                if(counterPageScroll != 0)
                {
                    isLastPageSwiped=true;
                    launchLoginScreen();
                }
                counterPageScroll++;
            }
            else
            {
                isLastPageSwiped=false;
                counterPageScroll=0;
            }
        }


    };


    private int getItem(int i) {

        return viewPager2.getCurrentItem() + i;
    }

    private void launchLoginScreen()
    {
        Intent intent1 = new Intent(MainActivity.this, ChooseLogin.class);
        startActivity(intent1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}