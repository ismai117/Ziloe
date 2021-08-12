package com.brand.ziloe.view.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brand.ziloe.R;
import com.brand.ziloe.db.DBHelper;
import com.brand.ziloe.model.ITEMS;
import com.brand.ziloe.view.adapters.CartAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Cart_Activity extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private String android_id;
    private TextView total;
    private RecyclerView cartShow_All;
    private Toolbar toolbar;
    private CartAdapter cartAdapter;
    private Button checkout;
    private EditText instructioninput;
    private double subtotal;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        total = findViewById(R.id.total);
        instructioninput = findViewById(R.id.instructions_input);
        checkout = findViewById(R.id.ocheckout_btn);
        cartShow_All = findViewById(R.id.cartView_All);
        fStore = FirebaseFirestore.getInstance();


        toolbar = findViewById(R.id.toolbar_cart);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String instructionValue = instructioninput.getText().toString();

                setInstruction(instructionValue);

                if (subtotal > 0){
                    startActivity(new Intent(getApplicationContext(), Information_Activity.class));

                } else {
                    Toast.makeText(Cart_Activity.this, "Cart Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });



        getItems();
        getsubtotal();

    }

    private void setInstruction(String instructionValue) {

        Map<String, Object> instruction = new HashMap<>();
        instruction.put("instruction", instructionValue);

        fStore.collection("order_instruction").document(android_id)
                .set(instruction)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("instructions", "success: submitted");
                        } else{
                            Log.d("instructions", "failed");
                        }
                    }
                });
    }


    private void getsubtotal() {
        fStore.collection("subtotal").document(android_id)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                      try {
                          if (value != null){
                              subtotal = value.getDouble("total");
                              total.setText("£" + subtotal);
                          }
                      } catch (Exception e){
                          e.printStackTrace();
                      }

                    }
                });
    }


    private void getItems() {

        Query query = fStore.collection("cart").document(android_id).collection("basket");

        FirestoreRecyclerOptions<ITEMS> options = new FirestoreRecyclerOptions.Builder<ITEMS>()
                .setQuery(query, ITEMS.class)
                .build();

        cartAdapter = new CartAdapter(options);

        cartShow_All.setAdapter(cartAdapter);
        cartShow_All.setHasFixedSize(true);
        cartShow_All.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        cartAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cartAdapter.stopListening();
    }
}