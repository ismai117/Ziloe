package com.brand.ziloe.view.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brand.ziloe.db.DBHelper;
import com.brand.ziloe.view.fragments.privacyPolicy.PrivacyPolicy_Fragment;
import com.brand.ziloe.view.fragments.returnsAndrefund.ReturnsRefund_Fragment;
import com.brand.ziloe.view.fragments.shippingInfo.ShippingInfo_Fragment;
import com.brand.ziloe.view.fragments.termsOFservice.TermsOfService_Fragment;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.brand.ziloe.R;
import com.brand.ziloe.view.fragments.contact.ContactUs_Fragment;
import com.brand.ziloe.view.fragments.about.About_Fragment;
import com.brand.ziloe.view.fragments.backpack.Luxe_Fragment;
import com.brand.ziloe.view.fragments.crossbody.CrossbodyOne_Fragment;
import com.brand.ziloe.view.fragments.crossbody.CrossbodyThree_Fragment;
import com.brand.ziloe.view.fragments.crossbody.CrossbodyTwo_Fragment;
import com.brand.ziloe.view.fragments.gallery.Gallery_Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView basketAdded;
    private ImageSlider imageSlider;
    private EditText newsletter_input;
    private AppCompatButton pre_order_Luxe, pre_order_cb1, pre_order_cb2, pre_order_cb3, subscribeBtn;
    private int luxe_times = 1;
    private int cb1_times = 1;
    private int cb2_times = 1;
    private int cb3_times = 1;


    DBHelper db;

    private int luxe_number, cb1_number, cb2_number, cb3_number;


    private String android_id;
    private FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_drawer);
        imageSlider = findViewById(R.id.image_slider);
        pre_order_Luxe = findViewById(R.id.pre_order_btn);
        pre_order_cb1 = findViewById(R.id.c1_pre_order_btn);
        pre_order_cb2 = findViewById(R.id.c2_pre_order_btn);
        pre_order_cb3 = findViewById(R.id.c3_pre_order_btn);
        newsletter_input = findViewById(R.id.email_newsletter_input);
        subscribeBtn = findViewById(R.id.subscribe_btn);
        basketAdded = findViewById(R.id.show_itemColour);


        fStore = FirebaseFirestore.getInstance();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.slider_one, ScaleTypes.CENTER_CROP));
        images.add(new SlideModel(R.drawable.slider_three, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(images);


        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        db = new DBHelper(this);

        pre_order_Luxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luxe_number = luxe_times++;
                addluxetoCart(luxe_number);
                basketAdded.setVisibility(View.VISIBLE);
            }
        });

        pre_order_cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb1_number = cb1_times++;
                addcb1tocart(cb1_number);
                basketAdded.setVisibility(View.VISIBLE);
            }
        });

        pre_order_cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb2_number = cb2_times++;
                addcb2tocart(cb2_number);
                basketAdded.setVisibility(View.VISIBLE);
            }
        });

        pre_order_cb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb3_number = cb3_times++;
                addcb3tocart(cb3_number);
                basketAdded.setVisibility(View.VISIBLE);
            }
        });


        subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userSubscribed = newsletter_input.getText().toString();

                if (TextUtils.isEmpty(userSubscribed)) {
                    newsletter_input.setError("Pleas enter in email address");
                    return;
                }

                newsletter_input.getText().clear();

                subscribedUser(userSubscribed);

            }
        });


    }


    private void subscribedUser(String userSubscribed) {

        Toast.makeText(this, "Subscribed!", Toast.LENGTH_SHORT).show();
        
        Map<String, Object> subscriber = new HashMap<>();
        subscriber.put("subscribed", userSubscribed);

        fStore.collection("subscribed").document(android_id)
                .set(subscriber)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("newsletter", "success");
                        } else {
                            Log.d("newsletter", " " + task.getException());
                        }
                    }
                });

        boolean checkifsubscribed = db.insert_newsletter(android_id, userSubscribed);

        if (checkifsubscribed == true){
            Log.d("newsletter_sql", "success");
        } else {
            Log.d("newsletter_sql", "failed");
        }



    }


    private void addluxetoCart(int luxe_number) {


        double luxe_price = 49.99;
//        double total = luxe_number * luxe_price ;


        Map<String, Object> luxe_basket = new HashMap<>();
        luxe_basket.put("productName", "LUXE");
        luxe_basket.put("currentid", android_id);
        luxe_basket.put("productImage", R.drawable.slider_three);
        luxe_basket.put("productPrice", luxe_price);
        luxe_basket.put("productTotal", 0);
        luxe_basket.put("productQuantity", luxe_number);


        fStore.collection("cart").document(android_id).collection("basket").document("luxeaddedtoCart")
                .set(luxe_basket)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "added to cart", Toast.LENGTH_SHORT).show();
                            Log.d("cart", "success");
                        } else {
                            Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            Log.d("cart", " " + task.getException());
                        }
                    }
                });

