package com.brand.ziloe.view.fragments.termsOFservice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brand.ziloe.R;


public class TermsOfService_Fragment extends Fragment {


    public TermsOfService_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_terms_of_service_, container, false);


        return view;
    }
}