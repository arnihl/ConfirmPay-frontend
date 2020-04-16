package is.hi.hbv.conpay.Model;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.hbv.conpay.FindEventActivity;
import is.hi.hbv.conpay.MainActivity;
import is.hi.hbv.conpay.Network.APIClient;
import is.hi.hbv.conpay.Network.EventAPI;
import is.hi.hbv.conpay.PaymentActivity;
import is.hi.hbv.conpay.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context mCtx;
    private List<Event> eventList;
    private boolean delete;
    Dialog viewSingleEvent;


    public EventAdapter(Context mCtx, List<Event> eventList, Boolean delete) {
        this.mCtx = mCtx;
        this.eventList = eventList;
        this.delete = delete;
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
                TextView textViewEventID = viewSingleEvent.findViewById(R.id.textViewEventID);
                Button buttonEventPayment = viewSingleEvent.findViewById(R.id.buttonEventPayment);
                Button buttonDeleteEvent = viewSingleEvent.findViewById(R.id.buttonDeleteEvent);
                if(delete){
                    buttonDeleteEvent.setVisibility(View.VISIBLE);
                }
                textViewExpDate.setText(String.valueOf(event.geteDate()));
                textViewTitle.setText(event.getName());
                textViewDesc.setText(event.getDescription());
                textViewPrice.setText(String.valueOf(event.getPriceCat()));
                textViewMaxPart.setText(String.valueOf(event.getMaxParticipants()));
                textViewMinPart.setText(String.valueOf(event.getMinParticipants()));
                textViewEventID.setText(String.valueOf(event.getId()));
                buttonEventPayment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent;
                        intent = new Intent(mCtx, PaymentActivity.class);
                        intent.putExtra("event", event);
                        mCtx.startActivity(intent);
                    }
                });
                buttonDeleteEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteEvent(event);
                    }
                });
                viewSingleEvent.show();
            }
        });
    }

    private void deleteEvent(Event event) {
        EventAPI eventAPI = APIClient.getEventClient().create(EventAPI.class);
        Call<Void> call = eventAPI.delete(event.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("HttpStatus", String.valueOf(response.body()));
                    Toast.makeText(mCtx, "Event has been deleted :(", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.err.println("Failure");
                Log.e("Network error", t.getMessage());
                call.cancel();
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