//        Toast.makeText(this, "" + luxe_number + ": " + "total: " + total, Toast.LENGTH_SHORT).show();

    }


    private void addcb1tocart(int cb1_number) {

        double cb1_price = 29.99;
//        double total = cb1_number * cb1_price;


        Map<String, Object> luxe_basket = new HashMap<>();
        luxe_basket.put("productName", "Crossbody Bag - Black");
        luxe_basket.put("currentid", android_id);
        luxe_basket.put("productImage", R.drawable.crossbody_one);
        luxe_basket.put("productPrice", cb1_price);
        luxe_basket.put("productTotal", 0);
        luxe_basket.put("productQuantity", cb1_number);

        fStore.collection("cart").document(android_id).collection("basket").document("cb1addedtoCart")
                .set(luxe_basket)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "added to cart", Toast.LENGTH_SHORT).show();
                            Log.d("cart", "success");
                        } else {
                            Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            Log.d("cart", " " + task.getException());
                        }
                    }
                });
    }

    private void addcb2tocart(int cb2_number) {
        double cb2_price = 29.99;
//        double total = cb2_number * cb2_price;


        Map<String, Object> luxe_basket = new HashMap<>();
        luxe_basket.put("productName", "Crossbody Bag - Grey/Black");
        luxe_basket.put("currentid", android_id);
        luxe_basket.put("productImage", R.drawable.crossbody2_one);
        luxe_basket.put("productPrice", cb2_price);
        luxe_basket.put("productTotal", 0);
        luxe_basket.put("productQuantity", cb2_number);

        fStore.collection("cart").document(android_id).collection("basket").document("cb2addedtoCart")
                .set(luxe_basket)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "added to cart", Toast.LENGTH_SHORT).show();
                            Log.d("cart", "success");
                        } else {
                            Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            Log.d("cart", " " + task.getException());
                        }
                    }
                });

    }


    private void addcb3tocart(int cb3_number) {
        double cb3_price = 29.99;
//        double total = cb3_number * cb3_price;


        Map<String, Object> luxe_basket = new HashMap<>();
        luxe_basket.put("productName", "Crossbody Bag - Silver/Black");
        luxe_basket.put("currentid", android_id);
        luxe_basket.put("productImage", R.drawable.crossbodythree_one);
        luxe_basket.put("productPrice", cb3_price);
        luxe_basket.put("productTotal", 0);
        luxe_basket.put("productQuantity", cb3_number);

        fStore.collection("cart").document(android_id).collection("basket").document("cb3addedtoCart")
                .set(luxe_basket)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "added to cart", Toast.LENGTH_SHORT).show();
                            Log.d("cart", "success");
                        } else {
                            Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            Log.d("cart", " " + task.getException());
                        }
                    }
                });
    }

    public void cart(View view) {

            startActivity(new Intent(getApplicationContext(), Cart_Activity.class));

    }

    public void luxeActivity(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.show_productsFrag_layout, new Luxe_Fragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void gotoCb1Activity(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.show_productsFrag_layout, new CrossbodyOne_Fragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void gotoCb2Activity(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.show_productsFrag_layout, new CrossbodyTwo_Fragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void gotoCb3Activity(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.show_productsFrag_layout, new CrossbodyThree_Fragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;


            case R.id.ziloe_gallary:
                fragmentTransaction.replace(R.id.show_productsFrag_layout, new Gallery_Fragment()).commit();
                break;

            case R.id.about_us:
                fragmentTransaction.replace(R.id.show_productsFrag_layout, new About_Fragment()).commit();
                break;


            case R.id.contact_us:
                fragmentTransaction.replace(R.id.show_productsFrag_layout, new ContactUs_Fragment()).commit();
                break;

            case R.id.privacy:
                fragmentTransaction.replace(R.id.show_productsFrag_layout, new PrivacyPolicy_Fragment()).commit();
                break;

            case R.id.refunds:
                fragmentTransaction.replace(R.id.show_productsFrag_layout, new ReturnsRefund_Fragment()).commit();
                break;

            case R.id.service:
                fragmentTransaction.replace(R.id.show_productsFrag_layout, new TermsOfService_Fragment()).commit();
                break;

        }


        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void gotoContactPage(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.show_productsFrag_layout, new ContactUs_Fragment()).commit();
    }

    public void gotoAboutPage(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.show_productsFrag_layout, new About_Fragment()).commit();
    }

    public void gotoShippingPage(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.show_productsFrag_layout, new ShippingInfo_Fragment()).commit();
    }

    public void gotoPPPage(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.show_productsFrag_layout, new PrivacyPolicy_Fragment()).commit();
    }

    public void gotoRRPage(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.show_productsFrag_layout, new ReturnsRefund_Fragment()).commit();
    }

    public void gotoTOSPage(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.show_productsFrag_layout, new TermsOfService_Fragment()).commit();
    }

    public void gotoInstagram(View view) {

        String instagramURL = "https://www.instagram.com/ziloe_/?hl=en-gb";

        Uri instagramURI = Uri.parse(instagramURL);
        startActivity(new Intent(Intent.ACTION_VIEW, instagramURI));


    }

    public void gotoPinterest(View view) {

        String pinterestURL = "https://www.pinterest.co.uk/ziloe_/";

        Uri pinterestURI = Uri.parse(pinterestURL);
        startActivity(new Intent(Intent.ACTION_VIEW, pinterestURI));
    }

    public void gotoFacebook(View view) {

        String facrbookURL = "https://www.facebook.com/ziloeworld";

        Uri facrbookURI = Uri.parse(facrbookURL);
        startActivity(new Intent(Intent.ACTION_VIEW, facrbookURI));
    }

    public void gotoTwitter(View view) {

        String twitterURL = "https://twitter.com/ziloe_/";

        Uri twittermURI = Uri.parse(twitterURL);
        startActivity(new Intent(Intent.ACTION_VIEW, twittermURI));
    }

    public void gotoLuxeIG(View view) {
        String instagramURL = "https://www.instagram.com/ziloe_/?hl=en-gb";

        Uri instagramURI = Uri.parse(instagramURL);
        startActivity(new Intent(Intent.ACTION_VIEW, instagramURI));
    }

    public void gotoLuxeTWI(View view) {
        String twitterURL = "https://twitter.com/ziloe_/";

        Uri twittermURI = Uri.parse(twitterURL);
        startActivity(new Intent(Intent.ACTION_VIEW, twittermURI));
    }

    public void gotoLuxeFB(View view) {
        String facrbookURL = "https://www.facebook.com/ziloeworld";

        Uri facrbookURI = Uri.parse(facrbookURL);
        startActivity(new Intent(Intent.ACTION_VIEW, facrbookURI));
    }
}