package com.brand.ziloe.view.fragments.crossbody;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.brand.ziloe.R;

import java.util.ArrayList;

public class CrossbodyOne_Fragment extends Fragment {

    ImageSlider imagesliderOne;
    TextView facebook, twitter, instagram;


    public CrossbodyOne_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crossbody_one_, container, false);


        facebook = view.findViewById(R.id.gotocb1Fb);
        twitter = view.findViewById(R.id.gotocb1Twi);
        instagram = view.findViewById(R.id.gotocb1IG);


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facrbookURL = "https://www.facebook.com/ziloeworld";

                Uri facrbookURI = Uri.parse(facrbookURL);
                startActivity(new Intent(Intent.ACTION_VIEW, facrbookURI));
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String twitterURL = "https://twitter.com/ziloe_/";

                Uri twittermURI = Uri.parse(twitterURL);
                startActivity(new Intent(Intent.ACTION_VIEW, twittermURI));
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String instagramURL = "https://www.instagram.com/ziloe_/?hl=en-gb";

                Uri instagramURI = Uri.parse(instagramURL);
                startActivity(new Intent(Intent.ACTION_VIEW, instagramURI));
            }
        });

        imagesliderOne = view.findViewById(R.id.crossbodyOne_image_slider);

        ArrayList<SlideModel> sliderOne = new ArrayList<>();

        sliderOne.add(new SlideModel(R.drawable.crossbody1_one, ScaleTypes.CENTER_CROP));
        sliderOne.add(new SlideModel(R.drawable.crossbody1_two, ScaleTypes.CENTER_CROP));
        sliderOne.add(new SlideModel(R.drawable.crossbody1_three, ScaleTypes.CENTER_CROP));
        sliderOne.add(new SlideModel(R.drawable.crossbody1_four, ScaleTypes.CENTER_CROP));

        imagesliderOne.setImageList(sliderOne);

        return view;
    }
}