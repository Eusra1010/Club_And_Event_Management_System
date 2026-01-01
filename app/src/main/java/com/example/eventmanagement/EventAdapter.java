package com.example.eventmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context context;
    private List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        Event event = eventList.get(position);

        holder.tvEventName.setText(event.getEventName());
        holder.tvClubName.setText("Organized by " + event.getClubName());
        holder.tvDate.setText("Date: " + event.getEventDate());
        holder.tvVenue.setText("Venue: " + event.getVenue());

        holder.btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(context, RegisterEventActivity.class);
            intent.putExtra("eventId", event.getEventId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView tvEventName, tvClubName, tvDate, tvVenue;
        Button btnRegister;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvClubName = itemView.findViewById(R.id.tvClubName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvVenue = itemView.findViewById(R.id.tvVenue);
            btnRegister = itemView.findViewById(R.id.btnRegister);
        }
    }
}
