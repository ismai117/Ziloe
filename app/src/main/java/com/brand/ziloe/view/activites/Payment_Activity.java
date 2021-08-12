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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brand.ziloe.R;
import com.brand.ziloe.db.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import papaya.in.sendmail.SendMail;

public class Payment_Activity extends AppCompatActivity {

    private TextView show_contact, show_address, show_method;
    private CheckBox sameAddress, differentAddress, rememberMe;
    private EditText diffFName, diffLName, diffAddress, diffCity, diffCountry, diffPostcode, diffPhone;
    private LinearLayout showNew_address;
    private Button paynow;
    private String android_id;
    private String confirmationCode;
    private FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        show_contact = findViewById(R.id.pay_show_email);
        show_address = findViewById(R.id.pay_show_address);
        show_method = findViewById(R.id.pay_show_method);
        sameAddress = findViewById(R.id.sameBilling_checkbox);
        differentAddress = findViewById(R.id.differentBilling_checkbox);
        showNew_address = findViewById(R.id.newaddress_layout);
        rememberMe = findViewById(R.id.rememberMe_checkbox);
        paynow = findViewById(R.id.payNow_btn);


        diffFName = findViewById(R.id.payment_fnameInput);
        diffLName = findViewById(R.id.payment_lnameInput);
        diffAddress = findViewById(R.id.payment_addressInput);
        diffCity = findViewById(R.id.payment_cityInput);
        diffCountry = findViewById(R.id.payment_countryInput);
        diffPostcode = findViewById(R.id.payment_postcodeInput);
        diffPhone = findViewById(R.id.payment_phoneInput);

        fstore = FirebaseFirestore.getInstance();

        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        sameAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sameAddress.isChecked()) {
                    differentAddress.setChecked(false);
                }

                showNew_address.setVisibility(View.GONE);

            }
        });

        differentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (differentAddress.isChecked()) {
                    sameAddress.setChecked(false);
                }

                showNew_address.setVisibility(View.VISIBLE);


            }
        });


        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                confirmationCode = UUID.randomUUID().toString();

                if (differentAddress.isChecked()) {


                    String new_fnameValue = diffFName.getText().toString();
                    String new_lnameValue = diffLName.getText().toString();
                    String new_addressValue = diffAddress.getText().toString();
                    String new_cityValue = diffCity.getText().toString();
                    String new_countryValue = diffCountry.getText().toString();
                    String new_postcodeValue = diffPostcode.getText().toString();
                    String new_phoneValue = diffPhone.getText().toString();

                    if (TextUtils.isEmpty(new_fnameValue) || TextUtils.isEmpty(new_lnameValue) || TextUtils.isEmpty(new_addressValue) || TextUtils.isEmpty(new_cityValue) || TextUtils.isEmpty(new_countryValue) || TextUtils.isEmpty(new_postcodeValue) || TextUtils.isEmpty(new_phoneValue)) {

                        diffFName.setError("Don't leave empty field");
                        diffLName.setError("Don't leave empty field");
                        diffAddress.setError("Don't leave empty field");
                        diffCity.setError("Don't leave empty field");
                        diffCountry.setError("Don't leave empty field");
                        diffPostcode.setError("Don't leave empty field");
                        diffPhone.setError("Don't leave empty field");

                        return;

                    } else {

                        startActivity(new Intent(getApplicationContext(), Confirmation_Activity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        finish();

                    }


                    storenewData(new_fnameValue, new_lnameValue, new_addressValue, new_cityValue, new_countryValue, new_postcodeValue, new_phoneValue, confirmationCode);
                }

                if (sameAddress.isChecked()) {

                    startActivity(new Intent(getApplicationContext(), Confirmation_Activity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();

                    Map<String, Object> storeCode = new HashMap<>();
                    storeCode.put("confirmationCode", confirmationCode);

                    fstore.collection("confirmation").document(android_id)
                            .set(storeCode)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("confirmationCode", " " + sameAddress);
                                    } else {
                                        Log.d("confirmationCode", " " + sameAddress);
                                    }
                                }
                            });


                    sendEmail();

                }

                if (!sameAddress.isChecked() && !differentAddress.isChecked()) {
                    Toast.makeText(Payment_Activity.this, "Please Select billing address", Toast.LENGTH_SHORT).show();
                }


            }
        });

        getDetails();


    }


    private void sendEmail() {


        fstore.collection("shipping_details")
                .document(android_id)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                        String getemail = value.getString("email");
                        try {
                            SendMail mail = new SendMail(
                                    "ziloeltd@gmail.com",
                                    "sender_pass",
                                    "" + getemail,
                                    "Ziloe Order Confirmation",
                                    "Hey\n" +
                                            "Your order is ocnfirmed\n" +
                                            "Thanks for shopping at Ziloe, We have recieved your order\n" +
                                            "We'll send you another email once yoru order has been dispatched if we have any problems with your order we will contact you\n" +
                                            "Thanks\n" +
                                            "Ziloe Customer Care"

                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


    }

    private void storenewData(String new_fnameValue, String new_lnameValue, String new_addressValue, String new_cityValue, String new_countryValue, String new_postcodeValue, String new_phoneValue, String confirmationCode) {


        Map<String, Object> newDetails = new HashMap<>();

        newDetails.put("firstname", new_fnameValue);
        newDetails.put("last", new_lnameValue);
        newDetails.put("address", new_addressValue);
        newDetails.put("city", new_cityValue);
        newDetails.put("country", new_countryValue);
        newDetails.put("postcode", new_postcodeValue);
        newDetails.put("phone", new_phoneValue);
        newDetails.put("confirmationCode", confirmationCode);

        fstore.collection("billing_details").document(android_id)
                .set(newDetails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("updated_billing", "success");


                            Toast.makeText(Payment_Activity.this, "Pre Order Successful", Toast.LENGTH_SHORT).show();

                        } else {
                            Log.d("updated_billing", " " + task.getException());
                        }
                    }
                });
    }


    private void getDetails() {

        fstore.collection("shipping_details").document(android_id)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        try {

                            if (value != null) {
                                String contact = value.getString("email");
                                String address = value.getString("address");

                                show_contact.setText(contact);
                                show_address.setText(address);

                            }
                        } catch (Exception e) {
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

    public void backtoShipping(View view) {
        startActivity(new Intent(getApplicationContext(), Shipping_Activity.class));
    }

    public void backtoInfo(View view) {
        startActivity(new Intent(getApplicationContext(), Information_Activity.class));
    }

    public void backToCart(View view) {
        startActivity(new Intent(getApplicationContext(), Cart_Activity.class));
    }

    public void returnToShip(View view) {
        startActivity(new Intent(getApplicationContext(), Shipping_Activity.class));
    }
}