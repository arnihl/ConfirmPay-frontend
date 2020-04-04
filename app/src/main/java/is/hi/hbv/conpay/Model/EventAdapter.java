package is.hi.hbv.conpay.Model;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.hbv.conpay.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context mCtx;
    private List<Event> eventList;
    Dialog viewSingleEvent;


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

        // búa til nýjan hlut fyrir "learn more" takkann
        viewSingleEvent = new Dialog(mCtx);
        viewSingleEvent.setContentView(R.layout.layout_single_event);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.textViewTitle.setText(event.getName());
        holder.textViewDesc.setText(event.getDescription());
        holder.textViewPrice.setText(String.valueOf(event.getPriceCat()));
        // onclick handler þegar ýtt er á VIEW takkan í cardviewinu:
        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // populatea dialog boxið með viðeigandi upplýsingum:
                TextView textViewTitle = viewSingleEvent.findViewById(R.id.textViewTitle);
                TextView textViewDesc = viewSingleEvent.findViewById(R.id.textViewShortDesc);
                TextView textViewPrice = viewSingleEvent.findViewById(R.id.textViewPrice);
                TextView textViewMaxPart = viewSingleEvent.findViewById(R.id.textViewMaxPart);
                TextView textViewMinPart = viewSingleEvent.findViewById(R.id.textViewMinPart);
                TextView textViewExpDate = viewSingleEvent.findViewById(R.id.textViewExpDate);
                textViewExpDate.setText(String.valueOf(event.geteDate()));
                textViewTitle.setText(event.getName());
                textViewDesc.setText(event.getDescription());
                textViewPrice.setText(String.valueOf(event.getPriceCat()));
                textViewMaxPart.setText(String.valueOf(event.getMaxParticipants()));
                textViewMinPart.setText(String.valueOf(event.getMinParticipants()));
                viewSingleEvent.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        Button btnViewDetails;
        TextView textViewTitle, textViewDesc, textViewPrice;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            btnViewDetails = itemView.findViewById(R.id.buttonViewDetailsEvent);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }

}
