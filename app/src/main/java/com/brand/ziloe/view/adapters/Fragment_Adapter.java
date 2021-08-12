package com.brand.ziloe.view.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Adapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;


    public void addFragment(Fragment fragment, String titles) {
        this.fragments.add(fragment);
        this.titles.add(titles);
    }

    public Fragment_Adapter(@NonNull FragmentManager fm) {
        super(fm);
        this.titles = new ArrayList<>();
        this.fragments = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.titles.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
