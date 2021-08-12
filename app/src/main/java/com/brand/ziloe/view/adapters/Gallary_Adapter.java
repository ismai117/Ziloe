package com.brand.ziloe.view.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.brand.ziloe.view.activites.PopUp_Activity;
import com.bumptech.glide.Glide;
import com.brand.ziloe.R;

public class Gallary_Adapter extends BaseAdapter {

    private Context context;
    private int[] images;
    public int imageSrc;

    LayoutInflater inflater;

    public Gallary_Adapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gallary_item_layout, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.gallary_images);

        Glide.with(context).load(images[position]).into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int imageID = images[position];

                context.startActivity(new Intent(context, PopUp_Activity.class).putExtra("imageID", imageID));


            }
        });


        return imageView;
    }
}
