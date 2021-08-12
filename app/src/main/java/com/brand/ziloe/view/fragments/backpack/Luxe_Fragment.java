package com.brand.ziloe.view.fragments.backpack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.brand.ziloe.R;

import java.util.ArrayList;


public class Luxe_Fragment extends Fragment {

    ImageSlider luxe_image_slider;


    public Luxe_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_luxe, container, false);

        luxe_image_slider = view.findViewById(R.id.luxe_image_slider);

        ArrayList<SlideModel> luxeImages = new ArrayList<>();
        luxeImages.add(new SlideModel(R.drawable.slider_three, ScaleTypes.CENTER_CROP));
        luxeImages.add(new SlideModel(R.drawable.luxe_2, ScaleTypes.CENTER_CROP));
        luxeImages.add(new SlideModel(R.drawable.luxe_3, ScaleTypes.CENTER_CROP));
        luxeImages.add(new SlideModel(R.drawable.luxe_4, ScaleTypes.CENTER_CROP));
        luxeImages.add(new SlideModel(R.drawable.luxe_5, ScaleTypes.CENTER_CROP));
        luxeImages.add(new SlideModel(R.drawable.luxe_6, ScaleTypes.CENTER_CROP));

        luxe_image_slider.setImageList(luxeImages);

        return view;

    }
}