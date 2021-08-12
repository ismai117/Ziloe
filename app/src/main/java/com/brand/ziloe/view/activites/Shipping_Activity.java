package com.brand.ziloe.view.activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.brand.ziloe.R;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Shipping_Activity extends AppCompatActivity {

    private TextView price;
    private FirebaseFirestore fstore;
    private TextView contact, address, phone;
    private AppCompatCheckBox delivery_standard;
    private Button continueBtn;
    private String androidID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_);


        contact = findViewById(R.id.show_email);
        address = findViewById(R.id.show_address);
        phone = findViewById(R.id.show_phone);
        price = findViewById(R.id.shipping_price_summary);
        delivery_standard = findViewById(R.id.appCompatCheckBox);
        continueBtn = findViewById(R.id.continueToPayment);
        fstore = FirebaseFirestore.getInstance();

        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        getTotal();
        getData();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!delivery_standard.isChecked()){
                    Toast.makeText(Shipping_Activity.this, "choose delivery option", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), Payment_Activity.class));
                }

            }
        });
    }

    private void getData() {
        fstore.collection("shipping_details").document(androidID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null){
                            String contactValue = value.getString("email");
                            String addressValue = value.getString("address");
                            String postcodeValue = value.getString("postcode");
                            String phoneValue = value.getString("phone");
                            contact.setText(contactValue);
                            address.setText(addressValue + ", " + postcodeValue);
                            phone.setText(phoneValue);
                        }
                    }
                });
    }


    private void getTotal() {

        fstore.collection("subtotal").document(androidID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        try {
                            if (value != null){
                                double subtotal = value.getDouble("total");
                                price.setText("£" + subtotal);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

    }

    public void backToCart(View view) {
        startActivity(new Intent(getApplicationContext(), Cart_Activity.class));
    }

    public void backtoInfo(View view) {
        startActivity(new Intent(getApplicationContext(), Information_Activity.class));
    }

    public void returnToInfo(View view) {
        startActivity(new Intent(getApplicationContext(), Information_Activity.class));
    }
}