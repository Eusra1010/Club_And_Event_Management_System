package com.example.eventmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RegistrationAdapter
        extends RecyclerView.Adapter<RegistrationAdapter.ViewHolder> {

    private final List<Registration> registrationList;

    public RegistrationAdapter(List<Registration> registrationList) {
        this.registrationList = registrationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_registration, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {

        Registration r = registrationList.get(position);

        holder.tvName.setText(r.getFullName());
        holder.tvEmail.setText(r.getEmail());
        holder.tvContact.setText("Contact: " + r.getContactNo());
    }

    @Override
    public int getItemCount() {
        return registrationList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvEmail, tvContact;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvStudentName);
            tvEmail = itemView.findViewById(R.id.tvStudentEmail);
            tvContact = itemView.findViewById(R.id.tvStudentContact);
        }
    }
}
