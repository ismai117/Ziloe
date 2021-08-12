package com.brand.ziloe.view.fragments.login_register;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brand.ziloe.R;
import com.google.android.material.textfield.TextInputEditText;


public class Register_Fragment extends Fragment {

    private TextInputEditText name, email, password;
    AppCompatButton submit;


    public Register_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_, container, false);

        name = view.findViewById(R.id.reg_name_input);
        email = view.findViewById(R.id.reg_email_input);
        password = view.findViewById(R.id.reg_password_input);
        submit = view.findViewById(R.id.register_submitBtn);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue = name.getText().toString();
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();

                if (TextUtils.isEmpty(nameValue) && TextUtils.isEmpty(emailValue) && TextUtils.isEmpty(passwordValue)) {
                    name.setError("Don't leave empty field");
                    email.setError("Don't leave empty field");
                    password.setError("Don't leave empty field");

                    return;
                }

                addDetails(nameValue, emailValue, passwordValue);

            }
        });


        return view;
    }

    private void addDetails(String nameValue, String emailValue, String passwordValue) {


    }
}