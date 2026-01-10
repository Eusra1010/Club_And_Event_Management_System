package com.example.eventmanagement;

import android.content.Context;
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

public class ManageEventAdapter extends RecyclerView.Adapter<ManageEventAdapter.EventViewHolder> {

    Context context;
    List<Event> eventList;
    DatabaseReference eventsRef;

    public ManageEventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
        this.eventsRef = FirebaseDatabase.getInstance().getReference("Events");
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manage_events, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        Event event = eventList.get(position);

        String name = event.getEventName() == null ? "" : event.getEventName();
        String date = event.getEventDate() == null ? "" : event.getEventDate();
        String venue = event.getVenue() == null ? "" : event.getVenue();
        String status = event.getStatus() == null ? "OPEN" : event.getStatus();

        holder.tvEventName.setText(name);
        holder.tvDateVenue.setText("Date: " + date + " | Venue: " + venue);
        holder.tvStatus.setText("Status: " + status);
        holder.tvRegistrationCount.setText("Registrations: " + event.getRegistrationCount());

        holder.btnEdit.setOnClickListener(v -> {});

        holder.btnCloseReg.setOnClickListener(v -> {
            String id = event.getEventId();
            if (id == null || id.trim().isEmpty()) {
                Toast.makeText(context, "Event ID missing", Toast.LENGTH_SHORT).show();
                return;
            }
            eventsRef.child(id).child("registrationOpen").setValue(false);
        });

        holder.btnCancel.setOnClickListener(v -> {
            String id = event.getEventId();
            if (id == null || id.trim().isEmpty()) {
                Toast.makeText(context, "Event ID missing", Toast.LENGTH_SHORT).show();
                return;
            }
            eventsRef.child(id).child("status").setValue("CANCELLED");
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView tvEventName, tvDateVenue, tvStatus, tvRegistrationCount;
        Button btnEdit, btnCloseReg, btnCancel;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvDateVenue = itemView.findViewById(R.id.tvDateVenue);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvRegistrationCount = itemView.findViewById(R.id.tvRegistrationCount);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnCloseReg = itemView.findViewById(R.id.btnCloseReg);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
