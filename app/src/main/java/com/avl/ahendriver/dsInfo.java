package com.avl.ahendriver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    TextInputLayout dsFullName, dsEmail, dsPhoneNo, dsAddress, dsName;


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

        dsFullName = findViewById(R.id.dsFullName);
        dsName = findViewById(R.id.dsName);
        dsEmail = findViewById(R.id.dsEmail);
        dsPhoneNo = findViewById(R.id.dsPhoneNo);
        dsAddress = findViewById(R.id.dsAddress);
        registerSchool = findViewById(R.id.registerSchool);


        registerSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("drivingSchool");

                if(!validateName() |!validateAddress() | !validatePhoneNo() | !validateEmail() | !validateFullName())
                {
                    return;
                }

                String regFullName = dsFullName.getEditText().getText().toString();
                String regDSName = dsName.getEditText().getText().toString();
                String regEmail = dsEmail.getEditText().getText().toString();
                String regPhone = dsPhoneNo.getEditText().getText().toString();
                String regAddress = dsAddress.getEditText().getText().toString();

                UserHelper helperClass = new UserHelper(regFullName, regEmail, regPhone, regAddress, regDSName);
                reference.child(regDSName).setValue(helperClass);

                Toast.makeText(dsInfo.this, "Driving School Registered Successfully!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Boolean validateName(){
        String val = dsName.getEditText().getText().toString();
        if (val.isEmpty()) {
            dsName.setError("Field cannot be empty");
            return false;
        }
        else {
            dsName.setErrorEnabled(false);
            dsName.setError(null);
            return true;
        }
    }

    private Boolean validateFullName(){
        String val = dsFullName.getEditText().getText().toString();
        if (val.isEmpty()) {
            dsFullName.setError("Field cannot be empty");
            return false;
        }
        else {
            dsFullName.setError(null);
            dsFullName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhoneNo() {
        String val = dsPhoneNo.getEditText().getText().toString();
        if (val.isEmpty()) {
            dsPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            dsPhoneNo.setError(null);
            dsPhoneNo.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = dsEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            dsEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            dsEmail.setError("Invalid email address");
            return false;
        } else {
            dsEmail.setError(null);
            dsEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateAddress() {
        String val = dsAddress.getEditText().getText().toString();
        if (val.isEmpty()) {
            dsAddress.setError("Field cannot be empty");
            return false;
        } else {
            dsAddress.setError(null);
            dsAddress.setErrorEnabled(false);
            return true;
        }
    }
}