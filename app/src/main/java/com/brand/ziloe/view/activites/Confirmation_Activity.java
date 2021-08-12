package com.brand.ziloe.view.activites;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.brand.ziloe.R;
import com.brand.ziloe.view.adapters.CartAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import org.jetbrains.annotations.NotNull;

public class Confirmation_Activity extends AppCompatActivity {


    private TextView confirmationNumber, name, subtotal, total;
    private FirebaseFirestore fstore;
    private TextView close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);


        confirmationNumber = findViewById(R.id.confirmation_code);
        name = findViewById(R.id.user_name);
        subtotal = findViewById(R.id.confirm_subtotal);
        total = findViewById(R.id.confirm_total);
        close = findViewById(R.id.close);


        fstore = FirebaseFirestore.getInstance();
        String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        getConfirmatonCode(androidId);
        getSubtotal(androidId);
        getName(androidId);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                fstore.collection("cart").document(androidId).collection("basket")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){

                                    for (QueryDocumentSnapshot queryDocumentSnapshots : task.getResult()){

                                        queryDocumentSnapshots.getReference().delete();

                                        fstore.collection("subtotal").document(androidId).delete();

                                    }
                                }
                            }
                        });

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


    }

    private void getName(String androidId) {
        fstore.collection("shipping_details").document(androidId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        try {
                            if (value != null){
                                name.setText("Hey " + value.getString("firstname"));
                            } } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getSubtotal(String androidId) {
        fstore.collection("subtotal").document(androidId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        try {
                            if (value != null){

                                double gettotal = value.getDouble("total");
                                subtotal.setText("Subtotal: " + gettotal);
                                total.setText("Total: " + gettotal);
                            } } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getConfirmatonCode(String androidId) {

        fstore.collection("confirmation").document(androidId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                       try {
                        if (value != null){
                            confirmationNumber.setText("Order Number: \n\n" + value.getString("confirmationCode"));
                        } } catch (Exception e){
                           e.printStackTrace();
                       }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}