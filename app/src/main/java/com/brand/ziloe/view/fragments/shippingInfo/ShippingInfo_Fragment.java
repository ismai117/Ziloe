package com.brand.ziloe.view.fragments.shippingInfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brand.ziloe.R;


public class ShippingInfo_Fragment extends Fragment {


    public ShippingInfo_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_shipping_info, container, false);
    }
}