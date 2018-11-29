package com.example.mike.week6daily3.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mike.week6daily3.R;
import com.example.mike.week6daily3.data.Location;

import java.util.Date;
import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    List<Location> locations;

    public LocationsAdapter(List<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.location_item, viewGroup, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Location location = locations.get(i);

        viewHolder.name.setText( location.getName() );
        viewHolder.event.setText( location.getEvent() );
        viewHolder.timestamp.setText( new Date(location.getTimestamp()).toString() );
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView event;
        TextView timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById( R.id.locItemLocationName );
            event = itemView.findViewById( R.id.locItemEvent );
            timestamp = itemView.findViewById( R.id.locItemTimestamp );
        }
    }

}
