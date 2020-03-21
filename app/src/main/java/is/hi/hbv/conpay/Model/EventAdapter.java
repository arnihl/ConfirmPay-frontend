package is.hi.hbv.conpay.Model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.hbv.conpay.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context mCtx;
    private List<Event> eventList;


    public EventAdapter(Context mCtx, List<Event> eventList) {
        this.mCtx = mCtx;
        this.eventList = eventList;
    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_events, null);
        EventViewHolder holder = new EventViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.textViewTitle.setText(event.getName());
        holder.textViewDesc.setText(event.getDescription());
        holder.textViewPrice.setText(String.valueOf(event.getPriceCat()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewDesc, textViewRating, textViewPrice;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }

}
