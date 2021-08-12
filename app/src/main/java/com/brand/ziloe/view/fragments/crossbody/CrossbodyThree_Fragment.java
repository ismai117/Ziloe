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


public class CrossbodyThree_Fragment extends Fragment {

    ImageSlider imagesliderThree;
    TextView facebook, twitter, instagram;

    public CrossbodyThree_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crossbody_three_, container, false);

        imagesliderThree = view.findViewById(R.id.crossbodyThree_image_slider);


        facebook = view.findViewById(R.id.gotocb3Fb);
        twitter = view.findViewById(R.id.gotocb3Twi);
        instagram = view.findViewById(R.id.gotocb3IG);


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


        ArrayList<SlideModel> sliderThree = new ArrayList<>();

        sliderThree.add(new SlideModel(R.drawable.crossbodythree_one, ScaleTypes.CENTER_CROP));
        sliderThree.add(new SlideModel(R.drawable.crossbodythree_two, ScaleTypes.CENTER_CROP));
        sliderThree.add(new SlideModel(R.drawable.crossbodythree_three, ScaleTypes.CENTER_CROP));
        sliderThree.add(new SlideModel(R.drawable.crossbodythree_four, ScaleTypes.CENTER_CROP));

        imagesliderThree.setImageList(sliderThree);

        return view;
    }
}