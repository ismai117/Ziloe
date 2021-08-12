package com.brand.ziloe.view.fragments.accounts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brand.ziloe.R;


public class AccountDetails_Fragment extends Fragment {


    public AccountDetails_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account_details_, container, false);
    }
}