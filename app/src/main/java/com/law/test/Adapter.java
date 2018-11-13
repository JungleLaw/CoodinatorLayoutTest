package com.law.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public Adapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new RvFragment());
        fragments.add(new RvFragment2());
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
