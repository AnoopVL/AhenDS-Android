package com.avl.ahendriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class dsInfo extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button registerSchool;
    TextInputLayout fullName, email, phoneNo, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phoneNo);
        address = findViewById(R.id.address);

        registerSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("drivingSchool");

                if(!validateName() |!validateAddress() | !validatePhoneNo() | !validateEmail())
                {
                    return;
                }

                String regFullName = fullName.getEditText().getText().toString();
                String regEmail = email.getEditText().getText().toString();
                String regPhone = phoneNo.getEditText().getText().toString();
                String regAddress = address.getEditText().getText().toString();

                UserHelper helperClass = new UserHelper(regFullName, regEmail, regPhone, regAddress);
                reference.child(regPhone).setValue(helperClass);

                Toast.makeText(dsInfo.this, "Driving School Registered Successfully!!", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private Boolean validateName(){
        String val = fullName.getEditText().getText().toString();
        if (val.isEmpty()) {
            fullName.setError("Field cannot be empty");
            return false;
        }
        else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhoneNo() {
        String val = phoneNo.getEditText().getText().toString();
        if (val.isEmpty()) {
            phoneNo.setError("Field cannot be empty");
            return false;
        } else {
            phoneNo.setError(null);
            phoneNo.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateAddress() {
        String val = address.getEditText().getText().toString();
        if (val.isEmpty()) {
            address.setError("Field cannot be empty");
            return false;
        } else {
            address.setError(null);
            address.setErrorEnabled(false);
            return true;
        }
    }

}