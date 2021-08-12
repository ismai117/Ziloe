package com.brand.ziloe.view.fragments.contact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.brand.ziloe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ContactUs_Fragment extends Fragment {

    EditText name, email, phone, comment;
    Button send;

    FirebaseFirestore fstore;

    public ContactUs_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_us_, container, false);


        fstore = FirebaseFirestore.getInstance();

        name = view.findViewById(R.id.contactName_input);
        email = view.findViewById(R.id.contactEmail_input);
        phone = view.findViewById(R.id.contactPhone_input);
        comment = view.findViewById(R.id.contactComment_input);

        send = view.findViewById(R.id.send_messageBtn);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameValue = name.getText().toString();
                String emailValue = email.getText().toString();
                String phoneValue = phone.getText().toString();
                String commentValue = comment.getText().toString();

                if (TextUtils.isEmpty(nameValue) || TextUtils.isEmpty(emailValue) || TextUtils.isEmpty(phoneValue) || TextUtils.isEmpty(commentValue)) {

                    name.setError("Don't leave field empty");
                    email.setError("Don't leave field empty");
                    phone.setError("Don't leave field empty");
                    comment.setError("Don't leave field empty");

                }

                insertMessage(nameValue, emailValue, phoneValue, commentValue);

            }
        });


        return view;
    }

    private void insertMessage(String nameValue, String emailValue, String phoneValue, String commentValue) {

        String android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        Map<String, Object> message = new HashMap<>();
        message.put("name", nameValue);
        message.put("email", emailValue);
        message.put("phone", phoneValue);
        message.put("comment", commentValue);


        fstore.collection("messages").document(android_id)
                .set(message)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("message", "success");

                            name.getText().clear();
                            email.getText().clear();
                            phone.getText().clear();
                           comment.getText().clear();



                        } else {
                            Log.d("message", " " + task.getException());
                        }
                    }
                });

    }
}