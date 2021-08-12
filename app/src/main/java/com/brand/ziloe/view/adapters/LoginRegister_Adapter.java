package com.brand.ziloe.view.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LoginRegister_Adapter extends FragmentPagerAdapter {

    List<String> titles;
    List<Fragment> fragments;

    public void addFragment(String titles, Fragment fragments){
        this.titles.add(titles);
        this.fragments.add(fragments);
    }

    public LoginRegister_Adapter(@NonNull FragmentManager fm) {
        super(fm);
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
