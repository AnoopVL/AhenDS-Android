package com.avl.ahendriver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class dsInfo extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button registerSchool;
    TextInputLayout fullName, email, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_info);
    }
}