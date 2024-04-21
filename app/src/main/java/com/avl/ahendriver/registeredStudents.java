package com.avl.ahendriver;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class registeredStudents extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<RegisteredStudent> studentsList;
    private RegisteredStudentAdapter adapter;
    private DatabaseReference requestsRef;
    private TextInputLayout dsNameInput;
    private Button searchBtn;
    private TextView noRequestText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_students);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // Set the status bar color to transparent
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        recyclerView = findViewById(R.id.bookingRequestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dsNameInput = findViewById(R.id.dsNameInput);
        searchBtn = findViewById(R.id.searchBtn);
        noRequestText = findViewById(R.id.noRequestText);

        requestsRef = FirebaseDatabase.getInstance().getReference().child("requests");

        // Initialize students list
        studentsList = new ArrayList<>();

        // Initialize adapter
        adapter = new RegisteredStudentAdapter(studentsList);
        recyclerView.setAdapter(adapter);

        // Load data from Firebase
        loadData();

        searchBtn.setOnClickListener(v->{
            String dsName = dsNameInput.getEditText().getText().toString().trim();
            if (!dsName.isEmpty()) {
                noRequestText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                filterRequests(dsName);
            } else {
                // Show message to enter driving school name
                Toast.makeText(this, "Please enter driving school name", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadData() {
        requestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RegisteredStudent student = snapshot.getValue(RegisteredStudent.class);
                    studentsList.add(student);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
    private void filterRequests(String dsName) {
        Query query = requestsRef.orderByChild("dsName").equalTo(dsName);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RegisteredStudent student = snapshot.getValue(RegisteredStudent.class);
                    studentsList.add(student);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}