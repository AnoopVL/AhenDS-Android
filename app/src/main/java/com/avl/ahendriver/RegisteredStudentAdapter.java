package com.avl.ahendriver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RegisteredStudentAdapter extends RecyclerView.Adapter<RegisteredStudentAdapter.ViewHolder> {
    private List<RegisteredStudent> studentsList;


    public RegisteredStudentAdapter(List<RegisteredStudent> studentsList) {
        this.studentsList = studentsList;
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Define views in the item layout
        // For example:
        TextView userNameTextView;
        TextView userPhoneTextView;
        TextView timeSlotTextView;
        Button acceptButton;
        Button declineButton;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initialize views
            // For example:
            userNameTextView = itemView.findViewById(R.id.studentName);
            userPhoneTextView = itemView.findViewById(R.id.studentPhone);
            timeSlotTextView = itemView.findViewById(R.id.timeSlotTextView);
            acceptButton = itemView.findViewById(R.id.acceptBtn);
            declineButton = itemView.findViewById(R.id.declineBtn);
        }
    }

    @NonNull
    @Override
    public RegisteredStudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registered_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisteredStudentAdapter.ViewHolder holder, int position) {
        RegisteredStudent student = studentsList.get(position);
        // Bind data to views
        // For example:
        holder.userNameTextView.setText(student.getUserName());
        holder.userPhoneTextView.setText(student.getUserPhone());
        holder.timeSlotTextView.setText(student.getTimeSlot());


        // Handle accept button click
        holder.acceptButton.setOnClickListener(v -> {
            // Update status in Firebase to 'accepted'
            updateStatus(student.getRequestId(), "accepted");
            Toast.makeText(holder.itemView.getContext(), "Request Accepted !!", Toast.LENGTH_SHORT).show();
        });

        // Handle reject button click
        holder.declineButton.setOnClickListener(v -> {
            // Update status in Firebase to 'rejected'
            updateStatus(student.getRequestId(), "rejected");
            Toast.makeText(holder.itemView.getContext(), "Request Declined !!", Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    private void updateStatus(String requestId, String newStatus) {
        DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference().child("requests").child(requestId);
        requestRef.child("status").setValue(newStatus)
                .addOnSuccessListener(aVoid -> {
                    // Status updated successfully
                })
                .addOnFailureListener(e -> {
                    // Failed to update status
                    // Handle error
                });
    }

}
