package com.law.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class SeActivity extends AppCompatActivity {
    private ViewPager mViewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se);

        mViewPager = findViewById(R.id.viewpager);
        Adapter mAdapter = new Adapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }
}
