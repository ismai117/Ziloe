package com.brand.ziloe.view.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brand.ziloe.R;
import com.brand.ziloe.db.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Information_Activity extends AppCompatActivity {
    private TextView price;
    private FirebaseFirestore fstore;
    private String androidID;
    private TextInputEditText fname, lname, email, address, building, city, country, postcode, phone;
    private MaterialCheckBox checkBox;
    private Button continueBtn;

    private DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        fname = findViewById(R.id.cart_fnameInput);
        lname = findViewById(R.id.cart_lnameInput);
        email = findViewById(R.id.cart_emailInput);
        address = findViewById(R.id.cart_addressInput);
        building = findViewById(R.id.cart_buildingInput);
        city = findViewById(R.id.cart_cityInput);
        country = findViewById(R.id.cart_countryInput);
        postcode = findViewById(R.id.cart_postcodeInput);
        phone = findViewById(R.id.cart_phoneInput);
        continueBtn = findViewById(R.id.continue_shippingBtn);

        checkBox = findViewById(R.id.tickbox_save);

        db = new DBHelper(this);

        price = findViewById(R.id.price_summary);
        fstore = FirebaseFirestore.getInstance();

        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkifinputValid(); 
            }
        });






        getItem();


    }



    private void checkifinputValid() {

        String fnameValue = fname.getText().toString();
        String lnameValue = lname.getText().toString();
        String emailValue = email.getText().toString();
        String addressValue = address.getText().toString();
        String buildingValue = building.getText().toString();
        String cityValue = city.getText().toString();
        String countryValue = country.getText().toString();
        String postcodeValue = postcode.getText().toString();
        String phoneValue = phone.getText().toString();

        if (TextUtils.isEmpty(fnameValue) || TextUtils.isEmpty(lnameValue) || TextUtils.isEmpty(emailValue) || TextUtils.isEmpty(addressValue) ||
                TextUtils.isEmpty(cityValue) || TextUtils.isEmpty(countryValue) || TextUtils.isEmpty(postcodeValue) || TextUtils.isEmpty(phoneValue)){

            fname.setError("Please fill in field");
            lname.setError("Please fill in field");
            email.setError("Please fill in field");
            address.setError("Please fill in field");
            city.setError("Please fill in field");
            country.setError("Please fill in field");
            postcode.setError("Please fill in field");
            phone.setError("Please fill in field");


            return;

        }


        storeshippingDetails(fnameValue, lnameValue, emailValue, addressValue, buildingValue, cityValue, countryValue, postcodeValue, phoneValue);


    }

    private void storeshippingDetails(String fnameValue, String lnameValue, String emailValue, String addressValue, String buildingValue, String cityValue, String countryValue, String postcodeValue, String phoneValue) {

        Map<String, Object> shippingDetails = new HashMap<>();
        shippingDetails.put("firstname", fnameValue);
        shippingDetails.put("lastname", lnameValue);
        shippingDetails.put("email", emailValue);
        shippingDetails.put("address", addressValue);
        shippingDetails.put("building", buildingValue);
        shippingDetails.put("city",  cityValue);
        shippingDetails.put("country",  countryValue);
        shippingDetails.put("postcode", postcodeValue);
        shippingDetails.put("phone", phoneValue);

        fstore.collection("shipping_details").document(androidID)
                .set(shippingDetails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("shipping_details", "success");

                            startActivity(new Intent(getApplicationContext(), Shipping_Activity.class));
                        } else{
                            Log.d("shipping_details", ": " + task.getException());
                        }
                    }
                });



     boolean checkifinserted = db.insert_userDetail(androidID, fnameValue, lnameValue, emailValue, addressValue, buildingValue, cityValue, countryValue, postcodeValue, phoneValue);

        if (checkifinserted == true){
            Log.d("sqlite_insert", "success");
        } else {
            Log.d("sqlite_insert", "failed");
        }

    }

    private void getItem() {

        fstore.collection("subtotal").document(androidID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                       if (value != null){
                         try {
                             double subtotal = value.getDouble("total");
                             price.setText("£" + subtotal);
                         } catch (Exception e){
                             e.printStackTrace();
                         }
                       }
                    }
                });

    }

    public void backToCart(View view) {
        startActivity(new Intent(getApplicationContext(), Cart_Activity.class));
    }
}