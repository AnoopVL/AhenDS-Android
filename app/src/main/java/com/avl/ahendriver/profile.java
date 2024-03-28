package com.avl.ahendriver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    private TextInputEditText full_name_edittext, email_edittext, phone_edittext;
    private TextView fullName_textView;
    private Button signOutBtn;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // Set the status bar color to transparent
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        signOutBtn = findViewById(R.id.signOutBtn);
        fullName_textView = findViewById(R.id.fullName_textView);
        full_name_edittext = findViewById(R.id.full_name_edittext);
        email_edittext = findViewById(R.id.email_edittext);
        phone_edittext = findViewById(R.id.phone_edittext);
        sharedPreferences = getSharedPreferences("userLogin", MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("phoneNumber", "");
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(phoneNumber);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the user data exists
                if (dataSnapshot.exists()) {
                    // Get the user data
                    String fullName = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String phone = dataSnapshot.child("phone").getValue(String.class);

                    // Set the retrieved data to the corresponding views
                    fullName_textView.setText(fullName);
                    full_name_edittext.setText(fullName);
                    email_edittext.setText(email);
                    phone_edittext.setText(phone);
                } else {
                    // Handle the case where the user data does not exist
                    Toast.makeText(profile.this, "User data not found!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profile.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear saved phone number from SharedPreferences
                sharedPreferences.edit().remove("phoneNumber").apply();
                // Go back to login screen (assuming login is the starting activity)
                Intent intent = new Intent(profile.this, login.class);
//                Intent intent = new Intent(profile.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);  // Clear activity stack
                startActivity(intent);
                finish();
            }
        });

    }
}