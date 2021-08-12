package com.brand.ziloe.view.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.brand.ziloe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class CheckOut_Activity extends AppCompatActivity {

    private TextView price;
    private FirebaseFirestore fstore;
    private String androidID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_);


        fstore = FirebaseFirestore.getInstance();

        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

    }


}