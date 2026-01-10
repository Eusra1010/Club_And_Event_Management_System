package com.example.eventmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    int[] cardColors = {
            R.drawable.bg_event_card_blue,
            R.drawable.bg_event_card_red,
            R.drawable.bg_event_card_purple,
            R.drawable.bg_event_card_teal
    };

    Context context;
    List<Event> eventList;

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

        holder.cardRoot.setBackgroundResource(
                cardColors[position % cardColors.length]
        );

        Event event = eventList.get(position);

        holder.tvEventName.setText(event.getEventName());
        holder.tvClubName.setText("Organized by " + event.getClubName());
        holder.tvDate.setText("Date: " + event.getEventDate());
        holder.tvVenue.setText("Venue: " + event.getVenue());

        String fees = event.getFees();
        if (fees == null || fees.equals("0") || fees.equalsIgnoreCase("free")) {
            holder.tvFees.setText("Fee: Free");
        } else {
            holder.tvFees.setText("Fee: ৳" + fees);
        }

        holder.btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(context, RegisterEventActivity.class);
            intent.putExtra("eventId", event.getEventId());
            intent.putExtra("eventName", event.getEventName());
            intent.putExtra("clubName", event.getClubName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        LinearLayout cardRoot;
        TextView tvEventName, tvClubName, tvDate, tvVenue, tvFees;
        Button btnRegister;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            cardRoot = itemView.findViewById(R.id.cardRoot);
            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvClubName = itemView.findViewById(R.id.tvClubName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvVenue = itemView.findViewById(R.id.tvVenue);
            tvFees = itemView.findViewById(R.id.tvFees);
            btnRegister = itemView.findViewById(R.id.btnRegister);
        }
    }
}
