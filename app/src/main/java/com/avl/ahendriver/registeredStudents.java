package com.avl.ahendriver;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class registeredStudents extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<RegisteredStudent> studentsList;
    private RegisteredStudentAdapter adapter;
    private DatabaseReference requestsRef;
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
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.bookingRequestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase reference
        requestsRef = FirebaseDatabase.getInstance().getReference().child("requests");

        // Initialize students list
        studentsList = new ArrayList<>();

        // Initialize adapter
        adapter = new RegisteredStudentAdapter(studentsList);
        recyclerView.setAdapter(adapter);

        // Load data from Firebase
        loadData();
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
}