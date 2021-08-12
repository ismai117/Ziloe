package com.brand.ziloe.view.fragments.returnsAndrefund;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brand.ziloe.R;


public class ReturnsRefund_Fragment extends Fragment {



    public ReturnsRefund_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_returns_refund_, container, false);


        return view;
    }
}