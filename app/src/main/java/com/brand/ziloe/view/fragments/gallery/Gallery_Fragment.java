package com.brand.ziloe.view.fragments.gallery;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.brand.ziloe.R;
import com.brand.ziloe.view.adapters.Gallary_Adapter;
import com.bumptech.glide.Glide;


public class Gallery_Fragment extends Fragment {


    GridView gridView;


    public Gallery_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_gallery_, container, false);

        gridView = view.findViewById(R.id.gridview);


        int[] images = {
                R.drawable.gallery_1,
                R.drawable.gallery_2,
                R.drawable.gallery_3,
                R.drawable.gallery_4,
                R.drawable.gallery_5,
                R.drawable.gallery_6,
                R.drawable.gallery_7,
                R.drawable.gallery_8,
                R.drawable.gallery_9,
                R.drawable.gallery_12,
                R.drawable.gallery_13,
                R.drawable.gallery_14,
                R.drawable.gallery_15,
                R.drawable.gallery_16,
                R.drawable.gallery_17,
                R.drawable.gallery_18,

        };


        Gallary_Adapter gallary_adapter = new Gallary_Adapter(getContext(), images);
        gridView.setAdapter(gallary_adapter);



        return view;
    }
}