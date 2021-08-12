package com.brand.ziloe.view.adapters;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.brand.ziloe.R;
import com.brand.ziloe.model.ITEMS;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends FirestoreRecyclerAdapter<ITEMS, CartAdapter.ItemViewHolder> {

    private FirebaseFirestore firebaseFirestore;

    private String id;

    public CartAdapter(@NonNull FirestoreRecyclerOptions<ITEMS> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull ITEMS model) {

        try {

            Glide.with(holder.itemView).load(model.getProductImage()).into(holder.productImage);

            holder.productName.setText(model.getProductName());
            holder.productQuantity.setText(String.valueOf(model.getProductQuantity()));

            double total = model.getProductPrice() * model.getProductQuantity();
            holder.productPrice.setText("£" + total);

            id = model.getCurrentid();
            gettotal(id);

            getSnapshots().getSnapshot(position).getReference().update("productTotal", total).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Log.d("updated_total", "updated");

                    } else{
                        Log.d("updated_total", "failed: " + task.getException());
                    }
                }
            });





        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartliststyle_layout, parent, false);
        return new ItemViewHolder(view);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productPrice, productName, update;
        EditText productQuantity;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);


            productImage = itemView.findViewById(R.id.productImage);
            productPrice = itemView.findViewById(R.id.price);
            productQuantity = itemView.findViewById(R.id.quantity);
            productName = itemView.findViewById(R.id.productName);
            update = itemView.findViewById(R.id.updateBtn);

            firebaseFirestore = FirebaseFirestore.getInstance();

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    String updated = productQuantity.getText().toString();

                    int updatedQty = Integer.parseInt(updated);

                    if (TextUtils.isEmpty(updated)) {
                        Log.d("cart_qty", "updated" + position);
                    }
                    if (updatedQty == 0) {
                        deleteItem(position);
                    }

                    updateqty(position, updatedQty);
                    gettotal(id);
                }
            });
        }
    }

    public void gettotal(String id){

        firebaseFirestore.collection("cart").document(id).collection("basket")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    double subtotal = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        double itemCost = document.getDouble("productTotal");
                        subtotal += itemCost;
                    }

                    Map<String, Object> cartTotal = new HashMap<>();
                    cartTotal.put("total", subtotal);

                    firebaseFirestore.collection("subtotal").document(id)
                            .set(cartTotal)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("cart_total", "updated");

                                }
                            });

                    Log.d("subtotal", String.valueOf(subtotal));
                }
            }
        });


    }

    public void updateqty(int position, int qty) {

        getSnapshots().getSnapshot(position).getReference().update("productQuantity", qty).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Log.d("qty_updated", " " + qty + ": " + position);
                } else {
                    Log.d("qty_updated", " " + task.getException());
                }
            }
        });

    }


    public void deleteItem(int position) {
        try {
            getSnapshots().getSnapshot(position).getReference().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
