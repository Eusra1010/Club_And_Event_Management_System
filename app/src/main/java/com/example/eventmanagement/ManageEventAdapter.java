package com.example.eventmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ManageEventAdapter
        extends RecyclerView.Adapter<ManageEventAdapter.EventViewHolder> {

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
    public EventViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_manage_events, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull EventViewHolder holder, int position) {

        Event event = eventList.get(position);

        String id = event.getEventId();
        String name = event.getEventName() == null ? "" : event.getEventName();
        String date = event.getEventDate() == null ? "" : event.getEventDate();
        String venue = event.getVenue() == null ? "" : event.getVenue();
        String status = event.getStatus() == null ? "ACTIVE" : event.getStatus();
        boolean registrationOpen = event.isRegistrationOpen();

        holder.tvEventName.setText(name);
        holder.tvDateVenue.setText("Date: " + date + " | Venue: " + venue);
        holder.tvStatus.setText("Status: " + status);
        holder.tvRegistrationCount.setText(
                "Registrations: " + event.getRegistrationCount()
        );



        if ("CANCELLED".equals(status)) {
            holder.btnEdit.setEnabled(false);
            holder.btnCloseReg.setEnabled(false);
            holder.btnCancel.setEnabled(false);
            holder.btnCloseReg.setText("Closed");
        } else {
            holder.btnEdit.setEnabled(true);
            holder.btnCancel.setEnabled(true);

            if (!registrationOpen) {
                holder.btnCloseReg.setEnabled(false);
                holder.btnCloseReg.setText("Closed");
            } else {
                holder.btnCloseReg.setEnabled(true);
                holder.btnCloseReg.setText("Close Registration");
            }
        }



        holder.btnEdit.setOnClickListener(v -> {
            if (id == null || id.trim().isEmpty()) {
                Toast.makeText(context,
                        "Event ID missing",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(context, EditEventActivity.class);
            intent.putExtra("eventId", id);
            context.startActivity(intent);
        });



        holder.btnCloseReg.setOnClickListener(v -> {

            if (id == null || id.trim().isEmpty()) {
                Toast.makeText(context,
                        "Event ID missing",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Close Registration")
                    .setMessage("Stop new registrations for this event?")
                    .setPositiveButton("Yes", (dialog, which) ->
                            eventsRef.child(id)
                                    .child("registrationOpen")
                                    .setValue(false)
                                    .addOnSuccessListener(aVoid ->
                                            Toast.makeText(context,
                                                    "Registration closed",
                                                    Toast.LENGTH_SHORT).show()
                                    )
                    )
                    .setNegativeButton("No", null)
                    .show();
        });



        holder.btnCancel.setOnClickListener(v -> {

            if (id == null || id.trim().isEmpty()) {
                Toast.makeText(context,
                        "Event ID missing",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Cancel Event")
                    .setMessage(
                            "This will cancel the event and close registration permanently."
                    )
                    .setPositiveButton("Cancel Event", (dialog, which) -> {

                        eventsRef.child(id).child("status")
                                .setValue("CANCELLED");

                        eventsRef.child(id).child("registrationOpen")
                                .setValue(false);

                        Toast.makeText(context,
                                "Event cancelled",
                                Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Back", null)
                    .show();
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
            tvRegistrationCount =
                    itemView.findViewById(R.id.tvRegistrationCount);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnCloseReg = itemView.findViewById(R.id.btnCloseReg);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
